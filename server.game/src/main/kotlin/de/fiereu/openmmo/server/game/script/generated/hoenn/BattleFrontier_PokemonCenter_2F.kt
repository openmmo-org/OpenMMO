package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * script body not found
 * ```
 */
internal object Common_EventScript_UnionRoomAttendant : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Common_EventScript_UnionRoomAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * script body not found
 * ```
 */
internal object Common_EventScript_WirelessClubAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Common_EventScript_WirelessClubAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * script body not found
 * ```
 */
internal object Common_EventScript_DirectCornerAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Common_EventScript_DirectCornerAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * specialvar VAR_RESULT, ShouldDistributeEonTicket
 * goto_if_eq VAR_RESULT, TRUE, CableClub_EventScript_DistributeEonTicket
 * goto CableClub_EventScript_TryWonderCardScript
 * end
 * ```
 */
internal object CableClub_EventScript_MysteryGiftMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CableClub_EventScript_MysteryGiftMan")
}

internal val BattleFrontier_PokemonCenter_2FScripts: Map<String, Script> =
    mapOf(
        "Common_EventScript_UnionRoomAttendant" to Common_EventScript_UnionRoomAttendant,
        "Common_EventScript_WirelessClubAttendant" to Common_EventScript_WirelessClubAttendant,
        "Common_EventScript_DirectCornerAttendant" to Common_EventScript_DirectCornerAttendant,
        "CableClub_EventScript_MysteryGiftMan" to CableClub_EventScript_MysteryGiftMan,
    )
