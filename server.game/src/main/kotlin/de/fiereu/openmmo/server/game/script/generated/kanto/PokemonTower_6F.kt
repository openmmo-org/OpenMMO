package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_ANGELICA, PokemonTower_6F_Text_AngelicaIntro, PokemonTower_6F_Text_AngelicaDefeat
 * msgbox PokemonTower_6F_Text_AngelicaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_6F_EventScript_Angelica : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_6F_EventScript_Angelica")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_JENNIFER, PokemonTower_6F_Text_JenniferIntro, PokemonTower_6F_Text_JenniferDefeat
 * msgbox PokemonTower_6F_Text_JenniferPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_6F_EventScript_Jennifer : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_6F_EventScript_Jennifer")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_EMILIA, PokemonTower_6F_Text_EmiliaIntro, PokemonTower_6F_Text_EmiliaDefeat
 * msgbox PokemonTower_6F_Text_EmiliaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_6F_EventScript_Emilia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_6F_EventScript_Emilia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object PokemonTower_6F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonTower_6F_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_X_ACCURACY
 * end
 * ```
 */
internal object PokemonTower_6F_EventScript_ItemXAccuracy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonTower_6F_EventScript_ItemXAccuracy")
}

internal val PokemonTower_6FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_6F_EventScript_Angelica" to PokemonTower_6F_EventScript_Angelica,
        "PokemonTower_6F_EventScript_Jennifer" to PokemonTower_6F_EventScript_Jennifer,
        "PokemonTower_6F_EventScript_Emilia" to PokemonTower_6F_EventScript_Emilia,
        "PokemonTower_6F_EventScript_ItemRareCandy" to PokemonTower_6F_EventScript_ItemRareCandy,
        "PokemonTower_6F_EventScript_ItemXAccuracy" to PokemonTower_6F_EventScript_ItemXAccuracy,
    )
