package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route110
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route110_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.WhichShouldIChoose)
}

internal object Route110_EventScript_CyclingGuy2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.BikeTechniques)
}

internal object Route110_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.WalkOnTheLowRoad)
}

internal object Route110_EventScript_CyclingGuy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.YouGotBikeFromRydel)
}

internal object Route110_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.TwoRoads)
}

internal object Route110_EventScript_CyclingGirl1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.HairStreamsBehindMe)
}

internal object Route110_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route110.RideBikeAtFullSpeed)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JASMINE, Route110_Text_JasmineIntro, Route110_Text_JasmineDefeated
 * msgbox Route110_Text_JasminePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Jasmine : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Jasmine")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ANTHONY, Route110_Text_AnthonyIntro, Route110_Text_AnthonyDefeated
 * msgbox Route110_Text_AnthonyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Anthony : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Anthony")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ABIGAIL_1, Route110_Text_AbigailIntro, Route110_Text_AbigailDefeated, Route110_EventScript_AbigailRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route110_EventScript_AbigailRematch
 * msgbox Route110_Text_AbigailPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_Abigail : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Abigail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BENJAMIN_1, Route110_Text_BenjaminIntro, Route110_Text_BenjaminDefeated, Route110_EventScript_BenjaminRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route110_EventScript_BenjaminRematch
 * msgbox Route110_Text_BenjaminPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_Benjamin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Benjamin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_EDWARD, Route110_Text_EdwardIntro, Route110_Text_EdwardDefeated
 * msgbox Route110_Text_EdwardPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Edward : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Edward")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JACLYN, Route110_Text_JaclynIntro, Route110_Text_JaclynDefeated
 * msgbox Route110_Text_JaclynPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Jaclyn : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Jaclyn")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_EDWIN_1, Route110_Text_EdwinIntro, Route110_Text_EdwinDefeated, Route110_EventScript_EdwinRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route110_EventScript_EdwinRematch
 * msgbox Route110_Text_EdwinPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_Edwin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Edwin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DALE, Route110_Text_DaleIntro, Route110_Text_DaleDefeated
 * msgbox Route110_Text_DalePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Dale : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Dale")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_DIRE_HIT
 * end
 * ```
 */
internal object Route110_EventScript_ItemDireHit : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_ItemDireHit")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object Route110_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * specialvar VAR_RESULT, GetPlayerAvatarBike
 * goto_if_eq VAR_RESULT, 1, Route110_EventScript_PlayerRidingAcroBike
 * goto_if_eq VAR_CYCLING_CHALLENGE_STATE, 0, Route110_EventScript_PlayerNotRidingBike
 * msgbox Route110_Text_AlwaysAimHigher, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_ChallengeGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_ChallengeGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route110_Text_WeCantTalkAboutAquaActivities, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route110_EventScript_AquaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_AquaGrunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route110_Text_KickUpARuckus, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route110_EventScript_AquaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_AquaGrunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route110_Text_MyFirstJobInAqua, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route110_EventScript_AquaGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_AquaGrunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route110_Text_AquaActionsBringSmiles, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route110_EventScript_AquaGrunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_AquaGrunt4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JACOB, Route110_Text_JacobIntro, Route110_Text_JacobDefeated
 * msgbox Route110_Text_JacobPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Jacob : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Jacob")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TIMMY, Route110_Text_TimmyIntro, Route110_Text_TimmyDefeated
 * msgbox Route110_Text_TimmyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Timmy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Timmy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ISABEL_1, Route110_Text_IsabelIntro, Route110_Text_IsabelDefeated, Route110_EventScript_IsabelRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route110_EventScript_IsabelRematch
 * msgbox Route110_Text_IsabelPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route110_EventScript_Isabel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Isabel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KALEB, Route110_Text_KalebIntro, Route110_Text_KalebDefeated
 * msgbox Route110_Text_KalebPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Kaleb : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Kaleb")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ALYSSA, Route110_Text_AlyssaIntro, Route110_Text_AlyssaDefeated
 * msgbox Route110_Text_AlyssaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Alyssa : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Alyssa")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JOSEPH, Route110_Text_JosephIntro, Route110_Text_JosephDefeated
 * msgbox Route110_Text_JosephPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_EventScript_Joseph : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_Joseph")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ELIXIR
 * end
 * ```
 */
internal object Route110_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route110_EventScript_ItemElixir")
}

internal object Route110_EventScript_VandalizedSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.AquaWasHere)
}

internal object Route110_EventScript_SeasideParkingSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.SeasideParkingSign)
}

internal object Route110_EventScript_CyclingRoadSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.CyclingRoadSign)
}

