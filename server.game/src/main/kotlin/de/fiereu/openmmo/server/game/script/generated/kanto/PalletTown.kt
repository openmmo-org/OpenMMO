package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region
import de.fiereu.openmmo.dialog.generated.kanto.PalletTown
import de.fiereu.openmmo.server.game.script.MovementStep
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_UP
import de.fiereu.openmmo.server.game.script.MovementStep.SET_INVISIBLE
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val LOCALID_SIGN_LADY = 0
private const val LOCALID_PROF_OAK = 2

private const val OAKS_LAB_BANK = 4
private const val OAKS_LAB_MAP = 3
private const val OAKS_LAB_ENTRY_X = 6
private const val OAKS_LAB_ENTRY_Y = 12

// The shared tail of every walk down to the lab door (the decomp walk_to_lab macro).
private val WALK_TO_LAB = listOf(WALK_LEFT) + List(11) { WALK_DOWN } + List(4) { WALK_RIGHT }

internal object PalletTown_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(KantoFlags.FLAG_WORLD_MAP_PALLET_TOWN)
    val notBlocking = ctx.isFlagSet(KantoFlags.FLAG_PALLET_LADY_NOT_BLOCKING_SIGN)
    // Once the player has a starter she stops blocking the sign, and once they have opened the
    // start menu on their own she has nothing left to teach.
    if (notBlocking &&
        ctx.isFlagSet(KantoFlags.FLAG_OPENED_START_MENU) &&
        ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY) < 1) {
      ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY, 1)
    }
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY) == 0) {
      if (notBlocking) ctx.repositionNpc(LOCALID_SIGN_LADY, 12, 2)
      else ctx.repositionNpc(LOCALID_SIGN_LADY, 5, 15)
    }
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY) == 1) {
      ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY, 2)
    }
  }
}

internal object PalletTown_EventScript_OakTriggerLeft : Script {
  override suspend fun run(ctx: ScriptContext) = oakTrigger(ctx, fromLeft = true)
}

internal object PalletTown_EventScript_OakTriggerRight : Script {
  override suspend fun run(ctx: ScriptContext) = oakTrigger(ctx, fromLeft = false)
}

/**
 * Oak stops the player from walking into the tall grass and walks them back to his lab. The player
 * enters from one of two tiles, which decides where Oak comes from and which way both of them walk.
 */
private suspend fun oakTrigger(ctx: ScriptContext, fromLeft: Boolean) {
  ctx.sign(PalletTown.OakDontGoOut)
  ctx.moveSelf(FACE_DOWN)
  ctx.showNpc(LOCALID_PROF_OAK)
  ctx.moveNpc(LOCALID_PROF_OAK, *oakApproach(fromLeft))
  ctx.sayNpc(LOCALID_PROF_OAK, PalletTown.OakGrassUnsafeNeedMon)

  val playerWalk =
      if (fromLeft) listOf(WALK_DOWN, WALK_DOWN) + WALK_TO_LAB
      else listOf(WALK_DOWN, WALK_DOWN, WALK_LEFT) + WALK_TO_LAB
  val oakWalk =
      (if (fromLeft) listOf(WALK_DOWN) else listOf(WALK_DOWN, WALK_LEFT)) +
          WALK_TO_LAB +
          listOf(WALK_RIGHT, FACE_UP)
  ctx.moveSelfAndNpcs(playerWalk, LOCALID_PROF_OAK to oakWalk)

  // TODO Animate the lab door during the Oak trigger
  //  The decomp opens and closes the door at (16, 13) around both of them stepping in. Doors need
  //  opendoor, closedoor and waitdooranim script verbs.
  ctx.moveNpc(LOCALID_PROF_OAK, WALK_UP, SET_INVISIBLE)
  ctx.moveSelf(WALK_RIGHT, WALK_UP)

  ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 1)
  ctx.clearFlag(KantoFlags.FLAG_HIDE_OAK_IN_HIS_LAB)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_OAK, 1)
  ctx.setFlag(KantoFlags.FLAG_HIDE_OAK_IN_PALLET_TOWN)
  ctx.setFlag(KantoFlags.FLAG_DONT_TRANSITION_MUSIC)
  ctx.warp(
      Region.KANTO.wireValue.toInt(),
      OAKS_LAB_BANK,
      OAKS_LAB_MAP,
      OAKS_LAB_ENTRY_X,
      OAKS_LAB_ENTRY_Y,
      Direction.UP,
  )
}

