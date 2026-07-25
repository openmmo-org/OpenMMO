package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_FRONTIER_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object BattleFrontier_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_PokemonCenter_1F_EventScript_Nurse")
}

internal object BattleFrontier_PokemonCenter_1F_EventScript_SchoolKid : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_PokemonCenter_1F.NeverSeenPokemon)
}

internal object BattleFrontier_PokemonCenter_1F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_PokemonCenter_1F.NextStopBattleArena)
}

internal object BattleFrontier_PokemonCenter_1F_EventScript_Picnicker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_PokemonCenter_1F.GoingThroughEveryChallenge)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_SKITTY, CRY_MODE_NORMAL
 * msgbox BattleFrontier_PokemonCenter_1F_Text_Skitty, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object BattleFrontier_PokemonCenter_1F_EventScript_Skitty : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_PokemonCenter_1F_EventScript_Skitty")
}

internal val BattleFrontier_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_PokemonCenter_1F_EventScript_Nurse" to
            BattleFrontier_PokemonCenter_1F_EventScript_Nurse,
        "BattleFrontier_PokemonCenter_1F_EventScript_SchoolKid" to
            BattleFrontier_PokemonCenter_1F_EventScript_SchoolKid,
        "BattleFrontier_PokemonCenter_1F_EventScript_Man" to
            BattleFrontier_PokemonCenter_1F_EventScript_Man,
        "BattleFrontier_PokemonCenter_1F_EventScript_Picnicker" to
            BattleFrontier_PokemonCenter_1F_EventScript_Picnicker,
        "BattleFrontier_PokemonCenter_1F_EventScript_Skitty" to
            BattleFrontier_PokemonCenter_1F_EventScript_Skitty,
    )
