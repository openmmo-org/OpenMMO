package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route119_WeatherInstitute_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_WEATHER_INST_1, Route119_WeatherInstitute_1F_Text_Grunt1Intro, Route119_WeatherInstitute_1F_Text_Grunt1Defeat
 * msgbox Route119_WeatherInstitute_1F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_WeatherInstitute_1F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_1F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_WEATHER_INST_4, Route119_WeatherInstitute_1F_Text_Grunt4Intro, Route119_WeatherInstitute_1F_Text_Grunt4Defeat
 * msgbox Route119_WeatherInstitute_1F_Text_Grunt4PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_WeatherInstitute_1F_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_1F_EventScript_Grunt4")
}

internal object Route119_WeatherInstitute_1F_EventScript_InstituteWorker2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route119_WeatherInstitute_1F.WhatWereAquasUpTo)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_SYS_GAME_CLEAR, Route119_WeatherInstitute_1F_EventScript_StudyingRain
 * setvar VAR_0x8004, 0
 * call_if_set FLAG_DEFEATED_KYOGRE, Route119_WeatherInstitute_1F_EventScript_LegendaryDefeated
 * call_if_set FLAG_DEFEATED_GROUDON, Route119_WeatherInstitute_1F_EventScript_LegendaryDefeated
 * goto_if_eq VAR_0x8004, 2, Route119_WeatherInstitute_1F_EventScript_StudyingRain  @ Both defeated
 * msgbox Route119_WeatherInstitute_1F_Text_NoticingAbnormalWeather, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route119_WeatherInstitute_1F_EventScript_InstituteWorker1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_1F_EventScript_InstituteWorker1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * special GetPlayerBigGuyGirlString
 * goto_if_eq VAR_WEATHER_INSTITUTE_STATE, 0, Route119_WeatherInstitute_1F_EventScript_LittleBoyTeamAquaHere
 * msgbox Route119_WeatherInstitute_1F_Text_WowYoureStrong, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route119_WeatherInstitute_1F_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_1F_EventScript_LittleBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox Route119_WeatherInstitute_1F_Text_TakeRestInBed, MSGBOX_DEFAULT
 * closemessage
 * call Common_EventScript_OutOfCenterPartyHeal
 * releaseall
 * end
 * ```
 */
internal object Route119_WeatherInstitute_1F_EventScript_Bed : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_1F_EventScript_Bed")
}

internal val Route119_WeatherInstitute_1FScripts: Map<String, Script> =
    mapOf(
        "Route119_WeatherInstitute_1F_EventScript_Grunt1" to
            Route119_WeatherInstitute_1F_EventScript_Grunt1,
        "Route119_WeatherInstitute_1F_EventScript_Grunt4" to
            Route119_WeatherInstitute_1F_EventScript_Grunt4,
        "Route119_WeatherInstitute_1F_EventScript_InstituteWorker2" to
            Route119_WeatherInstitute_1F_EventScript_InstituteWorker2,
        "Route119_WeatherInstitute_1F_EventScript_InstituteWorker1" to
            Route119_WeatherInstitute_1F_EventScript_InstituteWorker1,
        "Route119_WeatherInstitute_1F_EventScript_LittleBoy" to
            Route119_WeatherInstitute_1F_EventScript_LittleBoy,
        "Route119_WeatherInstitute_1F_EventScript_Bed" to
            Route119_WeatherInstitute_1F_EventScript_Bed,
    )
