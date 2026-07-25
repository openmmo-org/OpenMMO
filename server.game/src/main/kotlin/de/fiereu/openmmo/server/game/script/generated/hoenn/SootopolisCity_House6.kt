package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_WAILMER_DOLL, SootopolisCity_House6_EventScript_ReceivedWailmerDoll
 * msgbox SootopolisCity_House6_Text_FirstGuestInWhileTakeDoll, MSGBOX_YESNO
 * call_if_eq VAR_RESULT, NO, SootopolisCity_House6_EventScript_DeclineWailmerDoll
 * msgbox SootopolisCity_House6_Text_TakeGoodCareOfIt, MSGBOX_DEFAULT
 * givedecoration DECOR_WAILMER_DOLL
 * goto_if_eq VAR_RESULT, FALSE, SootopolisCity_House6_EventScript_NoRoomForWailmerDoll
 * setflag FLAG_RECEIVED_WAILMER_DOLL
 * release
 * end
 * ```
 */
internal object SootopolisCity_House6_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_House6_EventScript_Woman")
}

internal val SootopolisCity_House6Scripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_House6_EventScript_Woman" to SootopolisCity_House6_EventScript_Woman,
    )
