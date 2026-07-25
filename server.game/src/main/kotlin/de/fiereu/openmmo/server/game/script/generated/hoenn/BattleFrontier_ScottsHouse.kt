package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TEMP_4, BattleFrontier_ScottsHouse_EventScript_GivenBerry
 * goto_if_set FLAG_TEMP_3, BattleFrontier_ScottsHouse_EventScript_GivenShield
 * goto_if_set FLAG_TEMP_2, BattleFrontier_ScottsHouse_EventScript_GivenBattlePoints
 * goto BattleFrontier_ScottsHouse_EventScript_CheckGiveItems
 * end
 * ```
 */
internal object BattleFrontier_ScottsHouse_EventScript_Scott : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_ScottsHouse_EventScript_Scott")
}

internal val BattleFrontier_ScottsHouseScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_ScottsHouse_EventScript_Scott" to
            BattleFrontier_ScottsHouse_EventScript_Scott,
    )
