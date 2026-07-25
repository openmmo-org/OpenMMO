package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity_PokemonFanClub
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SlateportCity_PokemonFanClub_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_PokemonFanClub.PokemonDontLikeFainting)
}

internal object SlateportCity_PokemonFanClub_EventScript_Twin : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_PokemonFanClub.MonEnjoyedProtein)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * specialvar VAR_RESULT, IsLeadMonNicknamedOrNotEnglish
 * goto_if_eq VAR_RESULT, FALSE, SlateportCity_PokemonFanClub_EventScript_ReporterNoNickname
 * setvar VAR_0x8005, TVSHOW_PKMN_FAN_CLUB_OPINIONS
 * special InterviewBefore
 * goto_if_eq VAR_RESULT, TRUE, SlateportCity_PokemonFanClub_EventScript_AlreadyInterviewed
 * copyvar VAR_0x8009, VAR_0x8006
 * msgbox SlateportCity_PokemonFanClub_Text_InterviewRequestHasName, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, SlateportCity_PokemonFanClub_EventScript_AcceptInterview
 * goto_if_eq VAR_RESULT, NO, SlateportCity_PokemonFanClub_EventScript_DeclineInterview
 * end
 * ```
 */
internal object SlateportCity_PokemonFanClub_EventScript_Reporter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_PokemonFanClub_EventScript_Reporter")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_SOOTHE_BELL, SlateportCity_PokemonFanClub_EventScript_ReceivedSootheBell
 * msgbox SlateportCity_PokemonFanClub_Text_ShowMePokemonThatLoveYou, MSGBOX_DEFAULT
 * specialvar VAR_RESULT, GetLeadMonFriendshipScore
 * goto_if_ge VAR_RESULT, FRIENDSHIP_150_TO_199, SlateportCity_PokemonFanClub_EventScript_GiveSootheBell
 * release
 * end
 * ```
 */
internal object SlateportCity_PokemonFanClub_EventScript_SootheBellWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_PokemonFanClub_EventScript_SootheBellWoman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_ENTERED_CONTEST, SlateportCity_PokemonFanClub_EventScript_ChairmanNotEnteredContest
 * call_if_unset FLAG_MET_SLATEPORT_FANCLUB_CHAIRMAN, SlateportCity_PokemonFanClub_EventScript_MeetChairman
 * switch VAR_SLATEPORT_FAN_CLUB_STATE
 * case 0, SlateportCity_PokemonFanClub_EventScript_ChairmanFirstAssessment
 * case 1, SlateportCity_PokemonFanClub_EventScript_ChairmanTryAssessPokemon
 * case 2, SlateportCity_PokemonFanClub_EventScript_NoMoreScarves
 * release
 * end
 * ```
 */
internal object SlateportCity_PokemonFanClub_EventScript_Chairman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_PokemonFanClub_EventScript_Chairman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_ZIGZAGOON, CRY_MODE_NORMAL
 * msgbox SlateportCity_PokemonFanClub_Text_Zigzagoon, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SlateportCity_PokemonFanClub_EventScript_Zigzagoon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_PokemonFanClub_EventScript_Zigzagoon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_SKITTY, CRY_MODE_NORMAL
 * msgbox SlateportCity_PokemonFanClub_Text_Skitty, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SlateportCity_PokemonFanClub_EventScript_Skitty : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_PokemonFanClub_EventScript_Skitty")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_AZUMARILL, CRY_MODE_NORMAL
 * msgbox SlateportCity_PokemonFanClub_Text_Azumarill, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SlateportCity_PokemonFanClub_EventScript_Azumarill : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_PokemonFanClub_EventScript_Azumarill")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MOVE_TUTOR_TAUGHT_SWAGGER, MoveTutor_EventScript_SwaggerTaught
 * msgbox MoveTutor_Text_SwaggerTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_SwaggerDeclined
 * call MoveTutor_EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_SwaggerDeclined
 * msgbox MoveTutor_Text_SwaggerWhichMon, MSGBOX_DEFAULT
 * setvar VAR_0x8005, TUTOR_MOVE_SWAGGER
 * call MoveTutor_EventScript_OpenPartyMenu
 * goto_if_eq VAR_RESULT, 0, MoveTutor_EventScript_SwaggerDeclined
 * setflag FLAG_MOVE_TUTOR_TAUGHT_SWAGGER
 * goto MoveTutor_EventScript_SwaggerTaught
 * end
 * ```
 */
internal object SlateportCity_PokemonFanClub_EventScript_SwaggerTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_PokemonFanClub_EventScript_SwaggerTutor")
}

internal val SlateportCity_PokemonFanClubScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_PokemonFanClub_EventScript_Man" to
            SlateportCity_PokemonFanClub_EventScript_Man,
        "SlateportCity_PokemonFanClub_EventScript_Twin" to
            SlateportCity_PokemonFanClub_EventScript_Twin,
        "SlateportCity_PokemonFanClub_EventScript_Reporter" to
            SlateportCity_PokemonFanClub_EventScript_Reporter,
        "SlateportCity_PokemonFanClub_EventScript_SootheBellWoman" to
            SlateportCity_PokemonFanClub_EventScript_SootheBellWoman,
        "SlateportCity_PokemonFanClub_EventScript_Chairman" to
            SlateportCity_PokemonFanClub_EventScript_Chairman,
        "SlateportCity_PokemonFanClub_EventScript_Zigzagoon" to
            SlateportCity_PokemonFanClub_EventScript_Zigzagoon,
        "SlateportCity_PokemonFanClub_EventScript_Skitty" to
            SlateportCity_PokemonFanClub_EventScript_Skitty,
        "SlateportCity_PokemonFanClub_EventScript_Azumarill" to
            SlateportCity_PokemonFanClub_EventScript_Azumarill,
        "SlateportCity_PokemonFanClub_EventScript_SwaggerTutor" to
            SlateportCity_PokemonFanClub_EventScript_SwaggerTutor,
    )
