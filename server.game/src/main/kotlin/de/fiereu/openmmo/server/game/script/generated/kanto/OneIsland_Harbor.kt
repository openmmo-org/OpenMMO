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
 * setvar VAR_0x8004, SEAGALLOP_ONE_ISLAND
 * goto EventScript_ChooseDestFromOneIsland
 * end
 * ```
 */
internal object OneIsland_Harbor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port OneIsland_Harbor_EventScript_Sailor")
}

internal val OneIsland_HarborScripts: Map<String, Script> =
    mapOf(
        "OneIsland_Harbor_EventScript_Sailor" to OneIsland_Harbor_EventScript_Sailor,
    )
