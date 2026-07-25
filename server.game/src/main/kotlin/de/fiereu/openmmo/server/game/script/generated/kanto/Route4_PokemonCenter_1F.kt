package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route4_PokemonCenter_1F
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
internal object Route4_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route4_PokemonCenter_1F_EventScript_Nurse")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_BOUGHT_MAGIKARP, Route4_PokemonCenter_1F_EventScript_AlreadyBoughtMagikarp
 * showmoneybox 0, 0
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, Route4_PokemonCenter_1F_EventScript_AskBuyMagikarpMale
 * goto_if_eq VAR_RESULT, FEMALE, Route4_PokemonCenter_1F_EventScript_AskBuyMagikarpFemale
 * end
 * ```
 */
internal object Route4_PokemonCenter_1F_EventScript_MagikarpSalesman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route4_PokemonCenter_1F_EventScript_MagikarpSalesman")
}

internal object Route4_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route4_PokemonCenter_1F.TeamRocketAttacksCerulean)
}

internal object Route4_PokemonCenter_1F_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route4_PokemonCenter_1F.CanHaveSixMonsWithYou)
}

internal object Route4_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route4_PokemonCenter_1F.ShouldStoreMonsUsingPC)
}

internal object Route4_PokemonCenter_1F_EventScript_Newspaper : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route4_PokemonCenter_1F.ItsANewspaper)
}

internal val Route4_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "Route4_PokemonCenter_1F_EventScript_Nurse" to Route4_PokemonCenter_1F_EventScript_Nurse,
        "Route4_PokemonCenter_1F_EventScript_MagikarpSalesman" to
            Route4_PokemonCenter_1F_EventScript_MagikarpSalesman,
        "Route4_PokemonCenter_1F_EventScript_Gentleman" to
            Route4_PokemonCenter_1F_EventScript_Gentleman,
        "Route4_PokemonCenter_1F_EventScript_Boy" to Route4_PokemonCenter_1F_EventScript_Boy,
        "Route4_PokemonCenter_1F_EventScript_Youngster" to
            Route4_PokemonCenter_1F_EventScript_Youngster,
        "Route4_PokemonCenter_1F_EventScript_Newspaper" to
            Route4_PokemonCenter_1F_EventScript_Newspaper,
    )
