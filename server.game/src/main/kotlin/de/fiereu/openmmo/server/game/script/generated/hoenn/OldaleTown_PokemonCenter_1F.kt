package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.OldaleTown_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_OLDALE_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object OldaleTown_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) = healAtPokemonCenter(ctx)
}

internal object OldaleTown_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OldaleTown_PokemonCenter_1F.TrainersCanUsePC)
}

internal object OldaleTown_PokemonCenter_1F_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OldaleTown_PokemonCenter_1F.PokemonCentersAreGreat)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_POKEDEX_GET, OldaleTown_PokemonCenter_1F_EventScript_WirelessClubAvailable
 * msgbox OldaleTown_PokemonCenter_1F_Text_WirelessClubNotAvailable, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object OldaleTown_PokemonCenter_1F_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port OldaleTown_PokemonCenter_1F_EventScript_Girl")
}

internal val OldaleTown_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "OldaleTown_PokemonCenter_1F_EventScript_Nurse" to
            OldaleTown_PokemonCenter_1F_EventScript_Nurse,
        "OldaleTown_PokemonCenter_1F_EventScript_Gentleman" to
            OldaleTown_PokemonCenter_1F_EventScript_Gentleman,
        "OldaleTown_PokemonCenter_1F_EventScript_Boy" to
            OldaleTown_PokemonCenter_1F_EventScript_Boy,
        "OldaleTown_PokemonCenter_1F_EventScript_Girl" to
            OldaleTown_PokemonCenter_1F_EventScript_Girl,
    )
