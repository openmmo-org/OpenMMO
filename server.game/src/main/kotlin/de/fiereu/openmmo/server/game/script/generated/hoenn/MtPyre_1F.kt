package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtPyre_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_CLEANSE_TAG, MtPyre_1F_EventScript_ReceivedCleanseTag
 * msgbox MtPyre_1F_Text_TakeThisForYourOwnGood, MSGBOX_DEFAULT
 * giveitem ITEM_CLEANSE_TAG
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_CLEANSE_TAG
 * release
 * end
 * ```
 */
internal object MtPyre_1F_EventScript_CleanseTagWoman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_1F_EventScript_CleanseTagWoman")
}

internal object MtPyre_1F_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MtPyre_1F.ComeToPayRespects)
}

internal object MtPyre_1F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MtPyre_1F.RestingPlaceOfZigzagoon)
}

internal val MtPyre_1FScripts: Map<String, Script> =
    mapOf(
        "MtPyre_1F_EventScript_CleanseTagWoman" to MtPyre_1F_EventScript_CleanseTagWoman,
        "MtPyre_1F_EventScript_PokefanF" to MtPyre_1F_EventScript_PokefanF,
        "MtPyre_1F_EventScript_Man" to MtPyre_1F_EventScript_Man,
    )
