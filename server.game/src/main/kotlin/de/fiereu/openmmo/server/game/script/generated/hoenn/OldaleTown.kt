package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.OldaleTown
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_RIGHT
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val LOCALID_FOOTPRINTS_MAN = 2
private const val LOCALID_RIVAL = 3

internal object OldaleTown_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(HoennFlags.FLAG_VISITED_OLDALE_TOWN)
    if (!ctx.isFlagSet(HoennFlags.FLAG_ADVENTURE_STARTED)) {
      ctx.showNpcAt(LOCALID_FOOTPRINTS_MAN, 1, 11)
    } else {
      ctx.setVar(HoennVars.VAR_OLDALE_TOWN_STATE, 1)
    }
  }
}

internal object OldaleTown_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(OldaleTown.SavingMyProgress)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_POTION_OLDALE, OldaleTown_EventScript_ExplainPotion
 * goto_if_set FLAG_TEMP_1, OldaleTown_EventScript_ExplainPotion
 * setflag FLAG_TEMP_1
 * playbgm MUS_FOLLOW_ME, FALSE
 * msgbox OldaleTown_Text_IWorkAtPokemonMart, MSGBOX_DEFAULT
 * closemessage
 * switch VAR_FACING
 * case DIR_SOUTH, OldaleTown_EventScript_GoToMartSouth
 * case DIR_NORTH, OldaleTown_EventScript_GoToMartNorth
 * case DIR_EAST, OldaleTown_EventScript_GoToMartEast
 * end
 * ```
 */
internal object OldaleTown_EventScript_MartEmployee : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port OldaleTown_EventScript_MartEmployee")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_ADVENTURE_STARTED, OldaleTown_EventScript_NotBlockingPath
 * msgbox OldaleTown_Text_DiscoveredFootprints, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_FOOTPRINTS_MAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object OldaleTown_EventScript_FootprintsMan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_ADVENTURE_STARTED)) {
      ctx.say(OldaleTown.FinishedSketchingFootprints)
    } else {
      ctx.say(OldaleTown.DiscoveredFootprints)
    }
  }
}

internal object OldaleTown_EventScript_BlockedPath : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_ADVENTURE_STARTED)) return
    ctx.moveSelf(WALK_RIGHT)
    ctx.moveNpc(LOCALID_FOOTPRINTS_MAN, WALK_RIGHT)
    ctx.sayNpc(LOCALID_FOOTPRINTS_MAN, OldaleTown.WaitDontComeInHere)
    ctx.moveNpc(LOCALID_FOOTPRINTS_MAN, WALK_LEFT)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_OLDALE_RIVAL, Common_Movement_FacePlayer
 * waitmovement 0
 * setvar VAR_0x8009, 0
 * goto OldaleTown_EventScript_ShowRivalMessage
 * end
 * ```
 */
internal object OldaleTown_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) = finishRivalReturn(ctx)
}

internal object OldaleTown_EventScript_RivalTrigger1 : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.moveNpc(LOCALID_RIVAL, WALK_LEFT, WALK_LEFT)
    finishRivalReturn(ctx)
  }
}

internal object OldaleTown_EventScript_RivalTrigger2 : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.moveNpc(LOCALID_RIVAL, WALK_LEFT)
    finishRivalReturn(ctx)
  }
}

internal object OldaleTown_EventScript_RivalTrigger3 : Script {
  override suspend fun run(ctx: ScriptContext) = finishRivalReturn(ctx)
}

private suspend fun finishRivalReturn(ctx: ScriptContext) {
  if (ctx.getVar(HoennVars.VAR_OLDALE_RIVAL_STATE) != 1) return
  if (ctx.isFemale) ctx.sayNpc(LOCALID_RIVAL, OldaleTown.BrendanLetsGoBack)
  else ctx.sayNpc(LOCALID_RIVAL, OldaleTown.MayLetsGoBack)
  ctx.moveNpc(
      LOCALID_RIVAL,
      WALK_DOWN,
      WALK_DOWN,
      WALK_DOWN,
      WALK_DOWN,
      WALK_DOWN,
      WALK_DOWN,
      FACE_DOWN,
  )
  ctx.setVar(HoennVars.VAR_OLDALE_RIVAL_STATE, 2)
  ctx.setFlag(HoennFlags.FLAG_HIDE_OLDALE_TOWN_RIVAL)
}

internal object OldaleTown_EventScript_TownSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(OldaleTown.TownSign)
}

internal val OldaleTownScripts: Map<String, Script> =
    mapOf(
        "OldaleTown_OnTransition" to OldaleTown_OnTransition,
        "OldaleTown_EventScript_Girl" to OldaleTown_EventScript_Girl,
        "OldaleTown_EventScript_MartEmployee" to OldaleTown_EventScript_MartEmployee,
        "OldaleTown_EventScript_FootprintsMan" to OldaleTown_EventScript_FootprintsMan,
        "OldaleTown_EventScript_BlockedPath" to OldaleTown_EventScript_BlockedPath,
        "OldaleTown_EventScript_Rival" to OldaleTown_EventScript_Rival,
        "OldaleTown_EventScript_RivalTrigger1" to OldaleTown_EventScript_RivalTrigger1,
        "OldaleTown_EventScript_RivalTrigger2" to OldaleTown_EventScript_RivalTrigger2,
        "OldaleTown_EventScript_RivalTrigger3" to OldaleTown_EventScript_RivalTrigger3,
        "OldaleTown_EventScript_TownSign" to OldaleTown_EventScript_TownSign,
    )