private fun oakApproach(fromLeft: Boolean): Array<MovementStep> =
    if (fromLeft) {
      arrayOf(WALK_UP, WALK_UP, WALK_RIGHT, WALK_UP, WALK_UP, WALK_RIGHT, WALK_UP, WALK_UP)
    } else {
      arrayOf(
          WALK_RIGHT,
          WALK_UP,
          WALK_UP,
          WALK_RIGHT,
          WALK_UP,
          WALK_UP,
          WALK_RIGHT,
          WALK_UP,
          WALK_UP,
      )
    }

internal object PalletTown_EventScript_SignLady : Script {
  override suspend fun run(ctx: ScriptContext) {
    when (ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY)) {
      2 -> return ctx.say(PalletTown.RaisingMonsToo)
      1 -> return ctx.say(PalletTown.SignsAreUsefulArentThey)
    }
    // She only starts the sign lesson once she has moved to the route entrance.
    if (ctx.isFlagSet(KantoFlags.FLAG_PALLET_LADY_NOT_BLOCKING_SIGN)) {
      return signLadyShowSign(ctx)
    }
    // TODO Port the sign lady stepping aside
    //  The decomp shows an exclamation mark, then walks her one tile left or right out of the
    //  player's way and remembers it in FLAG_TEMP_2. Neither the emote nor per visit temp flags
    //  exist yet, so she only says her lines.
    ctx.say(PalletTown.HmmIsThatRight)
    ctx.say(PalletTown.OhLookLook)
  }
}

internal object PalletTown_EventScript_SignLadyTrigger : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.moveNpc(LOCALID_SIGN_LADY, FACE_RIGHT)
    ctx.moveSelf(FACE_LEFT)
    signLadyShowSign(ctx)
  }
}

/** She copies the trainer tips sign, which is how the game teaches the start menu. */
private suspend fun signLadyShowSign(ctx: ScriptContext) {
  ctx.moveNpc(LOCALID_SIGN_LADY, FACE_DOWN)
  ctx.say(PalletTown.LookCopiedTrainerTipsSign)
  ctx.setFlag(KantoFlags.FLAG_OPENED_START_MENU)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY, 1)
  ctx.sign(PalletTown.PressStartToOpenMenuCopy)
}

internal object PalletTown_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PalletTown.CanStoreItemsAndMonsInPC)
}

internal object PalletTown_EventScript_OaksLabSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PalletTown.OakPokemonResearchLab)
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

internal object PalletTown_EventScript_TrainerTips : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.sign(PalletTown.PressStartToOpenMenu)
    ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY, 1)
  }
}

internal val PalletTownScripts: Map<String, Script> =
    mapOf(
        "PalletTown_OnTransition" to PalletTown_OnTransition,
        "PalletTown_EventScript_OakTriggerLeft" to PalletTown_EventScript_OakTriggerLeft,
        "PalletTown_EventScript_OakTriggerRight" to PalletTown_EventScript_OakTriggerRight,
        "PalletTown_EventScript_SignLady" to PalletTown_EventScript_SignLady,
        "PalletTown_EventScript_SignLadyTrigger" to PalletTown_EventScript_SignLadyTrigger,
        "PalletTown_EventScript_FatMan" to PalletTown_EventScript_FatMan,
        "PalletTown_EventScript_OaksLabSign" to PalletTown_EventScript_OaksLabSign,
        "PalletTown_EventScript_PlayersHouseSign" to PalletTown_EventScript_PlayersHouseSign,
        "PalletTown_EventScript_RivalsHouseSign" to PalletTown_EventScript_RivalsHouseSign,
        "PalletTown_EventScript_TownSign" to PalletTown_EventScript_TownSign,
        "PalletTown_EventScript_TrainerTips" to PalletTown_EventScript_TrainerTips,
    )
