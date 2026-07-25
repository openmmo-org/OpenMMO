package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CinnabarIsland
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BLAINE, 3
 * msgbox CinnabarIsland_Text_BlaineLivedHereSinceBeforeLab
 * release
 * end
 * ```
 */
internal object CinnabarIsland_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_EventScript_Woman")
}

internal object CinnabarIsland_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CinnabarIsland.ScientistsExperimentInMansion)
}

internal object CinnabarIsland_EventScript_IslandSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CinnabarIsland.IslandSign)
}

internal object CinnabarIsland_EventScript_PokemonLabSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CinnabarIsland.PokemonLab)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_BLAINE, 0
 * msgbox CinnabarIsland_Text_GymSign
 * releaseall
 * end
 * ```
 */
internal object CinnabarIsland_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_EventScript_GymSign")
}

internal val CinnabarIslandScripts: Map<String, Script> =
    mapOf(
        "CinnabarIsland_EventScript_Woman" to CinnabarIsland_EventScript_Woman,
        "CinnabarIsland_EventScript_OldMan" to CinnabarIsland_EventScript_OldMan,
        "CinnabarIsland_EventScript_IslandSign" to CinnabarIsland_EventScript_IslandSign,
        "CinnabarIsland_EventScript_PokemonLabSign" to CinnabarIsland_EventScript_PokemonLabSign,
        "CinnabarIsland_EventScript_GymSign" to CinnabarIsland_EventScript_GymSign,
    )
