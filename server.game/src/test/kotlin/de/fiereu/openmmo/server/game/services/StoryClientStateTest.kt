package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.net.game.packets.PlayerVariableEntry
import de.fiereu.openmmo.net.game.packets.StoryFlagUpdatePacket
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class StoryClientStateTest :
    FunSpec({
      test("maps persisted GBA flags to the captured region and numeric id") {
        StoryClientState.flags(
            regionId = 1,
            flags = setOf(HoennFlags.FLAG_VISITED_LITTLEROOT_TOWN),
        ) shouldBe listOf(StoryFlagUpdatePacket(1, 0x086f, enabled = true))
      }

      test("maps GBA variable ids to their one-byte client keys") {
        StoryClientState.variables(
            regionId = 1,
            vars = mapOf(HoennVars.VAR_LITTLEROOT_INTRO_STATE to 7),
        ) shouldBe listOf(PlayerVariableEntry(0x92.toByte(), 7))
      }

      test("does not leak another region's story state") {
        StoryClientState.flags(
            regionId = 0,
            flags = setOf(HoennFlags.FLAG_VISITED_LITTLEROOT_TOWN),
        ) shouldBe emptyList()
        StoryClientState.variables(
            regionId = 0,
            vars = mapOf(HoennVars.VAR_LITTLEROOT_INTRO_STATE to 7),
        ) shouldBe emptyList()
      }
    })
