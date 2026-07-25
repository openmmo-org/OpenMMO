package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LittlerootTown
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_ADVENTURE_STARTED, LittlerootTown_EventScript_GoodLuck
 * goto_if_set FLAG_RESCUED_BIRCH, LittlerootTown_EventScript_YouSavedBirch
 * goto_if_ne VAR_LITTLEROOT_TOWN_STATE, 0, LittlerootTown_EventScript_GoSaveBirch
 * msgbox LittlerootTown_Text_IfYouGoInGrassPokemonWillJumpOut, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LittlerootTown_EventScript_Twin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LittlerootTown_EventScript_Twin")
}

internal object LittlerootTown_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LittlerootTown.CanUsePCToStoreItems)
}

internal object LittlerootTown_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LittlerootTown.BirchSpendsDaysInLab)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * checkplayergender
 * call_if_eq VAR_RESULT, MALE, LittlerootTown_EventScript_SetHomeDoorCoordsMale
 * call_if_eq VAR_RESULT, FEMALE, LittlerootTown_EventScript_SetHomeDoorCoordsFemale
 * call LittlerootTown_EventScript_GiveRunningShoes
 * applymovement LOCALID_LITTLEROOT_MOM, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * opendoor VAR_0x8009, VAR_0x800A
 * waitdooranim
 * applymovement LOCALID_LITTLEROOT_MOM, LittlerootTown_Movement_MomExitThroughDoor
 * waitmovement 0
 * hideobjectat LOCALID_LITTLEROOT_MOM, MAP_LITTLEROOT_TOWN
 * closedoor VAR_0x8009, VAR_0x800A
 * waitdooranim
 * goto LittlerootTown_EventScript_SetReceivedRunningShoes
 * end
 * ```
 */
internal object LittlerootTown_EventScript_Mom : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LittlerootTown_EventScript_Mom")
}

internal object LittlerootTown_EventScript_TownSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LittlerootTown.TownSign)
}

internal object LittlerootTown_EventScript_BirchsLabSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LittlerootTown.ProfBirchsLab)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * checkplayergender
 * call_if_eq VAR_RESULT, MALE, LittlerootTown_EventScript_PlayersHouseSignMale
 * call_if_eq VAR_RESULT, FEMALE, LittlerootTown_EventScript_BirchsHouseSignFemale
 * releaseall
 * end
 * ```
 */
internal object LittlerootTown_EventScript_BrendansHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_EventScript_BrendansHouseSign")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * checkplayergender
 * call_if_eq VAR_RESULT, MALE, LittlerootTown_EventScript_BirchsHouseSignMale
 * call_if_eq VAR_RESULT, FEMALE, LittlerootTown_EventScript_PlayersHouseSignFemale
 * releaseall
 * end
 * ```
 */
internal object LittlerootTown_EventScript_MaysHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_EventScript_MaysHouseSign")
}

internal val LittlerootTownScripts: Map<String, Script> =
    mapOf(
        "LittlerootTown_EventScript_Twin" to LittlerootTown_EventScript_Twin,
        "LittlerootTown_EventScript_FatMan" to LittlerootTown_EventScript_FatMan,
        "LittlerootTown_EventScript_Boy" to LittlerootTown_EventScript_Boy,
        "LittlerootTown_EventScript_Mom" to LittlerootTown_EventScript_Mom,
        "LittlerootTown_EventScript_TownSign" to LittlerootTown_EventScript_TownSign,
        "LittlerootTown_EventScript_BirchsLabSign" to LittlerootTown_EventScript_BirchsLabSign,
        "LittlerootTown_EventScript_BrendansHouseSign" to
            LittlerootTown_EventScript_BrendansHouseSign,
        "LittlerootTown_EventScript_MaysHouseSign" to LittlerootTown_EventScript_MaysHouseSign,
    )
