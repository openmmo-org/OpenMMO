package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_Flat2_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object RustboroCity_Flat2_2F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_Flat2_2F.DevonWasTinyInOldDays)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_PREMIER_BALL_RUSTBORO, RustboroCity_Flat2_2F_EventScript_GavePremierBall
 * msgbox RustboroCity_Flat2_2F_Text_MyDaddyMadeThisYouCanHaveIt, MSGBOX_DEFAULT
 * giveitem ITEM_PREMIER_BALL
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_PREMIER_BALL_RUSTBORO
 * release
 * end
 * ```
 */
internal object RustboroCity_Flat2_2F_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_Flat2_2F_EventScript_NinjaBoy")
}

internal val RustboroCity_Flat2_2FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_Flat2_2F_EventScript_OldMan" to RustboroCity_Flat2_2F_EventScript_OldMan,
        "RustboroCity_Flat2_2F_EventScript_NinjaBoy" to RustboroCity_Flat2_2F_EventScript_NinjaBoy,
    )
