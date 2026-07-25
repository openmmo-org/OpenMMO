package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemessage Braille_Text_Left
 * waitbuttonpress
 * releaseall
 * end
 * ```
 */
internal object SixIsland_DottedHole_B2F_EventScript_BrailleLeft : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_DottedHole_B2F_EventScript_BrailleLeft")
}

internal val SixIsland_DottedHole_B2FScripts: Map<String, Script> =
    mapOf(
        "SixIsland_DottedHole_B2F_EventScript_BrailleLeft" to
            SixIsland_DottedHole_B2F_EventScript_BrailleLeft,
    )
