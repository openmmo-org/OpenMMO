package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ICE_HEAL
 * end
 * ```
 */
internal object SeafoamIslands_1F_EventScript_ItemIceHeal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafoamIslands_1F_EventScript_ItemIceHeal")
}

internal val SeafoamIslands_1FScripts: Map<String, Script> =
    mapOf(
        "SeafoamIslands_1F_EventScript_ItemIceHeal" to SeafoamIslands_1F_EventScript_ItemIceHeal,
    )
