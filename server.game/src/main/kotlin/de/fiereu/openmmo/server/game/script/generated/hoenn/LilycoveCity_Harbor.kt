package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_SYS_GAME_CLEAR, LilycoveCity_Harbor_EventScript_FerryUnavailable
 * call LilycoveCity_Harbor_EventScript_GetEonTicketState
 * call LilycoveCity_Harbor_EventScript_GetAuroraTicketState
 * call LilycoveCity_Harbor_EventScript_GetOldSeaMapState
 * call LilycoveCity_Harbor_EventScript_GetMysticTicketState
 * call LilycoveCity_Harbor_EventScript_GetFirstTimeShowingTicket
 * call LilycoveCity_Harbor_EventScript_GetHasTicketsState
 * goto_if_eq VAR_TEMP_C, 2, LilycoveCity_Harbor_EventScript_OldSeaMapFirstTime
 * goto_if_eq VAR_TEMP_B, 1, LilycoveCity_Harbor_EventScript_EonTicketFirstTime
 * goto_if_eq VAR_TEMP_B, 2, LilycoveCity_Harbor_EventScript_AuroraTicketFirstTime
 * goto_if_eq VAR_TEMP_B, 4, LilycoveCity_Harbor_EventScript_OldSeaMapFirstTime
 * goto_if_eq VAR_TEMP_B, 8, LilycoveCity_Harbor_EventScript_MysticTicketFirstTime
 * goto_if_ne VAR_TEMP_B, 0, LilycoveCity_Harbor_EventScript_MultipleEventTicketsFirstTime
 * goto LilycoveCity_Harbor_EventScript_NoFirstTimeEventTickets
 * end
 * ```
 */
internal object LilycoveCity_Harbor_EventScript_FerryAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_Harbor_EventScript_FerryAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, LilycoveCity_Harbor_EventScript_SailorFerryAvailable
 * msgbox LilycoveCity_Harbor_Text_SailorFerryUnavailable, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_Harbor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_Harbor_EventScript_Sailor")
}

internal val LilycoveCity_HarborScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_Harbor_EventScript_FerryAttendant" to
            LilycoveCity_Harbor_EventScript_FerryAttendant,
        "LilycoveCity_Harbor_EventScript_Sailor" to LilycoveCity_Harbor_EventScript_Sailor,
    )
