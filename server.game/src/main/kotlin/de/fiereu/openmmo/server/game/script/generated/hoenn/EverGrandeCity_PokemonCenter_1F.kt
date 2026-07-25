package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.EverGrandeCity_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_EVER_GRANDE_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object EverGrandeCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port EverGrandeCity_PokemonCenter_1F_EventScript_Nurse")
}

internal object EverGrandeCity_PokemonCenter_1F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(EverGrandeCity_PokemonCenter_1F.LeagueAfterVictoryRoad)
}

internal object EverGrandeCity_PokemonCenter_1F_EventScript_ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(EverGrandeCity_PokemonCenter_1F.BelieveInYourPokemon)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox EverGrandeCity_PokemonCenter_1F_Text_ScottHappyForYou, MSGBOX_DEFAULT
 * closemessage
 * call_if_eq VAR_FACING, DIR_NORTH, EverGrandeCity_PokemonCenter_1F_EventScript_ScottExitNorth
 * call_if_eq VAR_FACING, DIR_EAST, EverGrandeCity_PokemonCenter_1F_EventScript_ScottExit
 * call_if_eq VAR_FACING, DIR_WEST, EverGrandeCity_PokemonCenter_1F_EventScript_ScottExit
 * addvar VAR_SCOTT_STATE, 1
 * setflag FLAG_MET_SCOTT_IN_EVERGRANDE
 * playse SE_EXIT
 * waitse
 * removeobject LOCALID_EVER_GRANDE_SCOTT
 * release
 * end
 * ```
 */
internal object EverGrandeCity_PokemonCenter_1F_EventScript_Scott : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port EverGrandeCity_PokemonCenter_1F_EventScript_Scott")
}

internal val EverGrandeCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "EverGrandeCity_PokemonCenter_1F_EventScript_Nurse" to
            EverGrandeCity_PokemonCenter_1F_EventScript_Nurse,
        "EverGrandeCity_PokemonCenter_1F_EventScript_Woman" to
            EverGrandeCity_PokemonCenter_1F_EventScript_Woman,
        "EverGrandeCity_PokemonCenter_1F_EventScript_ExpertM" to
            EverGrandeCity_PokemonCenter_1F_EventScript_ExpertM,
        "EverGrandeCity_PokemonCenter_1F_EventScript_Scott" to
            EverGrandeCity_PokemonCenter_1F_EventScript_Scott,
    )
