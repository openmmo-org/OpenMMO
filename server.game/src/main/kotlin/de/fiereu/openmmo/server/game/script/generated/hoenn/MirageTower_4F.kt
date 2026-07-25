package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox MirageTower_4F_Text_TakeRootFossil, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MirageTower_4F_EventScript_LeaveRootFossil
 * giveitem ITEM_ROOT_FOSSIL
 * closemessage
 * setflag FLAG_HIDE_MIRAGE_TOWER_ROOT_FOSSIL
 * setflag FLAG_HIDE_MIRAGE_TOWER_CLAW_FOSSIL
 * removeobject LOCALID_MIRAGE_ROOT_FOSSIL
 * delay 30
 * setflag FLAG_CHOSE_ROOT_FOSSIL
 * goto MirageTower_4F_EventScript_CollapseMirageTower
 * end
 * ```
 */
internal object MirageTower_4F_EventScript_RootFossil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MirageTower_4F_EventScript_RootFossil")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox MirageTower_4F_Text_TakeClawFossil, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MirageTower_4F_EventScript_LeaveClawFossil
 * giveitem ITEM_CLAW_FOSSIL
 * closemessage
 * setflag FLAG_HIDE_MIRAGE_TOWER_CLAW_FOSSIL
 * setflag FLAG_HIDE_MIRAGE_TOWER_ROOT_FOSSIL
 * removeobject LOCALID_MIRAGE_CLAW_FOSSIL
 * delay 30
 * setflag FLAG_CHOSE_CLAW_FOSSIL
 * goto MirageTower_4F_EventScript_CollapseMirageTower
 * end
 * ```
 */
internal object MirageTower_4F_EventScript_ClawFossil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MirageTower_4F_EventScript_ClawFossil")
}

internal val MirageTower_4FScripts: Map<String, Script> =
    mapOf(
        "MirageTower_4F_EventScript_RootFossil" to MirageTower_4F_EventScript_RootFossil,
        "MirageTower_4F_EventScript_ClawFossil" to MirageTower_4F_EventScript_ClawFossil,
    )
