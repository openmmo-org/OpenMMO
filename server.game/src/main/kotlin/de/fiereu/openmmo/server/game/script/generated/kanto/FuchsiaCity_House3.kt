package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox FuchsiaCity_House3_Text_WouldYouLikeToForgetMove, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, FuchsiaCity_House3_EventScript_ChooseMonForMoveDeleter
 * goto FuchsiaCity_House3_EventScript_CancelForgetMove
 * end
 * ```
 */
internal object FuchsiaCity_House3_EventScript_MoveDeleter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FuchsiaCity_House3_EventScript_MoveDeleter")
}

internal val FuchsiaCity_House3Scripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_House3_EventScript_MoveDeleter" to FuchsiaCity_House3_EventScript_MoveDeleter,
    )
