package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BerryBlender
import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_ContestLobby
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * special ClearLinkContestFlags
 * specialvar VAR_RESULT, IsContestDebugActive  @ Always FALSE
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_ContestLobby_EventScript_SetDebug
 * call LilycoveCity_ContestLobby_EventScript_SpeakToContestReceptionist
 * call LilycoveCity_ContestLobby_EventScript_LeadToContestHall
 * special SetContestTrainerGfxIds
 * call LilycoveCity_ContestLobby_EventScript_SetPlayerGfx
 * call LilycoveCity_ContestLobby_EventScript_SetContestType
 * call LilycoveCity_ContestLobby_EventScript_WarpToContestHall
 * waitstate
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_ContestReceptionist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_ContestReceptionist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * special ClearLinkContestFlags
 * lock
 * faceplayer
 * msgbox LilycoveCity_ContestLobby_Text_LinkContestReception, MSGBOX_DEFAULT
 * goto LilycoveCity_ContestLobby_EventScript_AskEnterLinkContest
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_LinkContestReceptionist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_LinkContestReceptionist")
}

internal object BerryBlender_EventScript_Blender2Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BerryBlender.SetNewBlenderRecord)
}

internal object LilycoveCity_ContestLobby_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestLobby.MasterRankHereICome)
}

internal object LilycoveCity_ContestLobby_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestLobby.WholeVarietyOfPokemonHere)
}

internal object LilycoveCity_ContestLobby_EventScript_Artist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestLobby.ContestFeastForEyes)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TEMP_2, LilycoveCity_ContestLobby_EventScript_AlreadyInterviewed
 * setvar VAR_0x8005, TVSHOW_BRAVO_TRAINER_POKEMON_PROFILE
 * special InterviewBefore
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_ContestLobby_EventScript_AlreadyInterviewed
 * copyvar VAR_0x8009, VAR_0x8006
 * msgbox LilycoveCity_ContestLobby_Text_InterviewRequest, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, LilycoveCity_ContestLobby_EventScript_AcceptInterview
 * goto_if_eq VAR_RESULT, NO, LilycoveCity_ContestLobby_EventScript_DeclineInterview
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_Reporter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_Reporter")
}

internal object LilycoveCity_ContestLobby_EventScript_Blender3Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BerryBlender.LetsGetBlendingAlready)
}

internal object BerryBlender_EventScript_Blender2Twin : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BerryBlender.MakeDeliciousPokeblocks)
}

internal object LilycoveCity_ContestLobby_EventScript_Fisherman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestLobby.LavishedCareOnMon)
}

internal object LilycoveCity_ContestLobby_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestLobby.ToughContestIsExtreme)
}

internal object LilycoveCity_ContestLobby_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_ContestLobby.MadePokeblocksWithFamily)
}

internal object BerryBlender_EventScript_Blender3PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BerryBlender.LookGoodAtBlendingJoinUs)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x8008, 15
 * goto BerryBlender_EventScript_ExpertMCheckGiveBerry
 * end
 * ```
 */
internal object BerryBlender_EventScript_Blender1ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BerryBlender_EventScript_Blender1ExpertM")
}

internal object LilycoveCity_ContestLobby_EventScript_Blender3Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BerryBlender.WhatKindOfPokeblockWillIGet)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * msgbox BerryBlender_Text_WickedlyFast, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * msgbox BerryBlender_Text_WhatAnExpert, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BerryBlender_Text_MadeAmazingPokeblocksWithMaster, MSGBOX_DEFAULT
 * goto LilycoveCity_ContestLobby_EventScript_FaceOriginalDirection
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BerryBlender_Text_QualitiesOfBlendMaster, MSGBOX_DEFAULT
 * goto LilycoveCity_ContestLobby_EventScript_FaceOriginalDirection
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BerryBlender_Text_MasterWorksOnSkillsInMountains, MSGBOX_DEFAULT
 * goto LilycoveCity_ContestLobby_EventScript_FaceOriginalDirection
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker6 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * msgbox BerryBlender_Text_WhoaAwesome, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BerryBlender_Text_BlendWithTheBlendMaster, MSGBOX_DEFAULT
 * goto LilycoveCity_ContestLobby_EventScript_FaceOriginalDirection
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_BlendMaster : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_BlendMaster")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_POKEBLOCK_CASE, LilycoveCity_ContestLobby_EventScript_LittleGirlHaveCase
 * msgbox LilycoveCity_ContestLobby_Text_LadyGaveMePokeblockCase, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_LittleGirl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * specialvar VAR_RESULT, PlayerHasBerries
 * goto_if_eq VAR_RESULT, FALSE, BerryBlender_EventScript_LinkBlenderNoBerries
 * checkitem ITEM_POKEBLOCK_CASE
 * goto_if_eq VAR_RESULT, FALSE, BerryBlender_EventScript_LinkBlenderNoCase
 * specialvar VAR_RESULT, GetFirstFreePokeblockSlot
 * goto_if_ne VAR_RESULT, 65535, BerryBlender_EventScript_LinkBlenderSaveGame
 * goto_if_eq VAR_RESULT, 65535, BerryBlender_EventScript_LinkBlenderCaseFull
 * end
 * ```
 */
