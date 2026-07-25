package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PacifidlogTown_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PacifidlogTown_House1_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PacifidlogTown_House1.RegiStory)
}

internal object PacifidlogTown_House1_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PacifidlogTown_House1.SixDotsOpenThreeDoors)
}

internal val PacifidlogTown_House1Scripts: Map<String, Script> =
    mapOf(
        "PacifidlogTown_House1_EventScript_Man" to PacifidlogTown_House1_EventScript_Man,
        "PacifidlogTown_House1_EventScript_Woman" to PacifidlogTown_House1_EventScript_Woman,
    )
