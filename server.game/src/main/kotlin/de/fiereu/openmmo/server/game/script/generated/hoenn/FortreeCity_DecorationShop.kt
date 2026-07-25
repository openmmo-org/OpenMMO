package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FortreeCity_DecorationShop
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FortreeCity_DecorationShop_EventScript_PokefanM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FortreeCity_DecorationShop.MerchandiseSentToPC)
}

internal object FortreeCity_DecorationShop_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FortreeCity_DecorationShop.BuyingDeskForDolls)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemartdecoration FortreeCity_DecorationShop_PokemartDecor_Desks
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FortreeCity_DecorationShop_EventScript_ClerkDesks : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FortreeCity_DecorationShop_EventScript_ClerkDesks")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemartdecoration FortreeCity_DecorationShop_PokemartDecor_Chairs
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FortreeCity_DecorationShop_EventScript_ClerkChairs : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FortreeCity_DecorationShop_EventScript_ClerkChairs")
}

internal val FortreeCity_DecorationShopScripts: Map<String, Script> =
    mapOf(
        "FortreeCity_DecorationShop_EventScript_PokefanM" to
            FortreeCity_DecorationShop_EventScript_PokefanM,
        "FortreeCity_DecorationShop_EventScript_Girl" to
            FortreeCity_DecorationShop_EventScript_Girl,
        "FortreeCity_DecorationShop_EventScript_ClerkDesks" to
            FortreeCity_DecorationShop_EventScript_ClerkDesks,
        "FortreeCity_DecorationShop_EventScript_ClerkChairs" to
            FortreeCity_DecorationShop_EventScript_ClerkChairs,
    )
