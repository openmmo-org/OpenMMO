package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MET_DIVING_TREASURE_HUNTER, Route124_DivingTreasureHuntersHouse_EventScript_SkipGreeting
 * msgbox Route124_DivingTreasureHuntersHouse_Text_Greeting, MSGBOX_DEFAULT
 * setflag FLAG_MET_DIVING_TREASURE_HUNTER
 * goto Route124_DivingTreasureHuntersHouse_EventScript_CheckPlayerHasShard
 * end
 * ```
 */
internal object Route124_DivingTreasureHuntersHouse_EventScript_TreasureHunter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route124_DivingTreasureHuntersHouse_EventScript_TreasureHunter")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * msgbox Route124_DivingTreasureHuntersHouse_Text_ShardTradeBoard, MSGBOX_SIGN
 * end
 * ```
 */
internal object Route124_DivingTreasureHuntersHouse_EventScript_ShardTradeBoard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route124_DivingTreasureHuntersHouse_EventScript_ShardTradeBoard")
}

internal val Route124_DivingTreasureHuntersHouseScripts: Map<String, Script> =
    mapOf(
        "Route124_DivingTreasureHuntersHouse_EventScript_TreasureHunter" to
            Route124_DivingTreasureHuntersHouse_EventScript_TreasureHunter,
        "Route124_DivingTreasureHuntersHouse_EventScript_ShardTradeBoard" to
            Route124_DivingTreasureHuntersHouse_EventScript_ShardTradeBoard,
    )
