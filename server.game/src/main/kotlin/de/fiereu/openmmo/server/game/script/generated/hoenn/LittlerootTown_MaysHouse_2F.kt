package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_LITTLEROOT_RIVAL_STATE, 2, LittlerootTown_MaysHouse_2F_EventScript_MeetMay
 * msgbox RivalsHouse_2F_Text_ItsRivalsPokeBall, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object LittlerootTown_MaysHouse_2F_EventScript_RivalsPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_MaysHouse_2F_EventScript_RivalsPokeBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, FEMALE
 * goto PlayersHouse_2F_EventScript_WallClock
 * end
 * ```
 */
internal object LittlerootTown_MaysHouse_2F_EventScript_WallClock : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_MaysHouse_2F_EventScript_WallClock")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, LittlerootTown_MaysHouse_2F_EventScript_CheckRivalsPC
 * goto_if_eq VAR_RESULT, FEMALE, LittlerootTown_MaysHouse_2F_EventScript_CheckPlayersPC
 * end
 * ```
 */
internal object LittlerootTown_MaysHouse_2F_EventScript_PC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_MaysHouse_2F_EventScript_PC")
}

internal val LittlerootTown_MaysHouse_2FScripts: Map<String, Script> =
    mapOf(
        "LittlerootTown_MaysHouse_2F_EventScript_RivalsPokeBall" to
            LittlerootTown_MaysHouse_2F_EventScript_RivalsPokeBall,
        "LittlerootTown_MaysHouse_2F_EventScript_WallClock" to
            LittlerootTown_MaysHouse_2F_EventScript_WallClock,
        "LittlerootTown_MaysHouse_2F_EventScript_PC" to LittlerootTown_MaysHouse_2F_EventScript_PC,
    )
