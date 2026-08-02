package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.hoenn.PlayersHouse_2F
import de.fiereu.openmmo.dialog.generated.hoenn.RivalsHouse_2F
import de.fiereu.openmmo.server.game.script.MovementStep.DELAY_16
import de.fiereu.openmmo.server.game.script.MovementStep.DELAY_8
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
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val LOCALID_BEDROOM_MOM = 13
private const val LOCALID_RIVAL = 0
private const val LOCALID_RIVALS_POKE_BALL = 14

internal object LittlerootTown_BrendansHouse_2F_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFemale && ctx.getVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE) < 2) {
      ctx.setVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE, 2)
    }
    if (ctx.isFemale &&
        ctx.getVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE) >= 3 &&
        ctx.getVar(HoennVars.VAR_BIRCH_LAB_STATE) < 2) {
      // Keep Brendan at his post-cutscene position.
      ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BRENDANS_HOUSE_RIVAL_BEDROOM)
      ctx.removeNpc(LOCALID_RIVAL)
      ctx.showNpcAt(LOCALID_RIVAL, 0, 2)
    }
    if (ctx.getVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE) == 4) {
      ctx.setVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE, 5)
    }
  }
}

internal object RivalsHouse_2F_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.sayNpc(
        LOCALID_RIVAL,
        if (ctx.isFemale) RivalsHouse_2F.BrendanGettingReady else RivalsHouse_2F.MayGettingReady,
    )
  }
}

internal object LittlerootTown_BrendansHouse_2F_EventScript_RivalsPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE) != 2) {
      ctx.say(RivalsHouse_2F.ItsRivalsPokeBall)
      return
    }

    ctx.showNpc(LOCALID_RIVAL)
    ctx.moveNpc(LOCALID_RIVAL, WALK_DOWN, WALK_DOWN, FACE_LEFT)
    when (ctx.facingDirection) {
      Direction.UP -> {
        ctx.moveNpc(LOCALID_RIVAL, WALK_LEFT, WALK_LEFT, WALK_DOWN, WALK_DOWN, WALK_LEFT)
        ctx.moveSelf(FACE_RIGHT)
        ctx.sayNpc(LOCALID_RIVAL, RivalsHouse_2F.BrendanWhoAreYou)
        ctx.moveSelf(FACE_UP, FACE_LEFT)
        ctx.moveNpc(
            LOCALID_RIVAL,
            WALK_UP,
            WALK_UP,
            WALK_UP,
            WALK_LEFT,
            WALK_LEFT,
            WALK_LEFT,
            WALK_LEFT,
            FACE_UP,
        )
      }
      Direction.DOWN,
      Direction.DIVE,
      Direction.EMERGE -> {
        ctx.moveNpc(LOCALID_RIVAL, WALK_LEFT, WALK_LEFT, WALK_LEFT)
        ctx.moveSelf(FACE_RIGHT)
        ctx.sayNpc(LOCALID_RIVAL, RivalsHouse_2F.BrendanWhoAreYou)
        ctx.moveSelf(FACE_UP, FACE_LEFT)
        ctx.moveNpc(LOCALID_RIVAL, WALK_UP, WALK_LEFT, WALK_LEFT, WALK_LEFT, WALK_LEFT, FACE_UP)
      }
      Direction.LEFT -> {
        ctx.moveNpc(LOCALID_RIVAL, WALK_LEFT, WALK_LEFT, WALK_DOWN, FACE_LEFT)
        ctx.moveSelf(FACE_RIGHT)
        ctx.sayNpc(LOCALID_RIVAL, RivalsHouse_2F.BrendanWhoAreYou)
        ctx.moveSelf(FACE_UP, FACE_LEFT)
        ctx.moveNpc(
            LOCALID_RIVAL,
            WALK_UP,
            WALK_UP,
            WALK_LEFT,
            WALK_LEFT,
            WALK_LEFT,
            WALK_LEFT,
            WALK_LEFT,
            FACE_UP,
        )
      }
      Direction.RIGHT -> {
        ctx.moveNpc(LOCALID_RIVAL, WALK_LEFT, WALK_LEFT, WALK_LEFT, WALK_LEFT, WALK_LEFT, FACE_DOWN)
        ctx.moveSelf(FACE_UP)
        ctx.sayNpc(LOCALID_RIVAL, RivalsHouse_2F.BrendanWhoAreYou)
        ctx.moveNpc(LOCALID_RIVAL, WALK_UP, WALK_LEFT, WALK_LEFT, FACE_UP)
      }
    }

    ctx.moveNpc(LOCALID_RIVALS_POKE_BALL, SET_INVISIBLE)
    ctx.setVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE, 3)
    ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BRENDANS_HOUSE_2F_POKE_BALL)
    ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_BRENDANS_HOUSE_RIVAL_BEDROOM)
    ctx.setVar(HoennVars.VAR_LITTLEROOT_TOWN_STATE, 1)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, LittlerootTown_BrendansHouse_2F_EventScript_CheckPlayersPC
 * goto_if_eq VAR_RESULT, FEMALE, LittlerootTown_BrendansHouse_2F_EventScript_CheckRivalsPC
 * end
 * ```
 */
internal object LittlerootTown_BrendansHouse_2F_EventScript_PC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_BrendansHouse_2F_EventScript_PC")
}

internal object PlayersHouse_2F_EventScript_Notebook : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PlayersHouse_2F.Notebook)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, MALE
 * goto PlayersHouse_2F_EventScript_WallClock
 * end
 * ```
 */
