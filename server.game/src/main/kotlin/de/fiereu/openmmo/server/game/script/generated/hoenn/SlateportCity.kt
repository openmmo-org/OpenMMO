package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_eq VAR_SLATEPORT_CITY_STATE, 1, SlateportCity_EventScript_FatManSternInterview
 * msgbox SlateportCity_Text_BushedHikingFromMauville, MSGBOX_NPC
 * end
 * ```
 */
internal object SlateportCity_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_FatMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_SLATEPORT_CITY_STATE, 1, SlateportCity_EventScript_Man1SternInterview
 * msgbox SlateportCity_Text_EveryoneCallsHimCaptStern, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_Man1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_Man1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_SLATEPORT_CITY_STATE, 1, SlateportCity_EventScript_RichBoySternInterview
 * msgbox SlateportCity_Text_GoingToCompeteInBattleTent, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_RichBoy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_RichBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DOCK_REJECTED_DEVON_GOODS, SlateportCity_EventScript_Woman1AquaGone
 * msgbox SlateportCity_Text_WhatsLongLineOverThere, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_Woman1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_Woman1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_Text_QuitPushing, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_SLATEPORT_CITY_STATE, 1, SlateportCity_EventScript_CookSternInterview
 * msgbox SlateportCity_Text_SeaweedFullOfLife, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_Cook : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_Cook")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_SLATEPORT_CITY_STATE, 1, SlateportCity_EventScript_OldWomanSternInterview
 * msgbox SlateportCity_Text_HowTownIsBornAndGrows, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_OldWoman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_SLATEPORT_CITY_STATE, 1, SlateportCity_EventScript_GirlSternInterview
 * goto_if_set FLAG_RECEIVED_SECRET_POWER, SlateportCity_EventScript_GirlSecretBase
 * msgbox SlateportCity_Text_SlateportWonderfulPlace, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_Girl")
}

internal object SlateportCity_EventScript_Ty : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity.BigSmileForCamera)
}

internal object SlateportCity_EventScript_Gabby : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity.MostInvaluableExperience)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox SlateportCity_Text_SternMoveAheadWithExploration, MSGBOX_DEFAULT
 * msgbox SlateportCity_Text_GabbyWonderfulThanksForInterview, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GABBY, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * delay 10
 * applymovement LOCALID_SLATEPORT_TY, Common_Movement_WalkInPlaceFasterDown
 * waitmovement 0
 * delay 25
 * applymovement LOCALID_SLATEPORT_CAPT_STERN, SlateportCity_Movement_SternWatchGabbyAndTyExit
 * applymovement LOCALID_PLAYER, SlateportCity_Movement_PlayerFaceStern
 * applymovement LOCALID_SLATEPORT_GABBY, SlateportCity_Movement_GabbyExit
 * applymovement LOCALID_SLATEPORT_TY, SlateportCity_Movement_TyExit
 * waitmovement 0
 * removeobject LOCALID_SLATEPORT_GABBY
 * removeobject LOCALID_SLATEPORT_TY
 * msgbox SlateportCity_Text_SternWhewFirstInterview, MSGBOX_DEFAULT
 * applymovement LOCALID_SLATEPORT_CAPT_STERN, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * msgbox SlateportCity_Text_OhPlayerWeMadeDiscovery, MSGBOX_DEFAULT
 * playbgm MUS_ENCOUNTER_AQUA, FALSE
 * msgbox SlateportCity_Text_AquaWillAssumeControlOfSubmarine, MSGBOX_DEFAULT
 * applymovement LOCALID_SLATEPORT_COOK, Common_Movement_WalkInPlaceFasterLeft
 * applymovement LOCALID_SLATEPORT_FAT_MAN, Common_Movement_WalkInPlaceFasterLeft
 * applymovement LOCALID_SLATEPORT_OLD_WOMAN, SlateportCity_Movement_OldWomanConcern
 * applymovement LOCALID_SLATEPORT_RICH_BOY, Common_Movement_QuestionMark
 * applymovement LOCALID_SLATEPORT_MAN_1, SlateportCity_Movement_ManConcern
 * waitmovement 0
 * applymovement LOCALID_SLATEPORT_CAPT_STERN, Common_Movement_WalkInPlaceFasterDown
 * waitmovement 0
 * msgbox SlateportCity_Text_SternWhatWasAllThat, MSGBOX_DEFAULT
 * playse SE_PIN
 * applymovement LOCALID_SLATEPORT_CAPT_STERN, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_SLATEPORT_CAPT_STERN, Common_Movement_Delay48
 * waitmovement 0
 * applymovement LOCALID_SLATEPORT_CAPT_STERN, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * msgbox SlateportCity_Text_FromHarborTryingToTakeSub, MSGBOX_DEFAULT
 * msgbox SlateportCity_Text_PleaseComeWithMe, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_CAPT_STERN, SlateportCity_Movement_SternEnterHarbor
 * applymovement LOCALID_PLAYER, SlateportCity_Movement_PlayerEnterHarbor
 * waitmovement 0
 * removeobject LOCALID_SLATEPORT_CAPT_STERN
 * clearflag FLAG_HIDE_SLATEPORT_CITY_HARBOR_CAPTAIN_STERN
 * clearflag FLAG_HIDE_SLATEPORT_CITY_HARBOR_SUBMARINE_SHADOW
 * clearflag FLAG_HIDE_SLATEPORT_CITY_HARBOR_AQUA_GRUNT
 * clearflag FLAG_HIDE_SLATEPORT_CITY_HARBOR_ARCHIE
 * setvar VAR_SLATEPORT_CITY_STATE, 2
 * warp MAP_SLATEPORT_CITY_HARBOR, 11, 14
 * waitstate
 * releaseall
 * end
 * ```
 */
