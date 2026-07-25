package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * special BufferBigGuyOrBigGirlString
 * msgbox ThreeIsland_House1_Text_YoureAlwaysSoCool
 * release
 * end
 * ```
 */
internal object ThreeIsland_House1_EventScript_Lostelle : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ThreeIsland_House1_EventScript_Lostelle")
}

internal object ThreeIsland_House1_EventScript_DisplayCase : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ThreeIsland_House1.RareRocksGemsDisplayed)
}

internal val ThreeIsland_House1Scripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_House1_EventScript_Lostelle" to ThreeIsland_House1_EventScript_Lostelle,
        "ThreeIsland_House1_EventScript_DisplayCase" to ThreeIsland_House1_EventScript_DisplayCase,
    )
