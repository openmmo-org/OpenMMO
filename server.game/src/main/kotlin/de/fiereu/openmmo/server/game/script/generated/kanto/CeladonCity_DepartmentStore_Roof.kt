package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_DepartmentStore_Roof
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_DepartmentStore_Roof_EventScript_CooltrainerM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_Roof.MySisterIsImmature)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * call CeladonCity_DepartmentStore_Roof_EventScript_CheckPlayerHasDrinks
 * goto_if_eq VAR_TEMP_1, 0, CeladonCity_DepartmentStore_Roof_EventScript_IWantDrink
 * goto CeladonCity_DepartmentStore_Roof_EventScript_AskGiveDrink
 * end
 * ```
 */
internal object CeladonCity_DepartmentStore_Roof_EventScript_ThirstyGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_DepartmentStore_Roof_EventScript_ThirstyGirl")
}

internal object CeladonCity_DepartmentStore_Roof_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_DepartmentStore_Roof.FloorSign)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * message CeladonCity_DepartmentStore_Roof_Text_VendingMachineWhatDoesItHave
 * waitmessage
 * showmoneybox 0, 0
 * goto CeladonCity_DepartmentStore_Roof_EventScript_ChooseDrink
 * end
 * ```
 */
internal object CeladonCity_DepartmentStore_Roof_EventScript_VendingMachine : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_DepartmentStore_Roof_EventScript_VendingMachine")
}

internal val CeladonCity_DepartmentStore_RoofScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_DepartmentStore_Roof_EventScript_CooltrainerM" to
            CeladonCity_DepartmentStore_Roof_EventScript_CooltrainerM,
        "CeladonCity_DepartmentStore_Roof_EventScript_ThirstyGirl" to
            CeladonCity_DepartmentStore_Roof_EventScript_ThirstyGirl,
        "CeladonCity_DepartmentStore_Roof_EventScript_FloorSign" to
            CeladonCity_DepartmentStore_Roof_EventScript_FloorSign,
        "CeladonCity_DepartmentStore_Roof_EventScript_VendingMachine" to
            CeladonCity_DepartmentStore_Roof_EventScript_VendingMachine,
    )
