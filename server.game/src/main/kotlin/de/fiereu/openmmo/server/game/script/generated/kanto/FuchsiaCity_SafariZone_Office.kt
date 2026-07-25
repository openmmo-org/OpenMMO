package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FuchsiaCity_SafariZone_Office
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FuchsiaCity_SafariZone_Office_EventScript_Worker2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FuchsiaCity_SafariZone_Office.WardenIsVeryKnowledgeable)
}

internal object FuchsiaCity_SafariZone_Office_EventScript_Worker3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FuchsiaCity_SafariZone_Office.CouldntUnderstandWarden)
}

internal object FuchsiaCity_SafariZone_Office_EventScript_Worker1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FuchsiaCity_SafariZone_Office.NicknamedWardenSlowpoke)
}

internal object FuchsiaCity_SafariZone_Office_EventScript_Worker4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FuchsiaCity_SafariZone_Office.PrizeInSafariZone)
}

internal val FuchsiaCity_SafariZone_OfficeScripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_SafariZone_Office_EventScript_Worker2" to
            FuchsiaCity_SafariZone_Office_EventScript_Worker2,
        "FuchsiaCity_SafariZone_Office_EventScript_Worker3" to
            FuchsiaCity_SafariZone_Office_EventScript_Worker3,
        "FuchsiaCity_SafariZone_Office_EventScript_Worker1" to
            FuchsiaCity_SafariZone_Office_EventScript_Worker1,
        "FuchsiaCity_SafariZone_Office_EventScript_Worker4" to
            FuchsiaCity_SafariZone_Office_EventScript_Worker4,
    )
