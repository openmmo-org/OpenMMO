package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_MOVE_RELEARNER, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_set FLAG_TEMP_1, FallarborTown_MoveRelearnersHouse_EventScript_AskTeachMove
 * msgbox FallarborTown_MoveRelearnersHouse_Text_ImTheMoveTutor, MSGBOX_DEFAULT
 * setflag FLAG_TEMP_1
 * goto FallarborTown_MoveRelearnersHouse_EventScript_AskTeachMove
 * end
 * ```
 */
internal object FallarborTown_MoveRelearnersHouse_EventScript_MoveRelearner : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FallarborTown_MoveRelearnersHouse_EventScript_MoveRelearner")
}

internal val FallarborTown_MoveRelearnersHouseScripts: Map<String, Script> =
    mapOf(
        "FallarborTown_MoveRelearnersHouse_EventScript_MoveRelearner" to
            FallarborTown_MoveRelearnersHouse_EventScript_MoveRelearner,
    )
