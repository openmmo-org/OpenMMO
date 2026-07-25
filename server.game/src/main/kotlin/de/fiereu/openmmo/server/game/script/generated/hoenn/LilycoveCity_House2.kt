package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_REST, LilycoveCity_House2_EventScript_ReceivedRest
 * msgbox LilycoveCity_House2_Text_NotAwakeYetHaveThis, MSGBOX_DEFAULT
 * giveitem ITEM_TM_REST
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_REST
 * msgbox LilycoveCity_House2_Text_SleepIsEssential, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_House2_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_House2_EventScript_FatMan")
}

internal val LilycoveCity_House2Scripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_House2_EventScript_FatMan" to LilycoveCity_House2_EventScript_FatMan,
    )
