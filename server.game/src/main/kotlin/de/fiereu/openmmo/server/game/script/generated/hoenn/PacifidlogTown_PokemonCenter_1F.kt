package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PacifidlogTown_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_PACIFIDLOG_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object PacifidlogTown_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PacifidlogTown_PokemonCenter_1F_EventScript_Nurse")
}

internal object PacifidlogTown_PokemonCenter_1F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PacifidlogTown_PokemonCenter_1F.AncestorsLivedOnBoats)
}

internal object PacifidlogTown_PokemonCenter_1F_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PacifidlogTown_PokemonCenter_1F.WhatColorTrainerCard)
}

internal object PacifidlogTown_PokemonCenter_1F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PacifidlogTown_PokemonCenter_1F.OnColonyOfCorsola)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MOVE_TUTOR_TAUGHT_EXPLOSION, MoveTutor_EventScript_ExplosionTaught
 * msgbox MoveTutor_Text_ExplosionTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_ExplosionDeclined
 * call MoveTutor_EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_ExplosionDeclined
 * msgbox MoveTutor_Text_ExplosionWhichMon, MSGBOX_DEFAULT
 * setvar VAR_0x8005, TUTOR_MOVE_EXPLOSION
 * call MoveTutor_EventScript_OpenPartyMenu
 * goto_if_eq VAR_RESULT, 0, MoveTutor_EventScript_ExplosionDeclined
 * setflag FLAG_MOVE_TUTOR_TAUGHT_EXPLOSION
 * goto MoveTutor_EventScript_ExplosionTaught
 * end
 * ```
 */
internal object PacifidlogTown_PokemonCenter_1F_EventScript_ExplosionTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PacifidlogTown_PokemonCenter_1F_EventScript_ExplosionTutor")
}

internal val PacifidlogTown_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "PacifidlogTown_PokemonCenter_1F_EventScript_Nurse" to
            PacifidlogTown_PokemonCenter_1F_EventScript_Nurse,
        "PacifidlogTown_PokemonCenter_1F_EventScript_OldMan" to
            PacifidlogTown_PokemonCenter_1F_EventScript_OldMan,
        "PacifidlogTown_PokemonCenter_1F_EventScript_Girl" to
            PacifidlogTown_PokemonCenter_1F_EventScript_Girl,
        "PacifidlogTown_PokemonCenter_1F_EventScript_Woman" to
            PacifidlogTown_PokemonCenter_1F_EventScript_Woman,
        "PacifidlogTown_PokemonCenter_1F_EventScript_ExplosionTutor" to
            PacifidlogTown_PokemonCenter_1F_EventScript_ExplosionTutor,
    )
