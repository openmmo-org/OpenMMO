package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_EXCHANGED_SCANNER, AbandonedShip_CaptainsOffice_EventScript_ThisIsSSCactus
 * checkitem ITEM_SCANNER
 * goto_if_eq VAR_RESULT, TRUE, AbandonedShip_CaptainsOffice_EventScript_CanYouDeliverScanner
 * goto_if_set FLAG_ITEM_ABANDONED_SHIP_HIDDEN_FLOOR_ROOM_2_SCANNER, AbandonedShip_CaptainsOffice_EventScript_ThisIsSSCactus
 * msgbox AbandonedShip_CaptainsOffice_Text_NoSuccessFindingScanner, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object AbandonedShip_CaptainsOffice_EventScript_CaptSternAide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_CaptainsOffice_EventScript_CaptSternAide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_STORAGE_KEY
 * end
 * ```
 */
internal object AbandonedShip_CaptainsOffice_EventScript_ItemStorageKey : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_CaptainsOffice_EventScript_ItemStorageKey")
}

internal val AbandonedShip_CaptainsOfficeScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_CaptainsOffice_EventScript_CaptSternAide" to
            AbandonedShip_CaptainsOffice_EventScript_CaptSternAide,
        "AbandonedShip_CaptainsOffice_EventScript_ItemStorageKey" to
            AbandonedShip_CaptainsOffice_EventScript_ItemStorageKey,
    )
