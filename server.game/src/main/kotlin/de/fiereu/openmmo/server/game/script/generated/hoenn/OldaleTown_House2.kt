package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.OldaleTown_House2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object OldaleTown_House2_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(OldaleTown_House2.PokemonLevelUp)
}

internal object OldaleTown_House2_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OldaleTown_House2.YoullGoFurtherWithStrongPokemon)
}

internal val OldaleTown_House2Scripts: Map<String, Script> =
    mapOf(
        "OldaleTown_House2_EventScript_Woman" to OldaleTown_House2_EventScript_Woman,
        "OldaleTown_House2_EventScript_Man" to OldaleTown_House2_EventScript_Man,
    )
