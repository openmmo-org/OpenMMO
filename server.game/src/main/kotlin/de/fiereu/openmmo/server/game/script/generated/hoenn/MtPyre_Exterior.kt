package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_POTION
 * end
 * ```
 */
internal object MtPyre_Exterior_EventScript_ItemMaxPotion : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MtPyre_Exterior_EventScript_ItemMaxPotion")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_SKILL_SWAP
 * end
 * ```
 */
internal object MtPyre_Exterior_EventScript_ItemTMSkillSwap : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MtPyre_Exterior_EventScript_ItemTMSkillSwap")
}

internal val MtPyre_ExteriorScripts: Map<String, Script> =
    mapOf(
        "MtPyre_Exterior_EventScript_ItemMaxPotion" to MtPyre_Exterior_EventScript_ItemMaxPotion,
        "MtPyre_Exterior_EventScript_ItemTMSkillSwap" to
            MtPyre_Exterior_EventScript_ItemTMSkillSwap,
    )
