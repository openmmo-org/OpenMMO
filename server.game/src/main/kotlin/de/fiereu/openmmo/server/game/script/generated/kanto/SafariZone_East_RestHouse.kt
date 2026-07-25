package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SafariZone_East
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SafariZone_East_RestHouse_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_East.HowManyDidYouCatch)
}

internal object SafariZone_East_RestHouse_EventScript_Rocker : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_East.CaughtChanseyAllWorthwhile)
}

internal object SafariZone_East_RestHouse_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_East.TiredFromAllTheFun)
}

internal val SafariZone_East_RestHouseScripts: Map<String, Script> =
    mapOf(
        "SafariZone_East_RestHouse_EventScript_Scientist" to
            SafariZone_East_RestHouse_EventScript_Scientist,
        "SafariZone_East_RestHouse_EventScript_Rocker" to
            SafariZone_East_RestHouse_EventScript_Rocker,
        "SafariZone_East_RestHouse_EventScript_BaldingMan" to
            SafariZone_East_RestHouse_EventScript_BaldingMan,
    )
