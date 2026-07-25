package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity_PokemonCenter_1F
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
internal object CeruleanCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCity_PokemonCenter_1F_EventScript_Nurse")
}

internal object CeruleanCity_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_PokemonCenter_1F.EveryoneCallsBillPokemaniac)
}

internal object CeruleanCity_PokemonCenter_1F_EventScript_Rocker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_PokemonCenter_1F.BillDoesWhateverForRareMons)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BILL, 1
 * msgbox CeruleanCity_PokemonCenter_1F_Text_BillCollectsRareMons
 * release
 * end
 * ```
 */
internal object CeruleanCity_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCity_PokemonCenter_1F_EventScript_Youngster")
}

internal object CeruleanCity_PokemonCenter_1F_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeruleanCity_PokemonCenter_1F.TryTradingUpstairs)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_MISTY, 5
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureMisty
 * release
 * end
 * ```
 */
internal object CeruleanCity_PokemonCenter_1F_EventScript_PokemonJournalMisty : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCity_PokemonCenter_1F_EventScript_PokemonJournalMisty")
}

internal val CeruleanCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_PokemonCenter_1F_EventScript_Nurse" to
            CeruleanCity_PokemonCenter_1F_EventScript_Nurse,
        "CeruleanCity_PokemonCenter_1F_EventScript_Gentleman" to
            CeruleanCity_PokemonCenter_1F_EventScript_Gentleman,
        "CeruleanCity_PokemonCenter_1F_EventScript_Rocker" to
            CeruleanCity_PokemonCenter_1F_EventScript_Rocker,
        "CeruleanCity_PokemonCenter_1F_EventScript_Youngster" to
            CeruleanCity_PokemonCenter_1F_EventScript_Youngster,
        "CeruleanCity_PokemonCenter_1F_EventScript_Lass" to
            CeruleanCity_PokemonCenter_1F_EventScript_Lass,
        "CeruleanCity_PokemonCenter_1F_EventScript_PokemonJournalMisty" to
            CeruleanCity_PokemonCenter_1F_EventScript_PokemonJournalMisty,
    )
