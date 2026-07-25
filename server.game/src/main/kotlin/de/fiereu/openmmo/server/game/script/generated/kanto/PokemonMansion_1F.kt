package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_TED, PokemonMansion_1F_Text_TedIntro, PokemonMansion_1F_Text_TedDefeat
 * msgbox PokemonMansion_1F_Text_TedPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonMansion_1F_EventScript_Ted : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_1F_EventScript_Ted")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_CARBOS
 * end
 * ```
 */
internal object PokemonMansion_1F_EventScript_ItemCarbos : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonMansion_1F_EventScript_ItemCarbos")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object PokemonMansion_1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonMansion_1F_EventScript_ItemEscapeRope")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PROTEIN
 * end
 * ```
 */
internal object PokemonMansion_1F_EventScript_ItemProtein : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonMansion_1F_EventScript_ItemProtein")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_YOUNGSTER_JOHNSON, PokemonMansion_1F_Text_JohnsonIntro, PokemonMansion_1F_Text_JohnsonDefeat
 * msgbox PokemonMansion_1F_Text_JohnsonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonMansion_1F_EventScript_Johnson : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_1F_EventScript_Johnson")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 0
 * call PokemonMansion_EventScript_SecretSwitch
 * playse SE_UNLOCK
 * special DrawWholeMapView
 * waitse
 * releaseall
 * end
 * ```
 */
internal object PokemonMansion_1F_EventScript_Statue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_1F_EventScript_Statue")
}

internal val PokemonMansion_1FScripts: Map<String, Script> =
    mapOf(
        "PokemonMansion_1F_EventScript_Ted" to PokemonMansion_1F_EventScript_Ted,
        "PokemonMansion_1F_EventScript_ItemCarbos" to PokemonMansion_1F_EventScript_ItemCarbos,
        "PokemonMansion_1F_EventScript_ItemEscapeRope" to
            PokemonMansion_1F_EventScript_ItemEscapeRope,
        "PokemonMansion_1F_EventScript_ItemProtein" to PokemonMansion_1F_EventScript_ItemProtein,
        "PokemonMansion_1F_EventScript_Johnson" to PokemonMansion_1F_EventScript_Johnson,
        "PokemonMansion_1F_EventScript_Statue" to PokemonMansion_1F_EventScript_Statue,
    )
