package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox Underwater_SealedChamber_Braille_GoUpHere
 * releaseall
 * end
 * ```
 */
internal object Underwater_SealedChamber_EventScript_Braille : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Underwater_SealedChamber_EventScript_Braille")
}

internal val Underwater_SealedChamberScripts: Map<String, Script> =
    mapOf(
        "Underwater_SealedChamber_EventScript_Braille" to
            Underwater_SealedChamber_EventScript_Braille,
    )
