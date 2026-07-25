package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PlayersHouse_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_MET_RIVAL_LILYCOVE, RivalsHouse_2F_EventScript_RivalPostLilycove
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, RivalsHouse_2F_EventScript_May
 * goto_if_eq VAR_RESULT, FEMALE, RivalsHouse_2F_EventScript_Brendan
 * end
 * ```
 */
internal object RivalsHouse_2F_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RivalsHouse_2F_EventScript_Rival")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_LITTLEROOT_RIVAL_STATE, 2, LittlerootTown_BrendansHouse_2F_EventScript_MeetBrendan
 * msgbox RivalsHouse_2F_Text_ItsRivalsPokeBall, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object LittlerootTown_BrendansHouse_2F_EventScript_RivalsPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_BrendansHouse_2F_EventScript_RivalsPokeBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, LittlerootTown_BrendansHouse_2F_EventScript_CheckPlayersPC
 * goto_if_eq VAR_RESULT, FEMALE, LittlerootTown_BrendansHouse_2F_EventScript_CheckRivalsPC
 * end
 * ```
 */
internal object LittlerootTown_BrendansHouse_2F_EventScript_PC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_BrendansHouse_2F_EventScript_PC")
}

internal object PlayersHouse_2F_EventScript_Notebook : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PlayersHouse_2F.Notebook)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, MALE
 * goto PlayersHouse_2F_EventScript_WallClock
 * end
 * ```
 */
internal object LittlerootTown_BrendansHouse_2F_EventScript_WallClock : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_BrendansHouse_2F_EventScript_WallClock")
}

internal object PlayersHouse_2F_EventScript_GameCube : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PlayersHouse_2F.ItsAGameCube)
}

internal val LittlerootTown_BrendansHouse_2FScripts: Map<String, Script> =
    mapOf(
        "RivalsHouse_2F_EventScript_Rival" to RivalsHouse_2F_EventScript_Rival,
        "LittlerootTown_BrendansHouse_2F_EventScript_RivalsPokeBall" to
            LittlerootTown_BrendansHouse_2F_EventScript_RivalsPokeBall,
        "LittlerootTown_BrendansHouse_2F_EventScript_PC" to
            LittlerootTown_BrendansHouse_2F_EventScript_PC,
        "PlayersHouse_2F_EventScript_Notebook" to PlayersHouse_2F_EventScript_Notebook,
        "LittlerootTown_BrendansHouse_2F_EventScript_WallClock" to
            LittlerootTown_BrendansHouse_2F_EventScript_WallClock,
        "PlayersHouse_2F_EventScript_GameCube" to PlayersHouse_2F_EventScript_GameCube,
    )
