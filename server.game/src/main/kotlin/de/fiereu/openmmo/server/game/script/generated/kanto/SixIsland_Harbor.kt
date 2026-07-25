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
 * setvar VAR_0x8004, SEAGALLOP_SIX_ISLAND
 * goto EventScript_ChooseDestFromIsland
 * end
 * ```
 */
internal object SixIsland_Harbor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SixIsland_Harbor_EventScript_Sailor")
}

internal val SixIsland_HarborScripts: Map<String, Script> =
    mapOf(
        "SixIsland_Harbor_EventScript_Sailor" to SixIsland_Harbor_EventScript_Sailor,
    )
