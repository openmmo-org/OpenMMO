package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route111
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setflag FLAG_LANDMARK_WINSTRATE_FAMILY
 * msgbox Route111_Text_BattleOurFamily, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route111_EventScript_BattleWinstrates
 * msgbox Route111_Text_IsThatSo, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Victor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Victor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HEIDI, Route111_Text_HeidiIntro, Route111_Text_HeidiDefeat
 * msgbox Route111_Text_HeidiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Heidi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Heidi")
}

internal object Route111_EventScript_Man1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route111.ToughToKeepWinningUpTheRanks)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DREW, Route111_Text_DrewIntro, Route111_Text_DrewDefeat
 * msgbox Route111_Text_DrewPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Drew : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Drew")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DUSTY_1, Route111_Text_DustyIntro, Route111_Text_DustyDefeat, Route111_EventScript_RegisterDusty
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route111_EventScript_RematchDusty
 * msgbox Route111_Text_DustyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Dusty : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Dusty")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BEAU, Route111_Text_BeauIntro, Route111_Text_BeauDefeat
 * msgbox Route111_Text_BeauPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Beau : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Beau")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BECKY, Route111_Text_BeckyIntro, Route111_Text_BeckyDefeat
 * msgbox Route111_Text_BeckyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Becky : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Becky")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_1, GabbyAndTy_Text_TyPreFirstBattle, GabbyAndTy_Text_TyDefeatFirstTime, GabbyAndTy_Text_TyNotEnoughMons, GabbyAndTy_EventScript_FirstInterview
 * msgbox GabbyAndTy_Text_TyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_TyBattle1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_TyBattle1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_1, GabbyAndTy_Text_GabbyPreFirstBattle, GabbyAndTy_Text_GabbyDefeatFirstTime, GabbyAndTy_Text_GabbyNotEnoughMons, GabbyAndTy_EventScript_FirstInterview
 * msgbox GabbyAndTy_Text_KeepingAnEyeOutForYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_GabbyBattle1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_GabbyBattle1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * goto_if_set FLAG_DAILY_ROUTE_111_RECEIVED_BERRY, Route111_EventScript_ReceivedBerry
 * msgbox Route111_Text_WateredPlantsEveryDayTakeBerry, MSGBOX_DEFAULT
 * giveitem ITEM_RAZZ_BERRY
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_DAILY_ROUTE_111_RECEIVED_BERRY
 * special GetPlayerBigGuyGirlString
 * msgbox Route111_Text_GoingToTryToMakeDifferentColorBerries, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Girl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_SANDSTORM
 * end
 * ```
 */
internal object Route111_EventScript_ItemTMSandstorm : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_ItemTMSandstorm")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_4, GabbyAndTy_Text_GabbyIntro, GabbyAndTy_Text_GabbyDefeat, GabbyAndTy_Text_GabbyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_KeepingAnEyeOutForYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_GabbyBattle4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_GabbyBattle4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_4, GabbyAndTy_Text_TyIntro, GabbyAndTy_Text_TyDefeat, GabbyAndTy_Text_TyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_TyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_TyBattle4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_TyBattle4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_6, GabbyAndTy_Text_GabbyIntro, GabbyAndTy_Text_GabbyDefeat, GabbyAndTy_Text_GabbyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_KeepingAnEyeOutForYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_GabbyBattle6 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_GabbyBattle6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_6, GabbyAndTy_Text_TyIntro, GabbyAndTy_Text_TyDefeat, GabbyAndTy_Text_TyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_TyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_TyBattle6 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_TyBattle6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_STARDUST
 * end
 * ```
 */
