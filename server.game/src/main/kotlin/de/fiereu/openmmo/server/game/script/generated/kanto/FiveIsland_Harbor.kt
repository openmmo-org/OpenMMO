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
 * setvar VAR_0x8004, SEAGALLOP_FIVE_ISLAND
 * goto EventScript_ChooseDestFromIsland
 * end
 * ```
 */
internal object FiveIsland_Harbor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FiveIsland_Harbor_EventScript_Sailor")
}

internal val FiveIsland_HarborScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_Harbor_EventScript_Sailor" to FiveIsland_Harbor_EventScript_Sailor,
    )
