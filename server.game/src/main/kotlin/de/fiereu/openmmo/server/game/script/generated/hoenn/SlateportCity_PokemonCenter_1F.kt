package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_SLATEPORT_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object SlateportCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_PokemonCenter_1F_EventScript_Nurse")
}

internal object SlateportCity_PokemonCenter_1F_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_PokemonCenter_1F.RaiseDifferentTypesOfPokemon)
}

internal object SlateportCity_PokemonCenter_1F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_PokemonCenter_1F.TradedMonWithFriend)
}

internal val SlateportCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_PokemonCenter_1F_EventScript_Nurse" to
            SlateportCity_PokemonCenter_1F_EventScript_Nurse,
        "SlateportCity_PokemonCenter_1F_EventScript_Sailor" to
            SlateportCity_PokemonCenter_1F_EventScript_Sailor,
        "SlateportCity_PokemonCenter_1F_EventScript_Woman" to
            SlateportCity_PokemonCenter_1F_EventScript_Woman,
    )
