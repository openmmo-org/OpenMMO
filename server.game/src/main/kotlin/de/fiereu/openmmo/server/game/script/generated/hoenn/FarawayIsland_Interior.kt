package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * applymovement LOCALID_FARAWAY_ISLAND_MEW, FarawayIsland_Interior_Movement_MewAppear
 * waitmovement 0
 * setvar VAR_0x8004, 0
 * special SetMewAboveGrass
 * message FarawayIsland_Interior_Text_Mew
 * waitse
 * playmoncry SPECIES_MEW, CRY_MODE_ENCOUNTER
 * call_if_eq VAR_FACING, DIR_NORTH, FarawayIsland_Interior_EventScript_FoundMewNorth
 * call_if_eq VAR_FACING, DIR_SOUTH, FarawayIsland_Interior_EventScript_FoundMewSouth
 * call_if_eq VAR_FACING, DIR_WEST, FarawayIsland_Interior_EventScript_FoundMewWest
 * call_if_eq VAR_FACING, DIR_EAST, FarawayIsland_Interior_EventScript_FoundMewEast
 * special DestroyMewEmergingGrassSprite
 * delay 40
 * waitmoncry
 * seteventmon SPECIES_MEW, 30
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * special BattleSetup_StartLegendaryBattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, FarawayIsland_Interior_EventScript_MewDefeated
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, FarawayIsland_Interior_EventScript_PlayerOrMewRan
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, FarawayIsland_Interior_EventScript_PlayerOrMewRan
 * goto_if_eq VAR_RESULT, B_OUTCOME_MON_TELEPORTED, FarawayIsland_Interior_EventScript_PlayerOrMewRan
 * setflag FLAG_CAUGHT_MEW
 * release
 * end
 * ```
 */
internal object FarawayIsland_Interior_EventScript_Mew : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FarawayIsland_Interior_EventScript_Mew")
}

internal val FarawayIsland_InteriorScripts: Map<String, Script> =
    mapOf(
        "FarawayIsland_Interior_EventScript_Mew" to FarawayIsland_Interior_EventScript_Mew,
    )
