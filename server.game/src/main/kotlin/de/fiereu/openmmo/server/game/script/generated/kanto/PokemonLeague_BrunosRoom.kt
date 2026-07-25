package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BRUNO, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * goto_if_set FLAG_DEFEATED_BRUNO, PokemonLeague_BrunosRoom_EventScript_PostBattle
 * famechecker FAMECHECKER_BRUNO, 0
 * famechecker FAMECHECKER_BRUNO, 1
 * call_if_unset FLAG_SYS_GAME_CLEAR, PokemonLeague_BrunosRoom_EventScript_Intro
 * call_if_set FLAG_SYS_GAME_CLEAR, PokemonLeague_BrunosRoom_EventScript_RematchIntro
 * setflag FLAG_TEMP_3
 * setflag FLAG_TEMP_5
 * call_if_unset FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_BrunosRoom_EventScript_Battle
 * call_if_set FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_BrunosRoom_EventScript_Rematch
 * clearflag FLAG_TEMP_5
 * goto PokemonLeague_BrunosRoom_EventScript_DefeatedBruno
 * end
 * ```
 */
internal object PokemonLeague_BrunosRoom_EventScript_Bruno : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonLeague_BrunosRoom_EventScript_Bruno")
}

internal val PokemonLeague_BrunosRoomScripts: Map<String, Script> =
    mapOf(
        "PokemonLeague_BrunosRoom_EventScript_Bruno" to PokemonLeague_BrunosRoom_EventScript_Bruno,
    )
