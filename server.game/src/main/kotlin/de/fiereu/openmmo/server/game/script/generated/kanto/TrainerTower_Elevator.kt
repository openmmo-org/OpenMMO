package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 6
 * call_if_unset FLAG_TEMP_2, EventScript_GetElevatorFloor
 * copyvar VAR_0x8005, VAR_ELEVATOR_FLOOR
 * special DrawElevatorCurrentFloorWindow
 * message Text_WantWhichFloor
 * waitmessage
 * setvar VAR_0x8004, 6
 * specialvar VAR_RESULT, InitElevatorFloorSelectMenuPos
 * switch VAR_RESULT
 * case 0, TrainerTower_Elevator_EventScript_FloorSelectFromRoof
 * case 1, TrainerTower_Elevator_EventScript_FloorSelectFromLobby
 * end
 * ```
 */
internal object TrainerTower_Elevator_EventScript_FloorSelect : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TrainerTower_Elevator_EventScript_FloorSelect")
}

internal val TrainerTower_ElevatorScripts: Map<String, Script> =
    mapOf(
        "TrainerTower_Elevator_EventScript_FloorSelect" to
            TrainerTower_Elevator_EventScript_FloorSelect,
    )
