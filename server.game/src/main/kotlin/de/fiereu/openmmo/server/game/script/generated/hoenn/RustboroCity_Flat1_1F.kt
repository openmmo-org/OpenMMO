package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_Flat1_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object RustboroCity_Flat1_1F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_Flat1_1F.EveryPokemonHasAbility)
}

internal object RustboroCity_Flat1_1F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity_Flat1_1F.PokemonStrange)
}

internal val RustboroCity_Flat1_1FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_Flat1_1F_EventScript_Man" to RustboroCity_Flat1_1F_EventScript_Man,
        "RustboroCity_Flat1_1F_EventScript_Woman" to RustboroCity_Flat1_1F_EventScript_Woman,
    )
