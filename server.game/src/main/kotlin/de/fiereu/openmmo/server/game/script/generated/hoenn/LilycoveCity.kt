package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TEAM_AQUA_ESCAPED_IN_SUBMARINE, LilycoveCity_EventScript_Sailor2AquaGone
 * msgbox LilycoveCity_Text_TeamAquaBeenTrainingWailmer, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_EventScript_Sailor2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_Sailor2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_BADGE07_GET, LilycoveCity_EventScript_GirlAquaGone
 * msgbox LilycoveCity_Text_StrangeCaveInCove, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_Girl")
}

internal object LilycoveCity_EventScript_Man3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity.ContestHallInTown)
}

internal object LilycoveCity_EventScript_RichBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity.ImFromKanto)
}

internal object LilycoveCity_EventScript_Man2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity.ImArtDealer)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TEAM_AQUA_ESCAPED_IN_SUBMARINE, LilycoveCity_EventScript_Woman2AquaGone
 * msgbox LilycoveCity_Text_SomeoneStoleMyPokemon, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_EventScript_Woman2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_Woman2")
}

internal object LilycoveCity_EventScript_ExpertM1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LilycoveCity.SeaRemainsForeverYoung)
}

internal object LilycoveCity_EventScript_ExpertF : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LilycoveCity.SixtyYearsAgoHusbandProposed)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_BADGE07_GET, LilycoveCity_EventScript_ExpertM2AquaGone
 * msgbox LilycoveCity_Text_TeamAquaRenovatedCavern, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_EventScript_ExpertM2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_ExpertM2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_MET_WAILMER_TRAINER, LilycoveCity_EventScript_MetWailmerTrainer
 * msgbox LilycoveCity_Text_WailmerLeapOutOfWater, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox LilycoveCity_Text_GetLostMessingUpTraining, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * setflag FLAG_MET_WAILMER_TRAINER
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_EventScript_WailmerTrainerGrunt : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_EventScript_WailmerTrainerGrunt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REPEL
 * end
 * ```
 */
internal object LilycoveCity_EventScript_ItemMaxRepel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_ItemMaxRepel")
}

internal object LilycoveCity_EventScript_AquaGrunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity.IfWorldBecomesOurs)
}

internal object LilycoveCity_EventScript_AquaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity.MovedLootIntoHideoutToday)
}

internal object LilycoveCity_EventScript_AquaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity.ChanceToDoBigThings)
}

internal object LilycoveCity_EventScript_AquaGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity.DontGoNearCaveInCove)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * goto_if_set FLAG_DAILY_LILYCOVE_RECEIVED_BERRY, LilycoveCity_EventScript_ReceivedBerry
 * msgbox LilycoveCity_Text_BerrySuitsYou, MSGBOX_DEFAULT
 * random 10
 * addvar VAR_RESULT, FIRST_BERRY_INDEX
 * giveitem VAR_RESULT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_DAILY_LILYCOVE_RECEIVED_BERRY
 * msgbox LilycoveCity_Text_BecauseYoureTrainer, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_EventScript_BerryGentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_EventScript_BerryGentleman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, LilycoveCity_EventScript_May
 * goto_if_eq VAR_RESULT, FEMALE, LilycoveCity_EventScript_Brendan
 * end
 * ```
 */
internal object LilycoveCity_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_Rival")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_Text_DoYouKnowAboutBerryBlender, MSGBOX_YESNO
 * call_if_eq VAR_RESULT, YES, LilycoveCity_EventScript_KnowAboutBerryBlender
 * call_if_eq VAR_RESULT, NO, LilycoveCity_EventScript_DontKnowAboutBerryBlender
 * release
 * end
 * ```
 */
internal object LilycoveCity_EventScript_SchoolKidM : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_SchoolKidM")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox LilycoveCity_Text_HoneymoonVowToSeeRarePokemon, MSGBOX_NPC
 * applymovement LOCALID_LILYCOVE_WOMAN_1, Common_Movement_FaceOriginalDirection
 * end
 * ```
 */
