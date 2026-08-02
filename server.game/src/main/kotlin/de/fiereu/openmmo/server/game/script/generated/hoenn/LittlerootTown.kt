package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LittlerootTown
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_UP
import de.fiereu.openmmo.server.game.script.MovementStep.SET_INVISIBLE
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

// Decomp local id of mom in this map's object events (LOCALID_LITTLEROOT_MOM).
private const val LOCALID_MOM = 3
private const val LOCALID_TWIN = 0

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
  override suspend fun run(ctx: ScriptContext) {
    when {
      ctx.isFlagSet(HoennFlags.FLAG_ADVENTURE_STARTED) ->
          ctx.say(LittlerootTown.GoodLuckCatchingPokemon)
      ctx.isFlagSet(HoennFlags.FLAG_RESCUED_BIRCH) -> ctx.say(LittlerootTown.YouSavedBirch)
      ctx.getVar(HoennVars.VAR_LITTLEROOT_TOWN_STATE) != 0 -> {
        ctx.say(LittlerootTown.CanYouGoSeeWhatsHappening)
        ctx.setVar(HoennVars.VAR_LITTLEROOT_TOWN_STATE, 2)
      }
      else -> ctx.say(LittlerootTown.IfYouGoInGrassPokemonWillJumpOut)
    }
  }
}

/**
 * Runs whenever the player enters Littleroot Town. Decomp body:
 * ```
 * setflag FLAG_VISITED_LITTLEROOT_TOWN
 * call Common_EventScript_SetupRivalGfxId
 * call_if_eq VAR_LITTLEROOT_INTRO_STATE, 2, LittlerootTown_EventScript_MoveMomToMaysDoor
 * ```
 *
 * Female-path positions are applied during spawning.
 */
internal object LittlerootTown_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(HoennFlags.FLAG_VISITED_LITTLEROOT_TOWN)
    if (ctx.getVar(HoennVars.VAR_LITTLEROOT_TOWN_STATE) == 3 &&
        !ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_RUNNING_SHOES)) {
      ctx.clearFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MOM_OUTSIDE)
      ctx.showNpcAt(LOCALID_MOM, if (ctx.isFemale) 14 else 5, 9)
    }
  }
}

/** Runs the male moving-truck cutscene. */
internal object LittlerootTown_EventScript_StepOffTruckMale : Script {
  override suspend fun run(ctx: ScriptContext) = stepOffTruck(ctx, female = false)
}

/** Female half of the moving-truck cutscene (intro state 2). */
internal object LittlerootTown_EventScript_StepOffTruckFemale : Script {
  override suspend fun run(ctx: ScriptContext) = stepOffTruck(ctx, female = true)
}

private suspend fun stepOffTruck(ctx: ScriptContext, female: Boolean) {
  val doorX = if (female) 14 else 5
  val houseMap = if (female) 2 else 0
  val houseX = if (female) 2 else 8

  // Approximate the scripted hop with ordinary movement.
  ctx.moveSelf(WALK_RIGHT)
  ctx.showNpcAt(LOCALID_MOM, doorX, 8)
  ctx.moveNpc(LOCALID_MOM, WALK_DOWN, WALK_DOWN, FACE_LEFT)
  ctx.say(LittlerootTown.OurNewHomeLetsGoInside)
  ctx.moveNpc(LOCALID_MOM, WALK_UP, WALK_UP, SET_INVISIBLE)
  ctx.moveSelf(WALK_RIGHT, FACE_UP, WALK_UP, WALK_UP)

  ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MOM_OUTSIDE)
  ctx.setFlag(
      if (female) HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MAYS_HOUSE_TRUCK
      else HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BRENDANS_HOUSE_TRUCK)
  ctx.clearFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_FAT_MAN)
  ctx.clearFlag(HoennFlags.FLAG_HIDE_MAP_NAME_POPUP)
  ctx.setVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE, 3)
  ctx.warp(
      regionId = 1, bankId = 51, mapId = houseMap, x = houseX, y = 8, facing = FACE_UP.direction)
}

internal object LittlerootTown_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LittlerootTown.CanUsePCToStoreItems)
}

internal object LittlerootTown_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LittlerootTown.BirchSpendsDaysInLab)
}

/** Starts Birch's rescue after meeting the rival. */
internal object LittlerootTown_EventScript_GoSaveBirchTrigger : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.moveNpc(LOCALID_TWIN, FACE_RIGHT)
    ctx.moveSelf(FACE_LEFT)
    ctx.sayNpc(LOCALID_TWIN, LittlerootTown.CanYouGoSeeWhatsHappening)
    ctx.moveNpc(LOCALID_TWIN, FACE_DOWN)
    ctx.setVar(HoennVars.VAR_LITTLEROOT_TOWN_STATE, 2)
  }
}

