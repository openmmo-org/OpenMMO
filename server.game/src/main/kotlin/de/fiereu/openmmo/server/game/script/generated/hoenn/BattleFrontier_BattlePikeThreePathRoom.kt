package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * frontier_get FRONTIER_DATA_BATTLE_NUM  @ Room number
 * switch VAR_RESULT
 * case 1, BattleFrontier_BattlePikeThreePathRoom_EventScript_AttendantRoom1
 * case 3, BattleFrontier_BattlePikeThreePathRoom_EventScript_AttendantRoom3
 * case 5, BattleFrontier_BattlePikeThreePathRoom_EventScript_AttendantRoom5
 * case 7, BattleFrontier_BattlePikeThreePathRoom_EventScript_AttendantRoom7
 * case 9, BattleFrontier_BattlePikeThreePathRoom_EventScript_AttendantRoom9
 * case 11, BattleFrontier_BattlePikeThreePathRoom_EventScript_AttendantRoom11
 * case 13, BattleFrontier_BattlePikeThreePathRoom_EventScript_AttendantRoom13
 * end
 * ```
 */
internal object BattleFrontier_BattlePikeThreePathRoom_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattlePikeThreePathRoom_EventScript_Attendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * pike_gethint
 * goto_if_eq VAR_RESULT, PIKE_HINT_BRAIN, BattleFrontier_BattlePikeThreePathRoom_EventScript_GiveBrainHint
 * lock
 * faceplayer
 * msgbox BattleFrontier_BattlePikeThreePathRoom_Text_FindingItDifficultToChoose, MSGBOX_YESNO
 * switch VAR_RESULT
 * case YES, BattleFrontier_BattlePikeThreePathRoom_EventScript_AcceptHint
 * case NO, BattleFrontier_BattlePikeThreePathRoom_EventScript_DeclineHint
 * case MULTI_B_PRESSED, BattleFrontier_BattlePikeThreePathRoom_EventScript_DeclineHint
 * release
 * end
 * ```
 */
internal object BattleFrontier_BattlePikeThreePathRoom_EventScript_HintGiver : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattlePikeThreePathRoom_EventScript_HintGiver")
}

internal val BattleFrontier_BattlePikeThreePathRoomScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_BattlePikeThreePathRoom_EventScript_Attendant" to
            BattleFrontier_BattlePikeThreePathRoom_EventScript_Attendant,
        "BattleFrontier_BattlePikeThreePathRoom_EventScript_HintGiver" to
            BattleFrontier_BattlePikeThreePathRoom_EventScript_HintGiver,
    )
