package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call EventScript_PkmnCenterNurse
 * release
 * end
 * ```
 */
internal object ThreeIsland_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ThreeIsland_PokemonCenter_1F_EventScript_Nurse")
}

internal object ThreeIsland_PokemonCenter_1F_EventScript_Rocker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ThreeIsland_PokemonCenter_1F.PCNetworkCanLinkWithKanto)
}

internal object ThreeIsland_PokemonCenter_1F_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ThreeIsland_PokemonCenter_1F.AlwaysBerriesInBerryForest)
}

internal object ThreeIsland_PokemonCenter_1F_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ThreeIsland_PokemonCenter_1F.ImpossibleToSurfBetweenIslands)
}

internal val ThreeIsland_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_PokemonCenter_1F_EventScript_Nurse" to
            ThreeIsland_PokemonCenter_1F_EventScript_Nurse,
        "ThreeIsland_PokemonCenter_1F_EventScript_Rocker" to
            ThreeIsland_PokemonCenter_1F_EventScript_Rocker,
        "ThreeIsland_PokemonCenter_1F_EventScript_Lass" to
            ThreeIsland_PokemonCenter_1F_EventScript_Lass,
        "ThreeIsland_PokemonCenter_1F_EventScript_Sailor" to
            ThreeIsland_PokemonCenter_1F_EventScript_Sailor,
    )
