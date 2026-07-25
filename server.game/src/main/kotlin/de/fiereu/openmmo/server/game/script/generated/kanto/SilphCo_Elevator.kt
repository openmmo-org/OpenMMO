package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 1
 * call_if_unset FLAG_TEMP_2, EventScript_GetElevatorFloor
 * copyvar VAR_0x8005, VAR_ELEVATOR_FLOOR
 * special DrawElevatorCurrentFloorWindow
 * message Text_WantWhichFloor
 * waitmessage
 * setvar VAR_0x8004, LISTMENU_SILPHCO_FLOORS
 * specialvar VAR_RESULT, InitElevatorFloorSelectMenuPos
 * special ListMenu
 * waitstate
 * switch VAR_RESULT
 * case 0, SilphCo_Elevator_EventScript_To11F
 * case 1, SilphCo_Elevator_EventScript_To10F
 * case 2, SilphCo_Elevator_EventScript_To9F
 * case 3, SilphCo_Elevator_EventScript_To8F
 * case 4, SilphCo_Elevator_EventScript_To7F
 * case 5, SilphCo_Elevator_EventScript_To6F
 * case 6, SilphCo_Elevator_EventScript_To5F
 * case 7, SilphCo_Elevator_EventScript_To4F
 * case 8, SilphCo_Elevator_EventScript_To3F
 * case 9, SilphCo_Elevator_EventScript_To2F
 * case 10, SilphCo_Elevator_EventScript_To1F
 * case 11, SilphCo_Elevator_EventScript_ExitFloorSelect
 * case 127, SilphCo_Elevator_EventScript_ExitFloorSelect
 * end
 * ```
 */
internal object SilphCo_Elevator_EventScript_FloorSelect : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SilphCo_Elevator_EventScript_FloorSelect")
}

internal val SilphCo_ElevatorScripts: Map<String, Script> =
    mapOf(
        "SilphCo_Elevator_EventScript_FloorSelect" to SilphCo_Elevator_EventScript_FloorSelect,
    )
