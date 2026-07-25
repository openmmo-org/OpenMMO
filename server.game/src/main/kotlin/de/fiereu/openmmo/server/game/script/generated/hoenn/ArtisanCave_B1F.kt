package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HP_UP
 * end
 * ```
 */
internal object ArtisanCave_B1F_EventScript_ItemHPUp : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ArtisanCave_B1F_EventScript_ItemHPUp")
}

internal val ArtisanCave_B1FScripts: Map<String, Script> =
    mapOf(
        "ArtisanCave_B1F_EventScript_ItemHPUp" to ArtisanCave_B1F_EventScript_ItemHPUp,
    )
