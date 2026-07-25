package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FourIsland_PokemonCenter_1F
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
internal object FourIsland_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FourIsland_PokemonCenter_1F_EventScript_Nurse")
}

internal object FourIsland_PokemonCenter_1F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FourIsland_PokemonCenter_1F.RareIceMonsInCave)
}

internal object FourIsland_PokemonCenter_1F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FourIsland_PokemonCenter_1F.DayCarePeopleFoundEggBefore)
}

internal object FourIsland_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FourIsland_PokemonCenter_1F.NewPokemonJournalHasntArrived)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_DAISY, 5
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureDaisyOak
 * releaseall
 * end
 * ```
 */
internal object FourIsland_PokemonCenter_1F_EventScript_PokemonJournal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FourIsland_PokemonCenter_1F_EventScript_PokemonJournal")
}

internal val FourIsland_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "FourIsland_PokemonCenter_1F_EventScript_Nurse" to
            FourIsland_PokemonCenter_1F_EventScript_Nurse,
        "FourIsland_PokemonCenter_1F_EventScript_Man" to
            FourIsland_PokemonCenter_1F_EventScript_Man,
        "FourIsland_PokemonCenter_1F_EventScript_Woman" to
            FourIsland_PokemonCenter_1F_EventScript_Woman,
        "FourIsland_PokemonCenter_1F_EventScript_Gentleman" to
            FourIsland_PokemonCenter_1F_EventScript_Gentleman,
        "FourIsland_PokemonCenter_1F_EventScript_PokemonJournal" to
            FourIsland_PokemonCenter_1F_EventScript_PokemonJournal,
    )
