package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_RankingHall
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object BattleFrontier_RankingHall_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_RankingHall.ExplainRankingHall)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_RankingHall_Text_IsYourNameOnThisList, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, BattleFrontier_RankingHall_EventScript_NinjaBoyNameOnList
 * msgbox BattleFrontier_RankingHall_Text_WorkHarderIfYouSawFriendsName, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_NinjaBoy")
}

internal object BattleFrontier_RankingHall_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_RankingHall.MyNamesNotUpThere)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_TOWER_SINGLES
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_TowerSinglesRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_TowerSinglesRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_TOWER_DOUBLES
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_TowerDoublesRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_TowerDoublesRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_TOWER_MULTIS
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_TowerMultisRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_TowerMultisRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_TOWER_LINK
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_TowerLinkRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_TowerLinkRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_ARENA
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_ArenaRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_ArenaRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_PALACE
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_PalaceRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_PalaceRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_FACTORY
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_FactoryRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_FactoryRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_DOME
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_DomeRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_DomeRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_PIKE
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_PikeRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_PikeRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, RANKING_HALL_PYRAMID
 * goto BattleFrontier_RankingHall_EventScript_ShowRecords
 * end
 * ```
 */
internal object BattleFrontier_RankingHall_EventScript_PyramidRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_RankingHall_EventScript_PyramidRecords")
}

internal object BattleFrontier_RankingHall_EventScript_DomePikeFactoryRecordsSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(BattleFrontier_RankingHall.DomePikeFactoryRecords)
}

internal object BattleFrontier_RankingHall_EventScript_PalaceArenaPyramidRecordsSIgn : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(BattleFrontier_RankingHall.PalaceArenaPyramidRecords)
}

internal val BattleFrontier_RankingHallScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_RankingHall_EventScript_Attendant" to
            BattleFrontier_RankingHall_EventScript_Attendant,
        "BattleFrontier_RankingHall_EventScript_NinjaBoy" to
            BattleFrontier_RankingHall_EventScript_NinjaBoy,
        "BattleFrontier_RankingHall_EventScript_Boy" to BattleFrontier_RankingHall_EventScript_Boy,
        "BattleFrontier_RankingHall_EventScript_TowerSinglesRecords" to
            BattleFrontier_RankingHall_EventScript_TowerSinglesRecords,
        "BattleFrontier_RankingHall_EventScript_TowerDoublesRecords" to
            BattleFrontier_RankingHall_EventScript_TowerDoublesRecords,
        "BattleFrontier_RankingHall_EventScript_TowerMultisRecords" to
            BattleFrontier_RankingHall_EventScript_TowerMultisRecords,
        "BattleFrontier_RankingHall_EventScript_TowerLinkRecords" to
            BattleFrontier_RankingHall_EventScript_TowerLinkRecords,
        "BattleFrontier_RankingHall_EventScript_ArenaRecords" to
            BattleFrontier_RankingHall_EventScript_ArenaRecords,
        "BattleFrontier_RankingHall_EventScript_PalaceRecords" to
            BattleFrontier_RankingHall_EventScript_PalaceRecords,
        "BattleFrontier_RankingHall_EventScript_FactoryRecords" to
            BattleFrontier_RankingHall_EventScript_FactoryRecords,
        "BattleFrontier_RankingHall_EventScript_DomeRecords" to
            BattleFrontier_RankingHall_EventScript_DomeRecords,
        "BattleFrontier_RankingHall_EventScript_PikeRecords" to
            BattleFrontier_RankingHall_EventScript_PikeRecords,
        "BattleFrontier_RankingHall_EventScript_PyramidRecords" to
            BattleFrontier_RankingHall_EventScript_PyramidRecords,
        "BattleFrontier_RankingHall_EventScript_DomePikeFactoryRecordsSign" to
            BattleFrontier_RankingHall_EventScript_DomePikeFactoryRecordsSign,
        "BattleFrontier_RankingHall_EventScript_PalaceArenaPyramidRecordsSIgn" to
            BattleFrontier_RankingHall_EventScript_PalaceArenaPyramidRecordsSIgn,
    )
