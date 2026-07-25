package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonTower_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PokemonTower_2F_EventScript_Channeler : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PokemonTower_2F.SilphScopeCouldUnmaskGhosts)
}

internal val PokemonTower_2FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_2F_EventScript_Channeler" to PokemonTower_2F_EventScript_Channeler,
    )
