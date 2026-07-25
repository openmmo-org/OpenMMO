package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route16_NorthEntrance_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route16_NorthEntrance_2F_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route16_NorthEntrance_2F.OnBikeRideWithGirlfriend)
}

internal object Route16_NorthEntrance_2F_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route16_NorthEntrance_2F.RidingTogetherOnNewBikes)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Route16_NorthEntrance_2F_EventScript_GetAideRequestInfo
 * goto_if_set FLAG_GOT_AMULET_COIN_FROM_OAKS_AIDE, Route16_NorthEntrance_2F_EventScript_AlreadyGotAmuletCoin
 * msgbox Route16_NorthEntrance_2F_Text_GiveAmuletCoinIfCaught40, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Aide_EventScript_DeclineCheckMons
 * setvar VAR_0x8004, 0
 * specialvar VAR_RESULT, GetPokedexCount
 * buffernumberstring STR_VAR_3, VAR_0x8006
 * call Route16_NorthEntrance_2F_EventScript_GetAideRequestInfo
 * goto_if_lt VAR_0x8006, REQUIRED_CAUGHT_MONS, Aide_EventScript_HaventCaughtEnough
 * msgbox Route16_NorthEntrance_2F_Text_GreatHereYouGo
 * checkitemspace ITEM_AMULET_COIN
 * goto_if_eq VAR_RESULT, FALSE, Aide_EventScript_NoRoomForItem
 * giveitem_msg Route16_NorthEntrance_2F_Text_ReceivedAmuletCoinFromAide, ITEM_AMULET_COIN
 * setflag FLAG_GOT_AMULET_COIN_FROM_OAKS_AIDE
 * msgbox Route16_NorthEntrance_2F_Text_ExplainAmuletCoin
 * release
 * end
 * ```
 */
internal object Route16_NorthEntrance_2F_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route16_NorthEntrance_2F_EventScript_Aide")
}

internal object Route16_NorthEntrance_2F_EventScript_LeftBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(Route16_NorthEntrance_2F.ItsCeladonDeptStore)
}

internal object Route16_NorthEntrance_2F_EventScript_RightBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(Route16_NorthEntrance_2F.LongPathOverWater)
}

internal val Route16_NorthEntrance_2FScripts: Map<String, Script> =
    mapOf(
        "Route16_NorthEntrance_2F_EventScript_LittleBoy" to
            Route16_NorthEntrance_2F_EventScript_LittleBoy,
        "Route16_NorthEntrance_2F_EventScript_LittleGirl" to
            Route16_NorthEntrance_2F_EventScript_LittleGirl,
        "Route16_NorthEntrance_2F_EventScript_Aide" to Route16_NorthEntrance_2F_EventScript_Aide,
        "Route16_NorthEntrance_2F_EventScript_LeftBinoculars" to
            Route16_NorthEntrance_2F_EventScript_LeftBinoculars,
        "Route16_NorthEntrance_2F_EventScript_RightBinoculars" to
            Route16_NorthEntrance_2F_EventScript_RightBinoculars,
    )
