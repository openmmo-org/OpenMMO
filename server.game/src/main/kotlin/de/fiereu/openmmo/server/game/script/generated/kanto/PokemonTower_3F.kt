package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_PATRICIA, PokemonTower_3F_Text_PatriciaIntro, PokemonTower_3F_Text_PatriciaDefeat
 * msgbox PokemonTower_3F_Text_PatriciaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_3F_EventScript_Patricia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_3F_EventScript_Patricia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_CARLY, PokemonTower_3F_Text_CarlyIntro, PokemonTower_3F_Text_CarlyDefeat
 * msgbox PokemonTower_3F_Text_CarlyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_3F_EventScript_Carly : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_3F_EventScript_Carly")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_HOPE, PokemonTower_3F_Text_HopeIntro, PokemonTower_3F_Text_HopeDefeat
 * msgbox PokemonTower_3F_Text_HopePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_3F_EventScript_Hope : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_3F_EventScript_Hope")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object PokemonTower_3F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonTower_3F_EventScript_ItemEscapeRope")
}

internal val PokemonTower_3FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_3F_EventScript_Patricia" to PokemonTower_3F_EventScript_Patricia,
        "PokemonTower_3F_EventScript_Carly" to PokemonTower_3F_EventScript_Carly,
        "PokemonTower_3F_EventScript_Hope" to PokemonTower_3F_EventScript_Hope,
        "PokemonTower_3F_EventScript_ItemEscapeRope" to PokemonTower_3F_EventScript_ItemEscapeRope,
    )
