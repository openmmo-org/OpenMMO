package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PalletTown_RivalsHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_DAISY, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * goto_if_set FLAG_SYS_GAME_CLEAR, PalletTown_RivalsHouse_EventScript_GroomMon
 * goto_if_eq RECEIVED_TOWN_MAP, TRUE, PalletTown_RivalsHouse_EventScript_PleaseGiveMonsRest
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_RIVALS_HOUSE, 2, PalletTown_RivalsHouse_EventScript_ExplainTownMap
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_RIVALS_HOUSE, 1, PalletTown_RivalsHouse_EventScript_GiveTownMap
 * goto_if_ge VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 1, PalletTown_RivalsHouse_EventScript_HeardBattledRival
 * msgbox PalletTown_RivalsHouse_Text_HiBrothersAtLab
 * closemessage
 * applymovement LOCALID_DAISY, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object PalletTown_RivalsHouse_EventScript_Daisy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_RivalsHouse_EventScript_Daisy")
}

internal object PalletTown_RivalsHouse_EventScript_TownMap : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PalletTown_RivalsHouse.ItsBigMapOfKanto)
}

internal object PalletTown_RivalsHouse_EventScript_Bookshelf : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(PalletTown_RivalsHouse.ShelvesCrammedFullOfBooks)
}

internal object PalletTown_RivalsHouse_EventScript_Picture : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(PalletTown_RivalsHouse.LovelyAndSweetClefairy)
}

internal val PalletTown_RivalsHouseScripts: Map<String, Script> =
    mapOf(
        "PalletTown_RivalsHouse_EventScript_Daisy" to PalletTown_RivalsHouse_EventScript_Daisy,
        "PalletTown_RivalsHouse_EventScript_TownMap" to PalletTown_RivalsHouse_EventScript_TownMap,
        "PalletTown_RivalsHouse_EventScript_Bookshelf" to
            PalletTown_RivalsHouse_EventScript_Bookshelf,
        "PalletTown_RivalsHouse_EventScript_Picture" to PalletTown_RivalsHouse_EventScript_Picture,
    )
