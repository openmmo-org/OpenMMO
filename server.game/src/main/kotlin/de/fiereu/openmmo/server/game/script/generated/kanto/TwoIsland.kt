package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.TwoIsland
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * goto_if_eq VAR_MAP_SCENE_TWO_ISLAND, 4, TwoIsland_EventScript_ClerkShopExpanded3
 * goto_if_eq VAR_MAP_SCENE_TWO_ISLAND, 3, TwoIsland_EventScript_ClerkShopExpanded2
 * goto_if_eq VAR_MAP_SCENE_TWO_ISLAND, 2, TwoIsland_EventScript_ClerkShopExpanded1
 * goto TwoIsland_EventScript_ClerkShopInitial
 * end
 * ```
 */
internal object TwoIsland_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TwoIsland_EventScript_Clerk")
}

internal object TwoIsland_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(TwoIsland.ShopkeepersBrotherWorksGameCorner)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BRUNO, 4
 * msgbox TwoIsland_Text_BrunoCameToIslandWhileBack
 * release
 * end
 * ```
 */
internal object TwoIsland_EventScript_Beauty : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TwoIsland_EventScript_Beauty")
}

internal object TwoIsland_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(TwoIsland.FellowMovedFromCeladonCity)
}

internal object TwoIsland_EventScript_PokeManiac : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(TwoIsland.BuyRareItemsHere)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_REVIVE
 * end
 * ```
 */
internal object TwoIsland_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TwoIsland_EventScript_ItemRevive")
}

internal object TwoIsland_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(TwoIsland.HaveYouTriedJumpingGame)
}

internal object TwoIsland_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(TwoIsland.OldWomanLivesOutOnCape)
}

internal object TwoIsland_EventScript_IslandSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(TwoIsland.IslandSign)
}

internal object TwoIsland_EventScript_JoyfulGameCornerSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(TwoIsland.JoyfulGameCornerSign)
}

internal object TwoIsland_EventScript_FastCurrentSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(TwoIsland.DangerFastCurrent)
}

internal val TwoIslandScripts: Map<String, Script> =
    mapOf(
        "TwoIsland_EventScript_Clerk" to TwoIsland_EventScript_Clerk,
        "TwoIsland_EventScript_Woman" to TwoIsland_EventScript_Woman,
        "TwoIsland_EventScript_Beauty" to TwoIsland_EventScript_Beauty,
        "TwoIsland_EventScript_Sailor" to TwoIsland_EventScript_Sailor,
        "TwoIsland_EventScript_PokeManiac" to TwoIsland_EventScript_PokeManiac,
        "TwoIsland_EventScript_ItemRevive" to TwoIsland_EventScript_ItemRevive,
        "TwoIsland_EventScript_LittleBoy" to TwoIsland_EventScript_LittleBoy,
        "TwoIsland_EventScript_Boy" to TwoIsland_EventScript_Boy,
        "TwoIsland_EventScript_IslandSign" to TwoIsland_EventScript_IslandSign,
        "TwoIsland_EventScript_JoyfulGameCornerSign" to TwoIsland_EventScript_JoyfulGameCornerSign,
        "TwoIsland_EventScript_FastCurrentSign" to TwoIsland_EventScript_FastCurrentSign,
    )
