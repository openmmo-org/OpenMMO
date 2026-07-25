package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BIG_PEARL
 * end
 * ```
 */
internal object SeafoamIslands_B2F_EventScript_ItemBigPearl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafoamIslands_B2F_EventScript_ItemBigPearl")
}

internal val SeafoamIslands_B2FScripts: Map<String, Script> =
    mapOf(
        "SeafoamIslands_B2F_EventScript_ItemBigPearl" to
            SeafoamIslands_B2F_EventScript_ItemBigPearl,
    )
