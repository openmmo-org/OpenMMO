package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message Text_WhereDoYouWantToSail
 * waitmessage
 * setvar VAR_0x8004, SEAGALLOP_THREE_ISLAND
 * goto EventScript_ChooseDestFromIsland
 * end
 * ```
 */
internal object ThreeIsland_Harbor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ThreeIsland_Harbor_EventScript_Sailor")
}

internal val ThreeIsland_HarborScripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_Harbor_EventScript_Sailor" to ThreeIsland_Harbor_EventScript_Sailor,
    )
