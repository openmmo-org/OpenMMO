package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PacifidlogTown
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PacifidlogTown_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PacifidlogTown.FastRunningCurrent)
}

internal object PacifidlogTown_EventScript_Fisherman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PacifidlogTown.SkyPillarTooScary)
}

internal object PacifidlogTown_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PacifidlogTown.NeatHousesOnWater)
}

internal object PacifidlogTown_EventScript_TownSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PacifidlogTown.TownSign)
}

internal val PacifidlogTownScripts: Map<String, Script> =
    mapOf(
        "PacifidlogTown_EventScript_Girl" to PacifidlogTown_EventScript_Girl,
        "PacifidlogTown_EventScript_Fisherman" to PacifidlogTown_EventScript_Fisherman,
        "PacifidlogTown_EventScript_NinjaBoy" to PacifidlogTown_EventScript_NinjaBoy,
        "PacifidlogTown_EventScript_TownSign" to PacifidlogTown_EventScript_TownSign,
    )
