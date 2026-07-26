package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LittlerootTown
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.SET_INVISIBLE
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

// Decomp local id of mom in this map's object events (LOCALID_LITTLEROOT_MOM).
private const val LOCALID_MOM = 3

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

/**
 * Runs whenever the player enters Littleroot Town. Decomp body:
 * ```
 * setflag FLAG_VISITED_LITTLEROOT_TOWN
 * call Common_EventScript_SetupRivalGfxId
 * call_if_eq VAR_LITTLEROOT_INTRO_STATE, 2, LittlerootTown_EventScript_MoveMomToMaysDoor
 * ```
 *
 * The two calls set the rival's sprite and move mom for the intro, which need object and movement
 * scripting that does not exist yet, so only the flag is ported for now.
 */
internal object LittlerootTown_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(HoennFlags.FLAG_VISITED_LITTLEROOT_TOWN)
  }
}

/**
 * The intro cutscene, run from the on-frame table when VAR_LITTLEROOT_INTRO_STATE is 1 (the player
 * just stepped off the truck). Lean port of LittlerootTown_EventScript_StepOffTruckMale plus
 * GoInsideWithMom: mom comes out and greets the player, then the intro advances so it plays once.
 */
internal object LittlerootTown_EventScript_StepOffTruckMale : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.showNpc(LOCALID_MOM)
    ctx.moveNpc(LOCALID_MOM, WALK_DOWN, WALK_DOWN, WALK_LEFT, FACE_LEFT)
    ctx.say(LittlerootTown.OurNewHomeLetsGoInside)
    // Mom walks back to her door and steps inside (goes invisible).
    ctx.moveNpc(LOCALID_MOM, WALK_RIGHT, WALK_UP, WALK_UP, SET_INVISIBLE)
    ctx.setVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE, 3)
    // TODO Finish the Littleroot intro cutscene
    //  Port the rest of GoInsideWithMom: the player jumping off the truck, the door open/close
    //  animations, the player following mom into the house, and the warpsilent inside. These need
    //  door, hideplayer, warpsilent and sound-effect script verbs.
  }
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
        "LittlerootTown_OnTransition" to LittlerootTown_OnTransition,
        "LittlerootTown_EventScript_StepOffTruckMale" to
            LittlerootTown_EventScript_StepOffTruckMale,
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
