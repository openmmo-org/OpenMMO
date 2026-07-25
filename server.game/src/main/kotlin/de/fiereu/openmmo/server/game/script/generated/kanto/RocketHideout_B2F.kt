package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_13, RocketHideout_B2F_Text_GruntIntro, RocketHideout_B2F_Text_GruntDefeat
 * msgbox RocketHideout_B2F_Text_GruntPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RocketHideout_B2F_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B2F_EventScript_Grunt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_X_SPEED
 * end
 * ```
 */
internal object RocketHideout_B2F_EventScript_ItemXSpeed : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B2F_EventScript_ItemXSpeed")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MOON_STONE
 * end
 * ```
 */
internal object RocketHideout_B2F_EventScript_ItemMoonStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B2F_EventScript_ItemMoonStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM12
 * end
 * ```
 */
internal object RocketHideout_B2F_EventScript_ItemTM12 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B2F_EventScript_ItemTM12")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SUPER_POTION
 * end
 * ```
 */
internal object RocketHideout_B2F_EventScript_ItemSuperPotion : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B2F_EventScript_ItemSuperPotion")
}

internal val RocketHideout_B2FScripts: Map<String, Script> =
    mapOf(
        "RocketHideout_B2F_EventScript_Grunt" to RocketHideout_B2F_EventScript_Grunt,
        "RocketHideout_B2F_EventScript_ItemXSpeed" to RocketHideout_B2F_EventScript_ItemXSpeed,
        "RocketHideout_B2F_EventScript_ItemMoonStone" to
            RocketHideout_B2F_EventScript_ItemMoonStone,
        "RocketHideout_B2F_EventScript_ItemTM12" to RocketHideout_B2F_EventScript_ItemTM12,
        "RocketHideout_B2F_EventScript_ItemSuperPotion" to
            RocketHideout_B2F_EventScript_ItemSuperPotion,
    )
