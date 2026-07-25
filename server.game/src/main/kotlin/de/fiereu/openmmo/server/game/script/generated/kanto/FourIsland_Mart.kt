package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FourIsland_Mart
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
 * pokemart FourIsland_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object FourIsland_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FourIsland_Mart_EventScript_Clerk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_LORELEI, 2
 * msgbox FourIsland_Mart_Text_LoreleiGrewUpOnThisIsland
 * release
 * end
 * ```
 */
internal object FourIsland_Mart_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FourIsland_Mart_EventScript_OldMan")
}

internal object FourIsland_Mart_EventScript_Camper : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FourIsland_Mart.IcefallCaveIsFrigid)
}

internal val FourIsland_MartScripts: Map<String, Script> =
    mapOf(
        "FourIsland_Mart_EventScript_Clerk" to FourIsland_Mart_EventScript_Clerk,
        "FourIsland_Mart_EventScript_OldMan" to FourIsland_Mart_EventScript_OldMan,
        "FourIsland_Mart_EventScript_Camper" to FourIsland_Mart_EventScript_Camper,
    )
