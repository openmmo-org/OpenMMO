package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_REGICE, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setwildbattle SPECIES_REGICE, 40
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * special StartRegiBattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, IslandCave_EventScript_DefeatedRegice
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, IslandCave_EventScript_RanFromRegice
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, IslandCave_EventScript_RanFromRegice
 * setflag FLAG_DEFEATED_REGICE
 * release
 * end
 * ```
 */
internal object IslandCave_EventScript_Regice : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port IslandCave_EventScript_Regice")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * call_if_set FLAG_TEMP_REGICE_PUZZLE_FAILED, IslandCave_EventScript_ClearSteps
 * goto_if_set FLAG_SYS_BRAILLE_REGICE_COMPLETED, IslandCave_EventScript_BigHoleInWall
 * braillemessage IslandCave_Braille_RunLapAroundWall
 * setflag FLAG_TEMP_REGICE_PUZZLE_STARTED
 * special ShouldDoBrailleRegicePuzzle
 * goto IslandCave_EventScript_CloseBrailleMsg
 * end
 * ```
 */
internal object IslandCave_EventScript_CaveEntranceMiddle : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port IslandCave_EventScript_CaveEntranceMiddle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * call_if_set FLAG_TEMP_REGICE_PUZZLE_FAILED, IslandCave_EventScript_ClearSteps
 * braillemessage IslandCave_Braille_RunLapAroundWall
 * goto_if_set FLAG_SYS_BRAILLE_REGICE_COMPLETED, IslandCave_EventScript_CloseBrailleMsg
 * setflag FLAG_TEMP_REGICE_PUZZLE_STARTED
 * special ShouldDoBrailleRegicePuzzle
 * goto IslandCave_EventScript_CloseBrailleMsg
 * end
 * ```
 */
internal object IslandCave_EventScript_CaveEntranceSide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port IslandCave_EventScript_CaveEntranceSide")
}

internal val IslandCaveScripts: Map<String, Script> =
    mapOf(
        "IslandCave_EventScript_Regice" to IslandCave_EventScript_Regice,
        "IslandCave_EventScript_CaveEntranceMiddle" to IslandCave_EventScript_CaveEntranceMiddle,
        "IslandCave_EventScript_CaveEntranceSide" to IslandCave_EventScript_CaveEntranceSide,
    )
