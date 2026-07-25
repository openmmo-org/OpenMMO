package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object ShoalCave_LowTideInnerRoom_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideInnerRoom_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_SHOAL_SALT_1, ShoalCave_LowTideInnerRoom_EventScript_ReceivedShoalSalt
 * giveitem ITEM_SHOAL_SALT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setmetatile 31, 8, METATILE_Cave_ShoalCave_DirtPile_Small, FALSE
 * special DrawWholeMapView
 * setflag FLAG_RECEIVED_SHOAL_SALT_1
 * releaseall
 * end
 * ```
 */
internal object ShoalCave_LowTideInnerRoom_EventScript_ShoalSalt1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideInnerRoom_EventScript_ShoalSalt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_SHOAL_SALT_2, ShoalCave_LowTideInnerRoom_EventScript_ReceivedShoalSalt
 * giveitem ITEM_SHOAL_SALT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setmetatile 14, 26, METATILE_Cave_ShoalCave_DirtPile_Small, FALSE
 * special DrawWholeMapView
 * setflag FLAG_RECEIVED_SHOAL_SALT_2
 * releaseall
 * end
 * ```
 */
internal object ShoalCave_LowTideInnerRoom_EventScript_ShoalSalt2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideInnerRoom_EventScript_ShoalSalt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_SHOAL_SHELL_1, ShoalCave_LowTideInnerRoom_EventScript_ReceivedShoalShell
 * giveitem ITEM_SHOAL_SHELL
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setmetatile 41, 20, METATILE_Cave_ShoalCave_BlueStone_Small, FALSE
 * special DrawWholeMapView
 * setflag FLAG_RECEIVED_SHOAL_SHELL_1
 * releaseall
 * end
 * ```
 */
internal object ShoalCave_LowTideInnerRoom_EventScript_ShoalShell1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideInnerRoom_EventScript_ShoalShell1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_SHOAL_SHELL_2, ShoalCave_LowTideInnerRoom_EventScript_ReceivedShoalShell
 * giveitem ITEM_SHOAL_SHELL
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setmetatile 41, 10, METATILE_Cave_ShoalCave_BlueStone_Small, FALSE
 * special DrawWholeMapView
 * setflag FLAG_RECEIVED_SHOAL_SHELL_2
 * releaseall
 * end
 * ```
 */
internal object ShoalCave_LowTideInnerRoom_EventScript_ShoalShell2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideInnerRoom_EventScript_ShoalShell2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_SHOAL_SHELL_3, ShoalCave_LowTideInnerRoom_EventScript_ReceivedShoalShell
 * giveitem ITEM_SHOAL_SHELL
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setmetatile 6, 9, METATILE_Cave_ShoalCave_BlueStone_Small, FALSE
 * special DrawWholeMapView
 * setflag FLAG_RECEIVED_SHOAL_SHELL_3
 * releaseall
 * end
 * ```
 */
internal object ShoalCave_LowTideInnerRoom_EventScript_ShoalShell3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideInnerRoom_EventScript_ShoalShell3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_SHOAL_SHELL_4, ShoalCave_LowTideInnerRoom_EventScript_ReceivedShoalShell
 * giveitem ITEM_SHOAL_SHELL
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setmetatile 16, 13, METATILE_Cave_ShoalCave_BlueStone_Small, FALSE
 * special DrawWholeMapView
 * setflag FLAG_RECEIVED_SHOAL_SHELL_4
 * releaseall
 * end
 * ```
 */
internal object ShoalCave_LowTideInnerRoom_EventScript_ShoalShell4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideInnerRoom_EventScript_ShoalShell4")
}

internal val ShoalCave_LowTideInnerRoomScripts: Map<String, Script> =
    mapOf(
        "ShoalCave_LowTideInnerRoom_EventScript_ItemRareCandy" to
            ShoalCave_LowTideInnerRoom_EventScript_ItemRareCandy,
        "ShoalCave_LowTideInnerRoom_EventScript_ShoalSalt1" to
            ShoalCave_LowTideInnerRoom_EventScript_ShoalSalt1,
        "ShoalCave_LowTideInnerRoom_EventScript_ShoalSalt2" to
            ShoalCave_LowTideInnerRoom_EventScript_ShoalSalt2,
        "ShoalCave_LowTideInnerRoom_EventScript_ShoalShell1" to
            ShoalCave_LowTideInnerRoom_EventScript_ShoalShell1,
        "ShoalCave_LowTideInnerRoom_EventScript_ShoalShell2" to
            ShoalCave_LowTideInnerRoom_EventScript_ShoalShell2,
        "ShoalCave_LowTideInnerRoom_EventScript_ShoalShell3" to
            ShoalCave_LowTideInnerRoom_EventScript_ShoalShell3,
        "ShoalCave_LowTideInnerRoom_EventScript_ShoalShell4" to
            ShoalCave_LowTideInnerRoom_EventScript_ShoalShell4,
    )
