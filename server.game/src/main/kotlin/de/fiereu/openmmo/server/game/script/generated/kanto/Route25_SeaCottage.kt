package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set RETURN_AFTER_SS_TICKET, Route25_SeaCottage_EventScript_BillGoLookAtPC
 * goto_if_set FLAG_GOT_SS_TICKET, Route25_SeaCottage_EventScript_BillGoToSSAnne
 * goto_if_set FLAG_HELPED_BILL_IN_SEA_COTTAGE, Route25_SeaCottage_EventScript_BillGiveSSTicket
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, Route25_SeaCottage_EventScript_BillAskForHelpMale
 * goto_if_eq VAR_RESULT, FEMALE, Route25_SeaCottage_EventScript_BillAskForHelpFemale
 * end
 * ```
 */
internal object Route25_SeaCottage_EventScript_Bill : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route25_SeaCottage_EventScript_Bill")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set RETURN_AFTER_SS_TICKET, Route25_SeaCottage_EventScript_OpenBillsMonList
 * goto_if_set BILL_IN_TELEPORTER, Route25_SeaCottage_EventScript_RunCellSeparator
 * msgbox Route25_SeaCottage_Text_TeleporterIsDisplayed
 * releaseall
 * end
 * ```
 */
internal object Route25_SeaCottage_EventScript_Computer : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route25_SeaCottage_EventScript_Computer")
}

internal val Route25_SeaCottageScripts: Map<String, Script> =
    mapOf(
        "Route25_SeaCottage_EventScript_Bill" to Route25_SeaCottage_EventScript_Bill,
        "Route25_SeaCottage_EventScript_Computer" to Route25_SeaCottage_EventScript_Computer,
    )
