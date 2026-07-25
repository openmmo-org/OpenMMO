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
 * setvar VAR_0x8004, SEAGALLOP_TWO_ISLAND
 * goto EventScript_ChooseDestFromTwoIsland
 * end
 * ```
 */
internal object TwoIsland_Harbor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TwoIsland_Harbor_EventScript_Sailor")
}

internal val TwoIsland_HarborScripts: Map<String, Script> =
    mapOf(
        "TwoIsland_Harbor_EventScript_Sailor" to TwoIsland_Harbor_EventScript_Sailor,
    )
