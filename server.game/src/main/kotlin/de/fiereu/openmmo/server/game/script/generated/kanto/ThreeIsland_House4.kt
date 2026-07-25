package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_House4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object ThreeIsland_House4_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ThreeIsland_House4.GhostsInBerryForest)
}

internal object ThreeIsland_House4_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ThreeIsland_House4.PapaKeepsLyingToMe)
}

internal val ThreeIsland_House4Scripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_House4_EventScript_BaldingMan" to ThreeIsland_House4_EventScript_BaldingMan,
        "ThreeIsland_House4_EventScript_LittleBoy" to ThreeIsland_House4_EventScript_LittleBoy,
    )
