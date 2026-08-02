package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PetalburgCity_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_PETALBURG_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object PetalburgCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) = healAtPokemonCenter(ctx)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_CHAT_USED, ProfileMan_EventScript_GivenProfileBefore
 * msgbox ProfileMan_Text_CollectTrainerProfiles, MSGBOX_DEFAULT
 * goto ProfileMan_EventScript_AskToSeeProfile
 * end
 * ```
 */
internal object ProfileMan_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ProfileMan_EventScript_Man")
}

internal object PetalburgCity_PokemonCenter_1F_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PetalburgCity_PokemonCenter_1F.PCStorageSystem)
}

internal object PetalburgCity_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PetalburgCity_PokemonCenter_1F.OranBerryRegainedHP)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox PetalburgCity_PokemonCenter_1F_Text_ManyTypesOfPokemon, MSGBOX_DEFAULT
 * specialvar VAR_RESULT, IsStarterInParty
 * goto_if_eq VAR_RESULT, TRUE, PetalburgCity_PokemonCenter_1F_EventScript_SayStarterTypeInfo
 * release
 * end
 * ```
 */
internal object PetalburgCity_PokemonCenter_1F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_PokemonCenter_1F_EventScript_Woman")
}

internal val PetalburgCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "PetalburgCity_PokemonCenter_1F_EventScript_Nurse" to
            PetalburgCity_PokemonCenter_1F_EventScript_Nurse,
        "ProfileMan_EventScript_Man" to ProfileMan_EventScript_Man,
        "PetalburgCity_PokemonCenter_1F_EventScript_FatMan" to
            PetalburgCity_PokemonCenter_1F_EventScript_FatMan,
        "PetalburgCity_PokemonCenter_1F_EventScript_Youngster" to
            PetalburgCity_PokemonCenter_1F_EventScript_Youngster,
        "PetalburgCity_PokemonCenter_1F_EventScript_Woman" to
            PetalburgCity_PokemonCenter_1F_EventScript_Woman,
    )
