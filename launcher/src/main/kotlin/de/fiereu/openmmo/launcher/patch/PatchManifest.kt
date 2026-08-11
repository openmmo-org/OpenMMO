package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.BytePattern
import de.fiereu.openmmo.launcher.ClientPatcher
import de.fiereu.openmmo.launcher.client.FeedParser
import de.fiereu.openmmo.launcher.parseHexBytes
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import net.peanuuutz.tomlkt.Toml

/** Stands for the client binary of whichever platform is running. */
const val CLIENT_TARGET = "@client"

// PokeMMO ids reach into the hundreds of millions, so ours start above them.
const val RESERVED_STRING_ID_MIN = 1_000_000_000

@Serializable
sealed interface Patch {
  val target: String

  val name: String

  fun validate()
}

/** Shared by the patches that replace bytes in a file, however they find them. */
interface BinaryPatch {
  val target: String

  val name: String

  /** [values] holds what is only known at apply time, such as a generated key. */
  fun compile(values: Map<String, String>): ClientPatcher.Patch
}

// [replaceRef] names a value supplied at apply time, such as a generated key.
@Serializable
@SerialName("binary_string")
data class BinaryStringPatch(
    override val target: String,
    override val name: String,
    val find: String,
    val replace: String? = null,
    @SerialName("replace_ref") val replaceRef: String? = null,
) : Patch, BinaryPatch {
  override fun validate() {
    require((replace == null) != (replaceRef == null)) {
      "$name needs exactly one of replace or replace_ref"
    }
    require(replace == null || replace.length == find.length) {
      "$name replaces ${find.length} characters with ${replace?.length}"
    }
  }

  override fun compile(values: Map<String, String>): ClientPatcher.Patch {
    val replacement =
        replace
            ?: requireNotNull(replaceRef?.let(values::get)) {
              "$name needs a value for $replaceRef"
            }
    return ClientPatcher.Patch.ofText(name, find, replacement)
  }
}

// Finds code, which has no constant to search for. [signature] locates the site, "??" matching any
// byte, and only [replace] is written, [offset] bytes into the match.
@Serializable
@SerialName("binary_signature")
data class BinarySignaturePatch(
    override val target: String,
    override val name: String,
    val signature: String,
    val replace: String,
    val offset: Int = 0,
) : Patch, BinaryPatch {
  // Building the patch checks the hex and that the replacement fits.
  override fun validate() {
    compile(emptyMap())
  }

  override fun compile(values: Map<String, String>): ClientPatcher.Patch =
      ClientPatcher.Patch(name, BytePattern.parse(signature), parseHexBytes(replace), offset)
}

// Adds entries to data/strings/strings_*.xml.
@Serializable
@SerialName("strings")
data class StringsPatch(
    override val target: String,
    override val name: String,
    val strings: Map<Int, String>,
) : Patch {
  override fun validate() {
    require(strings.isNotEmpty()) { "$name adds nothing" }
    strings.keys.forEach {
      require(it >= RESERVED_STRING_ID_MIN) {
        "string id $it is below the reserved range $RESERVED_STRING_ID_MIN"
      }
    }
  }
}

@Serializable
@SerialName("file")
data class FilePatch(
    override val target: String,
    override val name: String,
    val source: String,
    val sha256: String,
) : Patch {
  override fun validate() {
    require(sha256.length == 64) { "$name has a sha256 that is not a sha256" }
  }
}

@Serializable data class PatchManifest(val revision: Int, val patches: List<Patch>)

object PatchManifestParser {

  private val toml = Toml {
    ignoreUnknownKeys = false
    serializersModule = SerializersModule {
      polymorphic(Patch::class) {
        subclass(BinaryStringPatch::class)
        subclass(BinarySignaturePatch::class)
        subclass(StringsPatch::class)
        subclass(FilePatch::class)
      }
    }
  }

  fun parse(bytes: ByteArray): PatchManifest = parse(bytes.decodeToString())

  fun parse(text: String): PatchManifest {
    val manifest = toml.decodeFromString(PatchManifest.serializer(), text)
    require(manifest.revision > 0) { "patch manifest has no usable revision" }
    require(manifest.patches.isNotEmpty()) {
      "patch manifest for revision ${manifest.revision} has no patches"
    }
    manifest.patches.forEach {
      it.validate()
      require(it.target == CLIENT_TARGET || FeedParser.sanitize(it.target) != null) {
        "${it.name} has a target that escapes the install directory"
      }
    }
    return manifest
  }
}
