package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemessage Braille_Text_Down
 * waitbuttonpress
 * releaseall
 * end
 * ```
 */
internal object SixIsland_DottedHole_B4F_EventScript_BrailleDown : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_DottedHole_B4F_EventScript_BrailleDown")
}

internal val SixIsland_DottedHole_B4FScripts: Map<String, Script> =
    mapOf(
        "SixIsland_DottedHole_B4F_EventScript_BrailleDown" to
            SixIsland_DottedHole_B4F_EventScript_BrailleDown,
    )
