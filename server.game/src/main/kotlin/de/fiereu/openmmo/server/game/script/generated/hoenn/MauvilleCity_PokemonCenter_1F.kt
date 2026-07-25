package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MauvilleCity_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_MAUVILLE_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object MauvilleCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MauvilleCity_PokemonCenter_1F_EventScript_Nurse")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * special Script_GetCurrentMauvilleMan
 * switch VAR_RESULT
 * case MAUVILLE_MAN_BARD, MauvilleCity_PokemonCenter_1F_EventScript_Bard
 * case MAUVILLE_MAN_HIPSTER, MauvilleCity_PokemonCenter_1F_EventScript_Hipster
 * case MAUVILLE_MAN_TRADER, MauvilleCity_PokemonCenter_1F_EventScript_Trader
 * case MAUVILLE_MAN_STORYTELLER, MauvilleCity_PokemonCenter_1F_EventScript_Storyteller
 * case MAUVILLE_MAN_GIDDY, MauvilleCity_PokemonCenter_1F_EventScript_Giddy
 * end
 * ```
 */
internal object MauvilleCity_PokemonCenter_1F_EventScript_MauvilleOldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MauvilleCity_PokemonCenter_1F_EventScript_MauvilleOldMan")
}

internal object MauvilleCity_PokemonCenter_1F_EventScript_Woman1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MauvilleCity_PokemonCenter_1F.ManOverThereSaysWeirdThings)
}

internal object MauvilleCity_PokemonCenter_1F_EventScript_Woman2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MauvilleCity_PokemonCenter_1F.MyDataUpdatedFromRecordCorner)
}

internal object MauvilleCity_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MauvilleCity_PokemonCenter_1F.RecordCornerSoundsFun)
}

internal val MauvilleCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "MauvilleCity_PokemonCenter_1F_EventScript_Nurse" to
            MauvilleCity_PokemonCenter_1F_EventScript_Nurse,
        "MauvilleCity_PokemonCenter_1F_EventScript_MauvilleOldMan" to
            MauvilleCity_PokemonCenter_1F_EventScript_MauvilleOldMan,
        "MauvilleCity_PokemonCenter_1F_EventScript_Woman1" to
            MauvilleCity_PokemonCenter_1F_EventScript_Woman1,
        "MauvilleCity_PokemonCenter_1F_EventScript_Woman2" to
            MauvilleCity_PokemonCenter_1F_EventScript_Woman2,
        "MauvilleCity_PokemonCenter_1F_EventScript_Youngster" to
            MauvilleCity_PokemonCenter_1F_EventScript_Youngster,
    )
