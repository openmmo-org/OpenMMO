package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_LAUREL, PokemonTower_4F_Text_LaurelIntro, PokemonTower_4F_Text_LaurelDefeat
 * msgbox PokemonTower_4F_Text_LaurelPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_4F_EventScript_Laurel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_4F_EventScript_Laurel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_JODY, PokemonTower_4F_Text_JodyIntro, PokemonTower_4F_Text_JodyDefeat
 * msgbox PokemonTower_4F_Text_JodyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_4F_EventScript_Jody : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_4F_EventScript_Jody")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_PAULA, PokemonTower_4F_Text_PaulaIntro, PokemonTower_4F_Text_PaulaDefeat
 * msgbox PokemonTower_4F_Text_PaulaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_4F_EventScript_Paula : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_4F_EventScript_Paula")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ELIXIR
 * end
 * ```
 */
internal object PokemonTower_4F_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_4F_EventScript_ItemElixir")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_AWAKENING
 * end
 * ```
 */
internal object PokemonTower_4F_EventScript_ItemAwakening : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonTower_4F_EventScript_ItemAwakening")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GREAT_BALL
 * end
 * ```
 */
internal object PokemonTower_4F_EventScript_ItemGreatBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonTower_4F_EventScript_ItemGreatBall")
}

internal val PokemonTower_4FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_4F_EventScript_Laurel" to PokemonTower_4F_EventScript_Laurel,
        "PokemonTower_4F_EventScript_Jody" to PokemonTower_4F_EventScript_Jody,
        "PokemonTower_4F_EventScript_Paula" to PokemonTower_4F_EventScript_Paula,
        "PokemonTower_4F_EventScript_ItemElixir" to PokemonTower_4F_EventScript_ItemElixir,
        "PokemonTower_4F_EventScript_ItemAwakening" to PokemonTower_4F_EventScript_ItemAwakening,
        "PokemonTower_4F_EventScript_ItemGreatBall" to PokemonTower_4F_EventScript_ItemGreatBall,
    )
