package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart SlateportCity_Mart_Pokemart
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_Mart_EventScript_Clerk")
}

internal object SlateportCity_Mart_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SlateportCity_Mart.SomeItemsOnlyAtMart)
}

internal object SlateportCity_Mart_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SlateportCity_Mart.GreatBallIsBetter)
}

internal val SlateportCity_MartScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_Mart_EventScript_Clerk" to SlateportCity_Mart_EventScript_Clerk,
        "SlateportCity_Mart_EventScript_BlackBelt" to SlateportCity_Mart_EventScript_BlackBelt,
        "SlateportCity_Mart_EventScript_Man" to SlateportCity_Mart_EventScript_Man,
    )
