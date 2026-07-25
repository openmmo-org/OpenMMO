package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * end
 * ```
 */
internal object TrainerHill_Elevator_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TrainerHill_Elevator_EventScript_Attendant")
}

internal val TrainerHill_ElevatorScripts: Map<String, Script> =
    mapOf(
        "TrainerHill_Elevator_EventScript_Attendant" to TrainerHill_Elevator_EventScript_Attendant,
    )
