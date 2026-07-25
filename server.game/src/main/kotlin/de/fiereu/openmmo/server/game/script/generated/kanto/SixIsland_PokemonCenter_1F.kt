package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SixIsland_PokemonCenter_1F
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
internal object SixIsland_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_PokemonCenter_1F_EventScript_Nurse")
}

internal object SixIsland_PokemonCenter_1F_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SixIsland_PokemonCenter_1F.SomethingHiddenOnThisIsland)
}

internal object SixIsland_PokemonCenter_1F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SixIsland_PokemonCenter_1F.SomeMonsEvolveByTradingWithHeldItem)
}

internal val SixIsland_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "SixIsland_PokemonCenter_1F_EventScript_Nurse" to
            SixIsland_PokemonCenter_1F_EventScript_Nurse,
        "SixIsland_PokemonCenter_1F_EventScript_Hiker" to
            SixIsland_PokemonCenter_1F_EventScript_Hiker,
        "SixIsland_PokemonCenter_1F_EventScript_OldMan" to
            SixIsland_PokemonCenter_1F_EventScript_OldMan,
    )
