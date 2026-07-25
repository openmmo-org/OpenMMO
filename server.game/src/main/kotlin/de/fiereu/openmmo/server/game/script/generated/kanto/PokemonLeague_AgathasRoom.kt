package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_AGATHA, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * goto_if_set FLAG_DEFEATED_AGATHA, PokemonLeague_AgathasRoom_EventScript_PostBattle
 * famechecker FAMECHECKER_AGATHA, 0
 * famechecker FAMECHECKER_AGATHA, 4
 * famechecker FAMECHECKER_OAK, 4
 * call_if_unset FLAG_SYS_GAME_CLEAR, PokemonLeague_AgathasRoom_EventScript_Intro
 * call_if_set FLAG_SYS_GAME_CLEAR, PokemonLeague_AgathasRoom_EventScript_RematchIntro
 * setflag FLAG_TEMP_3
 * setflag FLAG_TEMP_5
 * call_if_unset FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_AgathasRoom_EventScript_Battle
 * call_if_set FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_AgathasRoom_EventScript_Rematch
 * clearflag FLAG_TEMP_5
 * goto PokemonLeague_AgathasRoom_EventScript_DefeatedAgatha
 * end
 * ```
 */
internal object PokemonLeague_AgathasRoom_EventScript_Agatha : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonLeague_AgathasRoom_EventScript_Agatha")
}

internal val PokemonLeague_AgathasRoomScripts: Map<String, Script> =
    mapOf(
        "PokemonLeague_AgathasRoom_EventScript_Agatha" to
            PokemonLeague_AgathasRoom_EventScript_Agatha,
    )
