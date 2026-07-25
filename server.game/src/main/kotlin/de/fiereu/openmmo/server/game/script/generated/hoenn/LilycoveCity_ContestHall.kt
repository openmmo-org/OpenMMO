package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_ContestHall
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object LilycoveCity_ContestHall_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestHall.TodayWonSmartnessContest)
}

internal object LilycoveCity_ContestHall_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestHall.EnteredBunchOfContests)
}

internal object LilycoveCity_ContestHall_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestHall.ManWhoWonEarlierHadPokeblocks)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_GiveItBestSmartAppeal, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SMART_MC, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_SmartContestMC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_SmartContestMC")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_AreYouEnjoyingThisContest, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SMART_JUDGE, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_SmartContestJudge : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_SmartContestJudge")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_EnteredWrongContest, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SMART_CONTESTANT_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_SmartContestant1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_SmartContestant1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_RaisedMonToBeSmart, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SMART_CONTESTANT_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_SmartContestant2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_SmartContestant2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_IfMonPullsSmartMoveNext, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SMART_CONTESTANT_3, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_SmartContestant3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_SmartContestant3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_DontAppreciateCuteLeechLife, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SMART_CONTESTANT_4, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_SmartContestant4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_SmartContestant4")
}

internal object LilycoveCity_ContestHall_EventScript_SmartContestAudience1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(LilycoveCity_ContestHall.YoureBeautifulGrandpa)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_StillLoveSmartnessContests, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SMART_AUDIENCE_4, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_SmartContestAudience4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_SmartContestAudience4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_AllSeemToUseDifferentMoves, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SMART_AUDIENCE_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_SmartContestAudience2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_SmartContestAudience2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_AreYouEnteringBeautyContest, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_BEAUTY_MC, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_BeautyContestMC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_BeautyContestMC")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_EveryPokemonPristineBeauty, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_BEAUTY_JUDGE, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_BeautyContestJudge : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_BeautyContestJudge")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_EyesWillBeGluedToMyBeauty, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_BEAUTY_CONTESTANT_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_BeautyContestant1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_BeautyContestant1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_OverdidGrooming, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_BEAUTY_CONTESTANT_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_BeautyContestant2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_BeautyContestant2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_JudgeWontSeeAuroraBeam, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_BEAUTY_CONTESTANT_3, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_BeautyContestant3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_BeautyContestant3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_PokemonLooksLikeYoungerMe, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_BEAUTY_CONTESTANT_4, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_BeautyContestant4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_BeautyContestant4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_WinBeautyContestMakesMeHappy, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_BEAUTY_AUDIENCE_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_BeautyContestAudience1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_BeautyContestAudience1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_CantWinOnBeautyAlone, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_BEAUTY_AUDIENCE_3, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_BeautyContestAudience3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_BeautyContestAudience3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_GanderAtAllThosePrettyPokemon, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_BEAUTY_AUDIENCE_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_BeautyContestAudience2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_BeautyContestAudience2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_PokemonSmarterThanTrainers, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SMART_AUDIENCE_3, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_SmartContestAudience3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_SmartContestAudience3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_InTheMiddleOfContest, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CUTE_MC, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_CuteContestMC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_CuteContestMC")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_SuchCharmingCuteAppeals, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CUTE_JUDGE, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_CuteContestJudge : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_CuteContestJudge")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_MyAzurillWasDistracted, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CUTE_CONTESTANT_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_CuteContestant1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_CuteContestant1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_NeverWonBattleButContest, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CUTE_CONTESTANT_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_CuteContestant2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_CuteContestant2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_PetalDanceIsMarvel, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CUTE_CONTESTANT_3, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_CuteContestant3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_CuteContestant3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_MyMonAppealSoMuchCuter, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CUTE_CONTESTANT_4, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_CuteContestant4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_CuteContestant4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_CUTE_AUDIENCE_1, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox LilycoveCity_ContestHall_Text_MyChildIsInContest, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CUTE_AUDIENCE_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * delay 25
 * msgbox LilycoveCity_ContestHall_Text_ComeOnDear, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_CuteContestAudience1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_CuteContestAudience1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_ThatGirlThereIsCutest, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CUTE_AUDIENCE_3, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_CuteContestAudience3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_CuteContestAudience3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestHall_Text_WantCuteMonOfMyOwn, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CUTE_AUDIENCE_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestHall_EventScript_CuteContestAudience2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestHall_EventScript_CuteContestAudience2")
}

internal object LilycoveCity_ContestHall_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestHall.IsntThisPlaceHumongous)
}

internal object LilycoveCity_ContestHall_EventScript_BeautyStageSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(LilycoveCity_ContestHall.BeautyContestStage)
}

internal object LilycoveCity_ContestHall_EventScript_CuteStageSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LilycoveCity_ContestHall.CuteContestStage)
}

internal object LilycoveCity_ContestHall_EventScript_SmartStageSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(LilycoveCity_ContestHall.SmartContestStage)
}