internal object BerryBlender_EventScript_BerryBlenderLink : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BerryBlender_EventScript_BerryBlenderLink")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8008, LOCALID_CONTEST_LOBBY_BLENDER_3_LEADER
 * setvar NUM_OPPONENTS, 3
 * applymovement LOCALID_CONTEST_LOBBY_BLENDER_3_PARTICIPANT_1, Common_Movement_FaceOriginalDirection
 * applymovement LOCALID_CONTEST_LOBBY_BLENDER_3_PARTICIPANT_2, Common_Movement_FaceOriginalDirection
 * applymovement VAR_0x8008, BerryBlender_Movement_BlendLeaderWalkInPlace
 * waitmovement 0
 * msgbox BerryBlender_Text_LookGoodAtBlendingJoinUs, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, BerryBlender_EventScript_TryUseBlender3
 * goto BerryBlender_EventScript_DeclineBlender3
 * end
 * ```
 */
internal object BerryBlender_EventScript_BerryBlender3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port BerryBlender_EventScript_BerryBlender3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * fadescreen FADE_TO_BLACK
 * showcontestpainting CONTEST_WINNER_HALL_4
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_ContestWinner4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_ContestWinner4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * fadescreen FADE_TO_BLACK
 * showcontestpainting CONTEST_WINNER_HALL_5
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_ContestWinner5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_ContestWinner5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * fadescreen FADE_TO_BLACK
 * showcontestpainting CONTEST_WINNER_HALL_6
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_ContestWinner6 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_ContestWinner6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * special ShowBerryBlenderRecordWindow
 * waitbuttonpress
 * special RemoveRecordsWindow
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_BerryBlenderSpeedRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_BerryBlenderSpeedRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * fadescreen FADE_TO_BLACK
 * showcontestpainting CONTEST_WINNER_HALL_1
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_ContestWinner1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_ContestWinner1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * fadescreen FADE_TO_BLACK
 * showcontestpainting CONTEST_WINNER_HALL_2
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_ContestWinner2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_ContestWinner2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * fadescreen FADE_TO_BLACK
 * showcontestpainting CONTEST_WINNER_HALL_3
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_ContestWinner3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_ContestWinner3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_unset FLAG_HIDE_LILYCOVE_CONTEST_HALL_BLEND_MASTER, BerryBlender_EventScript_BlendMasterPresent
 * setvar NUM_OPPONENTS, 1
 * applymovement LOCALID_BLEND_MASTER, BerryBlender_Movement_BlendLeaderWalkInPlace
 * waitmovement 0
 * msgbox BerryBlender_Text_WantToMakePokeblocks, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, BerryBlender_EventScript_TryUseBerryBlender1
 * goto BerryBlender_EventScript_DeclineBlender1
 * end
 * ```
 */
internal object BerryBlender_EventScript_BerryBlender1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port BerryBlender_EventScript_BerryBlender1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar NUM_OPPONENTS, 2
 * applymovement LOCALID_CONTEST_LOBBY_BLENDER_2_PARTICIPANT, Common_Movement_FaceOriginalDirection
 * applymovement LOCALID_CONTEST_LOBBY_BLENDER_2_LEADER, BerryBlender_Movement_BlendLeaderWalkInPlace
 * waitmovement 0
 * msgbox BerryBlender_Text_WantToBlendPokeblocksWithUs, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, BerryBlender_EventScript_TryUseBerryBlender2
 * goto BerryBlender_EventScript_DeclineBlender2
 * end
 * ```
 */
internal object BerryBlender_EventScript_BerryBlender2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port BerryBlender_EventScript_BerryBlender2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * frontier_results FACILITY_LINK_CONTEST
 * waitbuttonpress
 * special RemoveRecordsWindow
 * releaseall
 * end
 * ```
 */
internal object LilycoveCity_ContestLobby_EventScript_LinkContestResults : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_ContestLobby_EventScript_LinkContestResults")
}

