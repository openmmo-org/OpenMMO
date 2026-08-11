package de.fiereu.openmmo.launcher

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ClientPatcherTest :
    FunSpec({
      fun bytes(text: String) = text.toByteArray(Charsets.ISO_8859_1)

      test("replaces every occurrence") {
        val patch = ClientPatcher.Patch.ofText("Key", "abcd", "WXYZ")
        val data = bytes("..abcd..abcd.")

        ClientPatcher(listOf(patch)).apply(data)[patch] shouldBe 2
        data.toString(Charsets.ISO_8859_1) shouldBe "..WXYZ..WXYZ."
      }

      test("reports zero matches without touching the data") {
        val patch = ClientPatcher.Patch.ofText("Key", "abcd", "WXYZ")
        val data = bytes("nothing to see here")

        ClientPatcher(listOf(patch)).apply(data)[patch] shouldBe 0
        data.toString(Charsets.ISO_8859_1) shouldBe "nothing to see here"
      }

      test("applies independent patches in one pass") {
        val game = ClientPatcher.Patch.ofText("Game", "aaaa", "1111")
        val chat = ClientPatcher.Patch.ofText("Chat", "bbbb", "2222")
        val data = bytes("aaaa-bbbb")

        val results = ClientPatcher(listOf(game, chat)).apply(data)

        results[game] shouldBe 1
        results[chat] shouldBe 1
        data.toString(Charsets.ISO_8859_1) shouldBe "1111-2222"
      }

      test("rejects a replacement of a different length") {
        shouldThrow<IllegalArgumentException> { ClientPatcher.Patch.ofText("Key", "abcd", "xyz") }
      }

      test("writes a narrower replacement into the middle of what matched") {
        // Only the call is replaced. The bytes around it are there to find the site.
        val patch =
            ClientPatcher.Patch(
                "SkipCall",
                BytePattern.parse("83 7A 20 03 75 ?? E8 ?? ?? ?? ?? 90 41 83 7F 10 00"),
                parseHexBytes("B8 01 00 00 00"),
                offset = 6,
            )
        val data = parseHexBytes("CC 83 7A 20 03 75 1F E8 44 33 22 11 90 41 83 7F 10 00 CC")

        ClientPatcher(listOf(patch)).apply(data)[patch] shouldBe 1
        data shouldBe parseHexBytes("CC 83 7A 20 03 75 1F B8 01 00 00 00 90 41 83 7F 10 00 CC")
      }

      test("matches whatever sits under a wildcard") {
        val patch =
            ClientPatcher.Patch("Wild", BytePattern.parse("AA ?? CC"), parseHexBytes("FF"), 1)
        val data = parseHexBytes("AA 00 CC AA 7F CC")

        ClientPatcher(listOf(patch)).apply(data)[patch] shouldBe 2
        data shouldBe parseHexBytes("AA FF CC AA FF CC")
      }

      test("rejects a replacement that runs past the match") {
        shouldThrow<IllegalArgumentException> {
          ClientPatcher.Patch("Long", BytePattern.parse("AA BB"), parseHexBytes("01 02"), 1)
        }
      }

      test("rejects an offset that starts before the match") {
        shouldThrow<IllegalArgumentException> {
          ClientPatcher.Patch("Back", BytePattern.parse("AA BB"), parseHexBytes("01"), -1)
        }
      }
    })
