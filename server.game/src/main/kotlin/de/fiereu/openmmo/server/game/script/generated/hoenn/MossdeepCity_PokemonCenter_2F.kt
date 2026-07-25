package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MossdeepCity_PokemonCenter_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object MossdeepCity_PokemonCenter_2F_EventScript_Woman5 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity_PokemonCenter_2F.Woman5)
}

internal val MossdeepCity_PokemonCenter_2FScripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_PokemonCenter_2F_EventScript_Woman5" to
            MossdeepCity_PokemonCenter_2F_EventScript_Woman5,
    )
