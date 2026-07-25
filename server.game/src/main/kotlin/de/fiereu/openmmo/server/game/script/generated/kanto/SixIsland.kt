package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SixIsland
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SixIsland_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SixIsland.SkyAtNightIsFantastic)
}

internal object SixIsland_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SixIsland.ThatWayToWaterPathRuinValley)
}

internal object SixIsland_EventScript_IslandSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SixIsland.IslandSign)
}

internal val SixIslandScripts: Map<String, Script> =
    mapOf(
        "SixIsland_EventScript_Boy" to SixIsland_EventScript_Boy,
        "SixIsland_EventScript_Hiker" to SixIsland_EventScript_Hiker,
        "SixIsland_EventScript_IslandSign" to SixIsland_EventScript_IslandSign,
    )
