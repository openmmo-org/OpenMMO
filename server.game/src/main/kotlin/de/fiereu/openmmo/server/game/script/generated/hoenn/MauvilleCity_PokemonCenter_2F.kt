package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MauvilleCity_PokemonCenter_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object MauvilleCity_PokemonCenter_2F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MauvilleCity_PokemonCenter_2F.Youngster)
}

internal val MauvilleCity_PokemonCenter_2FScripts: Map<String, Script> =
    mapOf(
        "MauvilleCity_PokemonCenter_2F_EventScript_Youngster" to
            MauvilleCity_PokemonCenter_2F_EventScript_Youngster,
    )
