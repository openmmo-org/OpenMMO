package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.ClientPatcher
import de.fiereu.openmmo.launcher.client.FeedParser
import de.fiereu.openmmo.launcher.client.ManagedInstall
import de.fiereu.openmmo.launcher.client.UpdateFeed
import de.fiereu.openmmo.launcher.client.sha256
import de.fiereu.openmmo.launcher.xml.SecureXml
import java.nio.file.FileSystemException
import java.nio.file.Files
import java.nio.file.Path
import org.w3c.dom.Element

enum class Origin {
  FEED,
  PATCHED,
  ADDED,
}

data class RuntimeTree(val root: Path, val files: Map<String, Origin>) {
  fun declares(relativePath: String): Boolean = relativePath.replace('\\', '/') in files
}

fun interface PatchAssets {
  fun read(name: String): ByteArray

  companion object {
    fun ofDirectory(root: Path): PatchAssets = PatchAssets { name ->
      val resolved = root.resolve(name).normalize()
      if (!resolved.startsWith(root.normalize())) error("Asset escapes the bundle: $name")
      Files.readAllBytes(resolved)
    }

    fun none(): PatchAssets = PatchAssets { error("No patch assets are available for $it") }
  }
}

private const val STATE_FILE = ".openmmo-runtime"

class PatchFailedException(message: String) : Exception(message)

class PatchEngine(
    private val install: ManagedInstall,
    private val assets: PatchAssets = PatchAssets.none(),
    private val values: Map<String, String> = emptyMap(),
    private val clientExecutable: String? = null,
) {

  private val stateFile = install.runtime.resolve(STATE_FILE)

  private fun resolveTarget(target: String): String =
      if (target == CLIENT_TARGET) {
        clientExecutable ?: throw PatchFailedException("No client executable for $CLIENT_TARGET")
      } else {
        target
      }

  // [extra] runs after the manifest patches.
  fun apply(
      manifest: PatchManifest,
      feed: UpdateFeed,
      extra: List<Patch> = emptyList(),
  ): RuntimeTree {
    val byTarget = (manifest.patches + extra).groupBy { resolveTarget(it.target) }
    val feedNames = feed.files.map { it.name }.toSet()

    for (target in byTarget.keys) {
      val onlyAdds = byTarget.getValue(target).all { it is FilePatch }
      if (target !in feedNames && !onlyAdds) {
        throw PatchFailedException("$target is not in the feed, so there is nothing to patch")
      }
    }

    val previous = readState()
    Files.createDirectories(install.runtime)
    val files = mutableMapOf<String, Origin>()

    for (name in feedNames) {
      val source = install.resolve(name)
      if (!Files.isRegularFile(source)) continue
      val target = runtimePath(name)
      val patches = byTarget[name]
      if (patches == null) {
        link(source, target)
        files[name] = Origin.FEED
      } else {
        write(
            target, patches.fold(Files.readAllBytes(source)) { bytes, patch -> run(patch, bytes) })
        files[name] = Origin.PATCHED
      }
    }

    for ((target, patches) in byTarget) {
      if (target in files) continue
      if (target in feedNames) {
        throw PatchFailedException("$target is in the feed but not installed, so sync first")
      }
      val added =
          patches.fold(ByteArray(0)) { bytes, patch ->
            if (patch !is FilePatch) {
              throw PatchFailedException(
                  "$target is not in the feed, so ${patch.name} has no input")
            }
            run(patch, bytes)
          }
      write(runtimePath(target), added)
      files[target] = Origin.ADDED
    }

    prune(previous - files.keys)
    writeState(files.keys)
    return RuntimeTree(install.runtime, files)
  }

  private fun run(patch: Patch, bytes: ByteArray): ByteArray =
      when (patch) {
        is BinaryStringPatch -> applyBinary(patch, bytes)
        is BinarySignaturePatch -> applyBinary(patch, bytes)
        is StringsPatch -> applyStrings(patch, bytes)
        is FilePatch -> applyFile(patch)
      }

  private fun applyBinary(patch: BinaryPatch, bytes: ByteArray): ByteArray {
    val single =
        try {
          patch.compile(values)
        } catch (e: IllegalArgumentException) {
          throw PatchFailedException(e.message ?: "${patch.name} cannot be applied")
        }

    val copy = bytes.copyOf()
    val matches = ClientPatcher(listOf(single)).apply(copy).getValue(single)
    if (matches == 0) {
      throw PatchFailedException("${patch.name} found nothing to replace in ${patch.target}")
    }
    return copy
  }

  private fun applyStrings(patch: StringsPatch, bytes: ByteArray): ByteArray {
    val document = SecureXml.parse(bytes)
    val root =
        document.getElementsByTagName("strings").item(0) as? Element
            ?: throw PatchFailedException("${patch.target} is not a strings document")

    val existing =
        SecureXml.elements(root, "string")
            .mapNotNull { it.getAttribute("id").toIntOrNull() }
            .toSet()
    val clash = patch.strings.keys.filter { it in existing }
    if (clash.isNotEmpty()) {
      throw PatchFailedException("${patch.name} would overwrite string ids $clash")
    }

    for ((id, text) in patch.strings.entries.sortedBy { it.key }) {
      val element = document.createElement("string")
      element.setAttribute("id", id.toString())
      element.textContent = text
      root.appendChild(element)
    }
    return SecureXml.write(document)
  }

  private fun applyFile(patch: FilePatch): ByteArray {
    val bytes = assets.read(patch.source)
    val actual = sha256(bytes)
    if (!actual.equals(patch.sha256, ignoreCase = true)) {
      throw PatchFailedException("${patch.source} hashed to $actual, manifest says ${patch.sha256}")
    }
    return bytes
  }

  private fun runtimePath(name: String): Path {
    val sanitized = FeedParser.sanitize(name) ?: throw PatchFailedException("Unsafe name: $name")
    val resolved = install.runtime.resolve(sanitized).normalize()
    if (!resolved.startsWith(install.runtime.normalize())) {
      throw PatchFailedException("Name escapes the runtime tree: $name")
    }
    return resolved
  }

  private fun write(target: Path, bytes: ByteArray) {
    target.parent?.let(Files::createDirectories)
    // A previous run may have left a hard link here. Writing would truncate the shared inode and
    // corrupt the pristine copy, so the link has to be broken first.
    Files.deleteIfExists(target)
    Files.write(target, bytes)
  }

  private fun link(source: Path, target: Path) {
    target.parent?.let(Files::createDirectories)
    Files.deleteIfExists(target)
    try {
      Files.createLink(target, source)
    } catch (_: UnsupportedOperationException) {
      Files.copy(source, target)
    } catch (_: FileSystemException) {
      Files.copy(source, target)
    }
  }

  // The client uses this tree as its working directory, so only remove what a run produced.
  private fun prune(stale: Set<String>) {
    for (name in stale) {
      val file = runCatching { runtimePath(name) }.getOrNull() ?: continue
      Files.deleteIfExists(file)
      var parent = file.parent
      while (parent != null && parent != install.runtime && isEmpty(parent)) {
        Files.deleteIfExists(parent)
        parent = parent.parent
      }
    }
  }

  private fun isEmpty(directory: Path): Boolean =
      Files.isDirectory(directory) &&
          Files.newDirectoryStream(directory).use { !it.iterator().hasNext() }

  private fun readState(): Set<String> =
      if (Files.isRegularFile(stateFile)) {
        Files.readAllLines(stateFile).filter { it.isNotBlank() }.toSet()
      } else {
        emptySet()
      }

  private fun writeState(files: Set<String>) {
    Files.write(stateFile, files.sorted())
  }
}
