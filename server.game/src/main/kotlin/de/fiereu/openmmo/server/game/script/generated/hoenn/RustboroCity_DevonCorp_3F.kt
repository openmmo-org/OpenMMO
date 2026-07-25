package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_DevonCorp_3F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_EXP_SHARE, RustboroCity_DevonCorp_3F_EventScript_MrStoneAfterFavor
 * goto_if_set FLAG_DELIVERED_STEVEN_LETTER, RustboroCity_DevonCorp_3F_EventScript_GiveExpShare
 * msgbox RustboroCity_DevonCorp_3F_Text_CountingOnYou, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_3F_EventScript_MrStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_3F_EventScript_MrStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_REPEAT_BALL, RustboroCity_DevonCorp_3F_EventScript_EmployeeBalls
 * msgbox RustboroCity_DevonCorp_3F_Text_VisitCaptSternShipyard, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_3F_EventScript_Employee : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_3F_EventScript_Employee")
}

internal object RustboroCity_DevonCorp_3F_EventScript_RareRocksDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(RustboroCity_DevonCorp_3F.RareRocksDisplay)
}

internal val RustboroCity_DevonCorp_3FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_DevonCorp_3F_EventScript_MrStone" to
            RustboroCity_DevonCorp_3F_EventScript_MrStone,
        "RustboroCity_DevonCorp_3F_EventScript_Employee" to
            RustboroCity_DevonCorp_3F_EventScript_Employee,
        "RustboroCity_DevonCorp_3F_EventScript_RareRocksDisplay" to
            RustboroCity_DevonCorp_3F_EventScript_RareRocksDisplay,
    )
