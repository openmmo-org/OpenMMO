package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FallarborTown_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_FALLARBOR_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object FallarborTown_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FallarborTown_PokemonCenter_1F_EventScript_Nurse")
}

internal object FallarborTown_PokemonCenter_1F_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FallarborTown_PokemonCenter_1F.FossilManiacEdgeOfTown)
}

internal object FallarborTown_PokemonCenter_1F_EventScript_ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FallarborTown_PokemonCenter_1F.PlantHardyTrees)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox FallarborTown_PokemonCenter_1F_Text_LanetteGreeting, MSGBOX_DEFAULT
 * closemessage
 * switch VAR_FACING
 * case DIR_NORTH, FallarborTown_PokemonCenter_1F_EventScript_LanetteExitNorth
 * case DIR_WEST, FallarborTown_PokemonCenter_1F_EventScript_LanetteExitWest
 * end
 * ```
 */
internal object FallarborTown_PokemonCenter_1F_EventScript_Lanette : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FallarborTown_PokemonCenter_1F_EventScript_Lanette")
}

internal val FallarborTown_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "FallarborTown_PokemonCenter_1F_EventScript_Nurse" to
            FallarborTown_PokemonCenter_1F_EventScript_Nurse,
        "FallarborTown_PokemonCenter_1F_EventScript_Girl" to
            FallarborTown_PokemonCenter_1F_EventScript_Girl,
        "FallarborTown_PokemonCenter_1F_EventScript_ExpertM" to
            FallarborTown_PokemonCenter_1F_EventScript_ExpertM,
        "FallarborTown_PokemonCenter_1F_EventScript_Lanette" to
            FallarborTown_PokemonCenter_1F_EventScript_Lanette,
    )
