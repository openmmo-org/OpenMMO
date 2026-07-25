package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_Condominiums_RoofRoom
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_Condominiums_RoofRoom_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_Condominiums_RoofRoom.TheresNothingIDontKnow)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, SPECIES_EEVEE
 * givemon SPECIES_EEVEE, 25
 * goto_if_eq VAR_RESULT, 0, CeladonCity_Condominiums_RoofRoom_EventScript_GetEeveeParty
 * goto_if_eq VAR_RESULT, 1, CeladonCity_Condominiums_RoofRoom_EventScript_GetEeveePC
 * goto_if_eq VAR_RESULT, 2, EventScript_NoMoreRoomForPokemon
 * release
 * end
 * ```
 */
internal object CeladonCity_Condominiums_RoofRoom_EventScript_EeveeBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_Condominiums_RoofRoom_EventScript_EeveeBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox CeladonCity_Condominiums_RoofRoom_Text_WirelessAdapterLecture
 * message CeladonCity_Condominiums_RoofRoom_Text_ReadWhichHeading
 * waitmessage
 * setvar VAR_0x8004, 4
 * multichoice 0, 0, MULTICHOICE_LINKED_DIRECT_UNION, FALSE
 * switch VAR_RESULT
 * case 0, CeladonCity_Condominiums_RoofRoom_EventScript_WirelessClub
 * case 1, CeladonCity_Condominiums_RoofRoom_EventScript_DirectCorner
 * case 2, CeladonCity_Condominiums_RoofRoom_EventScript_UnionRoom
 * case 3, CeladonCity_Condominiums_RoofRoom_EventScript_ExitBlackboard
 * case 127, CeladonCity_Condominiums_RoofRoom_EventScript_ExitBlackboard
 * end
 * ```
 */
internal object CeladonCity_Condominiums_RoofRoom_EventScript_Blackboard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_Condominiums_RoofRoom_EventScript_Blackboard")
}

internal object CeladonCity_Condominiums_RoofRoom_EventScript_TMsPamphlet : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_Condominiums_RoofRoom.PamphletOnTMs)
}

internal val CeladonCity_Condominiums_RoofRoomScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_Condominiums_RoofRoom_EventScript_BlackBelt" to
            CeladonCity_Condominiums_RoofRoom_EventScript_BlackBelt,
        "CeladonCity_Condominiums_RoofRoom_EventScript_EeveeBall" to
            CeladonCity_Condominiums_RoofRoom_EventScript_EeveeBall,
        "CeladonCity_Condominiums_RoofRoom_EventScript_Blackboard" to
            CeladonCity_Condominiums_RoofRoom_EventScript_Blackboard,
        "CeladonCity_Condominiums_RoofRoom_EventScript_TMsPamphlet" to
            CeladonCity_Condominiums_RoofRoom_EventScript_TMsPamphlet,
    )
