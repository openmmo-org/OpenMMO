package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PalletTown
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY, 2, PalletTown_EventScript_SignLadyDone
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY, 1, PalletTown_EventScript_SignLadyJustShowedSign
 * goto_if_eq SIGN_LADY_READY, TRUE, PalletTown_EventScript_SignLadyStartShowSign
 * goto_if_set FLAG_TEMP_2, PalletTown_EventScript_SignLadyGoReadSign
 * msgbox PalletTown_Text_HmmIsThatRight
 * applymovement LOCALID_PALLET_SIGN_LADY, Common_Movement_FacePlayer
 * waitmovement 0
 * playse SE_PIN
 * applymovement LOCALID_PALLET_SIGN_LADY, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_PALLET_SIGN_LADY, Common_Movement_Delay48
 * waitmovement 0
 * msgbox PalletTown_Text_OhLookLook
 * closemessage
 * call_if_eq VAR_FACING, DIR_EAST, PalletTown_EventScript_SignLadyMoveOutOfWayRight
 * call_if_ne VAR_FACING, DIR_EAST, PalletTown_EventScript_SignLadyMoveOutOfWayLeft
 * copyobjectxytoperm LOCALID_PALLET_SIGN_LADY
 * setflag FLAG_TEMP_2
 * release
 * end
 * ```
 */
internal object PalletTown_EventScript_SignLady : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PalletTown_EventScript_SignLady")
}

internal object PalletTown_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PalletTown.CanStoreItemsAndMonsInPC)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_OAK, 0
 * msgbox PalletTown_Text_OakPokemonResearchLab
 * releaseall
 * end
 * ```
 */
internal object PalletTown_EventScript_OaksLabSign : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PalletTown_EventScript_OaksLabSign")
}

internal object PalletTown_EventScript_PlayersHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PalletTown.PlayersHouse)
}

internal object PalletTown_EventScript_RivalsHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PalletTown.RivalsHouse)
}

internal object PalletTown_EventScript_TownSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PalletTown.TownSign)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox PalletTown_Text_PressStartToOpenMenu
 * setvar VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY, 1
 * releaseall
 * end
 * ```
 */
internal object PalletTown_EventScript_TrainerTips : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PalletTown_EventScript_TrainerTips")
}

internal val PalletTownScripts: Map<String, Script> =
    mapOf(
        "PalletTown_EventScript_SignLady" to PalletTown_EventScript_SignLady,
        "PalletTown_EventScript_FatMan" to PalletTown_EventScript_FatMan,
        "PalletTown_EventScript_OaksLabSign" to PalletTown_EventScript_OaksLabSign,
        "PalletTown_EventScript_PlayersHouseSign" to PalletTown_EventScript_PlayersHouseSign,
        "PalletTown_EventScript_RivalsHouseSign" to PalletTown_EventScript_RivalsHouseSign,
        "PalletTown_EventScript_TownSign" to PalletTown_EventScript_TownSign,
        "PalletTown_EventScript_TrainerTips" to PalletTown_EventScript_TrainerTips,
    )
