package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ViridianCity_PokemonCenter_1F
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
internal object ViridianCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ViridianCity_PokemonCenter_1F_EventScript_Nurse")
}

internal object ViridianCity_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ViridianCity_PokemonCenter_1F.FeelFreeToUsePC)
}

internal object ViridianCity_PokemonCenter_1F_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ViridianCity_PokemonCenter_1F.PokeCenterInEveryTown)
}

internal object ViridianCity_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ViridianCity_PokemonCenter_1F.PokeCentersHealMons)
}

internal val ViridianCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "ViridianCity_PokemonCenter_1F_EventScript_Nurse" to
            ViridianCity_PokemonCenter_1F_EventScript_Nurse,
        "ViridianCity_PokemonCenter_1F_EventScript_Gentleman" to
            ViridianCity_PokemonCenter_1F_EventScript_Gentleman,
        "ViridianCity_PokemonCenter_1F_EventScript_Boy" to
            ViridianCity_PokemonCenter_1F_EventScript_Boy,
        "ViridianCity_PokemonCenter_1F_EventScript_Youngster" to
            ViridianCity_PokemonCenter_1F_EventScript_Youngster,
    )