internal object Route110_EventScript_SlateportCitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.SlateportCitySign)
}

internal object Route110_EventScript_Route103Sign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.Route103Sign)
}

internal object Route110_EventScript_MauvilleCitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.MauvilleCitySign)
}

internal object Route110_EventScript_TrainerTipsPrlzSleep : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.TrainerTipsPrlzSleep)
}

internal object Route110_EventScript_TrainerTipsRegisterItems : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.TrainerTipsRegisterItems)
}

internal object Route110_EventScript_TrickHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route110.TrickHouseSign)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * specialvar VAR_RESULT, GetRecordedCyclingRoadResults
 * goto_if_eq VAR_RESULT, FALSE, Route110_EventScript_NoRecordSet
 * msgbox Route110_Text_BestRecord, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object Route110_EventScript_CyclingRoadResultsSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_EventScript_CyclingRoadResultsSign")
}

internal val Route110Scripts: Map<String, Script> =
    mapOf(
        "Route110_EventScript_Boy2" to Route110_EventScript_Boy2,
        "Route110_EventScript_CyclingGuy2" to Route110_EventScript_CyclingGuy2,
        "Route110_EventScript_OldWoman" to Route110_EventScript_OldWoman,
        "Route110_EventScript_CyclingGuy1" to Route110_EventScript_CyclingGuy1,
        "Route110_EventScript_OldMan" to Route110_EventScript_OldMan,
        "Route110_EventScript_CyclingGirl1" to Route110_EventScript_CyclingGirl1,
        "Route110_EventScript_Boy1" to Route110_EventScript_Boy1,
        "Route110_EventScript_Jasmine" to Route110_EventScript_Jasmine,
        "Route110_EventScript_Anthony" to Route110_EventScript_Anthony,
        "Route110_EventScript_Abigail" to Route110_EventScript_Abigail,
        "Route110_EventScript_Benjamin" to Route110_EventScript_Benjamin,
        "Route110_EventScript_Edward" to Route110_EventScript_Edward,
        "Route110_EventScript_Jaclyn" to Route110_EventScript_Jaclyn,
        "Route110_EventScript_Edwin" to Route110_EventScript_Edwin,
        "Route110_EventScript_Dale" to Route110_EventScript_Dale,
        "Route110_EventScript_ItemDireHit" to Route110_EventScript_ItemDireHit,
        "Route110_EventScript_ItemRareCandy" to Route110_EventScript_ItemRareCandy,
        "Route110_EventScript_ChallengeGuy" to Route110_EventScript_ChallengeGuy,
        "Route110_EventScript_AquaGrunt1" to Route110_EventScript_AquaGrunt1,
        "Route110_EventScript_AquaGrunt2" to Route110_EventScript_AquaGrunt2,
        "Route110_EventScript_AquaGrunt3" to Route110_EventScript_AquaGrunt3,
        "Route110_EventScript_AquaGrunt4" to Route110_EventScript_AquaGrunt4,
        "Route110_EventScript_Jacob" to Route110_EventScript_Jacob,
        "Route110_EventScript_Timmy" to Route110_EventScript_Timmy,
        "Route110_EventScript_Isabel" to Route110_EventScript_Isabel,
        "Route110_EventScript_Kaleb" to Route110_EventScript_Kaleb,
        "Route110_EventScript_Alyssa" to Route110_EventScript_Alyssa,
        "Route110_EventScript_Joseph" to Route110_EventScript_Joseph,
        "Route110_EventScript_ItemElixir" to Route110_EventScript_ItemElixir,
        "Route110_EventScript_VandalizedSign" to Route110_EventScript_VandalizedSign,
        "Route110_EventScript_SeasideParkingSign" to Route110_EventScript_SeasideParkingSign,
        "Route110_EventScript_CyclingRoadSign" to Route110_EventScript_CyclingRoadSign,
        "Route110_EventScript_SlateportCitySign" to Route110_EventScript_SlateportCitySign,
        "Route110_EventScript_Route103Sign" to Route110_EventScript_Route103Sign,
        "Route110_EventScript_MauvilleCitySign" to Route110_EventScript_MauvilleCitySign,
        "Route110_EventScript_TrainerTipsPrlzSleep" to Route110_EventScript_TrainerTipsPrlzSleep,
        "Route110_EventScript_TrainerTipsRegisterItems" to
            Route110_EventScript_TrainerTipsRegisterItems,
        "Route110_EventScript_TrickHouseSign" to Route110_EventScript_TrickHouseSign,
        "Route110_EventScript_CyclingRoadResultsSign" to
            Route110_EventScript_CyclingRoadResultsSign,
    )
