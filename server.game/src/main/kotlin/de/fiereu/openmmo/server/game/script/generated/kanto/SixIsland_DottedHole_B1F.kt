package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemessage Braille_Text_Up
 * waitbuttonpress
 * releaseall
 * end
 * ```
 */
internal object SixIsland_DottedHole_B1F_EventScript_BrailleUp : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_DottedHole_B1F_EventScript_BrailleUp")
}

internal val SixIsland_DottedHole_B1FScripts: Map<String, Script> =
    mapOf(
        "SixIsland_DottedHole_B1F_EventScript_BrailleUp" to
            SixIsland_DottedHole_B1F_EventScript_BrailleUp,
    )
