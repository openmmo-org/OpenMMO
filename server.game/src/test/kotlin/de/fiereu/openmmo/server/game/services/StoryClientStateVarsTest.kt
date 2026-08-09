package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.story.generated.kanto.KantoVars
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

private const val KANTO: Byte = 0

class StoryClientStateVarsTest :
    FunSpec({
      test("a stored var is left out of the sparse list once it is back at zero") {
        val set = StoryClientState.variables(KANTO, mapOf(KantoVars.VAR_STARTER_MON to 2))
        val cleared = StoryClientState.variables(KANTO, emptyMap())

        set.size shouldBe 1
        cleared shouldBe emptyList()
      }

      test("the full list carries every var in the range, cleared ones as zero") {
        val full = StoryClientState.allVariables(KANTO, mapOf(KantoVars.VAR_STARTER_MON to 2))

        full.size shouldBe 256
        full.map { it.key.toInt() and 0xff } shouldBe (0..255).toList()
        val starter = StoryClientState.variables(KANTO, mapOf(KantoVars.VAR_STARTER_MON to 2))
        full.single { it.key == starter.single().key }.value shouldBe 2
        full.filter { it.key != starter.single().key }.all { it.value.toInt() == 0 } shouldBe true
      }
    })
