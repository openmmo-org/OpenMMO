package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_DepartmentStore_5F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_DepartmentStore_5F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_5F.ExplainStatEnhancers)
}

internal object CeladonCity_DepartmentStore_5F_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_5F.HereForStatEnhancers)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart CeladonCity_DepartmentStore_5F_XItems
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object CeladonCity_DepartmentStore_5F_EventScript_ClerkXItems : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_DepartmentStore_5F_EventScript_ClerkXItems")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart CeladonCity_DepartmentStore_5F_Vitamins
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object CeladonCity_DepartmentStore_5F_EventScript_ClerkVitamins : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_DepartmentStore_5F_EventScript_ClerkVitamins")
}

internal object CeladonCity_DepartmentStore_5F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeladonCity_DepartmentStore_5F.Drugstore)
}

internal val CeladonCity_DepartmentStore_5FScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_DepartmentStore_5F_EventScript_Gentleman" to
            CeladonCity_DepartmentStore_5F_EventScript_Gentleman,
        "CeladonCity_DepartmentStore_5F_EventScript_Sailor" to
            CeladonCity_DepartmentStore_5F_EventScript_Sailor,
        "CeladonCity_DepartmentStore_5F_EventScript_ClerkXItems" to
            CeladonCity_DepartmentStore_5F_EventScript_ClerkXItems,
        "CeladonCity_DepartmentStore_5F_EventScript_ClerkVitamins" to
            CeladonCity_DepartmentStore_5F_EventScript_ClerkVitamins,
        "CeladonCity_DepartmentStore_5F_EventScript_FloorSign" to
            CeladonCity_DepartmentStore_5F_EventScript_FloorSign,
    )
