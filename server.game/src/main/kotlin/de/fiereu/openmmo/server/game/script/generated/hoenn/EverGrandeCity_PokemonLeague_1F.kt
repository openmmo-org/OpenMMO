package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_LEAGUE_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object EverGrandeCity_PokemonLeague_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port EverGrandeCity_PokemonLeague_1F_EventScript_Nurse")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart EverGrandeCity_PokemonLeague_1F_Pokemart
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object EverGrandeCity_PokemonLeague_1F_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port EverGrandeCity_PokemonLeague_1F_EventScript_Clerk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_ENTERED_ELITE_FOUR, EverGrandeCity_PokemonLeague_1F_EventScript_GoForth
 * getplayerxy VAR_TEMP_0, VAR_TEMP_1
 * call_if_ge VAR_TEMP_0, 11, EverGrandeCity_PokemonLeague_1F_EventScript_PlayerMoveToFrontFromRight
 * call_if_le VAR_TEMP_0, 8, EverGrandeCity_PokemonLeague_1F_EventScript_PlayerMoveToFrontFromLeft
 * message EverGrandeCity_PokemonLeague_1F_Text_MustHaveAllGymBadges
 * waitmessage
 * delay 120
 * goto_if_unset FLAG_BADGE06_GET, EverGrandeCity_PokemonLeague_1F_EventScript_NotAllBadges
 * closemessage
 * applymovement LOCALID_LEAGUE_GUARD_1, EverGrandeCity_PokemonLeague_1F_Movement_LeftGuardOutOfWay
 * applymovement LOCALID_LEAGUE_GUARD_2, EverGrandeCity_PokemonLeague_1F_Movement_RightGuardOutOfWay
 * waitmovement 0
 * delay 10
 * playfanfare MUS_OBTAIN_BADGE
 * message EverGrandeCity_PokemonLeague_1F_Text_GoForth
 * waitmessage
 * waitfanfare
 * closemessage
 * copyobjectxytoperm LOCALID_LEAGUE_GUARD_1
 * copyobjectxytoperm LOCALID_LEAGUE_GUARD_2
 * setflag FLAG_ENTERED_ELITE_FOUR
 * releaseall
 * end
 * ```
 */
internal object EverGrandeCity_PokemonLeague_1F_EventScript_DoorGuard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port EverGrandeCity_PokemonLeague_1F_EventScript_DoorGuard")
}

internal val EverGrandeCity_PokemonLeague_1FScripts: Map<String, Script> =
    mapOf(
        "EverGrandeCity_PokemonLeague_1F_EventScript_Nurse" to
            EverGrandeCity_PokemonLeague_1F_EventScript_Nurse,
        "EverGrandeCity_PokemonLeague_1F_EventScript_Clerk" to
            EverGrandeCity_PokemonLeague_1F_EventScript_Clerk,
        "EverGrandeCity_PokemonLeague_1F_EventScript_DoorGuard" to
            EverGrandeCity_PokemonLeague_1F_EventScript_DoorGuard,
    )
