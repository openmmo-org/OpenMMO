package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_POKEMANIAC_DAWSON, VictoryRoad_2F_Text_DawsonIntro, VictoryRoad_2F_Text_DawsonDefeat
 * msgbox VictoryRoad_2F_Text_DawsonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_Dawson : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_2F_EventScript_Dawson")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLACK_BELT_DAISUKE, VictoryRoad_2F_Text_DaisukeIntro, VictoryRoad_2F_Text_DaisukeDefeat
 * msgbox VictoryRoad_2F_Text_DaisukePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_Daisuke : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_2F_EventScript_Daisuke")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JUGGLER_NELSON, VictoryRoad_2F_Text_NelsonIntro, VictoryRoad_2F_Text_NelsonDefeat
 * msgbox VictoryRoad_2F_Text_NelsonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_Nelson : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_2F_EventScript_Nelson")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TAMER_VINCENT, VictoryRoad_2F_Text_VincentIntro, VictoryRoad_2F_Text_VincentDefeat
 * msgbox VictoryRoad_2F_Text_VincentPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_Vincent : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_2F_EventScript_Vincent")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JUGGLER_GREGORY, VictoryRoad_2F_Text_GregoryIntro, VictoryRoad_2F_Text_GregoryDefeat
 * msgbox VictoryRoad_2F_Text_GregoryPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_Gregory : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_2F_EventScript_Gregory")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GUARD_SPEC
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_ItemGuardSpec : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_2F_EventScript_ItemGuardSpec")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM07
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_ItemTM07 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_2F_EventScript_ItemTM07")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_HEAL
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_2F_EventScript_ItemFullHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM37
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_ItemTM37 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_2F_EventScript_ItemTM37")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_DOUBLE_EDGE, EventScript_DoubleEdgeTaught
 * msgbox Text_DoubleEdgeTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_DoubleEdgeDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_DoubleEdgeDeclined
 * msgbox Text_DoubleEdgeWhichMon
 * setvar VAR_0x8005, MOVETUTOR_DOUBLE_EDGE
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_DoubleEdgeDeclined
 * setflag FLAG_TUTOR_DOUBLE_EDGE
 * goto EventScript_DoubleEdgeTaught
 * end
 * ```
 */
internal object VictoryRoad_2F_EventScript_DoubleEdgeTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_2F_EventScript_DoubleEdgeTutor")
}

internal val VictoryRoad_2FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_2F_EventScript_Dawson" to VictoryRoad_2F_EventScript_Dawson,
        "VictoryRoad_2F_EventScript_Daisuke" to VictoryRoad_2F_EventScript_Daisuke,
        "VictoryRoad_2F_EventScript_Nelson" to VictoryRoad_2F_EventScript_Nelson,
        "VictoryRoad_2F_EventScript_Vincent" to VictoryRoad_2F_EventScript_Vincent,
        "VictoryRoad_2F_EventScript_Gregory" to VictoryRoad_2F_EventScript_Gregory,
        "VictoryRoad_2F_EventScript_ItemGuardSpec" to VictoryRoad_2F_EventScript_ItemGuardSpec,
        "VictoryRoad_2F_EventScript_ItemTM07" to VictoryRoad_2F_EventScript_ItemTM07,
        "VictoryRoad_2F_EventScript_ItemFullHeal" to VictoryRoad_2F_EventScript_ItemFullHeal,
        "VictoryRoad_2F_EventScript_ItemTM37" to VictoryRoad_2F_EventScript_ItemTM37,
        "VictoryRoad_2F_EventScript_DoubleEdgeTutor" to VictoryRoad_2F_EventScript_DoubleEdgeTutor,
    )
