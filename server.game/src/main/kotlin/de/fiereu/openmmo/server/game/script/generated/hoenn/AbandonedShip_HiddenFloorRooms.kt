package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AbandonedShip_HiddenFloorRooms
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_LUXURY_BALL
 * end
 * ```
 */
internal object AbandonedShip_HiddenFloorRooms_EventScript_ItemLuxuryBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_HiddenFloorRooms_EventScript_ItemLuxuryBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SCANNER
 * end
 * ```
 */
internal object AbandonedShip_HiddenFloorRooms_EventScript_ItemScanner : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_HiddenFloorRooms_EventScript_ItemScanner")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_RAIN_DANCE
 * end
 * ```
 */
internal object AbandonedShip_HiddenFloorRooms_EventScript_ItemTMRainDance : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_HiddenFloorRooms_EventScript_ItemTMRainDance")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_WATER_STONE
 * end
 * ```
 */
internal object AbandonedShip_HiddenFloorRooms_EventScript_ItemWaterStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_HiddenFloorRooms_EventScript_ItemWaterStone")
}

internal object AbandonedShip_HiddenFloorRooms_EventScript_Trash : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(AbandonedShip_HiddenFloorRooms.BrightShinyTrash)
}

internal val AbandonedShip_HiddenFloorRoomsScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_HiddenFloorRooms_EventScript_ItemLuxuryBall" to
            AbandonedShip_HiddenFloorRooms_EventScript_ItemLuxuryBall,
        "AbandonedShip_HiddenFloorRooms_EventScript_ItemScanner" to
            AbandonedShip_HiddenFloorRooms_EventScript_ItemScanner,
        "AbandonedShip_HiddenFloorRooms_EventScript_ItemTMRainDance" to
            AbandonedShip_HiddenFloorRooms_EventScript_ItemTMRainDance,
        "AbandonedShip_HiddenFloorRooms_EventScript_ItemWaterStone" to
            AbandonedShip_HiddenFloorRooms_EventScript_ItemWaterStone,
        "AbandonedShip_HiddenFloorRooms_EventScript_Trash" to
            AbandonedShip_HiddenFloorRooms_EventScript_Trash,
    )
