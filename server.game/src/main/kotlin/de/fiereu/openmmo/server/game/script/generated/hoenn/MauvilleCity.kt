package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MauvilleCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object MauvilleCity_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MauvilleCity.NurseHurtMonBackToHealth)
}

internal object MauvilleCity_EventScript_RichBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MauvilleCity.PokemonCanJumpYouOnBike)
}

internal object MauvilleCity_EventScript_Maniac : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MauvilleCity.AllSortsOfPeopleComeThrough)
}

internal object MauvilleCity_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MauvilleCity.RydelVeryGenerous)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TV_EXPLAINED, MauvilleCity_EventScript_TVExplained
 * msgbox MauvilleCity_Text_ExplainTV, MSGBOX_DEFAULT
 * setflag FLAG_TV_EXPLAINED
 * release
 * end
 * ```
 */
internal object MauvilleCity_EventScript_SchoolKidM : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_EventScript_SchoolKidM")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_DECLINED_WALLY_BATTLE_MAUVILLE, MauvilleCity_EventScript_WallyRequestBattleAgain
 * applymovement LOCALID_MAUVILLE_WALLY, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * msgbox MauvilleCity_Text_WallyWantToChallengeGym, MSGBOX_DEFAULT
 * msgbox MauvilleCity_Text_UncleYourePushingIt, MSGBOX_DEFAULT
 * msgbox MauvilleCity_Text_WallyWeCanBeatAnyone, MSGBOX_DEFAULT
 * applymovement LOCALID_MAUVILLE_WALLY, Common_Movement_FacePlayer
 * waitmovement 0
 * playse SE_PIN
 * applymovement LOCALID_MAUVILLE_WALLY, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_MAUVILLE_WALLY, Common_Movement_Delay48
 * waitmovement 0
 * msgbox MauvilleCity_Text_WallyWillYouBattleMe, MSGBOX_YESNO
 * goto MauvilleCity_EventScript_BattleWallyPrompt
 * end
 * ```
 */
internal object MauvilleCity_EventScript_Wally : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_EventScript_Wally")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DECLINED_WALLY_BATTLE_MAUVILLE, MauvilleCity_EventScript_UncleAskPlayerToBattleWally
 * msgbox MauvilleCity_Text_UncleHesTooPeppy, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_MAUVILLE_WALLYS_UNCLE, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object MauvilleCity_EventScript_WallysUncle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_EventScript_WallysUncle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_TM_THUNDERBOLT_FROM_WATTSON, MauvilleCity_EventScript_ReceivedThunderbolt
 * goto_if_eq VAR_NEW_MAUVILLE_STATE, 2, MauvilleCity_EventScript_CompletedNewMauville
 * goto_if_set FLAG_GOT_BASEMENT_KEY_FROM_WATTSON, MauvilleCity_EventScript_BegunNewMauville
 * msgbox MauvilleCity_Text_WattsonNeedFavorTakeKey, MSGBOX_DEFAULT
 * giveitem ITEM_BASEMENT_KEY
 * setflag FLAG_GOT_BASEMENT_KEY_FROM_WATTSON
 * msgbox MauvilleCity_Text_WattsonWontBeChallenge, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MauvilleCity_EventScript_Wattson : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_EventScript_Wattson")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_X_SPEED
 * end
 * ```
 */
internal object MauvilleCity_EventScript_ItemXSpeed : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_EventScript_ItemXSpeed")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MOVE_TUTOR_TAUGHT_ROLLOUT, MoveTutor_EventScript_RolloutTaught
 * msgbox MoveTutor_Text_RolloutTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_RolloutDeclined
 * call MoveTutor_EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_RolloutDeclined
 * msgbox MoveTutor_Text_RolloutWhichMon, MSGBOX_DEFAULT
 * setvar VAR_0x8005, TUTOR_MOVE_ROLLOUT
 * call MoveTutor_EventScript_OpenPartyMenu
 * goto_if_eq VAR_RESULT, 0, MoveTutor_EventScript_RolloutDeclined
 * setflag FLAG_MOVE_TUTOR_TAUGHT_ROLLOUT
 * goto MoveTutor_EventScript_RolloutTaught
 * end
 * ```
 */
internal object MauvilleCity_EventScript_RolloutTutor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_EventScript_RolloutTutor")
}

internal object MauvilleCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MauvilleCity.GymSign)
}

internal object MauvilleCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MauvilleCity.CitySign)
}

internal object MauvilleCity_EventScript_BikeShopSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MauvilleCity.BikeShopSign)
}

internal object MauvilleCity_EventScript_GameCornerSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MauvilleCity.GameCornerSign)
}

internal val MauvilleCityScripts: Map<String, Script> =
    mapOf(
        "MauvilleCity_EventScript_Boy" to MauvilleCity_EventScript_Boy,
        "MauvilleCity_EventScript_RichBoy" to MauvilleCity_EventScript_RichBoy,
        "MauvilleCity_EventScript_Maniac" to MauvilleCity_EventScript_Maniac,
        "MauvilleCity_EventScript_Woman" to MauvilleCity_EventScript_Woman,
        "MauvilleCity_EventScript_SchoolKidM" to MauvilleCity_EventScript_SchoolKidM,
        "MauvilleCity_EventScript_Wally" to MauvilleCity_EventScript_Wally,
        "MauvilleCity_EventScript_WallysUncle" to MauvilleCity_EventScript_WallysUncle,
        "MauvilleCity_EventScript_Wattson" to MauvilleCity_EventScript_Wattson,
        "MauvilleCity_EventScript_ItemXSpeed" to MauvilleCity_EventScript_ItemXSpeed,
        "MauvilleCity_EventScript_RolloutTutor" to MauvilleCity_EventScript_RolloutTutor,
        "MauvilleCity_EventScript_GymSign" to MauvilleCity_EventScript_GymSign,
        "MauvilleCity_EventScript_CitySign" to MauvilleCity_EventScript_CitySign,
        "MauvilleCity_EventScript_BikeShopSign" to MauvilleCity_EventScript_BikeShopSign,
        "MauvilleCity_EventScript_GameCornerSign" to MauvilleCity_EventScript_GameCornerSign,
    )