internal object LilycoveCity_EventScript_Woman1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_Woman1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox LilycoveCity_Text_JustArrivedAndSawRarePokemon, MSGBOX_NPC
 * applymovement LOCALID_LILYCOVE_MAN_1, Common_Movement_FaceOriginalDirection
 * end
 * ```
 */
internal object LilycoveCity_EventScript_Man1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_Man1")
}

internal object LilycoveCity_EventScript_Sailor1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity.HeardTowerCalledSkyPillar)
}

internal object LilycoveCity_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity.SawTallTowerOnRoute131)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * specialvar VAR_0x8004, CountPlayerMuseumPaintings
 * switch VAR_0x8004
 * case 0, LilycoveCity_EventScript_MuseumSignNoPaintings
 * msgbox LilycoveCity_Text_MuseumSignPlayersExhibit, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_EventScript_MuseumSign : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_MuseumSign")
}

internal object LilycoveCity_EventScript_DepartmentStoreSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LilycoveCity.DepartmentStoreSign)
}

internal object LilycoveCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LilycoveCity.CitySign)
}

internal object LilycoveCity_EventScript_ContestHallSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LilycoveCity.ContestHallSign)
}

internal object LilycoveCity_EventScript_MotelSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LilycoveCity.MotelSign)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_SYS_GAME_CLEAR, LilycoveCity_EventScript_HarborSignFerryReady
 * msgbox LilycoveCity_Text_HarborSignUnderConstruction, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_EventScript_HarborSign : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_EventScript_HarborSign")
}

internal object LilycoveCity_EventScript_TrainerFanClubSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LilycoveCity.TrainerFanClubSign)
}

internal object LilycoveCity_EventScript_MoveDeletersHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LilycoveCity.MoveDeletersHouseSign)
}

internal val LilycoveCityScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_EventScript_Sailor2" to LilycoveCity_EventScript_Sailor2,
        "LilycoveCity_EventScript_Girl" to LilycoveCity_EventScript_Girl,
        "LilycoveCity_EventScript_Man3" to LilycoveCity_EventScript_Man3,
        "LilycoveCity_EventScript_RichBoy" to LilycoveCity_EventScript_RichBoy,
        "LilycoveCity_EventScript_Man2" to LilycoveCity_EventScript_Man2,
        "LilycoveCity_EventScript_Woman2" to LilycoveCity_EventScript_Woman2,
        "LilycoveCity_EventScript_ExpertM1" to LilycoveCity_EventScript_ExpertM1,
        "LilycoveCity_EventScript_ExpertF" to LilycoveCity_EventScript_ExpertF,
        "LilycoveCity_EventScript_ExpertM2" to LilycoveCity_EventScript_ExpertM2,
        "LilycoveCity_EventScript_WailmerTrainerGrunt" to
            LilycoveCity_EventScript_WailmerTrainerGrunt,
        "LilycoveCity_EventScript_ItemMaxRepel" to LilycoveCity_EventScript_ItemMaxRepel,
        "LilycoveCity_EventScript_AquaGrunt4" to LilycoveCity_EventScript_AquaGrunt4,
        "LilycoveCity_EventScript_AquaGrunt1" to LilycoveCity_EventScript_AquaGrunt1,
        "LilycoveCity_EventScript_AquaGrunt2" to LilycoveCity_EventScript_AquaGrunt2,
        "LilycoveCity_EventScript_AquaGrunt3" to LilycoveCity_EventScript_AquaGrunt3,
        "LilycoveCity_EventScript_BerryGentleman" to LilycoveCity_EventScript_BerryGentleman,
        "LilycoveCity_EventScript_Rival" to LilycoveCity_EventScript_Rival,
        "LilycoveCity_EventScript_SchoolKidM" to LilycoveCity_EventScript_SchoolKidM,
        "LilycoveCity_EventScript_Woman1" to LilycoveCity_EventScript_Woman1,
        "LilycoveCity_EventScript_Man1" to LilycoveCity_EventScript_Man1,
        "LilycoveCity_EventScript_Sailor1" to LilycoveCity_EventScript_Sailor1,
        "LilycoveCity_EventScript_FatMan" to LilycoveCity_EventScript_FatMan,
        "LilycoveCity_EventScript_MuseumSign" to LilycoveCity_EventScript_MuseumSign,
        "LilycoveCity_EventScript_DepartmentStoreSign" to
            LilycoveCity_EventScript_DepartmentStoreSign,
        "LilycoveCity_EventScript_CitySign" to LilycoveCity_EventScript_CitySign,
        "LilycoveCity_EventScript_ContestHallSign" to LilycoveCity_EventScript_ContestHallSign,
        "LilycoveCity_EventScript_MotelSign" to LilycoveCity_EventScript_MotelSign,
        "LilycoveCity_EventScript_HarborSign" to LilycoveCity_EventScript_HarborSign,
        "LilycoveCity_EventScript_TrainerFanClubSign" to
            LilycoveCity_EventScript_TrainerFanClubSign,
        "LilycoveCity_EventScript_MoveDeletersHouseSign" to
            LilycoveCity_EventScript_MoveDeletersHouseSign,
    )
