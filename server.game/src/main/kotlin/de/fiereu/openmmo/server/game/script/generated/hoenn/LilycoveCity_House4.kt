package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_House4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object LilycoveCity_House4_EventScript_Man1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity_House4.MysteriesAtBottomOfSea)
}

internal object LilycoveCity_House4_EventScript_Man2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_House4.UnderwaterTrenchMossdeepSootopolis)
}

internal val LilycoveCity_House4Scripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_House4_EventScript_Man1" to LilycoveCity_House4_EventScript_Man1,
        "LilycoveCity_House4_EventScript_Man2" to LilycoveCity_House4_EventScript_Man2,
    )
