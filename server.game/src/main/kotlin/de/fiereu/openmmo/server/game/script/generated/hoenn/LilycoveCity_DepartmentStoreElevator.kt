package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, 0
 * call_if_unset FLAG_TEMP_2, LilycoveCity_DepartmentStoreElevator_EventScript_SetFloor
 * copyvar VAR_0x8005, VAR_DEPT_STORE_FLOOR
 * special ShowDeptStoreElevatorFloorSelect
 * message gText_WhichFloorWouldYouLike
 * waitmessage
 * setvar VAR_0x8004, 0
 * specialvar VAR_RESULT, GetDeptStoreDefaultFloorChoice
 * switch VAR_RESULT
 * case 0, LilycoveCity_DepartmentStoreElevator_EventScript_ChooseFloorFrom5th
 * case 1, LilycoveCity_DepartmentStoreElevator_EventScript_ChooseFloorFrom4th
 * case 2, LilycoveCity_DepartmentStoreElevator_EventScript_ChooseFloorFrom3rd
 * case 3, LilycoveCity_DepartmentStoreElevator_EventScript_ChooseFloorFrom2nd
 * case 4, LilycoveCity_DepartmentStoreElevator_EventScript_ChooseFloorFrom1st
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStoreElevator_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStoreElevator_EventScript_Attendant")
}

internal val LilycoveCity_DepartmentStoreElevatorScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_DepartmentStoreElevator_EventScript_Attendant" to
            LilycoveCity_DepartmentStoreElevator_EventScript_Attendant,
    )