internal object Route111_EventScript_ItemStardust : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_ItemStardust")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HP_UP
 * end
 * ```
 */
internal object Route111_EventScript_ItemHPUp : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_ItemHPUp")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_IRENE, Route111_Text_IreneIntro, Route111_Text_IreneDefeat
 * msgbox Route111_Text_IrenePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Irene : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Irene")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TRAVIS, Route111_Text_TravisIntro, Route111_Text_TravisDefeat
 * msgbox Route111_Text_TravisPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Travis : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Travis")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DAISUKE, Route111_Text_DaisukeIntro, Route111_Text_DaisukeDefeat
 * msgbox Route111_Text_DaisukePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Daisuke : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Daisuke")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BROOKE_1, Route111_Text_BrookeIntro, Route111_Text_BrookeDefeat, Route111_EventScript_RegisterBrooke
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route111_EventScript_RematchBrooke
 * msgbox Route111_Text_BrookePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Brooke : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Brooke")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WILTON_1, Route111_Text_WiltonIntro, Route111_Text_WiltonDefeat, Route111_EventScript_RegisterWilton
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route111_EventScript_RematchWilton
 * msgbox Route111_Text_WiltonPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Wilton : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Wilton")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route111_Text_MakingRoomUseTMToMakeYourOwn, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route111_EventScript_GiveSecretPower
 * msgbox Route111_Text_DontWantThis, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_SecretPowerMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_SecretPowerMan")
}

internal object Route111_EventScript_Man2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route111.WinstrateFamilyDestroyedMe)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TYRON, Route111_Text_TyronIntro, Route111_Text_TyronDefeat
 * msgbox Route111_Text_TyronPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Tyron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Tyron")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CELINA, Route111_Text_CelinaIntro, Route111_Text_CelinaDefeat
 * msgbox Route111_Text_CelinaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Celina : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Celina")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BIANCA, Route111_Text_BiancaIntro, Route111_Text_BiancaDefeat
 * msgbox Route111_Text_BiancaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Bianca : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Bianca")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HAYDEN, Route111_Text_HaydenIntro, Route111_Text_HaydenDefeat
 * msgbox Route111_Text_HaydenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Hayden : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Hayden")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BRYAN, Route111_Text_BryanIntro, Route111_Text_BryanDefeat
 * msgbox Route111_Text_BryanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Bryan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Bryan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CELIA, Route111_Text_CeliaIntro, Route111_Text_CeliaDefeat
 * msgbox Route111_Text_CeliaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Celia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Celia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BRANDEN, Route111_Text_BrandenIntro, Route111_Text_BrandenDefeat
 * msgbox Route111_Text_BrandenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route111_EventScript_Branden : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Branden")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ELIXIR
 * end
 * ```
 */
internal object Route111_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_ItemElixir")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MIRAGE_TOWER_STATE, 3, Route111_EventScript_HikerMirageTowerGone
 * goto_if_eq VAR_MIRAGE_TOWER_STATE, 2, Route111_EventScript_HikerMirageTowerDisintegrated
 * goto_if_set FLAG_MIRAGE_TOWER_VISIBLE, Route111_EventScript_HikerMirageTowerVisible
 * msgbox Route111_Text_ShouldBeMirageTowerAroundHere, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route111_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route111_EventScript_Hiker")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_ROUTE111_ROCK_SMASH_MAN, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox Route111_Text_MauvilleUncleToldMeToTakeRockSmash, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE111_ROCK_SMASH_MAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object Route111_EventScript_RockSmashTipFatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route111_EventScript_RockSmashTipFatMan")
}

internal object Route111_EventScript_WinstrateHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.WinstrateHouseSign)
}

internal object Route111_EventScript_RouteSignMauville : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.RouteSignMauville)
}

internal object Route111_EventScript_RouteSign112 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.RouteSign112)
}

internal object Route111_EventScript_RouteSign113 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.RouteSign113)
}

internal object Route111_EventScript_OldLadysRestStopSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.OldLadysRestStopSign)
}

internal object Route111_EventScript_TrainerTipsSpAtkSpDef : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.TrainerTipsSpAtkSpDef)
}

internal object Route111_EventScript_TrainerHillSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route111.TrainerHillSign)
}

