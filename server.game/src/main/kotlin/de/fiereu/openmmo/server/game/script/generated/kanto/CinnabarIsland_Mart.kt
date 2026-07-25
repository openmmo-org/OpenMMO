package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CinnabarIsland_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart CinnabarIsland_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object CinnabarIsland_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Mart_EventScript_Clerk")
}

internal object CinnabarIsland_Mart_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CinnabarIsland_Mart.DontTheyHaveXAttack)
}

internal object CinnabarIsland_Mart_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CinnabarIsland_Mart.ExtraItemsNeverHurt)
}

internal val CinnabarIsland_MartScripts: Map<String, Script> =
    mapOf(
        "CinnabarIsland_Mart_EventScript_Clerk" to CinnabarIsland_Mart_EventScript_Clerk,
        "CinnabarIsland_Mart_EventScript_Woman" to CinnabarIsland_Mart_EventScript_Woman,
        "CinnabarIsland_Mart_EventScript_Scientist" to CinnabarIsland_Mart_EventScript_Scientist,
    )
