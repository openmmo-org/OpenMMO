package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, UR_INTERACT_ATTENDANT
 * waitstate
 * release
 * end
 * ```
 */
internal object UnionRoom_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port UnionRoom_EventScript_Attendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, UR_INTERACT_PLAYER_4
 * waitstate
 * release
 * end
 * ```
 */
internal object UnionRoom_EventScript_Player4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port UnionRoom_EventScript_Player4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, UR_INTERACT_PLAYER_8
 * waitstate
 * release
 * end
 * ```
 */
internal object UnionRoom_EventScript_Player8 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port UnionRoom_EventScript_Player8")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, UR_INTERACT_PLAYER_7
 * waitstate
 * release
 * end
 * ```
 */
internal object UnionRoom_EventScript_Player7 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port UnionRoom_EventScript_Player7")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, UR_INTERACT_PLAYER_6
 * waitstate
 * release
 * end
 * ```
 */
internal object UnionRoom_EventScript_Player6 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port UnionRoom_EventScript_Player6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, UR_INTERACT_PLAYER_5
 * waitstate
 * release
 * end
 * ```
 */
internal object UnionRoom_EventScript_Player5 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port UnionRoom_EventScript_Player5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, UR_INTERACT_PLAYER_3
 * waitstate
 * release
 * end
 * ```
 */
internal object UnionRoom_EventScript_Player3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port UnionRoom_EventScript_Player3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, UR_INTERACT_PLAYER_2
 * waitstate
 * release
 * end
 * ```
 */
internal object UnionRoom_EventScript_Player2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port UnionRoom_EventScript_Player2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, UR_INTERACT_PLAYER_1
 * waitstate
 * release
 * end
 * ```
 */
internal object UnionRoom_EventScript_Player1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port UnionRoom_EventScript_Player1")
}

internal val UnionRoomScripts: Map<String, Script> =
    mapOf(
        "UnionRoom_EventScript_Attendant" to UnionRoom_EventScript_Attendant,
        "UnionRoom_EventScript_Player4" to UnionRoom_EventScript_Player4,
        "UnionRoom_EventScript_Player8" to UnionRoom_EventScript_Player8,
        "UnionRoom_EventScript_Player7" to UnionRoom_EventScript_Player7,
        "UnionRoom_EventScript_Player6" to UnionRoom_EventScript_Player6,
        "UnionRoom_EventScript_Player5" to UnionRoom_EventScript_Player5,
        "UnionRoom_EventScript_Player3" to UnionRoom_EventScript_Player3,
        "UnionRoom_EventScript_Player2" to UnionRoom_EventScript_Player2,
        "UnionRoom_EventScript_Player1" to UnionRoom_EventScript_Player1,
    )
