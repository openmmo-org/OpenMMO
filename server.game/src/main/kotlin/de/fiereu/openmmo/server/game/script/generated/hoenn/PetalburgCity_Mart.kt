package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PetalburgCity_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * goto_if_set FLAG_PETALBURG_MART_EXPANDED_ITEMS, PetalburgCity_Mart_EventScript_ExpandedItems
 * pokemart PetalburgCity_Mart_Pokemart_Basic
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object PetalburgCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Mart_EventScript_Clerk")
}

internal object PetalburgCity_Mart_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PetalburgCity_Mart.TakeSomeAntidotesWithYou)
}

internal object PetalburgCity_Mart_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity_Mart.RepelIsUseful)
}

internal object PetalburgCity_Mart_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity_Mart.WeakWillGrowStronger)
}

internal val PetalburgCity_MartScripts: Map<String, Script> =
    mapOf(
        "PetalburgCity_Mart_EventScript_Clerk" to PetalburgCity_Mart_EventScript_Clerk,
        "PetalburgCity_Mart_EventScript_Man" to PetalburgCity_Mart_EventScript_Man,
        "PetalburgCity_Mart_EventScript_Boy" to PetalburgCity_Mart_EventScript_Boy,
        "PetalburgCity_Mart_EventScript_Woman" to PetalburgCity_Mart_EventScript_Woman,
    )
