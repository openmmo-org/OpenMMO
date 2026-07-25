package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_RocketWarehouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_47, FiveIsland_RocketWarehouse_Text_Grunt2Intro, FiveIsland_RocketWarehouse_Text_Grunt2Defeat
 * msgbox FiveIsland_RocketWarehouse_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_48, FiveIsland_RocketWarehouse_Text_Grunt3Intro, FiveIsland_RocketWarehouse_Text_Grunt3Defeat
 * msgbox FiveIsland_RocketWarehouse_Text_Grunt3PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_Grunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_ADMIN, FiveIsland_RocketWarehouse_Text_Admin1Intro, FiveIsland_RocketWarehouse_Text_Admin1Defeat, FiveIsland_RocketWarehouse_EventScript_DefeatedAdmin1
 * msgbox FiveIsland_RocketWarehouse_Text_MadeItSoYouCanComeBackThrough, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_Admin1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_Admin1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_ADMIN_2, FiveIsland_RocketWarehouse_Text_Admin2Intro, FiveIsland_RocketWarehouse_Text_Admin2Defeat, FiveIsland_RocketWarehouse_EventScript_DefeatedAdmin2
 * msgbox FiveIsland_RocketWarehouse_Text_Admin2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_Admin2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_Admin2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_42, FiveIsland_RocketWarehouse_Text_Grunt1Intro, FiveIsland_RocketWarehouse_Text_Grunt1Defeat
 * msgbox FiveIsland_RocketWarehouse_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_GIDEON, FiveIsland_RocketWarehouse_Text_GideonIntro, FiveIsland_RocketWarehouse_Text_GideonDefeat, FiveIsland_RocketWarehouse_EventScript_DefeatedGideon
 * goto_if_set FLAG_SYS_CAN_LINK_WITH_RS, FiveIsland_RocketWarehouse_EventScript_MentionGiovannisKid
 * msgbox FiveIsland_RocketWarehouse_Text_GetLostLeaveMeBe, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_Gideon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_Gideon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BIG_PEARL
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_ItemBigPearl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_ItemBigPearl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM36
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_ItemTM36 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_ItemTM36")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PEARL
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_ItemPearl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_ItemPearl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_UP_GRADE
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_ItemUpGrade : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_ItemUpGrade")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_DEFEATED_ROCKETS_IN_WAREHOUSE, FiveIsland_RocketWarehouse_EventScript_CageUnlocked
 * msgbox FiveIsland_RocketWarehouse_Text_ManyMonsLockedInPen
 * releaseall
 * end
 * ```
 */
internal object FiveIsland_RocketWarehouse_EventScript_Cage : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_RocketWarehouse_EventScript_Cage")
}

internal object FiveIsland_RocketWarehouse_EventScript_Computer : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(FiveIsland_RocketWarehouse.ReportWithGraphsAndText)
}

internal val FiveIsland_RocketWarehouseScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_RocketWarehouse_EventScript_Grunt2" to
            FiveIsland_RocketWarehouse_EventScript_Grunt2,
        "FiveIsland_RocketWarehouse_EventScript_Grunt3" to
            FiveIsland_RocketWarehouse_EventScript_Grunt3,
        "FiveIsland_RocketWarehouse_EventScript_Admin1" to
            FiveIsland_RocketWarehouse_EventScript_Admin1,
        "FiveIsland_RocketWarehouse_EventScript_Admin2" to
            FiveIsland_RocketWarehouse_EventScript_Admin2,
        "FiveIsland_RocketWarehouse_EventScript_Grunt1" to
            FiveIsland_RocketWarehouse_EventScript_Grunt1,
        "FiveIsland_RocketWarehouse_EventScript_Gideon" to
            FiveIsland_RocketWarehouse_EventScript_Gideon,
        "FiveIsland_RocketWarehouse_EventScript_ItemBigPearl" to
            FiveIsland_RocketWarehouse_EventScript_ItemBigPearl,
        "FiveIsland_RocketWarehouse_EventScript_ItemTM36" to
            FiveIsland_RocketWarehouse_EventScript_ItemTM36,
        "FiveIsland_RocketWarehouse_EventScript_ItemPearl" to
            FiveIsland_RocketWarehouse_EventScript_ItemPearl,
        "FiveIsland_RocketWarehouse_EventScript_ItemUpGrade" to
            FiveIsland_RocketWarehouse_EventScript_ItemUpGrade,
        "FiveIsland_RocketWarehouse_EventScript_Cage" to
            FiveIsland_RocketWarehouse_EventScript_Cage,
        "FiveIsland_RocketWarehouse_EventScript_Computer" to
            FiveIsland_RocketWarehouse_EventScript_Computer,
    )
