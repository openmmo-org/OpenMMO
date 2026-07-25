package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FortreeCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FortreeCity_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity.SawGiganticPokemonInSky)
}

internal object FortreeCity_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity.TreesGrowByDrinkingRainwater)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_KECLEON_FLED_FORTREE, FortreeCity_EventScript_WomanGymAccessible
 * msgbox FortreeCity_Text_SomethingBlockingGym, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FortreeCity_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_EventScript_Woman")
}

internal object FortreeCity_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity.BugPokemonComeThroughWindow)
}

internal object FortreeCity_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity.EveryoneHealthyAndLively)
}

internal object FortreeCity_EventScript_GameboyKid : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FortreeCity.PokemonThatEvolveWhenTraded)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * checkitem ITEM_DEVON_SCOPE
 * goto_if_eq VAR_RESULT, TRUE, FortreeCity_EventScript_AskUseDevonScope
 * msgbox FortreeCity_Text_SomethingUnseeable, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FortreeCity_EventScript_Kecleon : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_EventScript_Kecleon")
}

internal object FortreeCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FortreeCity.CitySign)
}

internal object FortreeCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FortreeCity.GymSign)
}

internal val FortreeCityScripts: Map<String, Script> =
    mapOf(
        "FortreeCity_EventScript_Man" to FortreeCity_EventScript_Man,
        "FortreeCity_EventScript_Girl" to FortreeCity_EventScript_Girl,
        "FortreeCity_EventScript_Woman" to FortreeCity_EventScript_Woman,
        "FortreeCity_EventScript_Boy" to FortreeCity_EventScript_Boy,
        "FortreeCity_EventScript_OldMan" to FortreeCity_EventScript_OldMan,
        "FortreeCity_EventScript_GameboyKid" to FortreeCity_EventScript_GameboyKid,
        "FortreeCity_EventScript_Kecleon" to FortreeCity_EventScript_Kecleon,
        "FortreeCity_EventScript_CitySign" to FortreeCity_EventScript_CitySign,
        "FortreeCity_EventScript_GymSign" to FortreeCity_EventScript_GymSign,
    )
