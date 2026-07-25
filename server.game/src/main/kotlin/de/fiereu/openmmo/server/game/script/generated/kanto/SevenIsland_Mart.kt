package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SevenIsland_Mart
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
 * pokemart SevenIsland_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object SevenIsland_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SevenIsland_Mart_EventScript_Clerk")
}

internal object SevenIsland_Mart_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SevenIsland_Mart.MonHavePersonalitiesOfTheirOwn)
}

internal object SevenIsland_Mart_EventScript_Fisher : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SevenIsland_Mart.NeedToFishOnSevenIsland)
}

internal object SevenIsland_Mart_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SevenIsland_Mart.PreparationsCompleteForRuins)
}

internal val SevenIsland_MartScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_Mart_EventScript_Clerk" to SevenIsland_Mart_EventScript_Clerk,
        "SevenIsland_Mart_EventScript_Lass" to SevenIsland_Mart_EventScript_Lass,
        "SevenIsland_Mart_EventScript_Fisher" to SevenIsland_Mart_EventScript_Fisher,
        "SevenIsland_Mart_EventScript_Hiker" to SevenIsland_Mart_EventScript_Hiker,
    )
