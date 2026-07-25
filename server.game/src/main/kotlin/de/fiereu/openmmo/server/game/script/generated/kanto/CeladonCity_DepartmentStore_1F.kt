package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_DepartmentStore_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_DepartmentStore_1F_EventScript_Receptionist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_1F.WelcomeToDeptStore)
}

internal object CeladonCity_DepartmentStore_1F_EventScript_LayoutSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_DepartmentStore_1F.FloorDescriptions)
}

internal object CeladonCity_DepartmentStore_1F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_DepartmentStore_1F.ServiceCounter)
}

internal val CeladonCity_DepartmentStore_1FScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_DepartmentStore_1F_EventScript_Receptionist" to
            CeladonCity_DepartmentStore_1F_EventScript_Receptionist,
        "CeladonCity_DepartmentStore_1F_EventScript_LayoutSign" to
            CeladonCity_DepartmentStore_1F_EventScript_LayoutSign,
        "CeladonCity_DepartmentStore_1F_EventScript_FloorSign" to
            CeladonCity_DepartmentStore_1F_EventScript_FloorSign,
    )
