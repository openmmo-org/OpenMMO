package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BIG_PEARL
 * end
 * ```
 */
internal object ShoalCave_LowTideEntranceRoom_EventScript_ItemBigPearl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideEntranceRoom_EventScript_ItemBigPearl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * call_if_set FLAG_SYS_SHOAL_ITEM, ShoalCave_LowTideEntranceRoom_EventScript_ResetShoalItems
 * checkitem ITEM_SHOAL_SALT, 4
 * goto_if_eq VAR_RESULT, FALSE, ShoalCave_LowTideEntranceRoom_EventScript_NotEnoughShoalSaltOrShells
 * checkitem ITEM_SHOAL_SHELL, 4
 * goto_if_eq VAR_RESULT, FALSE, ShoalCave_LowTideEntranceRoom_EventScript_NotEnoughShoalSaltOrShells
 * msgbox ShoalCave_LowTideEntranceRoom_Text_WouldYouLikeShellBell, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, ShoalCave_LowTideEntranceRoom_EventScript_DeclineShellBell
 * checkitemspace ITEM_SHELL_BELL
 * call_if_eq VAR_RESULT, FALSE, ShoalCave_LowTideEntranceRoom_EventScript_CheckSpaceWillBeFreed
 * goto_if_eq VAR_RESULT, 2, ShoalCave_LowTideEntranceRoom_EventScript_NoRoomForShellBell
 * msgbox ShoalCave_LowTideEntranceRoom_Text_MakeShellBellRightAway, MSGBOX_DEFAULT
 * removeitem ITEM_SHOAL_SALT, 4
 * removeitem ITEM_SHOAL_SHELL, 4
 * giveitem ITEM_SHELL_BELL
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull  @ Never FALSE, we already made sure there will be room in the bag.
 * msgbox ShoalCave_LowTideEntranceRoom_Text_ExplainShellBell, MSGBOX_DEFAULT
 * setflag FLAG_TEMP_2  @ Not read. Perhaps meant to stop him from re-explaining the Shell Bell if multiple are received in one sitting.
 * release
 * end
 * ```
 */
internal object ShoalCave_LowTideEntranceRoom_EventScript_ShellBellExpert : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ShoalCave_LowTideEntranceRoom_EventScript_ShellBellExpert")
}

internal val ShoalCave_LowTideEntranceRoomScripts: Map<String, Script> =
    mapOf(
        "ShoalCave_LowTideEntranceRoom_EventScript_ItemBigPearl" to
            ShoalCave_LowTideEntranceRoom_EventScript_ItemBigPearl,
        "ShoalCave_LowTideEntranceRoom_EventScript_ShellBellExpert" to
            ShoalCave_LowTideEntranceRoom_EventScript_ShellBellExpert,
    )
