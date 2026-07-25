package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.OldaleTown_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * goto_if_set FLAG_ADVENTURE_STARTED, OldaleTown_Mart_ExpandedItems
 * pokemart OldaleTown_Mart_Pokemart_Basic
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object OldaleTown_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port OldaleTown_Mart_EventScript_Clerk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_ADVENTURE_STARTED, OldaleTown_Mart_EventScript_PokeBallsInStock
 * msgbox OldaleTown_Mart_Text_PokeBallsAreSoldOut, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object OldaleTown_Mart_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port OldaleTown_Mart_EventScript_Woman")
}

internal object OldaleTown_Mart_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(OldaleTown_Mart.RestoreHPWithPotion)
}

internal val OldaleTown_MartScripts: Map<String, Script> =
    mapOf(
        "OldaleTown_Mart_EventScript_Clerk" to OldaleTown_Mart_EventScript_Clerk,
        "OldaleTown_Mart_EventScript_Woman" to OldaleTown_Mart_EventScript_Woman,
        "OldaleTown_Mart_EventScript_Boy" to OldaleTown_Mart_EventScript_Boy,
    )
