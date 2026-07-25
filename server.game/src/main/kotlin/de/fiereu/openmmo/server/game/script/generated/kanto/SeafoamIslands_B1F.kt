package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_WATER_STONE
 * end
 * ```
 */
internal object SeafoamIslands_B1F_EventScript_ItemWaterStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafoamIslands_B1F_EventScript_ItemWaterStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_REVIVE
 * end
 * ```
 */
internal object SeafoamIslands_B1F_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafoamIslands_B1F_EventScript_ItemRevive")
}

internal val SeafoamIslands_B1FScripts: Map<String, Script> =
    mapOf(
        "SeafoamIslands_B1F_EventScript_ItemWaterStone" to
            SeafoamIslands_B1F_EventScript_ItemWaterStone,
        "SeafoamIslands_B1F_EventScript_ItemRevive" to SeafoamIslands_B1F_EventScript_ItemRevive,
    )
