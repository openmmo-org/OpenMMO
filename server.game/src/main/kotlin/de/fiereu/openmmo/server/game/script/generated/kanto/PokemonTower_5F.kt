package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonTower_5F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_RUTH, PokemonTower_5F_Text_RuthIntro, PokemonTower_5F_Text_RuthDefeat
 * msgbox PokemonTower_5F_Text_RuthPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_5F_EventScript_Ruth : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_5F_EventScript_Ruth")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_TAMMY, PokemonTower_5F_Text_TammyIntro, PokemonTower_5F_Text_TammyDefeat
 * msgbox PokemonTower_5F_Text_TammyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_5F_EventScript_Tammy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_5F_EventScript_Tammy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_KARINA, PokemonTower_5F_Text_KarinaIntro, PokemonTower_5F_Text_KarinaDefeat
 * msgbox PokemonTower_5F_Text_KarinaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_5F_EventScript_Karina : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_5F_EventScript_Karina")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_JANAE, PokemonTower_5F_Text_JanaeIntro, PokemonTower_5F_Text_JanaeDefeat
 * msgbox PokemonTower_5F_Text_JanaePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_5F_EventScript_Janae : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_5F_EventScript_Janae")
}

internal object PokemonTower_5F_EventScript_Channeler : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonTower_5F.RestHereInPurifiedSpace)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object PokemonTower_5F_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_5F_EventScript_ItemNugget")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_CLEANSE_TAG
 * end
 * ```
 */
internal object PokemonTower_5F_EventScript_ItemCleanseTag : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonTower_5F_EventScript_ItemCleanseTag")
}

internal val PokemonTower_5FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_5F_EventScript_Ruth" to PokemonTower_5F_EventScript_Ruth,
        "PokemonTower_5F_EventScript_Tammy" to PokemonTower_5F_EventScript_Tammy,
        "PokemonTower_5F_EventScript_Karina" to PokemonTower_5F_EventScript_Karina,
        "PokemonTower_5F_EventScript_Janae" to PokemonTower_5F_EventScript_Janae,
        "PokemonTower_5F_EventScript_Channeler" to PokemonTower_5F_EventScript_Channeler,
        "PokemonTower_5F_EventScript_ItemNugget" to PokemonTower_5F_EventScript_ItemNugget,
        "PokemonTower_5F_EventScript_ItemCleanseTag" to PokemonTower_5F_EventScript_ItemCleanseTag,
    )
