package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_MECHADOLL_1, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_TEMP_1, 1, Route110_TrickHousePuzzle5_EventScript_CorrectGoThrough
 * setvar VAR_TEMP_9, 0
 * goto Route110_TrickHousePuzzle5_EventScript_Mechadoll1Activate
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle5_EventScript_Mechadoll1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle5_EventScript_Mechadoll1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_MECHADOLL_2, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_TEMP_2, 1, Route110_TrickHousePuzzle5_EventScript_CorrectGoThrough
 * setvar VAR_TEMP_9, 0
 * goto Route110_TrickHousePuzzle5_EventScript_Mechadoll2Activate
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle5_EventScript_Mechadoll2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle5_EventScript_Mechadoll2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_MECHADOLL_3, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_TEMP_3, 1, Route110_TrickHousePuzzle5_EventScript_CorrectGoThrough
 * setvar VAR_TEMP_9, 0
 * goto Route110_TrickHousePuzzle5_EventScript_Mechadoll3Activate
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle5_EventScript_Mechadoll3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle5_EventScript_Mechadoll3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_MECHADOLL_4, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_TEMP_4, 1, Route110_TrickHousePuzzle5_EventScript_CorrectGoThrough
 * setvar VAR_TEMP_9, 0
 * goto Route110_TrickHousePuzzle5_EventScript_Mechadoll4Activate
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle5_EventScript_Mechadoll4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle5_EventScript_Mechadoll4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_MECHADOLL_5, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_TEMP_5, 1, Route110_TrickHousePuzzle5_EventScript_CorrectGoThrough
 * setvar VAR_TEMP_9, 0
 * goto Route110_TrickHousePuzzle5_EventScript_Mechadoll5Activate
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle5_EventScript_Mechadoll5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle5_EventScript_Mechadoll5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_5_STATE, 0, Route110_TrickHousePuzzle5_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle5_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle5_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle5Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle5_EventScript_Mechadoll1" to
            Route110_TrickHousePuzzle5_EventScript_Mechadoll1,
        "Route110_TrickHousePuzzle5_EventScript_Mechadoll2" to
            Route110_TrickHousePuzzle5_EventScript_Mechadoll2,
        "Route110_TrickHousePuzzle5_EventScript_Mechadoll3" to
            Route110_TrickHousePuzzle5_EventScript_Mechadoll3,
        "Route110_TrickHousePuzzle5_EventScript_Mechadoll4" to
            Route110_TrickHousePuzzle5_EventScript_Mechadoll4,
        "Route110_TrickHousePuzzle5_EventScript_Mechadoll5" to
            Route110_TrickHousePuzzle5_EventScript_Mechadoll5,
        "Route110_TrickHousePuzzle5_EventScript_Scroll" to
            Route110_TrickHousePuzzle5_EventScript_Scroll,
    )
