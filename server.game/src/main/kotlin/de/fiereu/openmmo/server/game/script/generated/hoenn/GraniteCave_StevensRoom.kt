package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox GraniteCave_StevensRoom_Text_ImStevenLetterForMe, MSGBOX_DEFAULT
 * setvar VAR_0x8004, ITEM_LETTER
 * call Common_EventScript_PlayerHandedOverTheItem
 * setflag FLAG_DELIVERED_STEVEN_LETTER
 * msgbox GraniteCave_StevensRoom_Text_ThankYouTakeThis, MSGBOX_DEFAULT
 * giveitem ITEM_TM_STEEL_WING
 * call_if_eq VAR_RESULT, FALSE, GraniteCave_StevensRoom_EventScript_BagFull
 * msgbox GraniteCave_StevensRoom_Text_CouldBecomeChampionLetsRegister, MSGBOX_DEFAULT
 * closemessage
 * delay 30
 * playfanfare MUS_REGISTER_MATCH_CALL
 * msgbox GraniteCave_StevensRoom_Text_RegisteredSteven, MSGBOX_DEFAULT
 * waitfanfare
 * closemessage
 * delay 30
 * setflag FLAG_REGISTERED_STEVEN_POKENAV
 * msgbox GraniteCave_StevensRoom_Text_IveGotToHurryAlong, MSGBOX_DEFAULT
 * closemessage
 * call_if_eq VAR_FACING, DIR_NORTH, GraniteCave_StevensRoom_EventScript_StevenExitNorth
 * call_if_eq VAR_FACING, DIR_SOUTH, GraniteCave_StevensRoom_EventScript_StevenExitSouth
 * call_if_eq VAR_FACING, DIR_WEST, GraniteCave_StevensRoom_EventScript_StevenExitWestEast
 * call_if_eq VAR_FACING, DIR_EAST, GraniteCave_StevensRoom_EventScript_StevenExitWestEast
 * playse SE_EXIT
 * removeobject LOCALID_GRANITE_CAVE_STEVEN
 * release
 * end
 * ```
 */
internal object GraniteCave_StevensRoom_EventScript_Steven : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port GraniteCave_StevensRoom_EventScript_Steven")
}

internal val GraniteCave_StevensRoomScripts: Map<String, Script> =
    mapOf(
        "GraniteCave_StevensRoom_EventScript_Steven" to GraniteCave_StevensRoom_EventScript_Steven,
    )
