package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LavenderTown_House2_Text_WantMeToRateNicknames, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, LavenderTown_House2_EventScript_ChooseMon
 * goto_if_eq VAR_RESULT, NO, LavenderTown_House2_EventScript_DontRateNickname
 * end
 * ```
 */
internal object LavenderTown_House2_EventScript_NameRater : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavenderTown_House2_EventScript_NameRater")
}

internal val LavenderTown_House2Scripts: Map<String, Script> =
    mapOf(
        "LavenderTown_House2_EventScript_NameRater" to LavenderTown_House2_EventScript_NameRater,
    )