internal val LilycoveCity_ContestHallScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_ContestHall_EventScript_Boy1" to LilycoveCity_ContestHall_EventScript_Boy1,
        "LilycoveCity_ContestHall_EventScript_Boy2" to LilycoveCity_ContestHall_EventScript_Boy2,
        "LilycoveCity_ContestHall_EventScript_Girl" to LilycoveCity_ContestHall_EventScript_Girl,
        "LilycoveCity_ContestHall_EventScript_SmartContestMC" to
            LilycoveCity_ContestHall_EventScript_SmartContestMC,
        "LilycoveCity_ContestHall_EventScript_SmartContestJudge" to
            LilycoveCity_ContestHall_EventScript_SmartContestJudge,
        "LilycoveCity_ContestHall_EventScript_SmartContestant1" to
            LilycoveCity_ContestHall_EventScript_SmartContestant1,
        "LilycoveCity_ContestHall_EventScript_SmartContestant2" to
            LilycoveCity_ContestHall_EventScript_SmartContestant2,
        "LilycoveCity_ContestHall_EventScript_SmartContestant3" to
            LilycoveCity_ContestHall_EventScript_SmartContestant3,
        "LilycoveCity_ContestHall_EventScript_SmartContestant4" to
            LilycoveCity_ContestHall_EventScript_SmartContestant4,
        "LilycoveCity_ContestHall_EventScript_SmartContestAudience1" to
            LilycoveCity_ContestHall_EventScript_SmartContestAudience1,
        "LilycoveCity_ContestHall_EventScript_SmartContestAudience4" to
            LilycoveCity_ContestHall_EventScript_SmartContestAudience4,
        "LilycoveCity_ContestHall_EventScript_SmartContestAudience2" to
            LilycoveCity_ContestHall_EventScript_SmartContestAudience2,
        "LilycoveCity_ContestHall_EventScript_BeautyContestMC" to
            LilycoveCity_ContestHall_EventScript_BeautyContestMC,
        "LilycoveCity_ContestHall_EventScript_BeautyContestJudge" to
            LilycoveCity_ContestHall_EventScript_BeautyContestJudge,
        "LilycoveCity_ContestHall_EventScript_BeautyContestant1" to
            LilycoveCity_ContestHall_EventScript_BeautyContestant1,
        "LilycoveCity_ContestHall_EventScript_BeautyContestant2" to
            LilycoveCity_ContestHall_EventScript_BeautyContestant2,
        "LilycoveCity_ContestHall_EventScript_BeautyContestant3" to
            LilycoveCity_ContestHall_EventScript_BeautyContestant3,
        "LilycoveCity_ContestHall_EventScript_BeautyContestant4" to
            LilycoveCity_ContestHall_EventScript_BeautyContestant4,
        "LilycoveCity_ContestHall_EventScript_BeautyContestAudience1" to
            LilycoveCity_ContestHall_EventScript_BeautyContestAudience1,
        "LilycoveCity_ContestHall_EventScript_BeautyContestAudience3" to
            LilycoveCity_ContestHall_EventScript_BeautyContestAudience3,
        "LilycoveCity_ContestHall_EventScript_BeautyContestAudience2" to
            LilycoveCity_ContestHall_EventScript_BeautyContestAudience2,
        "LilycoveCity_ContestHall_EventScript_SmartContestAudience3" to
            LilycoveCity_ContestHall_EventScript_SmartContestAudience3,
        "LilycoveCity_ContestHall_EventScript_CuteContestMC" to
            LilycoveCity_ContestHall_EventScript_CuteContestMC,
        "LilycoveCity_ContestHall_EventScript_CuteContestJudge" to
            LilycoveCity_ContestHall_EventScript_CuteContestJudge,
        "LilycoveCity_ContestHall_EventScript_CuteContestant1" to
            LilycoveCity_ContestHall_EventScript_CuteContestant1,
        "LilycoveCity_ContestHall_EventScript_CuteContestant2" to
            LilycoveCity_ContestHall_EventScript_CuteContestant2,
        "LilycoveCity_ContestHall_EventScript_CuteContestant3" to
            LilycoveCity_ContestHall_EventScript_CuteContestant3,
        "LilycoveCity_ContestHall_EventScript_CuteContestant4" to
            LilycoveCity_ContestHall_EventScript_CuteContestant4,
        "LilycoveCity_ContestHall_EventScript_CuteContestAudience1" to
            LilycoveCity_ContestHall_EventScript_CuteContestAudience1,
        "LilycoveCity_ContestHall_EventScript_CuteContestAudience3" to
            LilycoveCity_ContestHall_EventScript_CuteContestAudience3,
        "LilycoveCity_ContestHall_EventScript_CuteContestAudience2" to
            LilycoveCity_ContestHall_EventScript_CuteContestAudience2,
        "LilycoveCity_ContestHall_EventScript_Sailor" to
            LilycoveCity_ContestHall_EventScript_Sailor,
        "LilycoveCity_ContestHall_EventScript_BeautyStageSign" to
            LilycoveCity_ContestHall_EventScript_BeautyStageSign,
        "LilycoveCity_ContestHall_EventScript_CuteStageSign" to
            LilycoveCity_ContestHall_EventScript_CuteStageSign,
        "LilycoveCity_ContestHall_EventScript_SmartStageSign" to
            LilycoveCity_ContestHall_EventScript_SmartStageSign,
    )
