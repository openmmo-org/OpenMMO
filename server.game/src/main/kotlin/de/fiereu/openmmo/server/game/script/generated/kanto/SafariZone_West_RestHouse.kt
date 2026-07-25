package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SafariZone_West
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SafariZone_West_RestHouse_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SafariZone_West.RocksMakeMonRunButEasierCatch)
}

internal object SafariZone_West_RestHouse_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_West.BaitMakesMonStickAround)
}

internal object SafariZone_West_RestHouse_EventScript_CooltrainerF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SafariZone_West.HikedLotsDidntSeeMonIWanted)
}

internal val SafariZone_West_RestHouseScripts: Map<String, Script> =
    mapOf(
        "SafariZone_West_RestHouse_EventScript_Scientist" to
            SafariZone_West_RestHouse_EventScript_Scientist,
        "SafariZone_West_RestHouse_EventScript_Man" to SafariZone_West_RestHouse_EventScript_Man,
        "SafariZone_West_RestHouse_EventScript_CooltrainerF" to
            SafariZone_West_RestHouse_EventScript_CooltrainerF,
    )
