package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.DewfordTown_House2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_SILK_SCARF, DewfordTown_House2_EventScript_ExplainSilkScarf
 * msgbox DewfordTown_House2_Text_WantYouToHaveSilkScarf, MSGBOX_DEFAULT
 * giveitem ITEM_SILK_SCARF
 * goto_if_eq VAR_RESULT, FALSE, DewfordTown_House2_EventScript_NoRoomForScarf
 * setflag FLAG_RECEIVED_SILK_SCARF
 * release
 * end
 * ```
 */
internal object DewfordTown_House2_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_House2_EventScript_Man")
}

internal object DewfordTown_House2_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(DewfordTown_House2.BrawlySoCool)
}

internal val DewfordTown_House2Scripts: Map<String, Script> =
    mapOf(
        "DewfordTown_House2_EventScript_Man" to DewfordTown_House2_EventScript_Man,
        "DewfordTown_House2_EventScript_Boy" to DewfordTown_House2_EventScript_Boy,
    )
