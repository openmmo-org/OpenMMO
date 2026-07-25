package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * playfanfare MUS_OBTAIN_KEY_ITEM
 * message SixIsland_DottedHole_SapphireRoom_Text_FoundSapphire
 * waitmessage
 * waitfanfare
 * closemessage
 * setobjectxyperm LOCALID_DOTTED_HOLE_THIEF, 5, 0
 * addobject LOCALID_DOTTED_HOLE_THIEF
 * playse SE_FALL
 * applymovement LOCALID_DOTTED_HOLE_THIEF, SixIsland_DottedHole_SapphireRoom_Movement_ThiefFallIn
 * waitmovement 0
 * playse SE_M_STRENGTH
 * call_if_eq VAR_FACING, DIR_NORTH, SixIsland_DottedHole_SapphireRoom_EventScript_PlayerFaceThiefLeft
 * call_if_eq VAR_FACING, DIR_SOUTH, SixIsland_DottedHole_SapphireRoom_EventScript_PlayerFaceThiefLeft
 * call_if_eq VAR_FACING, DIR_EAST, SixIsland_DottedHole_SapphireRoom_EventScript_PlayerFaceThiefDown
 * call_if_eq VAR_FACING, DIR_WEST, SixIsland_DottedHole_SapphireRoom_EventScript_PlayerFaceThiefDown
 * setvar VAR_0x8004, 3
 * setvar VAR_0x8005, 0
 * setvar VAR_0x8006, 12
 * setvar VAR_0x8007, 3
 * special ShakeScreen
 * delay 60
 * call_if_eq VAR_FACING, DIR_NORTH, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefLookAtSapphireNorth
 * call_if_eq VAR_FACING, DIR_SOUTH, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefLookAtSapphireSouth
 * call_if_eq VAR_FACING, DIR_EAST, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefLookAtSapphireEast
 * call_if_eq VAR_FACING, DIR_WEST, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefLookAtSapphireWest
 * textcolor NPC_TEXT_COLOR_MALE
 * msgbox SixIsland_DottedHole_SapphireRoom_Text_IWasRightInTailingYou
 * closemessage
 * call_if_eq VAR_FACING, DIR_NORTH, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefGetSapphireNorth
 * call_if_eq VAR_FACING, DIR_SOUTH, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefGetSapphireSouth
 * call_if_eq VAR_FACING, DIR_EAST, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefGetSapphireEast
 * call_if_eq VAR_FACING, DIR_WEST, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefGetSapphireWest
 * removeobject LOCALID_SAPPHIRE
 * call_if_eq VAR_FACING, DIR_NORTH, SixIsland_DottedHole_SapphireRoom_EventScript_PlayerFaceThiefLeft2
 * call_if_eq VAR_FACING, DIR_SOUTH, SixIsland_DottedHole_SapphireRoom_EventScript_PlayerFaceThiefLeft2
 * call_if_eq VAR_FACING, DIR_EAST, SixIsland_DottedHole_SapphireRoom_EventScript_PlayerFaceThiefDown2
 * call_if_eq VAR_FACING, DIR_WEST, SixIsland_DottedHole_SapphireRoom_EventScript_PlayerFaceThiefDown2
 * msgbox SixIsland_DottedHole_SapphireRoom_Text_SellToTeamRocketTellPassword
 * closemessage
 * call_if_eq VAR_FACING, DIR_NORTH, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefExitNorth
 * call_if_eq VAR_FACING, DIR_SOUTH, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefExitSouth
 * call_if_eq VAR_FACING, DIR_EAST, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefExitEast
 * call_if_eq VAR_FACING, DIR_WEST, SixIsland_DottedHole_SapphireRoom_EventScript_ThiefExitWest
 * playse SE_EXIT
 * delay 35
 * removeobject LOCALID_DOTTED_HOLE_THIEF
 * setflag FLAG_LEARNED_YES_NAH_CHANSEY
 * release
 * end
 * ```
 */
internal object SixIsland_DottedHole_SapphireRoom_EventScript_Sapphire : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_DottedHole_SapphireRoom_EventScript_Sapphire")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, 130
 * braillemessage_wait Braille_Text_LetTheTwo
 * braillemessage_wait Braille_Text_Glittering
 * braillemessage_wait Braille_Text_Stones
 * braillemessage_wait Braille_Text_OneInRed
 * braillemessage_wait Braille_Text_OneInBlue
 * braillemessage_wait Braille_Text_ConnectThe
 * braillemessage_wait Braille_Text_Past
 * braillemessage_wait Braille_Text_TwoFriends
 * braillemessage_wait Braille_Text_Sharing
 * braillemessage_wait Braille_Text_PowerOpen
 * braillemessage_wait Braille_Text_AWindowTo
 * braillemessage_wait Braille_Text_ANewWorld
 * braillemessage_wait Braille_Text_ThatGlows
 * braillemessage_wait Braille_Text_TheNext
 * braillemessage_wait Braille_Text_WorldWaits
 * braillemessage Braille_Text_ForYou
 * waitbuttonpress
 * releaseall
 * end
 * ```
 */
internal object SixIsland_DottedHole_SapphireRoom_EventScript_BrailleMessage : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_DottedHole_SapphireRoom_EventScript_BrailleMessage")
}

internal val SixIsland_DottedHole_SapphireRoomScripts: Map<String, Script> =
    mapOf(
        "SixIsland_DottedHole_SapphireRoom_EventScript_Sapphire" to
            SixIsland_DottedHole_SapphireRoom_EventScript_Sapphire,
        "SixIsland_DottedHole_SapphireRoom_EventScript_BrailleMessage" to
            SixIsland_DottedHole_SapphireRoom_EventScript_BrailleMessage,
    )
