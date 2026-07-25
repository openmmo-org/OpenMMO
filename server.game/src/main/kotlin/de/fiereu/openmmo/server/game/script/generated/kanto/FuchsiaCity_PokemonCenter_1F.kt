package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FuchsiaCity_PokemonCenter_1F
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
internal object FuchsiaCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FuchsiaCity_PokemonCenter_1F_EventScript_Nurse")
}

internal object FuchsiaCity_PokemonCenter_1F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FuchsiaCity_PokemonCenter_1F.CantBecomeGoodTrainerWithOneMon)
}

internal object FuchsiaCity_PokemonCenter_1F_EventScript_CooltrainerF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FuchsiaCity_PokemonCenter_1F.PokemonLeagueWestOfViridian)
}

internal object FuchsiaCity_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FuchsiaCity_PokemonCenter_1F.VisitSafariZoneForPokedex)
}

internal val FuchsiaCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_PokemonCenter_1F_EventScript_Nurse" to
            FuchsiaCity_PokemonCenter_1F_EventScript_Nurse,
        "FuchsiaCity_PokemonCenter_1F_EventScript_Man" to
            FuchsiaCity_PokemonCenter_1F_EventScript_Man,
        "FuchsiaCity_PokemonCenter_1F_EventScript_CooltrainerF" to
            FuchsiaCity_PokemonCenter_1F_EventScript_CooltrainerF,
        "FuchsiaCity_PokemonCenter_1F_EventScript_Youngster" to
            FuchsiaCity_PokemonCenter_1F_EventScript_Youngster,
    )
