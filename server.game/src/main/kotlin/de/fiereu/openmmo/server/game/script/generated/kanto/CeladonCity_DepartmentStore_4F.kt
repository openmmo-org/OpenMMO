package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_DepartmentStore_4F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_DepartmentStore_4F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_4F.GettingPokeDollAsPresent)
}

internal object CeladonCity_DepartmentStore_4F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_4F.CanRunAwayWithPokeDoll)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart CeladonCity_DepartmentStore_4F_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object CeladonCity_DepartmentStore_4F_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_DepartmentStore_4F_EventScript_Clerk")
}

internal object CeladonCity_DepartmentStore_4F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeladonCity_DepartmentStore_4F.FloorSign)
}

internal val CeladonCity_DepartmentStore_4FScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_DepartmentStore_4F_EventScript_Man" to
            CeladonCity_DepartmentStore_4F_EventScript_Man,
        "CeladonCity_DepartmentStore_4F_EventScript_Youngster" to
            CeladonCity_DepartmentStore_4F_EventScript_Youngster,
        "CeladonCity_DepartmentStore_4F_EventScript_Clerk" to
            CeladonCity_DepartmentStore_4F_EventScript_Clerk,
        "CeladonCity_DepartmentStore_4F_EventScript_FloorSign" to
            CeladonCity_DepartmentStore_4F_EventScript_FloorSign,
    )
