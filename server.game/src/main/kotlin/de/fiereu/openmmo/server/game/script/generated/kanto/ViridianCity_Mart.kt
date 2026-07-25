package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ViridianCity_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MAP_SCENE_VIRIDIAN_CITY_MART, 1, ViridianCity_Mart_EventScript_SayHiToOak
 * goto_if_questlog EventScript_ReleaseEnd
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart ViridianCity_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object ViridianCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Mart_EventScript_Clerk")
}

internal object ViridianCity_Mart_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianCity_Mart.GotToBuySomePotions)
}

internal object ViridianCity_Mart_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ViridianCity_Mart.ShopDoesGoodBusinessInAntidotes)
}

internal val ViridianCity_MartScripts: Map<String, Script> =
    mapOf(
        "ViridianCity_Mart_EventScript_Clerk" to ViridianCity_Mart_EventScript_Clerk,
        "ViridianCity_Mart_EventScript_Youngster" to ViridianCity_Mart_EventScript_Youngster,
        "ViridianCity_Mart_EventScript_Woman" to ViridianCity_Mart_EventScript_Woman,
    )
