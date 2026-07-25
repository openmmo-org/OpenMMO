package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route22
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route22_EventScript_LeagueGateSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route22.LeagueGateSign)
}

internal val Route22Scripts: Map<String, Script> =
    mapOf(
        "Route22_EventScript_LeagueGateSign" to Route22_EventScript_LeagueGateSign,
    )
