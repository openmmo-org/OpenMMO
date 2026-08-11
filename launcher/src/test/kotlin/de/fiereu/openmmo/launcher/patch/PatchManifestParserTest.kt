package de.fiereu.openmmo.launcher.patch

import de.fiereu.openmmo.launcher.launch.GeneratedKeys
import io.kotest.assertions.throwables.shouldThrowAny
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import java.nio.file.Files
import java.nio.file.Path

class PatchManifestParserTest :
    FunSpec({
      fun manifest(body: String) =
          PatchManifestParser.parse(
              """
              revision = 32763
              $body
              """
                  .trimIndent())

      test("reads each patch kind") {
        val parsed =
            manifest(
                """
                [[patches]]
                type = "binary_string"
                name = "Host"
                target = "@client"
                find = "aaaa"
                replace = "bbbb"

                [[patches]]
                type = "binary_string"
                name = "Key"
                target = "@client"
                find = "cccc"
                replace_ref = "key.game.public"

                [[patches]]
                type = "binary_signature"
                name = "Call"
                target = "@client"
                signature = "83 7A 20 03 75 ?? E8 ?? ?? ?? ?? 90 41 83 7F 10 00"
                offset = 6
                replace = "B8 01 00 00 00"

                [[patches]]
                type = "strings"
                name = "Branding"
                target = "data/strings/strings_en.xml"
                strings = { 1000000000 = "OpenMMO" }

                [[patches]]
                type = "file"
                name = "Mod"
                target = "data/mods/openmmo.zip"
                source = "mod.zip"
                sha256 = "${"a".repeat(64)}"
                """)

        parsed.revision shouldBe 32763
        parsed.patches.size shouldBe 5
        (parsed.patches[0] as BinaryStringPatch).replace shouldBe "bbbb"
        (parsed.patches[1] as BinaryStringPatch).replaceRef shouldBe "key.game.public"
        (parsed.patches[2] as BinarySignaturePatch).offset shouldBe 6
        (parsed.patches[2] as BinarySignaturePatch).replace shouldBe "B8 01 00 00 00"
        (parsed.patches[3] as StringsPatch).strings shouldBe mapOf(1000000000 to "OpenMMO")
        (parsed.patches[4] as FilePatch).source shouldBe "mod.zip"
      }

      test("a signature patch overwrites the match from its start by default") {
        val parsed =
            manifest(
                """
                [[patches]]
                type = "binary_signature"
                name = "Nop"
                target = "@client"
                signature = "E8 ?? ?? ?? ??"
                replace = "90 90 90 90 90"
                """)

        (parsed.patches[0] as BinarySignaturePatch).offset shouldBe 0
      }

      test("rejects a signature patch that would write past what it matched") {
        shouldThrowAny {
          manifest(
              """
              [[patches]]
              type = "binary_signature"
              name = "N"
              target = "@client"
              signature = "83 7A 20 03 75 ?? E8 ?? ?? ?? ??"
              offset = 6
              replace = "B8 01 00 00 00 00"
              """)
        }
      }

      test("rejects a signature or a replacement that is not hex") {
        shouldThrowAny {
          manifest(
              """
              [[patches]]
              type = "binary_signature"
              name = "N"
              target = "@client"
              signature = "83 7A ZZ"
              replace = "90"
              """)
        }
        // A wildcard does not say what to write, so a replacement may not hold one.
        shouldThrowAny {
          manifest(
              """
              [[patches]]
              type = "binary_signature"
              name = "N"
              target = "@client"
              signature = "83 7A 20"
              replace = "90 ??"
              """)
        }
      }

      test("keeps a value that another format might coerce") {
        val parsed =
            manifest(
                """
                [[patches]]
                type = "binary_string"
                name = "Literal"
                target = "@client"
                find = "true"
                replace = "no12"
                """)

        (parsed.patches[0] as BinaryStringPatch).replace shouldBe "no12"
      }

      test("rejects a binary replacement of a different length") {
        shouldThrowAny {
          manifest(
              """
              [[patches]]
              type = "binary_string"
              name = "N"
              target = "@client"
              find = "aaaa"
              replace = "bb"
              """)
        }
      }

      test("rejects a binary patch with both or neither replacement") {
        shouldThrowAny {
          manifest(
              """
              [[patches]]
              type = "binary_string"
              name = "N"
              target = "@client"
              find = "aaaa"
              replace = "bbbb"
              replace_ref = "x"
              """)
        }
        shouldThrowAny {
          manifest(
              """
              [[patches]]
              type = "binary_string"
              name = "N"
              target = "@client"
              find = "aaaa"
              """)
        }
      }

      test("rejects string ids that belong to PokeMMO") {
        // 24 and 271009757 are the kind of ids revision 32763 actually ships, the latter being the
        // highest one in strings_en.xml, so neither may be claimed.
        listOf("24", "90000", "271009757").forEach { id ->
          shouldThrowAny {
            manifest(
                """
                [[patches]]
                type = "strings"
                name = "N"
                target = "data/strings/strings_en.xml"
                strings = { $id = "stolen" }
                """)
          }
        }
      }

      test("rejects a target that escapes the install directory") {
        shouldThrowAny {
          manifest(
              """
              [[patches]]
              type = "binary_string"
              name = "N"
              target = "../evil"
              find = "aaaa"
              replace = "bbbb"
              """)
        }
      }

      test("rejects a manifest with no patches") { shouldThrowAny { manifest("patches = []") } }

      test("rejects a key the schema does not know, rather than ignoring it") {
        shouldThrowAny {
          manifest(
              """
              [[patches]]
              type = "binary_string"
              name = "N"
              target = "@client"
              find = "aaaa"
              replace = "bbbb"
              typo_field = "silently lost"
              """)
        }
      }

      test("reads the manifest the launcher actually ships") {
        val shipped =
            PatchManifestParser.parse(Files.readAllBytes(Path.of("manifests/manifest-32763.toml")))

        shipped.revision shouldBe 32763
        shipped.patches.map { it.name } shouldBe
            listOf(
                "GamePubKey",
                "ChatPubKey",
                "FeedPubKey0",
                "FeedPubKey1",
                "SupportUrl",
            )
      }

      test("trusts the feed key this build signs with, never a literal") {
        // Only slot 0 verifies main_feed. A literal there means the client rejects any feed we
        // sign, which the client reports as "Error verifying SHA256withRSA feed signature".
        val shipped =
            PatchManifestParser.parse(Files.readAllBytes(Path.of("manifests/manifest-32763.toml")))

        shipped.patches
            .filterIsInstance<BinaryStringPatch>()
            .filter { it.name.startsWith("FeedPubKey") }
            .forEach { it.replaceRef shouldBe GeneratedKeys.FEED_PUBLIC }
      }
    })
