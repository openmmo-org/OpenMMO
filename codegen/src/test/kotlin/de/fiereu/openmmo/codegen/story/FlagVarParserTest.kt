package de.fiereu.openmmo.codegen.story

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class FlagVarParserTest :
    FunSpec({
      test("resolves literals, aliases, expressions, hints, and function macros") {
        val decomp = kotlin.io.path.createTempDirectory("story-constants").toFile()
        val constants = decomp.resolve("include/constants").also { it.mkdirs() }
        constants.resolve("support.h").writeText("#define EXTERNAL_COUNT 0x20\n")
        constants
            .resolve("flags.h")
            .writeText(
                """
            #define FLAGS_START 0x100
            #define FLAGS_END (FLAGS_START + EXTERNAL_COUNT - 1) // 0x11F
            #define FLAG_FACTORY(index) (FLAGS_END + 1 + (index * 2))
            #define FLAG_LITERAL 0x42
            #define FLAG_ALIAS FLAG_LITERAL
            #define FLAG_EXPRESSION (FLAGS_START + 0x0F)
            #define FLAG_HINT (UNKNOWN_VALUE + 1) // FLAG_0x234
            #define FLAG_FUNCTION FLAG_FACTORY(3)
            """
                    .trimIndent())
        constants
            .resolve("vars.h")
            .writeText(
                """
            #define VARS_START 0x4000
            #define VAR_FIRST (VARS_START + 1)
            """
                    .trimIndent())

        FlagVarParser.flags(decomp) shouldBe
            listOf(
                StoryConstant("FLAG_LITERAL", 0x42),
                StoryConstant("FLAG_ALIAS", 0x42),
                StoryConstant("FLAG_EXPRESSION", 0x10F),
                StoryConstant("FLAG_HINT", 0x234),
                StoryConstant("FLAG_FUNCTION", 0x126),
            )
        FlagVarParser.vars(decomp) shouldBe listOf(StoryConstant("VAR_FIRST", 0x4001))
      }

      test("extracts initial and gender-specific flags from script blocks") {
        val decomp = kotlin.io.path.createTempDirectory("story-script-flags").toFile()
        decomp.resolve("data/scripts").mkdirs()
        decomp.resolve("data/maps/InsideOfTruck").mkdirs()
        decomp
            .resolve("data/scripts/new_game.inc")
            .writeText(
                """
            EventScript_ResetAllMapFlags::
                setflag FLAG_INITIAL
                setflag FLAG_INITIAL
                end
            AnotherScript::
                setflag FLAG_NOT_INCLUDED
            """
                    .trimIndent())
        decomp
            .resolve("data/maps/InsideOfTruck/scripts.inc")
            .writeText(
                """
            InsideOfTruck_EventScript_SetIntroFlagsMale::
                setflag FLAG_MALE
                end
            InsideOfTruck_EventScript_SetIntroFlagsFemale::
                setflag FLAG_FEMALE
                end
            """
                    .trimIndent())

        FlagVarParser.initialFlags(decomp) shouldBe listOf("FLAG_INITIAL")
        FlagVarParser.maleIntroFlags(decomp) shouldBe listOf("FLAG_MALE")
        FlagVarParser.femaleIntroFlags(decomp) shouldBe listOf("FLAG_FEMALE")
      }
    })
