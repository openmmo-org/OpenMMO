package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_GEORGE, VictoryRoad_3F_Text_GeorgeIntro, VictoryRoad_3F_Text_GeorgeDefeat
 * msgbox VictoryRoad_3F_Text_GeorgePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_3F_EventScript_George : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_3F_EventScript_George")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_ALEXA, VictoryRoad_3F_Text_AlexaIntro, VictoryRoad_3F_Text_AlexaDefeat
 * msgbox VictoryRoad_3F_Text_AlexaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_3F_EventScript_Alexa : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_3F_EventScript_Alexa")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_COLBY, VictoryRoad_3F_Text_ColbyIntro, VictoryRoad_3F_Text_ColbyDefeat
 * msgbox VictoryRoad_3F_Text_ColbyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_3F_EventScript_Colby : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_3F_EventScript_Colby")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_CAROLINE, VictoryRoad_3F_Text_CarolineIntro, VictoryRoad_3F_Text_CarolineDefeat
 * msgbox VictoryRoad_3F_Text_CarolinePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_3F_EventScript_Caroline : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_3F_EventScript_Caroline")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REVIVE
 * end
 * ```
 */
internal object VictoryRoad_3F_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_3F_EventScript_ItemMaxRevive")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM50
 * end
 * ```
 */
internal object VictoryRoad_3F_EventScript_ItemTM50 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_3F_EventScript_ItemTM50")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_COOL_COUPLE_RAY_TYRA, VictoryRoad_3F_Text_RayIntro, VictoryRoad_3F_Text_RayDefeat, VictoryRoad_3F_Text_RayNotEnoughMons
 * msgbox VictoryRoad_3F_Text_RayPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_3F_EventScript_Ray : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_3F_EventScript_Ray")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_COOL_COUPLE_RAY_TYRA, VictoryRoad_3F_Text_TyraIntro, VictoryRoad_3F_Text_TyraDefeat, VictoryRoad_3F_Text_TyraNotEnoughMons
 * msgbox VictoryRoad_3F_Text_TyraPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_3F_EventScript_Tyra : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_3F_EventScript_Tyra")
}

internal val VictoryRoad_3FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_3F_EventScript_George" to VictoryRoad_3F_EventScript_George,
        "VictoryRoad_3F_EventScript_Alexa" to VictoryRoad_3F_EventScript_Alexa,
        "VictoryRoad_3F_EventScript_Colby" to VictoryRoad_3F_EventScript_Colby,
        "VictoryRoad_3F_EventScript_Caroline" to VictoryRoad_3F_EventScript_Caroline,
        "VictoryRoad_3F_EventScript_ItemMaxRevive" to VictoryRoad_3F_EventScript_ItemMaxRevive,
        "VictoryRoad_3F_EventScript_ItemTM50" to VictoryRoad_3F_EventScript_ItemTM50,
        "VictoryRoad_3F_EventScript_Ray" to VictoryRoad_3F_EventScript_Ray,
        "VictoryRoad_3F_EventScript_Tyra" to VictoryRoad_3F_EventScript_Tyra,
    )