internal object LittlerootTown_EventScript_NeedPokemonTriggerLeft : Script {
  override suspend fun run(ctx: ScriptContext) = preventLeavingWithoutPokemon(ctx)
}

internal object LittlerootTown_EventScript_NeedPokemonTriggerRight : Script {
  override suspend fun run(ctx: ScriptContext) = preventLeavingWithoutPokemon(ctx)
}

private suspend fun preventLeavingWithoutPokemon(ctx: ScriptContext) {
  ctx.sayNpc(LOCALID_TWIN, LittlerootTown.IfYouGoInGrassPokemonWillJumpOut)
  ctx.moveSelf(WALK_DOWN)
  ctx.sayNpc(LOCALID_TWIN, LittlerootTown.DangerousIfYouDontHavePokemon)
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
  override suspend fun run(ctx: ScriptContext) = giveRunningShoes(ctx)
}

internal object LittlerootTown_EventScript_GiveRunningShoes : Script {
  override suspend fun run(ctx: ScriptContext) = giveRunningShoes(ctx)
}

private suspend fun giveRunningShoes(ctx: ScriptContext) {
  if (ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_RUNNING_SHOES)) return
  ctx.sayNpc(LOCALID_MOM, LittlerootTown.WaitPlayer)
  ctx.sayNpc(LOCALID_MOM, LittlerootTown.WearTheseRunningShoes)
  ctx.sayNpc(LOCALID_MOM, LittlerootTown.SwitchShoesWithRunningShoes)
  ctx.setFlag(HoennFlags.FLAG_RECEIVED_RUNNING_SHOES)
  ctx.setFlag(HoennFlags.FLAG_SYS_B_DASH)
  ctx.sayNpc(LOCALID_MOM, LittlerootTown.ExplainRunningShoes)
  ctx.sayNpc(LOCALID_MOM, LittlerootTown.ComeHomeIfAnythingHappens)
  ctx.moveNpc(LOCALID_MOM, WALK_UP, SET_INVISIBLE)
  ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MOM_OUTSIDE)
  ctx.setVar(HoennVars.VAR_LITTLEROOT_TOWN_STATE, 4)
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
        "LittlerootTown_OnTransition" to LittlerootTown_OnTransition,
        "LittlerootTown_EventScript_StepOffTruckMale" to
            LittlerootTown_EventScript_StepOffTruckMale,
        "LittlerootTown_EventScript_StepOffTruckFemale" to
            LittlerootTown_EventScript_StepOffTruckFemale,
        "LittlerootTown_EventScript_Twin" to LittlerootTown_EventScript_Twin,
        "LittlerootTown_EventScript_FatMan" to LittlerootTown_EventScript_FatMan,
        "LittlerootTown_EventScript_Boy" to LittlerootTown_EventScript_Boy,
        "LittlerootTown_EventScript_GoSaveBirchTrigger" to
            LittlerootTown_EventScript_GoSaveBirchTrigger,
        "LittlerootTown_EventScript_NeedPokemonTriggerLeft" to
            LittlerootTown_EventScript_NeedPokemonTriggerLeft,
        "LittlerootTown_EventScript_NeedPokemonTriggerRight" to
            LittlerootTown_EventScript_NeedPokemonTriggerRight,
        "LittlerootTown_EventScript_GiveRunningShoesTrigger0" to
            LittlerootTown_EventScript_GiveRunningShoes,
        "LittlerootTown_EventScript_GiveRunningShoesTrigger1" to
            LittlerootTown_EventScript_GiveRunningShoes,
        "LittlerootTown_EventScript_GiveRunningShoesTrigger2" to
            LittlerootTown_EventScript_GiveRunningShoes,
        "LittlerootTown_EventScript_GiveRunningShoesTrigger3" to
            LittlerootTown_EventScript_GiveRunningShoes,
        "LittlerootTown_EventScript_GiveRunningShoesTrigger4" to
            LittlerootTown_EventScript_GiveRunningShoes,
        "LittlerootTown_EventScript_GiveRunningShoesTrigger5" to
            LittlerootTown_EventScript_GiveRunningShoes,
        "LittlerootTown_EventScript_Mom" to LittlerootTown_EventScript_Mom,
        "LittlerootTown_EventScript_TownSign" to LittlerootTown_EventScript_TownSign,
        "LittlerootTown_EventScript_BirchsLabSign" to LittlerootTown_EventScript_BirchsLabSign,
        "LittlerootTown_EventScript_BrendansHouseSign" to
            LittlerootTown_EventScript_BrendansHouseSign,
        "LittlerootTown_EventScript_MaysHouseSign" to LittlerootTown_EventScript_MaysHouseSign,
    )
