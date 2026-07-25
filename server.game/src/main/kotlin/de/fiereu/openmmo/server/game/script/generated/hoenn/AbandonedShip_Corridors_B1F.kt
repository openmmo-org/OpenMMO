package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AbandonedShip_Corridors_B1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object AbandonedShip_Corridors_B1F_EventScript_TuberM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(AbandonedShip_Corridors_B1F.YayItsAShip)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DUNCAN, AbandonedShip_Corridors_B1F_Text_DuncanIntro, AbandonedShip_Corridors_B1F_Text_DuncanDefeat
 * msgbox AbandonedShip_Corridors_B1F_Text_DuncanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object AbandonedShip_Corridors_B1F_EventScript_Duncan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Corridors_B1F_EventScript_Duncan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_USED_STORAGE_KEY, AbandonedShip_Corridors_B1F_EventScript_DoorIsUnlocked
 * checkitem ITEM_STORAGE_KEY
 * goto_if_eq VAR_RESULT, FALSE, AbandonedShip_Corridors_B1F_EventScript_DoorIsLocked
 * msgbox AbandonedShip_Corridors_B1F_Text_InsertedStorageKey, MSGBOX_DEFAULT
 * playse SE_PIN
 * removeitem ITEM_STORAGE_KEY
 * setflag FLAG_USED_STORAGE_KEY
 * call AbandonedShip_Corridors_B1F_EventScript_UnlockStorageRoom
 * special DrawWholeMapView
 * releaseall
 * end
 * ```
 */
internal object AbandonedShip_Corridors_B1F_EventScript_StorageRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Corridors_B1F_EventScript_StorageRoomDoor")
}

internal val AbandonedShip_Corridors_B1FScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_Corridors_B1F_EventScript_TuberM" to
            AbandonedShip_Corridors_B1F_EventScript_TuberM,
        "AbandonedShip_Corridors_B1F_EventScript_Duncan" to
            AbandonedShip_Corridors_B1F_EventScript_Duncan,
        "AbandonedShip_Corridors_B1F_EventScript_StorageRoomDoor" to
            AbandonedShip_Corridors_B1F_EventScript_StorageRoomDoor,
    )
