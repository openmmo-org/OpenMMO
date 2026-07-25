package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SSAnne_1F_Room6_Text_TakeAShortRest, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, SSAnne_1F_Room6_EventScript_DeclineHeal
 * closemessage
 * call EventScript_OutOfCenterPartyHeal
 * msgbox SSAnne_1F_Room6_Text_GladEveryoneIsRefreshed
 * release
 * end
 * ```
 */
internal object SSAnne_1F_Room6_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_1F_Room6_EventScript_Woman")
}

internal val SSAnne_1F_Room6Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room6_EventScript_Woman" to SSAnne_1F_Room6_EventScript_Woman,
    )