internal val LilycoveCity_ContestLobbyScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_ContestLobby_EventScript_ContestReceptionist" to
            LilycoveCity_ContestLobby_EventScript_ContestReceptionist,
        "LilycoveCity_ContestLobby_EventScript_LinkContestReceptionist" to
            LilycoveCity_ContestLobby_EventScript_LinkContestReceptionist,
        "BerryBlender_EventScript_Blender2Man" to BerryBlender_EventScript_Blender2Man,
        "LilycoveCity_ContestLobby_EventScript_BlackBelt" to
            LilycoveCity_ContestLobby_EventScript_BlackBelt,
        "LilycoveCity_ContestLobby_EventScript_Girl" to LilycoveCity_ContestLobby_EventScript_Girl,
        "LilycoveCity_ContestLobby_EventScript_Artist" to
            LilycoveCity_ContestLobby_EventScript_Artist,
        "LilycoveCity_ContestLobby_EventScript_Reporter" to
            LilycoveCity_ContestLobby_EventScript_Reporter,
        "LilycoveCity_ContestLobby_EventScript_Blender3Boy" to
            LilycoveCity_ContestLobby_EventScript_Blender3Boy,
        "BerryBlender_EventScript_Blender2Twin" to BerryBlender_EventScript_Blender2Twin,
        "LilycoveCity_ContestLobby_EventScript_Fisherman" to
            LilycoveCity_ContestLobby_EventScript_Fisherman,
        "LilycoveCity_ContestLobby_EventScript_FatMan" to
            LilycoveCity_ContestLobby_EventScript_FatMan,
        "LilycoveCity_ContestLobby_EventScript_NinjaBoy" to
            LilycoveCity_ContestLobby_EventScript_NinjaBoy,
        "BerryBlender_EventScript_Blender3PokefanF" to BerryBlender_EventScript_Blender3PokefanF,
        "BerryBlender_EventScript_Blender1ExpertM" to BerryBlender_EventScript_Blender1ExpertM,
        "LilycoveCity_ContestLobby_EventScript_Blender3Girl" to
            LilycoveCity_ContestLobby_EventScript_Blender3Girl,
        "LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker2" to
            LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker2,
        "LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker3" to
            LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker3,
        "LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker4" to
            LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker4,
        "LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker5" to
            LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker5,
        "LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker6" to
            LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker6,
        "LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker1" to
            LilycoveCity_ContestLobby_EventScript_BlendMasterOnlooker1,
        "LilycoveCity_ContestLobby_EventScript_BlendMaster" to
            LilycoveCity_ContestLobby_EventScript_BlendMaster,
        "LilycoveCity_ContestLobby_EventScript_LittleGirl" to
            LilycoveCity_ContestLobby_EventScript_LittleGirl,
        "BerryBlender_EventScript_BerryBlenderLink" to BerryBlender_EventScript_BerryBlenderLink,
        "BerryBlender_EventScript_BerryBlender3" to BerryBlender_EventScript_BerryBlender3,
        "LilycoveCity_ContestLobby_EventScript_ContestWinner4" to
            LilycoveCity_ContestLobby_EventScript_ContestWinner4,
        "LilycoveCity_ContestLobby_EventScript_ContestWinner5" to
            LilycoveCity_ContestLobby_EventScript_ContestWinner5,
        "LilycoveCity_ContestLobby_EventScript_ContestWinner6" to
            LilycoveCity_ContestLobby_EventScript_ContestWinner6,
        "LilycoveCity_ContestLobby_EventScript_BerryBlenderSpeedRecords" to
            LilycoveCity_ContestLobby_EventScript_BerryBlenderSpeedRecords,
        "LilycoveCity_ContestLobby_EventScript_ContestWinner1" to
            LilycoveCity_ContestLobby_EventScript_ContestWinner1,
        "LilycoveCity_ContestLobby_EventScript_ContestWinner2" to
            LilycoveCity_ContestLobby_EventScript_ContestWinner2,
        "LilycoveCity_ContestLobby_EventScript_ContestWinner3" to
            LilycoveCity_ContestLobby_EventScript_ContestWinner3,
        "BerryBlender_EventScript_BerryBlender1" to BerryBlender_EventScript_BerryBlender1,
        "BerryBlender_EventScript_BerryBlender2" to BerryBlender_EventScript_BerryBlender2,
        "LilycoveCity_ContestLobby_EventScript_LinkContestResults" to
            LilycoveCity_ContestLobby_EventScript_LinkContestResults,
    )
