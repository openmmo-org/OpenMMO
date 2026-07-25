package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FourIsland
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * special GetDaycareMonNicknames
 * specialvar VAR_RESULT, GetDaycareState
 * goto_if_eq VAR_RESULT, DAYCARE_EGG_WAITING, FourIsland_EventScript_DaycareEggWaiting
 * goto_if_eq VAR_RESULT, DAYCARE_ONE_MON, FourIsland_EventScript_CheckOnOneMon
 * goto_if_eq VAR_RESULT, DAYCARE_TWO_MONS, FourIsland_EventScript_CheckOnTwoMons
 * msgbox DayCare_Text_ImDaycareManSpeakToMyWife
 * release
 * end
 * ```
 */
internal object FourIsland_EventScript_DaycareMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FourIsland_EventScript_DaycareMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_CAN_LINK_WITH_RS, FourIsland_EventScript_OldWomanLoreleiLeft
 * msgbox FourIsland_Text_LoreleiHasReturned
 * release
 * end
 * ```
 */
internal object FourIsland_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FourIsland_EventScript_OldWoman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_STAR_PIECE
 * end
 * ```
 */
internal object FourIsland_EventScript_ItemStarPiece : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FourIsland_EventScript_ItemStarPiece")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_STARDUST
 * end
 * ```
 */
internal object FourIsland_EventScript_ItemStardust : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FourIsland_EventScript_ItemStardust")
}

internal object FourIsland_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FourIsland.MadeSlushieUsingIcefallCaveIce)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_LORELEI, 4
 * msgbox FourIsland_Text_LoreleiHasLotsOfStuffedDolls
 * release
 * end
 * ```
 */
internal object FourIsland_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FourIsland_EventScript_LittleGirl")
}

internal object FourIsland_EventScript_IslandSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FourIsland.IslandSign)
}

internal object FourIsland_EventScript_LoreleisHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FourIsland.LoreleisHouse)
}

internal val FourIslandScripts: Map<String, Script> =
    mapOf(
        "FourIsland_EventScript_DaycareMan" to FourIsland_EventScript_DaycareMan,
        "FourIsland_EventScript_OldWoman" to FourIsland_EventScript_OldWoman,
        "FourIsland_EventScript_ItemStarPiece" to FourIsland_EventScript_ItemStarPiece,
        "FourIsland_EventScript_ItemStardust" to FourIsland_EventScript_ItemStardust,
        "FourIsland_EventScript_FatMan" to FourIsland_EventScript_FatMan,
        "FourIsland_EventScript_LittleGirl" to FourIsland_EventScript_LittleGirl,
        "FourIsland_EventScript_IslandSign" to FourIsland_EventScript_IslandSign,
        "FourIsland_EventScript_LoreleisHouseSign" to FourIsland_EventScript_LoreleisHouseSign,
    )
