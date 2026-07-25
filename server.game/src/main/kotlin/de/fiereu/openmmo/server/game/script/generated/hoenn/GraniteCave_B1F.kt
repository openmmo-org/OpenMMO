package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_POKE_BALL
 * end
 * ```
 */
internal object GraniteCave_B1F_EventScript_ItemPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port GraniteCave_B1F_EventScript_ItemPokeBall")
}

internal val GraniteCave_B1FScripts: Map<String, Script> =
    mapOf(
        "GraniteCave_B1F_EventScript_ItemPokeBall" to GraniteCave_B1F_EventScript_ItemPokeBall,
    )
