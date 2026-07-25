package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_LORELEI, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * goto_if_set FLAG_DEFEATED_LORELEI, PokemonLeague_LoreleisRoom_EventScript_PostBattle
 * famechecker FAMECHECKER_LORELEI, 0
 * famechecker FAMECHECKER_LORELEI, 1
 * call_if_unset FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_LoreleisRoom_EventScript_Intro
 * call_if_set FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_LoreleisRoom_EventScript_RematchIntro
 * setflag FLAG_TEMP_3
 * setflag FLAG_TEMP_5
 * call_if_unset FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_LoreleisRoom_EventScript_Battle
 * call_if_set FLAG_SYS_CAN_LINK_WITH_RS, PokemonLeague_LoreleisRoom_EventScript_Rematch
 * clearflag FLAG_TEMP_5
 * goto PokemonLeague_LoreleisRoom_EventScript_DefeatedLorelei
 * end
 * ```
 */
internal object PokemonLeague_LoreleisRoom_EventScript_Lorelei : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonLeague_LoreleisRoom_EventScript_Lorelei")
}

internal val PokemonLeague_LoreleisRoomScripts: Map<String, Script> =
    mapOf(
        "PokemonLeague_LoreleisRoom_EventScript_Lorelei" to
            PokemonLeague_LoreleisRoom_EventScript_Lorelei,
    )
