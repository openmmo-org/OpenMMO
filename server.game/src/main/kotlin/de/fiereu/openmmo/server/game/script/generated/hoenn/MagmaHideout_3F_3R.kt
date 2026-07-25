package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object MagmaHideout_3F_3R_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MagmaHideout_3F_3R_EventScript_ItemEscapeRope")
}

internal val MagmaHideout_3F_3RScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_3F_3R_EventScript_ItemEscapeRope" to
            MagmaHideout_3F_3R_EventScript_ItemEscapeRope,
    )
