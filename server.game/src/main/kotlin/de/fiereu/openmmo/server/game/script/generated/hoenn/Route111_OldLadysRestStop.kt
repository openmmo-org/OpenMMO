package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route111_OldLadysRestStop_Text_RestUpHere, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route111_OldLadysRestStop_EventScript_Rest
 * goto_if_eq VAR_RESULT, NO, Route111_OldLadysRestStop_EventScript_DeclineRest
 * end
 * ```
 */
internal object Route111_OldLadysRestStop_EventScript_OldLady : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route111_OldLadysRestStop_EventScript_OldLady")
}

internal val Route111_OldLadysRestStopScripts: Map<String, Script> =
    mapOf(
        "Route111_OldLadysRestStop_EventScript_OldLady" to
            Route111_OldLadysRestStop_EventScript_OldLady,
    )
