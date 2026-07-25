package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 3
 * call_if_unset FLAG_TEMP_2, EventScript_GetElevatorFloor
 * copyvar VAR_0x8005, VAR_ELEVATOR_FLOOR
 * special DrawElevatorCurrentFloorWindow
 * message Text_WantWhichFloor
 * waitmessage
 * setvar VAR_0x8004, 3
 * specialvar VAR_RESULT, InitElevatorFloorSelectMenuPos
 * switch VAR_RESULT
 * case 0, CeladonCity_DepartmentStore_Elevator_EventScript_FloorSelectFrom5F
 * case 1, CeladonCity_DepartmentStore_Elevator_EventScript_FloorSelectFrom4F
 * case 2, CeladonCity_DepartmentStore_Elevator_EventScript_FloorSelectFrom3F
 * case 3, CeladonCity_DepartmentStore_Elevator_EventScript_FloorSelectFrom2F
 * case 4, CeladonCity_DepartmentStore_Elevator_EventScript_FloorSelectFrom1F
 * end
 * ```
 */
internal object CeladonCity_DepartmentStore_Elevator_EventScript_FloorSelect : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_DepartmentStore_Elevator_EventScript_FloorSelect")
}

internal val CeladonCity_DepartmentStore_ElevatorScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_DepartmentStore_Elevator_EventScript_FloorSelect" to
            CeladonCity_DepartmentStore_Elevator_EventScript_FloorSelect,
    )
