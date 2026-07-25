package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * clearflag HAS_BIG_MUSHROOM
 * clearflag HAS_TINY_MUSHROOMS
 * clearflag HAS_BOTH_MUSHROOMS
 * goto_if_set CHECKED_MUSHROOMS, TwoIsland_House_EventScript_CheckPlayerHasMushrooms
 * msgbox TwoIsland_House_Text_TeachMonMoveForMushroom
 * setflag CHECKED_MUSHROOMS
 * goto TwoIsland_House_EventScript_CheckPlayerHasMushrooms
 * end
 * ```
 */
internal object TwoIsland_House_EventScript_MoveManiac : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TwoIsland_House_EventScript_MoveManiac")
}

internal val TwoIsland_HouseScripts: Map<String, Script> =
    mapOf(
        "TwoIsland_House_EventScript_MoveManiac" to TwoIsland_House_EventScript_MoveManiac,
    )
