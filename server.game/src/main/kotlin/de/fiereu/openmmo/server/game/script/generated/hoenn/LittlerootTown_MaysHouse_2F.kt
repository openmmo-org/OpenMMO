package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.hoenn.RivalsHouse_2F
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

private const val LOCALID_RIVAL_MAY = 0
private const val LOCALID_RIVALS_POKE_BALL_MAY = 15

internal object LittlerootTown_MaysHouse_2F_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.isFemale && ctx.getVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE) < 2) {
      ctx.setVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE, 2)
    }
    if (!ctx.isFemale &&
        ctx.getVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE) >= 3 &&
        ctx.getVar(HoennVars.VAR_BIRCH_LAB_STATE) < 2) {
      ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MAYS_HOUSE_RIVAL_BEDROOM)
      ctx.removeNpc(LOCALID_RIVAL_MAY)
      ctx.showNpcAt(LOCALID_RIVAL_MAY, 8, 2)
    }
    if (ctx.getVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE) == 4) {
      ctx.setVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE, 5)
    }
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_LITTLEROOT_RIVAL_STATE, 2, LittlerootTown_MaysHouse_2F_EventScript_MeetMay
 * msgbox RivalsHouse_2F_Text_ItsRivalsPokeBall, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object LittlerootTown_MaysHouse_2F_EventScript_RivalsPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE) != 2) {
      ctx.say(RivalsHouse_2F.ItsRivalsPokeBall)
      return
    }

    ctx.showNpc(LOCALID_RIVAL_MAY)
    ctx.moveNpc(LOCALID_RIVAL_MAY, WALK_DOWN, WALK_DOWN, FACE_RIGHT)
    when (ctx.facingDirection) {
      Direction.UP -> {
        ctx.moveNpc(LOCALID_RIVAL_MAY, WALK_RIGHT, WALK_RIGHT, WALK_DOWN, WALK_DOWN, WALK_RIGHT)
        ctx.moveSelf(FACE_LEFT)
        ctx.sayNpc(LOCALID_RIVAL_MAY, RivalsHouse_2F.MayWhoAreYou)
        ctx.moveSelf(FACE_UP, FACE_RIGHT)
        ctx.moveNpc(
            LOCALID_RIVAL_MAY,
            WALK_UP,
            WALK_UP,
            WALK_UP,
            FACE_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            FACE_UP,
        )
      }
      Direction.DOWN,
      Direction.DIVE,
      Direction.EMERGE -> {
        ctx.moveNpc(LOCALID_RIVAL_MAY, WALK_RIGHT, WALK_RIGHT, WALK_RIGHT)
        ctx.moveSelf(FACE_LEFT)
        ctx.sayNpc(LOCALID_RIVAL_MAY, RivalsHouse_2F.MayWhoAreYou)
        ctx.moveSelf(FACE_UP, FACE_RIGHT)
        ctx.moveNpc(
            LOCALID_RIVAL_MAY,
            WALK_UP,
            FACE_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            FACE_UP,
        )
      }
      Direction.LEFT -> {
        ctx.moveNpc(
            LOCALID_RIVAL_MAY,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            FACE_DOWN,
        )
        ctx.moveSelf(FACE_UP)
        ctx.sayNpc(LOCALID_RIVAL_MAY, RivalsHouse_2F.MayWhoAreYou)
        ctx.moveNpc(LOCALID_RIVAL_MAY, WALK_UP, WALK_RIGHT, WALK_RIGHT, FACE_UP)
      }
      Direction.RIGHT -> {
        ctx.moveNpc(LOCALID_RIVAL_MAY, WALK_RIGHT, WALK_RIGHT, WALK_DOWN, FACE_RIGHT)
        ctx.moveSelf(FACE_LEFT)
        ctx.sayNpc(LOCALID_RIVAL_MAY, RivalsHouse_2F.MayWhoAreYou)
        ctx.moveSelf(FACE_UP, FACE_RIGHT)
        ctx.moveNpc(
            LOCALID_RIVAL_MAY,
            WALK_UP,
            WALK_UP,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            WALK_RIGHT,
            FACE_UP,
        )
      }
    }

    ctx.moveNpc(LOCALID_RIVALS_POKE_BALL_MAY, SET_INVISIBLE)
    ctx.setVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE, 3)
    ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MAYS_HOUSE_2F_POKE_BALL)
    ctx.setFlag(HoennFlags.FLAG_HIDE_LITTLEROOT_TOWN_MAYS_HOUSE_RIVAL_BEDROOM)
    ctx.setVar(HoennVars.VAR_LITTLEROOT_TOWN_STATE, 1)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, FEMALE
 * goto PlayersHouse_2F_EventScript_WallClock
 * end
 * ```
 */
internal object LittlerootTown_MaysHouse_2F_EventScript_WallClock : Script {
  override suspend fun run(ctx: ScriptContext) = setWallClock(ctx, female = true)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, LittlerootTown_MaysHouse_2F_EventScript_CheckRivalsPC
 * goto_if_eq VAR_RESULT, FEMALE, LittlerootTown_MaysHouse_2F_EventScript_CheckPlayersPC
 * end
 * ```
 */
internal object LittlerootTown_MaysHouse_2F_EventScript_PC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_MaysHouse_2F_EventScript_PC")
}

internal val LittlerootTown_MaysHouse_2FScripts: Map<String, Script> =
    mapOf(
        "LittlerootTown_MaysHouse_2F_OnTransition" to LittlerootTown_MaysHouse_2F_OnTransition,
        "LittlerootTown_MaysHouse_2F_EventScript_RivalsPokeBall" to
            LittlerootTown_MaysHouse_2F_EventScript_RivalsPokeBall,
        "LittlerootTown_MaysHouse_2F_EventScript_WallClock" to
            LittlerootTown_MaysHouse_2F_EventScript_WallClock,
        "LittlerootTown_MaysHouse_2F_EventScript_PC" to LittlerootTown_MaysHouse_2F_EventScript_PC,
    )
