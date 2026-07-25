package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * special Script_FacePlayer
 * msgbox BattleColosseum_2P_Text_TakePlaceStartBattle, MSGBOX_DEFAULT
 * special Script_ClearHeldMovement
 * closemessage
 * end
 * ```
 */
internal object BattleColosseum_2P_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleColosseum_2P_EventScript_Attendant")
}

internal val BattleColosseum_2PScripts: Map<String, Script> =
    mapOf(
        "BattleColosseum_2P_EventScript_Attendant" to BattleColosseum_2P_EventScript_Attendant,
    )
