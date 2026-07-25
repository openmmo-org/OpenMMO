package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox CaveOfOrigin_B1F_Text_WallaceStory, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_CAVE_OF_ORIGIN_WALLACE, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * delay 60
 * playse SE_PIN
 * applymovement LOCALID_CAVE_OF_ORIGIN_WALLACE, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_CAVE_OF_ORIGIN_WALLACE, Common_Movement_Delay48
 * waitmovement 0
 * delay 30
 * applymovement LOCALID_CAVE_OF_ORIGIN_WALLACE, Common_Movement_FacePlayer
 * waitmovement 0
 * message CaveOfOrigin_B1F_Text_WhereIsRayquaza
 * waitmessage
 * goto CaveOfOrigin_B1F_EventScript_WheresRayquaza
 * ```
 */
internal object CaveOfOrigin_B1F_EventScript_Wallace : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CaveOfOrigin_B1F_EventScript_Wallace")
}

internal val CaveOfOrigin_B1FScripts: Map<String, Script> =
    mapOf(
        "CaveOfOrigin_B1F_EventScript_Wallace" to CaveOfOrigin_B1F_EventScript_Wallace,
    )
