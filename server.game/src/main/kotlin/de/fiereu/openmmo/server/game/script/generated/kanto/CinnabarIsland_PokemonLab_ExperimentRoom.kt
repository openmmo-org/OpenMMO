package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8008, INGAME_TRADE_SEEL
 * call EventScript_GetInGameTradeSpeciesInfo
 * goto_if_set FLAG_DID_SEELOR_TRADE, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_AlreadyTraded
 * msgbox Trade_Text_LookingForMonWannaTradeForMon, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_DeclineTrade
 * call EventScript_ChooseMonForInGameTrade
 * goto_if_ge VAR_0x8004, PARTY_SIZE, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_DeclineTrade
 * call EventScript_GetInGameTradeSpecies
 * goto_if_ne VAR_RESULT, VAR_0x8009, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_NotRequestedMon
 * call EventScript_DoInGameTrade
 * msgbox Trade_Text_HeyThanks
 * setflag FLAG_DID_SEELOR_TRADE
 * release
 * end
 * ```
 */
internal object CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_Garett : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_Garett")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_RESULT, FALSE
 * call_if_set FLAG_REVIVED_AMBER, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_CheckRevivedMtMoonFossil
 * goto_if_eq VAR_RESULT, TRUE, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_RevivedAllFossils
 * goto_if_eq VAR_MAP_SCENE_CINNABAR_ISLAND_POKEMON_LAB_EXPERIMENT_ROOM_REVIVE_STATE, 2, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_GiveRevivedMon
 * goto_if_eq VAR_MAP_SCENE_CINNABAR_ISLAND_POKEMON_LAB_EXPERIMENT_ROOM_REVIVE_STATE, 1, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_FossilStillReviving
 * call CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_CheckAddHelixFossilToList
 * goto_if_eq VAR_RESULT, TRUE, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_ChooseFossilHelix
 * call CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_CheckAddDomeFossilToList
 * goto_if_eq VAR_RESULT, TRUE, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_ChooseFossilDome
 * call CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_CheckAddOldAmberToList
 * goto_if_eq VAR_RESULT, TRUE, CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_ChooseFossilAmber
 * msgbox CinnabarIsland_PokemonLab_ExperimentRoom_Text_HaveYouAFossilForMe
 * goto CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_DontShowFossil
 * end
 * ```
 */
internal object CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_FossilScientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_FossilScientist")
}

internal val CinnabarIsland_PokemonLab_ExperimentRoomScripts: Map<String, Script> =
    mapOf(
        "CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_Garett" to
            CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_Garett,
        "CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_FossilScientist" to
            CinnabarIsland_PokemonLab_ExperimentRoom_EventScript_FossilScientist,
    )
