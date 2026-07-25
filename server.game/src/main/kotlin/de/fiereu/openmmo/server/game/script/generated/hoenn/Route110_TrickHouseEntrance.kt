package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * switch VAR_TRICK_HOUSE_ENTRANCE_STATE
 * case 0, Route110_TrickHouseEntrance_EventScript_FoundTrickMaster
 * case 2, Route110_TrickHouseEntrance_EventScript_GiveReward
 * case 3, Route110_TrickHouseEntrance_EventScript_StillMakingPuzzle
 * case 6, Route110_TrickHouseEntrance_EventScript_MechadollReward
 * end
 * ```
 */
internal object Route110_TrickHouseEntrance_EventScript_TrickMaster : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHouseEntrance_EventScript_TrickMaster")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * switch VAR_TRICK_HOUSE_ENTRANCE_STATE
 * case 0, Route110_TrickHouseEntrance_EventScript_ItsAScroll
 * case 1, Route110_TrickHouseEntrance_EventScript_GoInHolePrompt
 * case 4, Route110_TrickHouseEntrance_EventScript_LeftOnJourneyNote
 * case 5, Route110_TrickHouseEntrance_EventScript_CheckLevelForMessage
 * end
 * ```
 */
internal object Route110_TrickHouseEntrance_EventScript_Door : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHouseEntrance_EventScript_Door")
}

internal val Route110_TrickHouseEntranceScripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHouseEntrance_EventScript_TrickMaster" to
            Route110_TrickHouseEntrance_EventScript_TrickMaster,
        "Route110_TrickHouseEntrance_EventScript_Door" to
            Route110_TrickHouseEntrance_EventScript_Door,
    )
