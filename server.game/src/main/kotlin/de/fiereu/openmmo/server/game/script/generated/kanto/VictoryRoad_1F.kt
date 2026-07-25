package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_ROLANDO, VictoryRoad_1F_Text_RolandoIntro, VictoryRoad_1F_Text_RolandoDefeat
 * msgbox VictoryRoad_1F_Text_RolandoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_Rolando : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_Rolando")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_NAOMI, VictoryRoad_1F_Text_NaomiIntro, VictoryRoad_1F_Text_NaomiDefeat
 * msgbox VictoryRoad_1F_Text_NaomiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_Naomi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_Naomi")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_1F_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM02
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_ItemTM02 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_ItemTM02")
}

internal val VictoryRoad_1FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_1F_EventScript_Rolando" to VictoryRoad_1F_EventScript_Rolando,
        "VictoryRoad_1F_EventScript_Naomi" to VictoryRoad_1F_EventScript_Naomi,
        "VictoryRoad_1F_EventScript_ItemRareCandy" to VictoryRoad_1F_EventScript_ItemRareCandy,
        "VictoryRoad_1F_EventScript_ItemTM02" to VictoryRoad_1F_EventScript_ItemTM02,
    )
