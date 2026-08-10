package de.fiereu.openmmo.launcher

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ClientPatcherTest :
    FunSpec({
      fun bytes(text: String) = text.toByteArray(Charsets.ISO_8859_1)

      test("replaces every occurrence") {
        val patch = ClientPatcher.Patch("Key", "abcd", "WXYZ")
        val data = bytes("..abcd..abcd.")

        ClientPatcher(listOf(patch)).apply(data)[patch] shouldBe 2
        data.toString(Charsets.ISO_8859_1) shouldBe "..WXYZ..WXYZ."
      }

      test("reports zero matches without touching the data") {
        val patch = ClientPatcher.Patch("Key", "abcd", "WXYZ")
        val data = bytes("nothing to see here")

        ClientPatcher(listOf(patch)).apply(data)[patch] shouldBe 0
        data.toString(Charsets.ISO_8859_1) shouldBe "nothing to see here"
      }

      test("applies independent patches in one pass") {
        val game = ClientPatcher.Patch("Game", "aaaa", "1111")
        val chat = ClientPatcher.Patch("Chat", "bbbb", "2222")
        val data = bytes("aaaa-bbbb")

        val results = ClientPatcher(listOf(game, chat)).apply(data)

        results[game] shouldBe 1
        results[chat] shouldBe 1
        data.toString(Charsets.ISO_8859_1) shouldBe "1111-2222"
      }

      test("rejects a replacement of a different length") {
        shouldThrow<IllegalArgumentException> { ClientPatcher.Patch("Key", "abcd", "xyz") }
      }
    })
