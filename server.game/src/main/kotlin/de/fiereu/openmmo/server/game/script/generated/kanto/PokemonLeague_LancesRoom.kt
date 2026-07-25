package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_LANCE, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * goto_if_set FLAG_DEFEATED_LANCE, PokemonLeague_LancesRoom_EventScript_PostBattle
 * famechecker FAMECHECKER_LANCE, 0
 * famechecker FAMECHECKER_LANCE, 1
 * call_if_unset FLAG_SYS_GAME_CLEAR, PokemonLeague_LancesRoom_EventScript_Intro
 * call_if_set FLAG_SYS_GAME_CLEAR, PokemonLeague_LancesRoom_EventScript_RematchIntro
 * setflag FLAG_TEMP_3
 * setflag FLAG_TEMP_5
 * call_if_unset FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_LancesRoom_EventScript_Battle
 * call_if_set FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_LancesRoom_EventScript_Rematch
 * clearflag FLAG_TEMP_5
 * goto PokemonLeague_LancesRoom_EventScript_DefeatedLance
 * end
 * ```
 */
internal object PokemonLeague_LancesRoom_EventScript_Lance : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonLeague_LancesRoom_EventScript_Lance")
}

internal val PokemonLeague_LancesRoomScripts: Map<String, Script> =
    mapOf(
        "PokemonLeague_LancesRoom_EventScript_Lance" to PokemonLeague_LancesRoom_EventScript_Lance,
    )
