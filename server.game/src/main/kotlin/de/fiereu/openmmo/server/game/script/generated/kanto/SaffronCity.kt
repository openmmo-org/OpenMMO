package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SaffronCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SaffronCity_EventScript_RocketGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.WhatDoYouWantGetLost)
}

internal object SaffronCity_EventScript_RocketGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.BossTakeTownForTeamRocket)
}

internal object SaffronCity_EventScript_RocketGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.DontGetDefiantOrIllHurtYou)
}

internal object SaffronCity_EventScript_RocketGrunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.SaffronBelongsToTeamRocket)
}

internal object SaffronCity_EventScript_RocketGrunt5 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.CriminalLifeMakesMeFeelAlive)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_set FLAG_RESCUED_MR_FUJI, SaffronCity_EventScript_DoorGuardAsleep
 * applymovement LOCALID_SAFFRON_DOOR_GUARD_GRUNT, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox SaffronCity_Text_ImASecurityGuard
 * release
 * end
 * ```
 */
internal object SaffronCity_EventScript_DoorGuardGrunt : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_EventScript_DoorGuardGrunt")
}

internal object SaffronCity_EventScript_RocketGrunt7 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.WeCanExploitMonsAroundWorld)
}

internal object SaffronCity_EventScript_RocketGrunt6 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.WatchWhereYoureWalking)
}

internal object SaffronCity_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.SafeToGoOutAgain)
}

internal object SaffronCity_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.YouBeatTeamRocket)
}

internal object SaffronCity_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.SawRocketBossEscaping)
}

internal object SaffronCity_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.FlewHereOnPidgeot)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_PIDGEOT, CRY_MODE_NORMAL
 * msgbox SaffronCity_Text_Pidgeot
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SaffronCity_EventScript_Pidgeot : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_EventScript_Pidgeot")
}

internal object SaffronCity_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity.PeopleComingBackToSaffron)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_LANCE, 2
 * msgbox SaffronCity_Text_HowCanClubNotRecognizeLance
 * release
 * end
 * ```
 */
internal object SaffronCity_EventScript_CrushGirl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_EventScript_CrushGirl")
}

internal object SaffronCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity.CitySign)
}

internal object SaffronCity_EventScript_DojoSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity.FightingDojo)
}

internal object SaffronCity_EventScript_SilphProductSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity.SilphsLatestProduct)
}

internal object SaffronCity_EventScript_TrainerTips2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity.GreatBallImprovedCatchRate)
}

internal object SaffronCity_EventScript_SilphCoSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity.SilphCoSign)
}

internal object SaffronCity_EventScript_TrainerTips1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity.FullHealCuresStatus)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_SABRINA, 0
 * msgbox SaffronCity_Text_GymSign
 * releaseall
 * end
 * ```
 */
internal object SaffronCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_EventScript_GymSign")
}

internal object SaffronCity_EventScript_MrPsychicsHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity.MrPsychicsHouse)
}

internal object SaffronCity_EventScript_TrainerFanClubSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity.TrainerFanClubSign)
}

internal val SaffronCityScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_EventScript_RocketGrunt1" to SaffronCity_EventScript_RocketGrunt1,
        "SaffronCity_EventScript_RocketGrunt2" to SaffronCity_EventScript_RocketGrunt2,
        "SaffronCity_EventScript_RocketGrunt3" to SaffronCity_EventScript_RocketGrunt3,
        "SaffronCity_EventScript_RocketGrunt4" to SaffronCity_EventScript_RocketGrunt4,
        "SaffronCity_EventScript_RocketGrunt5" to SaffronCity_EventScript_RocketGrunt5,
        "SaffronCity_EventScript_DoorGuardGrunt" to SaffronCity_EventScript_DoorGuardGrunt,
        "SaffronCity_EventScript_RocketGrunt7" to SaffronCity_EventScript_RocketGrunt7,
        "SaffronCity_EventScript_RocketGrunt6" to SaffronCity_EventScript_RocketGrunt6,
        "SaffronCity_EventScript_Youngster" to SaffronCity_EventScript_Youngster,
        "SaffronCity_EventScript_WorkerM" to SaffronCity_EventScript_WorkerM,
        "SaffronCity_EventScript_Man" to SaffronCity_EventScript_Man,
        "SaffronCity_EventScript_Boy" to SaffronCity_EventScript_Boy,
        "SaffronCity_EventScript_Pidgeot" to SaffronCity_EventScript_Pidgeot,
        "SaffronCity_EventScript_Lass" to SaffronCity_EventScript_Lass,
        "SaffronCity_EventScript_CrushGirl" to SaffronCity_EventScript_CrushGirl,
        "SaffronCity_EventScript_CitySign" to SaffronCity_EventScript_CitySign,
        "SaffronCity_EventScript_DojoSign" to SaffronCity_EventScript_DojoSign,
        "SaffronCity_EventScript_SilphProductSign" to SaffronCity_EventScript_SilphProductSign,
        "SaffronCity_EventScript_TrainerTips2" to SaffronCity_EventScript_TrainerTips2,
        "SaffronCity_EventScript_SilphCoSign" to SaffronCity_EventScript_SilphCoSign,
        "SaffronCity_EventScript_TrainerTips1" to SaffronCity_EventScript_TrainerTips1,
        "SaffronCity_EventScript_GymSign" to SaffronCity_EventScript_GymSign,
        "SaffronCity_EventScript_MrPsychicsHouseSign" to
            SaffronCity_EventScript_MrPsychicsHouseSign,
        "SaffronCity_EventScript_TrainerFanClubSign" to SaffronCity_EventScript_TrainerFanClubSign,
    )
