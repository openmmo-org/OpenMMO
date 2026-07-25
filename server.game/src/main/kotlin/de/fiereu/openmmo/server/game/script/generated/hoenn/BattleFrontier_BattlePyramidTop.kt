package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ne VAR_TEMP_D, 0, BattleFrontier_BattlePyramidTop_EventScript_StepForwardWhenReady
 * goto_if_ne VAR_TEMP_C, FRONTIER_BRAIN_NOT_READY, BattleFrontier_BattlePyramidTop_EventScript_BrandonHereMoveAside
 * msgbox BattleFrontier_BattlePyramidTop_Text_ReachedSummitUpYouGo, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_PYRAMID_TOP_ATTENDANT, BattleFrontier_BattlePyramidTop_Movement_AttendantMoveAside
 * waitmovement 0
 * applymovement LOCALID_PLAYER, BattleFrontier_BattlePyramidTop_Movement_PlayerClimbToTop
 * waitmovement 0
 * applymovement LOCALID_PYRAMID_TOP_ATTENDANT, BattleFrontier_BattlePyramidTop_Movement_AttendantBlockPath
 * waitmovement 0
 * message BattleFrontier_BattlePyramidTop_Text_PlayerConqueredPyramid
 * waitmessage
 * playfanfare MUS_OBTAIN_B_POINTS
 * waitfanfare
 * closemessage
 * frontier_set FRONTIER_DATA_CHALLENGE_STATUS, CHALLENGE_STATUS_WON
 * warp MAP_BATTLE_FRONTIER_BATTLE_PYRAMID_LOBBY, 7, 13
 * waitstate
 * end
 * ```
 */
internal object BattleFrontier_BattlePyramidTop_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattlePyramidTop_EventScript_Attendant")
}

internal val BattleFrontier_BattlePyramidTopScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_BattlePyramidTop_EventScript_Attendant" to
            BattleFrontier_BattlePyramidTop_EventScript_Attendant,
    )
