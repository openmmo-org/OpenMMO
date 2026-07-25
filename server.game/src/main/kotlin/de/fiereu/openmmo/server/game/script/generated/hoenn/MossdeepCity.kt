package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MossdeepCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_HM_DIVE, MossdeepCity_EventScript_SailorMagmaGone
 * msgbox MossdeepCity_Text_MossdeepTargetedByMagma, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_EventScript_Sailor")
}

internal object MossdeepCity_EventScript_ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity.LifeNeedsSeaToLive)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_HM_DIVE, MossdeepCity_EventScript_PokefanFMagmaGone
 * msgbox MossdeepCity_Text_SpaceCenterReceivedLetter, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_EventScript_PokefanF")
}

internal object MossdeepCity_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity.WailmerWatching)
}

internal object MossdeepCity_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity.NiceIfWorldCoveredByFlowers)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NET_BALL
 * end
 * ```
 */
internal object MossdeepCity_EventScript_ItemNetBall : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_EventScript_ItemNetBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox MossdeepCity_Text_SurfExhilarating, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object MossdeepCity_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_EventScript_Man")
}

internal object MossdeepCity_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity.SpecialSpaceCenterRock)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_KINGS_ROCK, MossdeepCity_EventScript_ReceivedKingsRock
 * msgbox MossdeepCity_Text_WantKingsRockStevenGaveMe, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MossdeepCity_EventScript_DeclineKingsRock
 * msgbox MossdeepCity_Text_YouCanKeepIt, MSGBOX_DEFAULT
 * giveitem ITEM_KINGS_ROCK
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_KINGS_ROCK
 * release
 * end
 * ```
 */
internal object MossdeepCity_EventScript_KingsRockBoy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_EventScript_KingsRockBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MOVE_TUTOR_TAUGHT_DYNAMICPUNCH, MoveTutor_EventScript_DynamicPunchTaught
 * msgbox MoveTutor_Text_DynamicPunchTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_DynamicPunchDeclined
 * call MoveTutor_EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_DynamicPunchDeclined
 * msgbox MoveTutor_Text_DynamicPunchWhichMon, MSGBOX_DEFAULT
 * setvar VAR_0x8005, TUTOR_MOVE_DYNAMIC_PUNCH
 * call MoveTutor_EventScript_OpenPartyMenu
 * goto_if_eq VAR_RESULT, 0, MoveTutor_EventScript_DynamicPunchDeclined
 * setflag FLAG_MOVE_TUTOR_TAUGHT_DYNAMICPUNCH
 * goto MoveTutor_EventScript_DynamicPunchTaught
 * end
 * ```
 */
internal object MossdeepCity_EventScript_DynamicPunchTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_EventScript_DynamicPunchTutor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox MossdeepCity_Text_ScottSomethingWrongWithTown, MSGBOX_DEFAULT
 * closemessage
 * call_if_eq VAR_FACING, DIR_NORTH, MossdeepCity_EventScript_ScottExitNorth
 * call_if_eq VAR_FACING, DIR_EAST, MossdeepCity_EventScript_ScottExitEast
 * addvar VAR_SCOTT_STATE, 1
 * removeobject LOCALID_MOSSDEEP_SCOTT
 * release
 * end
 * ```
 */
internal object MossdeepCity_EventScript_Scott : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_EventScript_Scott")
}

internal object MossdeepCity_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity.SootopolisNewGymLeader)
}

internal object MossdeepCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MossdeepCity.CitySign)
}

internal object MossdeepCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MossdeepCity.GymSign)
}

internal object MossdeepCity_EventScript_SpaceCenterSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MossdeepCity.SpaceCenterSign)
}

internal object MossdeepCity_EventScript_WhiteRock : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MossdeepCity.ItsAWhiteRock)
}

internal val MossdeepCityScripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_EventScript_Sailor" to MossdeepCity_EventScript_Sailor,
        "MossdeepCity_EventScript_ExpertM" to MossdeepCity_EventScript_ExpertM,
        "MossdeepCity_EventScript_PokefanF" to MossdeepCity_EventScript_PokefanF,
        "MossdeepCity_EventScript_NinjaBoy" to MossdeepCity_EventScript_NinjaBoy,
        "MossdeepCity_EventScript_Girl" to MossdeepCity_EventScript_Girl,
        "MossdeepCity_EventScript_ItemNetBall" to MossdeepCity_EventScript_ItemNetBall,
        "MossdeepCity_EventScript_Man" to MossdeepCity_EventScript_Man,
        "MossdeepCity_EventScript_Woman" to MossdeepCity_EventScript_Woman,
        "MossdeepCity_EventScript_KingsRockBoy" to MossdeepCity_EventScript_KingsRockBoy,
        "MossdeepCity_EventScript_DynamicPunchTutor" to MossdeepCity_EventScript_DynamicPunchTutor,
        "MossdeepCity_EventScript_Scott" to MossdeepCity_EventScript_Scott,
        "MossdeepCity_EventScript_BlackBelt" to MossdeepCity_EventScript_BlackBelt,
        "MossdeepCity_EventScript_CitySign" to MossdeepCity_EventScript_CitySign,
        "MossdeepCity_EventScript_GymSign" to MossdeepCity_EventScript_GymSign,
        "MossdeepCity_EventScript_SpaceCenterSign" to MossdeepCity_EventScript_SpaceCenterSign,
        "MossdeepCity_EventScript_WhiteRock" to MossdeepCity_EventScript_WhiteRock,
    )
