package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_ABC
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BrailleABC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BrailleABC")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_GHI
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BrailleGHI : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BrailleGHI")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_MNO
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BrailleMNO : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BrailleMNO")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_TUV
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BrailleTUV : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BrailleTUV")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_DEF
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BrailleDEF : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BrailleDEF")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_JKL
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BrailleJKL : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BrailleJKL")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_PQRS
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BraillePQRS : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BraillePQRS")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_WXYZ
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BrailleWXYZ : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BrailleWXYZ")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_Period
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BraillePeriod : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BraillePeriod")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_Comma
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BrailleComma : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BrailleComma")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_SYS_BRAILLE_DIG, SealedChamber_OuterRoom_EventScript_HoleInWall
 * braillemsgbox SealedChamber_OuterRoom_Braille_DigHere
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_InnerRoomEntranceWall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_InnerRoomEntranceWall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox SealedChamber_OuterRoom_Braille_DigHere
 * releaseall
 * end
 * ```
 */
internal object SealedChamber_OuterRoom_EventScript_BrailleDigHere : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SealedChamber_OuterRoom_EventScript_BrailleDigHere")
}

internal val SealedChamber_OuterRoomScripts: Map<String, Script> =
    mapOf(
        "SealedChamber_OuterRoom_EventScript_BrailleABC" to
            SealedChamber_OuterRoom_EventScript_BrailleABC,
        "SealedChamber_OuterRoom_EventScript_BrailleGHI" to
            SealedChamber_OuterRoom_EventScript_BrailleGHI,
        "SealedChamber_OuterRoom_EventScript_BrailleMNO" to
            SealedChamber_OuterRoom_EventScript_BrailleMNO,
        "SealedChamber_OuterRoom_EventScript_BrailleTUV" to
            SealedChamber_OuterRoom_EventScript_BrailleTUV,
        "SealedChamber_OuterRoom_EventScript_BrailleDEF" to
            SealedChamber_OuterRoom_EventScript_BrailleDEF,
        "SealedChamber_OuterRoom_EventScript_BrailleJKL" to
            SealedChamber_OuterRoom_EventScript_BrailleJKL,
        "SealedChamber_OuterRoom_EventScript_BraillePQRS" to
            SealedChamber_OuterRoom_EventScript_BraillePQRS,
        "SealedChamber_OuterRoom_EventScript_BrailleWXYZ" to
            SealedChamber_OuterRoom_EventScript_BrailleWXYZ,
        "SealedChamber_OuterRoom_EventScript_BraillePeriod" to
            SealedChamber_OuterRoom_EventScript_BraillePeriod,
        "SealedChamber_OuterRoom_EventScript_BrailleComma" to
            SealedChamber_OuterRoom_EventScript_BrailleComma,
        "SealedChamber_OuterRoom_EventScript_InnerRoomEntranceWall" to
            SealedChamber_OuterRoom_EventScript_InnerRoomEntranceWall,
        "SealedChamber_OuterRoom_EventScript_BrailleDigHere" to
            SealedChamber_OuterRoom_EventScript_BrailleDigHere,
    )