internal object SlateportCity_EventScript_CaptStern : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_CaptStern")
}

internal object SlateportCity_EventScript_Sailor1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SlateportCity.SeaIsSoWet)
}

internal object SlateportCity_EventScript_Sailor2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SlateportCity.SinkOldBoats)
}

internal object SlateportCity_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SlateportCity.BuyTooMuch)
}

internal object SlateportCity_EventScript_Man2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SlateportCity.BattleTentBuiltRecently)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_SLATEPORT_CITY_STATE, 1, SlateportCity_EventScript_ManiacSternInterview
 * msgbox SlateportCity_Text_GetNameRaterToHelpYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_Maniac : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_Maniac")
}

internal object SlateportCity_EventScript_Woman2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SlateportCity.CantChangeTradeMonName)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_Text_AquaHasPolicy, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_Text_BossIsBrilliant, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_3, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_RECEIVED_SECRET_POWER, SlateportCity_EventScript_ComeBackWithSecretPower
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemartdecoration SlateportCity_PokemartDecor
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_DecorClerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_DecorClerk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemartdecoration SlateportCity_PokemartDecor_Dolls
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_DollClerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_DollClerk")
}

internal object SlateportCity_EventScript_Man3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity.WonderIfLighthouseStartlesPokemon)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * bufferleadmonspeciesname STR_VAR_1
 * msgbox SlateportCity_Text_OhYourPokemon, MSGBOX_DEFAULT
 * specialvar VAR_RESULT, LeadMonHasEffortRibbon
 * call_if_eq VAR_RESULT, TRUE, SlateportCity_EventScript_MonHasEffortRibbon
 * specialvar VAR_RESULT, Special_AreLeadMonEVsMaxedOut
 * call_if_eq VAR_RESULT, FALSE, SlateportCity_EventScript_MonEVsNotMaxed
 * msgbox SlateportCity_Text_PleaseGiveItThisEffortRibbon, MSGBOX_DEFAULT
 * playfanfare MUS_OBTAIN_ITEM
 * message SlateportCity_Text_ReceivedEffortRibbon
 * waitfanfare
 * msgbox SlateportCity_Text_PutEffortRibbonOnMon, MSGBOX_DEFAULT
 * special GiveLeadMonEffortRibbon
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_EffortRibbonWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_EventScript_EffortRibbonWoman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart SlateportCity_Pokemart_PowerTMs
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_PowerTMClerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_PowerTMClerk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message SlateportCity_Text_EnergyGuruSellWhatYouNeed
 * waitmessage
 * pokemart SlateportCity_Pokemart_EnergyGuru
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_EnergyGuru : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_EnergyGuru")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_Text_WhatsNewSchemeIWonder, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_4, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_Text_ShouldTakeItAll, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_5, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt5 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_Text_DontButtIn, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_6, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt6 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_Text_RemindsMeOfLongLineForGames, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_7, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt7 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt7")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_Text_WhyAreWeLiningUp, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_8, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt8 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt8")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * playse SE_PIN
 * applymovement LOCALID_SLATEPORT_GRUNT_9, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_SLATEPORT_GRUNT_9, Common_Movement_Delay48
 * waitmovement 0
 * msgbox SlateportCity_Text_WhatDoYouWant, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_9, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * applymovement LOCALID_SLATEPORT_GRUNT_9, SlateportCity_Movement_DelayAquaGrunt
 * waitmovement 0
 * applymovement LOCALID_SLATEPORT_GRUNT_9, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox SlateportCity_Text_IllReadSignForYou, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_9, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * applymovement LOCALID_SLATEPORT_GRUNT_9, SlateportCity_Movement_DelayAquaGrunt
 * waitmovement 0
 * msgbox SlateportCity_Text_SaysSomethingLikeSeaIsEndless, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_9, Common_Movement_FacePlayer
 * waitmovement 0
 * applymovement LOCALID_SLATEPORT_GRUNT_9, SlateportCity_Movement_DelayAquaGrunt
 * waitmovement 0
 * applymovement LOCALID_SLATEPORT_GRUNT_9, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt9 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt9")
}

