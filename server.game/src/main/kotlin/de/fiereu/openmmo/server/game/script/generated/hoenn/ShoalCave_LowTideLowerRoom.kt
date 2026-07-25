package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_FOCUS_BAND, ShoalCave_LowTideLowerRoom_EventScript_ReceivedFocusBand
 * msgbox ShoalCave_LowTideLowerRoom_Text_CanOvercomeColdWithFocus, MSGBOX_DEFAULT
 * giveitem ITEM_FOCUS_BAND
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_FOCUS_BAND
 * release
 * end
 * ```
 */
internal object ShoalCave_LowTideLowerRoom_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideLowerRoom_EventScript_BlackBelt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_SHOAL_SALT_4, ShoalCave_LowTideLowerRoom_EventScript_ReceivedShoalSalt
 * giveitem ITEM_SHOAL_SALT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setmetatile 18, 2, METATILE_Cave_ShoalCave_DirtPile_Small, FALSE
 * special DrawWholeMapView
 * setflag FLAG_RECEIVED_SHOAL_SALT_4
 * releaseall
 * end
 * ```
 */
internal object ShoalCave_LowTideLowerRoom_EventScript_ShoalSalt4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideLowerRoom_EventScript_ShoalSalt4")
}

internal val ShoalCave_LowTideLowerRoomScripts: Map<String, Script> =
    mapOf(
        "ShoalCave_LowTideLowerRoom_EventScript_BlackBelt" to
            ShoalCave_LowTideLowerRoom_EventScript_BlackBelt,
        "ShoalCave_LowTideLowerRoom_EventScript_ShoalSalt4" to
            ShoalCave_LowTideLowerRoom_EventScript_ShoalSalt4,
    )
