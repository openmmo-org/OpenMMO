package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.DewfordTown_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_DEWFORD_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object DewfordTown_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port DewfordTown_PokemonCenter_1F_EventScript_Nurse")
}

internal object DewfordTown_PokemonCenter_1F_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(DewfordTown_PokemonCenter_1F.StoneCavern)
}

internal object DewfordTown_PokemonCenter_1F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(DewfordTown_PokemonCenter_1F.FaintedMonCanUseHM)
}

internal val DewfordTown_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "DewfordTown_PokemonCenter_1F_EventScript_Nurse" to
            DewfordTown_PokemonCenter_1F_EventScript_Nurse,
        "DewfordTown_PokemonCenter_1F_EventScript_PokefanF" to
            DewfordTown_PokemonCenter_1F_EventScript_PokefanF,
        "DewfordTown_PokemonCenter_1F_EventScript_Man" to
            DewfordTown_PokemonCenter_1F_EventScript_Man,
    )