internal val Route111Scripts: Map<String, Script> =
    mapOf(
        "Route111_EventScript_Victor" to Route111_EventScript_Victor,
        "Route111_EventScript_Heidi" to Route111_EventScript_Heidi,
        "Route111_EventScript_Man1" to Route111_EventScript_Man1,
        "Route111_EventScript_Drew" to Route111_EventScript_Drew,
        "Route111_EventScript_Dusty" to Route111_EventScript_Dusty,
        "Route111_EventScript_Beau" to Route111_EventScript_Beau,
        "Route111_EventScript_Becky" to Route111_EventScript_Becky,
        "GabbyAndTy_EventScript_TyBattle1" to GabbyAndTy_EventScript_TyBattle1,
        "GabbyAndTy_EventScript_GabbyBattle1" to GabbyAndTy_EventScript_GabbyBattle1,
        "Route111_EventScript_Girl" to Route111_EventScript_Girl,
        "Route111_EventScript_ItemTMSandstorm" to Route111_EventScript_ItemTMSandstorm,
        "GabbyAndTy_EventScript_GabbyBattle4" to GabbyAndTy_EventScript_GabbyBattle4,
        "GabbyAndTy_EventScript_TyBattle4" to GabbyAndTy_EventScript_TyBattle4,
        "GabbyAndTy_EventScript_GabbyBattle6" to GabbyAndTy_EventScript_GabbyBattle6,
        "GabbyAndTy_EventScript_TyBattle6" to GabbyAndTy_EventScript_TyBattle6,
        "Route111_EventScript_ItemStardust" to Route111_EventScript_ItemStardust,
        "Route111_EventScript_ItemHPUp" to Route111_EventScript_ItemHPUp,
        "Route111_EventScript_Irene" to Route111_EventScript_Irene,
        "Route111_EventScript_Travis" to Route111_EventScript_Travis,
        "Route111_EventScript_Daisuke" to Route111_EventScript_Daisuke,
        "Route111_EventScript_Brooke" to Route111_EventScript_Brooke,
        "Route111_EventScript_Wilton" to Route111_EventScript_Wilton,
        "Route111_EventScript_SecretPowerMan" to Route111_EventScript_SecretPowerMan,
        "Route111_EventScript_Man2" to Route111_EventScript_Man2,
        "Route111_EventScript_Tyron" to Route111_EventScript_Tyron,
        "Route111_EventScript_Celina" to Route111_EventScript_Celina,
        "Route111_EventScript_Bianca" to Route111_EventScript_Bianca,
        "Route111_EventScript_Hayden" to Route111_EventScript_Hayden,
        "Route111_EventScript_Bryan" to Route111_EventScript_Bryan,
        "Route111_EventScript_Celia" to Route111_EventScript_Celia,
        "Route111_EventScript_Branden" to Route111_EventScript_Branden,
        "Route111_EventScript_ItemElixir" to Route111_EventScript_ItemElixir,
        "Route111_EventScript_Hiker" to Route111_EventScript_Hiker,
        "Route111_EventScript_RockSmashTipFatMan" to Route111_EventScript_RockSmashTipFatMan,
        "Route111_EventScript_WinstrateHouseSign" to Route111_EventScript_WinstrateHouseSign,
        "Route111_EventScript_RouteSignMauville" to Route111_EventScript_RouteSignMauville,
        "Route111_EventScript_RouteSign112" to Route111_EventScript_RouteSign112,
        "Route111_EventScript_RouteSign113" to Route111_EventScript_RouteSign113,
        "Route111_EventScript_OldLadysRestStopSign" to Route111_EventScript_OldLadysRestStopSign,
        "Route111_EventScript_TrainerTipsSpAtkSpDef" to Route111_EventScript_TrainerTipsSpAtkSpDef,
        "Route111_EventScript_TrainerHillSign" to Route111_EventScript_TrainerHillSign,
    )
