package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_CHOSE_ROOT_FOSSIL, DesertUnderpass_EventScript_GiveClawFossil
 * goto_if_set FLAG_CHOSE_CLAW_FOSSIL, DesertUnderpass_EventScript_GiveRootFossil
 * release
 * end
 * ```
 */
internal object DesertUnderpass_EventScript_Fossil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DesertUnderpass_EventScript_Fossil")
}

internal val DesertUnderpassScripts: Map<String, Script> =
    mapOf(
        "DesertUnderpass_EventScript_Fossil" to DesertUnderpass_EventScript_Fossil,
    )
