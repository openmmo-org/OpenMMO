package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_RUSTBORO_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object RustboroCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_PokemonCenter_1F_EventScript_Nurse")
}

internal object RustboroCity_PokemonCenter_1F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_PokemonCenter_1F.PokemonHavePersonalities)
}

internal object RustboroCity_PokemonCenter_1F_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_PokemonCenter_1F.MaleAndFemalePokemon)
}

internal object RustboroCity_PokemonCenter_1F_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_PokemonCenter_1F.HMCutNextDoor)
}

internal val RustboroCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_PokemonCenter_1F_EventScript_Nurse" to
            RustboroCity_PokemonCenter_1F_EventScript_Nurse,
        "RustboroCity_PokemonCenter_1F_EventScript_Man" to
            RustboroCity_PokemonCenter_1F_EventScript_Man,
        "RustboroCity_PokemonCenter_1F_EventScript_Boy" to
            RustboroCity_PokemonCenter_1F_EventScript_Boy,
        "RustboroCity_PokemonCenter_1F_EventScript_Girl" to
            RustboroCity_PokemonCenter_1F_EventScript_Girl,
    )
