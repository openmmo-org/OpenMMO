package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PewterCity_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PewterCity_Mart_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_Mart.BoughtWeirdFishFromShadyGuy)
}

internal object PewterCity_Mart_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_Mart.GoodThingsIfRaiseMonsDiligently)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart PewterCity_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object PewterCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PewterCity_Mart_EventScript_Clerk")
}

internal val PewterCity_MartScripts: Map<String, Script> =
    mapOf(
        "PewterCity_Mart_EventScript_Youngster" to PewterCity_Mart_EventScript_Youngster,
        "PewterCity_Mart_EventScript_Boy" to PewterCity_Mart_EventScript_Boy,
        "PewterCity_Mart_EventScript_Clerk" to PewterCity_Mart_EventScript_Clerk,
    )
