package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * pike_getroomtype
 * switch VAR_RESULT
 * case PIKE_ROOM_NPC, BattleFrontier_BattlePikeRoomNormal_EventScript_NormalNPC
 * case PIKE_ROOM_STATUS, BattleFrontier_BattlePikeRoomNormal_EventScript_StatusNPC
 * case PIKE_ROOM_HEAL_PART, BattleFrontier_BattlePikeRoomNormal_EventScript_HealNPC
 * lock
 * faceplayer
 * pike_getnpcmsg
 * msgbox gStringVar4, MSGBOX_DEFAULT
 * waitmessage
 * closemessage
 * release
 * end
 * ```
 */
internal object BattleFrontier_BattlePikeRoomNormal_EventScript_NPC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattlePikeRoomNormal_EventScript_NPC")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_BattlePikeRoomNormal_Text_Silence, MSGBOX_DEFAULT
 * closemessage
 * release
 * applymovement LOCALID_PIKE_ROOM_NPC_2, BattleFrontier_BattlePikeRoomNormal_Movement_MonFaceRight
 * waitmovement 0
 * end
 * ```
 */
internal object BattleFrontier_BattlePikeRoomNormal_EventScript_StatusMon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattlePikeRoomNormal_EventScript_StatusMon")
}

internal val BattleFrontier_BattlePikeRoomNormalScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_BattlePikeRoomNormal_EventScript_NPC" to
            BattleFrontier_BattlePikeRoomNormal_EventScript_NPC,
        "BattleFrontier_BattlePikeRoomNormal_EventScript_StatusMon" to
            BattleFrontier_BattlePikeRoomNormal_EventScript_StatusMon,
    )
