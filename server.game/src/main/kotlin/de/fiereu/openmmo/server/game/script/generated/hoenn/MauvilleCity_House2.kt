package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_COIN_CASE, MauvilleCity_House2_EventScript_ReceivedCoinCase
 * msgbox MauvilleCity_House2_Text_BuyHarborMailAtSlateport, MSGBOX_DEFAULT
 * checkitem ITEM_HARBOR_MAIL
 * goto_if_eq VAR_RESULT, TRUE, MauvilleCity_House2_EventScript_AskToTradeForHarborMail
 * release
 * end
 * ```
 */
internal object MauvilleCity_House2_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_House2_EventScript_Woman")
}

internal val MauvilleCity_House2Scripts: Map<String, Script> =
    mapOf(
        "MauvilleCity_House2_EventScript_Woman" to MauvilleCity_House2_EventScript_Woman,
    )
