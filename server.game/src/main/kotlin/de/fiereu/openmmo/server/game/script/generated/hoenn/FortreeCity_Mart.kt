package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FortreeCity_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart FortreeCity_Mart_Pokemart
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FortreeCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Mart_EventScript_Clerk")
}

internal object FortreeCity_Mart_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity_Mart.SuperRepelBetter)
}

internal object FortreeCity_Mart_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity_Mart.StockUpOnItems)
}

internal object FortreeCity_Mart_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity_Mart.RareCandyMakesMonGrow)
}

internal val FortreeCity_MartScripts: Map<String, Script> =
    mapOf(
        "FortreeCity_Mart_EventScript_Clerk" to FortreeCity_Mart_EventScript_Clerk,
        "FortreeCity_Mart_EventScript_Woman" to FortreeCity_Mart_EventScript_Woman,
        "FortreeCity_Mart_EventScript_Girl" to FortreeCity_Mart_EventScript_Girl,
        "FortreeCity_Mart_EventScript_Boy" to FortreeCity_Mart_EventScript_Boy,
    )
