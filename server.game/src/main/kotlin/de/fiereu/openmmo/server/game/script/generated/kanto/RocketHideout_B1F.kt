package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_9, RocketHideout_B1F_Text_Grunt2Intro, RocketHideout_B1F_Text_Grunt2Defeat
 * msgbox RocketHideout_B1F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RocketHideout_B1F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B1F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_8, RocketHideout_B1F_Text_Grunt1Intro, RocketHideout_B1F_Text_Grunt1Defeat
 * msgbox RocketHideout_B1F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RocketHideout_B1F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B1F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_11, RocketHideout_B1F_Text_Grunt4Intro, RocketHideout_B1F_Text_Grunt4Defeat
 * msgbox RocketHideout_B1F_Text_Grunt4PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RocketHideout_B1F_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B1F_EventScript_Grunt4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_10, RocketHideout_B1F_Text_Grunt3Intro, RocketHideout_B1F_Text_Grunt3Defeat
 * msgbox RocketHideout_B1F_Text_Grunt3PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RocketHideout_B1F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B1F_EventScript_Grunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_12, RocketHideout_B1F_Text_Grunt5Intro, RocketHideout_B1F_Text_Grunt5Defeat, RocketHideout_B1F_EventScript_DefeatedGrunt5
 * msgbox RocketHideout_B1F_Text_Grunt5PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RocketHideout_B1F_EventScript_Grunt5 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B1F_EventScript_Grunt5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object RocketHideout_B1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B1F_EventScript_ItemEscapeRope")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HYPER_POTION
 * end
 * ```
 */
internal object RocketHideout_B1F_EventScript_ItemHyperPotion : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B1F_EventScript_ItemHyperPotion")
}

internal val RocketHideout_B1FScripts: Map<String, Script> =
    mapOf(
        "RocketHideout_B1F_EventScript_Grunt2" to RocketHideout_B1F_EventScript_Grunt2,
        "RocketHideout_B1F_EventScript_Grunt1" to RocketHideout_B1F_EventScript_Grunt1,
        "RocketHideout_B1F_EventScript_Grunt4" to RocketHideout_B1F_EventScript_Grunt4,
        "RocketHideout_B1F_EventScript_Grunt3" to RocketHideout_B1F_EventScript_Grunt3,
        "RocketHideout_B1F_EventScript_Grunt5" to RocketHideout_B1F_EventScript_Grunt5,
        "RocketHideout_B1F_EventScript_ItemEscapeRope" to
            RocketHideout_B1F_EventScript_ItemEscapeRope,
        "RocketHideout_B1F_EventScript_ItemHyperPotion" to
            RocketHideout_B1F_EventScript_ItemHyperPotion,
    )
