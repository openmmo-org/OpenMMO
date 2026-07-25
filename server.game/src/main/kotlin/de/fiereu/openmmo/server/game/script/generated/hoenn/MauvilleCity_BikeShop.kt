package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MauvilleCity_BikeShop
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_BIKE, MauvilleCity_BikeShop_EventScript_AskSwitchBikes
 * goto_if_set FLAG_DECLINED_BIKE, MauvilleCity_BikeShop_EventScript_SkipGreeting
 * msgbox MauvilleCity_BikeShop_Text_RydelGreeting, MSGBOX_DEFAULT
 * msgbox MauvilleCity_BikeShop_Text_DidYouComeFromFarAway, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, MauvilleCity_BikeShop_EventScript_YesFar
 * goto_if_eq VAR_RESULT, NO, MauvilleCity_BikeShop_EventScript_NotFar
 * end
 * ```
 */
internal object MauvilleCity_BikeShop_EventScript_Rydel : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MauvilleCity_BikeShop_EventScript_Rydel")
}

internal object MauvilleCity_BikeShop_EventScript_Assistant : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MauvilleCity_BikeShop.HandbooksAreInBack)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * message MauvilleCity_BikeShop_Text_MachHandbookWhichPage
 * waitmessage
 * goto MauvilleCity_BikeShop_EventScript_ChooseMachHandbookPage
 * end
 * ```
 */
internal object MauvilleCity_BikeShop_EventScript_MachBikeHandbook : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MauvilleCity_BikeShop_EventScript_MachBikeHandbook")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * message MauvilleCity_BikeShop_Text_AcroHandbookWhichPage
 * waitmessage
 * goto MauvilleCity_BikeShop_EventScript_ChooseAcroHandbookPage
 * end
 * ```
 */
internal object MauvilleCity_BikeShop_EventScript_AcroBikeHandbook : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MauvilleCity_BikeShop_EventScript_AcroBikeHandbook")
}

internal val MauvilleCity_BikeShopScripts: Map<String, Script> =
    mapOf(
        "MauvilleCity_BikeShop_EventScript_Rydel" to MauvilleCity_BikeShop_EventScript_Rydel,
        "MauvilleCity_BikeShop_EventScript_Assistant" to
            MauvilleCity_BikeShop_EventScript_Assistant,
        "MauvilleCity_BikeShop_EventScript_MachBikeHandbook" to
            MauvilleCity_BikeShop_EventScript_MachBikeHandbook,
        "MauvilleCity_BikeShop_EventScript_AcroBikeHandbook" to
            MauvilleCity_BikeShop_EventScript_AcroBikeHandbook,
    )
