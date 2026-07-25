package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_InnerRoom_Braille_FirstWailordLastRelicanth
 * goto_if_set FLAG_REGI_DOORS_OPENED, SealedChamber_InnerRoom_EventScript_NoEffect
 * specialvar VAR_RESULT, CheckRelicanthWailord
 * goto_if_eq VAR_RESULT, FALSE, SealedChamber_InnerRoom_EventScript_NoEffect
 * fadeoutbgm 0
 * playse SE_TRUCK_MOVE
 * special DoSealedChamberShakingEffect_Long
 * delay 40
 * special DoSealedChamberShakingEffect_Short
 * playse SE_DOOR
 * delay 40
 * special DoSealedChamberShakingEffect_Short
 * playse SE_DOOR
 * delay 40
 * special DoSealedChamberShakingEffect_Short
 * playse SE_DOOR
 * delay 40
 * msgbox gText_DoorOpenedFarAway, MSGBOX_DEFAULT
 * closemessage
 * fadeinbgm 0
 * setflag FLAG_REGI_DOORS_OPENED
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_InnerRoom_EventScript_BrailleBackWall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_InnerRoom_EventScript_BrailleBackWall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_InnerRoom_Braille_InThisCaveWeHaveLived
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_InnerRoom_EventScript_BrailleStoryPart1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_InnerRoom_EventScript_BrailleStoryPart1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_InnerRoom_Braille_WeOweAllToThePokemon
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_InnerRoom_EventScript_BrailleStoryPart2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_InnerRoom_EventScript_BrailleStoryPart2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_InnerRoom_Braille_ButWeSealedThePokemonAway
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_InnerRoom_EventScript_BrailleStoryPart3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_InnerRoom_EventScript_BrailleStoryPart3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_InnerRoom_Braille_WeFearedIt
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_InnerRoom_EventScript_BrailleStoryPart4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_InnerRoom_EventScript_BrailleStoryPart4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_InnerRoom_Braille_ThoseWithCourageHope
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_InnerRoom_EventScript_BrailleStoryPart5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_InnerRoom_EventScript_BrailleStoryPart5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_InnerRoom_Braille_OpenDoorEternalPokemonWaits
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_InnerRoom_EventScript_BrailleStoryPart6 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_InnerRoom_EventScript_BrailleStoryPart6")
}

internal val SealedChamber_InnerRoomScripts: Map<String, Script> =
    mapOf(
        "SealedChamber_InnerRoom_EventScript_BrailleBackWall" to
            SealedChamber_InnerRoom_EventScript_BrailleBackWall,
        "SealedChamber_InnerRoom_EventScript_BrailleStoryPart1" to
            SealedChamber_InnerRoom_EventScript_BrailleStoryPart1,
        "SealedChamber_InnerRoom_EventScript_BrailleStoryPart2" to
            SealedChamber_InnerRoom_EventScript_BrailleStoryPart2,
        "SealedChamber_InnerRoom_EventScript_BrailleStoryPart3" to
            SealedChamber_InnerRoom_EventScript_BrailleStoryPart3,
        "SealedChamber_InnerRoom_EventScript_BrailleStoryPart4" to
            SealedChamber_InnerRoom_EventScript_BrailleStoryPart4,
        "SealedChamber_InnerRoom_EventScript_BrailleStoryPart5" to
            SealedChamber_InnerRoom_EventScript_BrailleStoryPart5,
        "SealedChamber_InnerRoom_EventScript_BrailleStoryPart6" to
            SealedChamber_InnerRoom_EventScript_BrailleStoryPart6,
    )
