package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_unset FLAG_CAN_USE_ROCKET_HIDEOUT_LIFT, RocketHideout_Elevator_EventScript_NeedKey
 * setvar VAR_0x8004, 2
 * call_if_unset FLAG_TEMP_2, EventScript_GetElevatorFloor
 * copyvar VAR_0x8005, VAR_ELEVATOR_FLOOR
 * special DrawElevatorCurrentFloorWindow
 * message Text_WantWhichFloor
 * waitmessage
 * setvar VAR_0x8004, 2
 * specialvar VAR_RESULT, InitElevatorFloorSelectMenuPos
 * switch VAR_RESULT
 * case 0, RocketHideout_Elevator_EventScript_FloorSelectFromB1F
 * case 1, RocketHideout_Elevator_EventScript_FloorSelectFromB2F
 * case 2, RocketHideout_Elevator_EventScript_FloorSelectFromB4F
 * end
 * ```
 */
internal object RocketHideout_Elevator_EventScript_FloorSelect : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_Elevator_EventScript_FloorSelect")
}

internal val RocketHideout_ElevatorScripts: Map<String, Script> =
    mapOf(
        "RocketHideout_Elevator_EventScript_FloorSelect" to
            RocketHideout_Elevator_EventScript_FloorSelect,
    )
