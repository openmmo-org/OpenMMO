package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_MOVE_DELETER, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox LilycoveCity_MoveDeletersHouse_Text_ICanMakeMonForgetMove, MSGBOX_YESNO
 * switch VAR_RESULT
 * case YES, LilycoveCity_MoveDeletersHouse_EventScript_ChooseMonAndMoveToForget
 * case NO, LilycoveCity_MoveDeletersHouse_EventScript_ComeAgain
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_MoveDeletersHouse_EventScript_MoveDeleter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_MoveDeletersHouse_EventScript_MoveDeleter")
}

internal val LilycoveCity_MoveDeletersHouseScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_MoveDeletersHouse_EventScript_MoveDeleter" to
            LilycoveCity_MoveDeletersHouse_EventScript_MoveDeleter,
    )