internal object SlateportCity_EventScript_AquaGrunt10 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity.ShouldveBroughtMyGameBoy)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_Text_HotSpringsAfterOperation, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SLATEPORT_GRUNT_11, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_AquaGrunt11 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_AquaGrunt11")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_POWDER_JAR, SlateportCity_EventScript_ReceivedPowderJar
 * msgbox SlateportCity_Text_ExplainBerries, MSGBOX_DEFAULT
 * giveitem ITEM_POWDER_JAR
 * setflag FLAG_RECEIVED_POWDER_JAR
 * msgbox SlateportCity_Text_ExplainBerryPowder, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_EventScript_BerryPowderClerk : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_EventScript_BerryPowderClerk")
}

internal object SlateportCity_EventScript_NameRatersHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity.NameRatersHouseSign)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_SYS_GAME_CLEAR, SlateportCity_EventScript_HarborSignFerryComplete
 * msgbox SlateportCity_Text_HarborFerryUnderConstruction, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object SlateportCity_EventScript_HarborSign : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SlateportCity_EventScript_HarborSign")
}

internal object SlateportCity_EventScript_MarketSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity.MarketSign)
}

internal object SlateportCity_EventScript_OceanicMuseumSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity.OceanicMuseumSign)
}

internal object SlateportCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity.CitySign)
}

internal object SlateportCity_EventScript_PokemonFanClubSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity.PokemonFanClubSign)
}

