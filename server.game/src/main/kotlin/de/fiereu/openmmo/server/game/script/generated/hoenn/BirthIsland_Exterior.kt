package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * special DoDeoxysRockInteraction
 * switch VAR_RESULT
 * case DEOXYS_ROCK_FAILED,     BirthIsland_Exterior_EventScript_Failed
 * case DEOXYS_ROCK_PROGRESSED, BirthIsland_Exterior_EventScript_Progressed
 * case DEOXYS_ROCK_SOLVED,     BirthIsland_Exterior_EventScript_Deoxys
 * case DEOXYS_ROCK_COMPLETE,   BirthIsland_Exterior_EventScript_Complete
 * end
 * ```
 */
internal object BirthIsland_Exterior_EventScript_Triangle : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BirthIsland_Exterior_EventScript_Triangle")
}

internal val BirthIsland_ExteriorScripts: Map<String, Script> =
    mapOf(
        "BirthIsland_Exterior_EventScript_Triangle" to BirthIsland_Exterior_EventScript_Triangle,
    )
