package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * trainerhill_inchallenge
 * goto_if_eq VAR_RESULT, FALSE, TrainerHill_Entrance_EventScript_ThanksForPlaying
 * msgbox TrainerHill_Entrance_Text_HopeYouGiveItYourBest, MSGBOX_DEFAULT
 * goto TrainerHill_Entrance_EventScript_AttendantEnd
 * ```
 */
internal object TrainerHill_Entrance_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TrainerHill_Entrance_EventScript_Attendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_TRAINER_HILL_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object TrainerHill_Entrance_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TrainerHill_Entrance_EventScript_Nurse")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * goto_if_set FLAG_SYS_GAME_CLEAR, TrainerHill_Entrance_EventScript_ExpandedPokemart
 * pokemart TrainerHill_Entrance_Pokemart_Basic
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object TrainerHill_Entrance_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TrainerHill_Entrance_EventScript_Clerk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_unset FLAG_SYS_GAME_CLEAR, TrainerHill_Entrance_EventScript_GirlTrainerHillClosed
 * msgbox TrainerHill_Entrance_Text_FriendsTryingToReachTimeBoardTop, MSGBOX_NPC
 * end
 * ```
 */
internal object TrainerHill_Entrance_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TrainerHill_Entrance_EventScript_Girl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_unset FLAG_SYS_GAME_CLEAR, TrainerHill_Entrance_EventScript_ManTrainerHillClosed
 * msgbox TrainerHill_Entrance_Text_WhatSortOfTrainersAreAhead, MSGBOX_NPC
 * end
 * ```
 */
internal object TrainerHill_Entrance_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TrainerHill_Entrance_EventScript_Man")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * fadescreen FADE_TO_BLACK
 * special ShowTrainerHillRecords
 * releaseall
 * end
 * ```
 */
internal object TrainerHill_Entrance_EventScript_Records : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TrainerHill_Entrance_EventScript_Records")
}

internal val TrainerHill_EntranceScripts: Map<String, Script> =
    mapOf(
        "TrainerHill_Entrance_EventScript_Attendant" to TrainerHill_Entrance_EventScript_Attendant,
        "TrainerHill_Entrance_EventScript_Nurse" to TrainerHill_Entrance_EventScript_Nurse,
        "TrainerHill_Entrance_EventScript_Clerk" to TrainerHill_Entrance_EventScript_Clerk,
        "TrainerHill_Entrance_EventScript_Girl" to TrainerHill_Entrance_EventScript_Girl,
        "TrainerHill_Entrance_EventScript_Man" to TrainerHill_Entrance_EventScript_Man,
        "TrainerHill_Entrance_EventScript_Records" to TrainerHill_Entrance_EventScript_Records,
    )
