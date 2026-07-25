package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SafariZone_Center
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SafariZone_Center_RestHouse_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_Center.CatchingMonsAsGifts)
}

internal object SafariZone_Center_RestHouse_EventScript_Sara : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_Center.WhereDidErikGo)
}

internal val SafariZone_Center_RestHouseScripts: Map<String, Script> =
    mapOf(
        "SafariZone_Center_RestHouse_EventScript_Scientist" to
            SafariZone_Center_RestHouse_EventScript_Scientist,
        "SafariZone_Center_RestHouse_EventScript_Sara" to
            SafariZone_Center_RestHouse_EventScript_Sara,
    )
