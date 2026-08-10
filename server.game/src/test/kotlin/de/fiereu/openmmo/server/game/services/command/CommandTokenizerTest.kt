package de.fiereu.openmmo.server.game.services.command

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class CommandTokenizerTest :
    FunSpec({
      test("splits plain arguments on whitespace runs") {
        tokenizeCommandLine("give potion  5") shouldBe listOf("give", "potion", "5")
      }

      test("keeps a quoted argument together") {
        tokenizeCommandLine("give \"Great Ball\" 5") shouldBe listOf("give", "Great Ball", "5")
      }

      test("joins a quote that starts mid-word") {
        tokenizeCommandLine("whisper Nurse\" Joy\" hi") shouldBe
            listOf("whisper", "Nurse Joy", "hi")
      }

      test("escapes a quote inside quotes") {
        tokenizeCommandLine("say \"a \\\" mark\"") shouldBe listOf("say", "a \" mark")
      }

      test("an unterminated quote takes the rest of the line") {
        tokenizeCommandLine("find \"Mr. Mime forever") shouldBe listOf("find", "Mr. Mime forever")
      }

      test("empty quotes make an empty argument") {
        tokenizeCommandLine("rename \"\" after") shouldBe listOf("rename", "", "after")
      }

      test("empty line has no arguments") { tokenizeCommandLine("") shouldBe emptyList() }
    })
