package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.VerdanturfTown_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_VERDANTURF_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object VerdanturfTown_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_PokemonCenter_1F_EventScript_Nurse")
}

internal object VerdanturfTown_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VerdanturfTown_PokemonCenter_1F.FaithInYourPokemon)
}

internal object VerdanturfTown_PokemonCenter_1F_EventScript_ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VerdanturfTown_PokemonCenter_1F.VisitForBattleTent)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MOVE_TUTOR_TAUGHT_FURY_CUTTER, MoveTutor_EventScript_FuryCutterTaught
 * msgbox MoveTutor_Text_FuryCutterTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_FuryCutterDeclined
 * call MoveTutor_EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_FuryCutterDeclined
 * msgbox MoveTutor_Text_FuryCutterWhichMon, MSGBOX_DEFAULT
 * setvar VAR_0x8005, TUTOR_MOVE_FURY_CUTTER
 * call MoveTutor_EventScript_OpenPartyMenu
 * goto_if_eq VAR_RESULT, 0, MoveTutor_EventScript_FuryCutterDeclined
 * setflag FLAG_MOVE_TUTOR_TAUGHT_FURY_CUTTER
 * goto MoveTutor_EventScript_FuryCutterTaught
 * end
 * ```
 */
internal object VerdanturfTown_PokemonCenter_1F_EventScript_FuryCutterTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_PokemonCenter_1F_EventScript_FuryCutterTutor")
}

internal val VerdanturfTown_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "VerdanturfTown_PokemonCenter_1F_EventScript_Nurse" to
            VerdanturfTown_PokemonCenter_1F_EventScript_Nurse,
        "VerdanturfTown_PokemonCenter_1F_EventScript_Gentleman" to
            VerdanturfTown_PokemonCenter_1F_EventScript_Gentleman,
        "VerdanturfTown_PokemonCenter_1F_EventScript_ExpertM" to
            VerdanturfTown_PokemonCenter_1F_EventScript_ExpertM,
        "VerdanturfTown_PokemonCenter_1F_EventScript_FuryCutterTutor" to
            VerdanturfTown_PokemonCenter_1F_EventScript_FuryCutterTutor,
    )
