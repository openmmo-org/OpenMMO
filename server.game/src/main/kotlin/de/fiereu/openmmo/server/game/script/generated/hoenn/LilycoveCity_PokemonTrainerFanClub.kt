package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER1
 * special BufferFanClubTrainerName
 * goto_if_eq VAR_LILYCOVE_FAN_CLUB_STATE, 0, LilycoveCity_PokemonTrainerFanClub_EventScript_LassPlayerNotChampion
 * specialvar VAR_RESULT, IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_PokemonTrainerFanClub_EventScript_LassPlayersFan
 * specialvar VAR_RESULT, GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), LilycoveCity_PokemonTrainerFanClub_EventScript_LassOnlyNonFan
 * msgbox LilycoveCity_PokemonTrainerFanClub_Text_ICantHelpLikingBrawly, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonTrainerFanClub_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonTrainerFanClub_EventScript_Lass")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER6
 * special BufferFanClubTrainerName
 * goto_if_eq VAR_LILYCOVE_FAN_CLUB_STATE, 0, LilycoveCity_PokemonTrainerFanClub_EventScript_ManPlayerNotChampion
 * specialvar VAR_RESULT, IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_PokemonTrainerFanClub_EventScript_ManPlayersFan
 * specialvar VAR_RESULT, GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), LilycoveCity_PokemonTrainerFanClub_EventScript_ManOnlyNonFan
 * msgbox LilycoveCity_PokemonTrainerFanClub_Text_TrainersPowerIsOutOfTheOrdinary, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonTrainerFanClub_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonTrainerFanClub_EventScript_Man")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER2
 * special BufferFanClubTrainerName
 * goto_if_eq VAR_LILYCOVE_FAN_CLUB_STATE, 0, LilycoveCity_PokemonTrainerFanClub_EventScript_PokefanMPlayerNotChampion
 * specialvar VAR_RESULT, IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_PokemonTrainerFanClub_EventScript_PokefanMPlayersFan
 * specialvar VAR_RESULT, GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), LilycoveCity_PokemonTrainerFanClub_EventScript_PokefanMOnlyNonFan
 * msgbox LilycoveCity_PokemonTrainerFanClub_Text_LongWayToGoComparedToNorman, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonTrainerFanClub_EventScript_PokefanM : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonTrainerFanClub_EventScript_PokefanM")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER3
 * special BufferFanClubTrainerName
 * goto_if_eq VAR_LILYCOVE_FAN_CLUB_STATE, 0, LilycoveCity_PokemonTrainerFanClub_EventScript_LittleGirlPlayerNotChampion
 * specialvar VAR_RESULT, IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_PokemonTrainerFanClub_EventScript_LittleGirlPlayersFan
 * specialvar VAR_RESULT, GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), LilycoveCity_PokemonTrainerFanClub_EventScript_LittleGirlOnlyNonFan
 * msgbox LilycoveCity_PokemonTrainerFanClub_Text_EveryoneThinksTrainerIsCool, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonTrainerFanClub_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonTrainerFanClub_EventScript_LittleGirl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER4
 * special BufferFanClubTrainerName
 * specialvar VAR_RESULT, IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_PokemonTrainerFanClub_EventScript_NinjaBoyPlayersFan
 * specialvar VAR_RESULT, GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), LilycoveCity_PokemonTrainerFanClub_EventScript_NinjaBoyOnlyNonFan
 * msgbox LilycoveCity_PokemonTrainerFanClub_Text_TrainerIsWickedlyCool, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonTrainerFanClub_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonTrainerFanClub_EventScript_NinjaBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER5
 * special BufferFanClubTrainerName
 * specialvar VAR_RESULT, IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_PokemonTrainerFanClub_EventScript_BoyPlayersFan
 * specialvar VAR_RESULT, GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), LilycoveCity_PokemonTrainerFanClub_EventScript_BoyOnlyNonFan
 * msgbox LilycoveCity_PokemonTrainerFanClub_Text_ThinkTrainerIsNumberOne, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonTrainerFanClub_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonTrainerFanClub_EventScript_Boy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER7
 * special BufferFanClubTrainerName
 * specialvar VAR_RESULT, IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_PokemonTrainerFanClub_EventScript_WomanPlayersFan
 * specialvar VAR_RESULT, GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), LilycoveCity_PokemonTrainerFanClub_EventScript_WomanOnlyNonFan
 * msgbox LilycoveCity_PokemonTrainerFanClub_Text_TrainerIsStandout, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonTrainerFanClub_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonTrainerFanClub_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER8
 * special BufferFanClubTrainerName
 * specialvar VAR_RESULT, IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_PokemonTrainerFanClub_EventScript_ExpertFPlayersFan
 * specialvar VAR_RESULT, GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), LilycoveCity_PokemonTrainerFanClub_EventScript_ExpertFOnlyNonFan
 * msgbox LilycoveCity_PokemonTrainerFanClub_Text_HaventRealizedPotential, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonTrainerFanClub_EventScript_ExpertF : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonTrainerFanClub_EventScript_ExpertF")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_FAN_CLUB_STRENGTH_SHARED, LilycoveCity_PokemonTrainerFanClub_EventScript_AlreadyInterviewed
 * goto_if_unset FLAG_FAN_CLUB_STRENGTH_SHARED, LilycoveCity_PokemonTrainerFanClub_EventScript_Interview
 * end
 * ```
 */
internal object LilycoveCity_PokemonTrainerFanClub_EventScript_Interviewer : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonTrainerFanClub_EventScript_Interviewer")
}

internal val LilycoveCity_PokemonTrainerFanClubScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_PokemonTrainerFanClub_EventScript_Lass" to
            LilycoveCity_PokemonTrainerFanClub_EventScript_Lass,
        "LilycoveCity_PokemonTrainerFanClub_EventScript_Man" to
            LilycoveCity_PokemonTrainerFanClub_EventScript_Man,
        "LilycoveCity_PokemonTrainerFanClub_EventScript_PokefanM" to
            LilycoveCity_PokemonTrainerFanClub_EventScript_PokefanM,
        "LilycoveCity_PokemonTrainerFanClub_EventScript_LittleGirl" to
            LilycoveCity_PokemonTrainerFanClub_EventScript_LittleGirl,
        "LilycoveCity_PokemonTrainerFanClub_EventScript_NinjaBoy" to
            LilycoveCity_PokemonTrainerFanClub_EventScript_NinjaBoy,
        "LilycoveCity_PokemonTrainerFanClub_EventScript_Boy" to
            LilycoveCity_PokemonTrainerFanClub_EventScript_Boy,
        "LilycoveCity_PokemonTrainerFanClub_EventScript_Woman" to
            LilycoveCity_PokemonTrainerFanClub_EventScript_Woman,
        "LilycoveCity_PokemonTrainerFanClub_EventScript_ExpertF" to
            LilycoveCity_PokemonTrainerFanClub_EventScript_ExpertF,
        "LilycoveCity_PokemonTrainerFanClub_EventScript_Interviewer" to
            LilycoveCity_PokemonTrainerFanClub_EventScript_Interviewer,
    )
