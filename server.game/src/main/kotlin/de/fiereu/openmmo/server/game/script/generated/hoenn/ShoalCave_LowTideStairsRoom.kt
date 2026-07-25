package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ICE_HEAL
 * end
 * ```
 */
internal object ShoalCave_LowTideStairsRoom_EventScript_ItemIceHeal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideStairsRoom_EventScript_ItemIceHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_SHOAL_SALT_3, ShoalCave_LowTideStairsRoom_EventScript_ReceivedShoalSalt
 * giveitem ITEM_SHOAL_SALT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setmetatile 11, 11, METATILE_Cave_ShoalCave_DirtPile_Small, FALSE
 * special DrawWholeMapView
 * setflag FLAG_RECEIVED_SHOAL_SALT_3
 * releaseall
 * end
 * ```
 */
internal object ShoalCave_LowTideStairsRoom_EventScript_ShoalSalt3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideStairsRoom_EventScript_ShoalSalt3")
}

internal val ShoalCave_LowTideStairsRoomScripts: Map<String, Script> =
    mapOf(
        "ShoalCave_LowTideStairsRoom_EventScript_ItemIceHeal" to
            ShoalCave_LowTideStairsRoom_EventScript_ItemIceHeal,
        "ShoalCave_LowTideStairsRoom_EventScript_ShoalSalt3" to
            ShoalCave_LowTideStairsRoom_EventScript_ShoalSalt3,
    )
