package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_PokemonCenter_1F
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
internal object FiveIsland_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_PokemonCenter_1F_EventScript_Nurse")
}

internal object FiveIsland_PokemonCenter_1F_EventScript_CooltrainerF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FiveIsland_PokemonCenter_1F.YoureOnQuestToCatchEveryMon)
}

internal object FiveIsland_PokemonCenter_1F_EventScript_Chef : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FiveIsland_PokemonCenter_1F.OnMyWayToPrepareMeal)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_LORELEI, 3
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureLorelei
 * release
 * end
 * ```
 */
internal object FiveIsland_PokemonCenter_1F_EventScript_PokemonJournal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_PokemonCenter_1F_EventScript_PokemonJournal")
}

internal val FiveIsland_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_PokemonCenter_1F_EventScript_Nurse" to
            FiveIsland_PokemonCenter_1F_EventScript_Nurse,
        "FiveIsland_PokemonCenter_1F_EventScript_CooltrainerF" to
            FiveIsland_PokemonCenter_1F_EventScript_CooltrainerF,
        "FiveIsland_PokemonCenter_1F_EventScript_Chef" to
            FiveIsland_PokemonCenter_1F_EventScript_Chef,
        "FiveIsland_PokemonCenter_1F_EventScript_PokemonJournal" to
            FiveIsland_PokemonCenter_1F_EventScript_PokemonJournal,
    )
