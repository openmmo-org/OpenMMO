package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * call PacifidlogTown_House2_EventScript_UpdateFanClubTMFlag
 * goto_if_set FLAG_RECEIVED_FANCLUB_TM_THIS_WEEK, PacifidlogTown_House2_EventScript_ComeBackInXDays
 * call_if_set FLAG_MET_FANCLUB_YOUNGER_BROTHER, PacifidlogTown_House2_EventScript_MonAssessment
 * call_if_unset FLAG_MET_FANCLUB_YOUNGER_BROTHER, PacifidlogTown_House2_EventScript_FirstMonAssessment
 * setflag FLAG_MET_FANCLUB_YOUNGER_BROTHER
 * specialvar VAR_RESULT, GetLeadMonFriendshipScore
 * goto_if_ge VAR_RESULT, FRIENDSHIP_150_TO_199, PacifidlogTown_House2_EventScript_GiveReturn
 * specialvar VAR_RESULT, GetLeadMonFriendshipScore
 * goto_if_ge VAR_RESULT, FRIENDSHIP_50_TO_99, PacifidlogTown_House2_EventScript_PutInEffort
 * goto PacifidlogTown_House2_EventScript_GiveFrustration
 * end
 * ```
 */
internal object PacifidlogTown_House2_EventScript_FanClubYoungerBrother : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PacifidlogTown_House2_EventScript_FanClubYoungerBrother")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_AZURILL, CRY_MODE_NORMAL
 * msgbox PacifidlogTown_House2_Text_Rurii, MSGBOX_DEFAULT
 * waitmoncry
 * msgbox PacifidlogTown_House2_Text_VeryFriendlyWithTrainer, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object PacifidlogTown_House2_EventScript_HappyAzurill : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PacifidlogTown_House2_EventScript_HappyAzurill")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_AZURILL, CRY_MODE_ENCOUNTER
 * msgbox PacifidlogTown_House2_Text_Rururi, MSGBOX_DEFAULT
 * waitmoncry
 * msgbox PacifidlogTown_House2_Text_DoesntLikeTrainerVeryMuch, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object PacifidlogTown_House2_EventScript_UnhappyAzurill : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PacifidlogTown_House2_EventScript_UnhappyAzurill")
}

internal val PacifidlogTown_House2Scripts: Map<String, Script> =
    mapOf(
        "PacifidlogTown_House2_EventScript_FanClubYoungerBrother" to
            PacifidlogTown_House2_EventScript_FanClubYoungerBrother,
        "PacifidlogTown_House2_EventScript_HappyAzurill" to
            PacifidlogTown_House2_EventScript_HappyAzurill,
        "PacifidlogTown_House2_EventScript_UnhappyAzurill" to
            PacifidlogTown_House2_EventScript_UnhappyAzurill,
    )