internal object SlateportCity_EventScript_BattleTentSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity.BattleTentSign)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_SYS_GAME_CLEAR, SlateportCity_EventScript_SternsShipyardFerryComplete
 * goto_if_set FLAG_BADGE07_GET, SlateportCity_EventScript_SternsShipyardNearsCompletion
 * msgbox SlateportCity_Text_SternsShipyardWantedSign, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object SlateportCity_EventScript_SternsShipyardSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_EventScript_SternsShipyardSign")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * special ShowBerryCrushRankings
 * releaseall
 * end
 * ```
 */
internal object SlateportCity_EventScript_BerryCrushRankingsSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_EventScript_BerryCrushRankingsSign")
}

internal val SlateportCityScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_EventScript_FatMan" to SlateportCity_EventScript_FatMan,
        "SlateportCity_EventScript_Man1" to SlateportCity_EventScript_Man1,
        "SlateportCity_EventScript_RichBoy" to SlateportCity_EventScript_RichBoy,
        "SlateportCity_EventScript_Woman1" to SlateportCity_EventScript_Woman1,
        "SlateportCity_EventScript_AquaGrunt1" to SlateportCity_EventScript_AquaGrunt1,
        "SlateportCity_EventScript_Cook" to SlateportCity_EventScript_Cook,
        "SlateportCity_EventScript_OldWoman" to SlateportCity_EventScript_OldWoman,
        "SlateportCity_EventScript_Girl" to SlateportCity_EventScript_Girl,
        "SlateportCity_EventScript_Ty" to SlateportCity_EventScript_Ty,
        "SlateportCity_EventScript_Gabby" to SlateportCity_EventScript_Gabby,
        "SlateportCity_EventScript_CaptStern" to SlateportCity_EventScript_CaptStern,
        "SlateportCity_EventScript_Sailor1" to SlateportCity_EventScript_Sailor1,
        "SlateportCity_EventScript_Sailor2" to SlateportCity_EventScript_Sailor2,
        "SlateportCity_EventScript_PokefanF" to SlateportCity_EventScript_PokefanF,
        "SlateportCity_EventScript_Man2" to SlateportCity_EventScript_Man2,
        "SlateportCity_EventScript_Maniac" to SlateportCity_EventScript_Maniac,
        "SlateportCity_EventScript_Woman2" to SlateportCity_EventScript_Woman2,
        "SlateportCity_EventScript_AquaGrunt2" to SlateportCity_EventScript_AquaGrunt2,
        "SlateportCity_EventScript_AquaGrunt3" to SlateportCity_EventScript_AquaGrunt3,
        "SlateportCity_EventScript_DecorClerk" to SlateportCity_EventScript_DecorClerk,
        "SlateportCity_EventScript_DollClerk" to SlateportCity_EventScript_DollClerk,
        "SlateportCity_EventScript_Man3" to SlateportCity_EventScript_Man3,
        "SlateportCity_EventScript_EffortRibbonWoman" to
            SlateportCity_EventScript_EffortRibbonWoman,
        "SlateportCity_EventScript_PowerTMClerk" to SlateportCity_EventScript_PowerTMClerk,
        "SlateportCity_EventScript_EnergyGuru" to SlateportCity_EventScript_EnergyGuru,
        "SlateportCity_EventScript_AquaGrunt4" to SlateportCity_EventScript_AquaGrunt4,
        "SlateportCity_EventScript_AquaGrunt5" to SlateportCity_EventScript_AquaGrunt5,
        "SlateportCity_EventScript_AquaGrunt6" to SlateportCity_EventScript_AquaGrunt6,
        "SlateportCity_EventScript_AquaGrunt7" to SlateportCity_EventScript_AquaGrunt7,
        "SlateportCity_EventScript_AquaGrunt8" to SlateportCity_EventScript_AquaGrunt8,
        "SlateportCity_EventScript_AquaGrunt9" to SlateportCity_EventScript_AquaGrunt9,
        "SlateportCity_EventScript_AquaGrunt10" to SlateportCity_EventScript_AquaGrunt10,
        "SlateportCity_EventScript_AquaGrunt11" to SlateportCity_EventScript_AquaGrunt11,
        "SlateportCity_EventScript_BerryPowderClerk" to SlateportCity_EventScript_BerryPowderClerk,
        "SlateportCity_EventScript_NameRatersHouseSign" to
            SlateportCity_EventScript_NameRatersHouseSign,
        "SlateportCity_EventScript_HarborSign" to SlateportCity_EventScript_HarborSign,
        "SlateportCity_EventScript_MarketSign" to SlateportCity_EventScript_MarketSign,
        "SlateportCity_EventScript_OceanicMuseumSign" to
            SlateportCity_EventScript_OceanicMuseumSign,
        "SlateportCity_EventScript_CitySign" to SlateportCity_EventScript_CitySign,
        "SlateportCity_EventScript_PokemonFanClubSign" to
            SlateportCity_EventScript_PokemonFanClubSign,
        "SlateportCity_EventScript_BattleTentSign" to SlateportCity_EventScript_BattleTentSign,
        "SlateportCity_EventScript_SternsShipyardSign" to
            SlateportCity_EventScript_SternsShipyardSign,
        "SlateportCity_EventScript_BerryCrushRankingsSign" to
            SlateportCity_EventScript_BerryCrushRankingsSign,
    )
