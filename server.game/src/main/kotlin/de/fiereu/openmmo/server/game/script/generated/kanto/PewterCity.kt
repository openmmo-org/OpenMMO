package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PewterCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PewterCity_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PewterCity.ClefairyCameFromMoon)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox PewterCity_Text_DidYouCheckOutMuseum, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PewterCity_EventScript_CheckedOutMuseum
 * msgbox PewterCity_Text_ReallyYouHaveToGo
 * closemessage
 * delay 10
 * playbgm MUS_FOLLOW_ME, 0
 * call_if_eq VAR_FACING, DIR_NORTH, PewterCity_EventScript_LeadToMuseumNorth
 * call_if_eq VAR_FACING, DIR_SOUTH, PewterCity_EventScript_LeadToMuseumSouth
 * call_if_eq VAR_FACING, DIR_WEST, PewterCity_EventScript_LeadToMuseumWest
 * call_if_eq VAR_FACING, DIR_EAST, PewterCity_EventScript_LeadToMuseumEast
 * msgbox PewterCity_Text_ThisIsTheMuseum
 * closemessage
 * delay 10
 * applymovement LOCALID_PEWTER_MUSEUM_GUIDE, PewterCity_Movement_MuseumGuideExit
 * waitmovement 0
 * fadedefaultbgm
 * removeobject LOCALID_PEWTER_MUSEUM_GUIDE
 * clearflag FLAG_HIDE_PEWTER_MUSEUM_GUIDE
 * release
 * end
 * ```
 */
internal object PewterCity_EventScript_MuseumGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PewterCity_EventScript_MuseumGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BROCK, 2
 * msgbox PewterCity_Text_BrockOnlySeriousTrainerHere
 * release
 * end
 * ```
 */
internal object PewterCity_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PewterCity_EventScript_FatMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox PewterCity_Text_DoYouKnowWhatImDoing, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PewterCity_EventScript_KnowWhatTheyreDoing
 * msgbox PewterCity_Text_SprayingRepelToKeepWildMonsOut
 * release
 * end
 * ```
 */
internal object PewterCity_EventScript_BugCatcher : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PewterCity_EventScript_BugCatcher")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox PewterCity_Text_BrocksLookingForChallengersFollowMe
 * closemessage
 * playbgm MUS_FOLLOW_ME, 0
 * call_if_eq VAR_FACING, DIR_EAST, PewterCity_EventScript_WalkToGymEast
 * msgbox PewterCity_Text_GoTakeOnBrock
 * closemessage
 * applymovement LOCALID_PEWTER_GYM_GUIDE, PewterCity_Movement_GymGuideExit
 * waitmovement 0
 * fadedefaultbgm
 * removeobject LOCALID_PEWTER_GYM_GUIDE
 * clearflag FLAG_HIDE_PEWTER_CITY_GYM_GUIDE
 * release
 * end
 * ```
 */
internal object PewterCity_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PewterCity_EventScript_GymGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, 0
 * call PewterCity_EventScript_AideGiveRunningShoes
 * release
 * end
 * ```
 */
internal object PewterCity_EventScript_RunningShoesAide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PewterCity_EventScript_RunningShoesAide")
}

internal object PewterCity_EventScript_MuseumSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PewterCity.MuseumOfScience)
}

internal object PewterCity_EventScript_PoliceNotice : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PewterCity.CallPoliceIfInfoOnThieves)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_BROCK, 0
 * msgbox PewterCity_Text_GymSign
 * releaseall
 * end
 * ```
 */
internal object PewterCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PewterCity_EventScript_GymSign")
}

internal object PewterCity_EventScript_TrainerTips : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PewterCity.TrainerTipsEarningEXP)
}

internal object PewterCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PewterCity.CitySign)
}

internal val PewterCityScripts: Map<String, Script> =
    mapOf(
        "PewterCity_EventScript_Lass" to PewterCity_EventScript_Lass,
        "PewterCity_EventScript_MuseumGuide" to PewterCity_EventScript_MuseumGuide,
        "PewterCity_EventScript_FatMan" to PewterCity_EventScript_FatMan,
        "PewterCity_EventScript_BugCatcher" to PewterCity_EventScript_BugCatcher,
        "PewterCity_EventScript_GymGuide" to PewterCity_EventScript_GymGuide,
        "PewterCity_EventScript_RunningShoesAide" to PewterCity_EventScript_RunningShoesAide,
        "PewterCity_EventScript_MuseumSign" to PewterCity_EventScript_MuseumSign,
        "PewterCity_EventScript_PoliceNotice" to PewterCity_EventScript_PoliceNotice,
        "PewterCity_EventScript_GymSign" to PewterCity_EventScript_GymSign,
        "PewterCity_EventScript_TrainerTips" to PewterCity_EventScript_TrainerTips,
        "PewterCity_EventScript_CitySign" to PewterCity_EventScript_CitySign,
    )
