package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_Mart
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
 * pokemart ThreeIsland_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object ThreeIsland_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ThreeIsland_Mart_EventScript_Clerk")
}

internal object ThreeIsland_Mart_EventScript_Picnicker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ThreeIsland_Mart.TrueThatCeldadonDeptStoreBigger)
}

internal object ThreeIsland_Mart_EventScript_BugCatcher : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ThreeIsland_Mart.PeopleHealWithBerriesFromForest)
}

internal object ThreeIsland_Mart_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ThreeIsland_Mart.BikersWereAboutToTrashMart)
}

internal val ThreeIsland_MartScripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_Mart_EventScript_Clerk" to ThreeIsland_Mart_EventScript_Clerk,
        "ThreeIsland_Mart_EventScript_Picnicker" to ThreeIsland_Mart_EventScript_Picnicker,
        "ThreeIsland_Mart_EventScript_BugCatcher" to ThreeIsland_Mart_EventScript_BugCatcher,
        "ThreeIsland_Mart_EventScript_Youngster" to ThreeIsland_Mart_EventScript_Youngster,
    )