internal object LittlerootTown_BrendansHouse_2F_EventScript_WallClock : Script {
  override suspend fun run(ctx: ScriptContext) = setWallClock(ctx, female = false)
}

internal suspend fun setWallClock(ctx: ScriptContext, female: Boolean) {
  if (ctx.isFlagSet(HoennFlags.FLAG_SET_WALL_CLOCK)) return
  ctx.sign(PlayersHouse_2F.ClockIsStopped)
  ctx.setVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE, 6)
  ctx.setFlag(HoennFlags.FLAG_SET_WALL_CLOCK)
  ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_PLAYERS_HOUSE_VIGOROTH_1)
  ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_PLAYERS_HOUSE_VIGOROTH_2)

  ctx.showNpcAt(LOCALID_BEDROOM_MOM, if (female) 1 else 7, 1)
  ctx.moveNpc(
      LOCALID_BEDROOM_MOM,
      DELAY_8,
      WALK_DOWN,
      if (female) FACE_RIGHT else FACE_LEFT,
      DELAY_16,
      DELAY_8,
      if (female) WALK_RIGHT else WALK_LEFT,
  )
  ctx.moveSelf(if (female) FACE_LEFT else FACE_RIGHT)
  ctx.sayNpc(LOCALID_BEDROOM_MOM, PlayersHouse_2F.HowDoYouLikeYourRoom)
  ctx.moveNpc(
      LOCALID_BEDROOM_MOM,
      if (female) WALK_LEFT else WALK_RIGHT,
      WALK_UP,
      DELAY_8,
  )
  // Removing Mom clears her stair collision.
  ctx.removeNpc(LOCALID_BEDROOM_MOM)
}

internal object PlayersHouse_2F_EventScript_GameCube : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PlayersHouse_2F.ItsAGameCube)
}

internal val LittlerootTown_BrendansHouse_2FScripts: Map<String, Script> =
    mapOf(
        "LittlerootTown_BrendansHouse_2F_OnTransition" to
            LittlerootTown_BrendansHouse_2F_OnTransition,
        "RivalsHouse_2F_EventScript_Rival" to RivalsHouse_2F_EventScript_Rival,
        "LittlerootTown_BrendansHouse_2F_EventScript_RivalsPokeBall" to
            LittlerootTown_BrendansHouse_2F_EventScript_RivalsPokeBall,
        "LittlerootTown_BrendansHouse_2F_EventScript_PC" to
            LittlerootTown_BrendansHouse_2F_EventScript_PC,
        "PlayersHouse_2F_EventScript_Notebook" to PlayersHouse_2F_EventScript_Notebook,
        "LittlerootTown_BrendansHouse_2F_EventScript_WallClock" to
            LittlerootTown_BrendansHouse_2F_EventScript_WallClock,
        "PlayersHouse_2F_EventScript_GameCube" to PlayersHouse_2F_EventScript_GameCube,
    )
