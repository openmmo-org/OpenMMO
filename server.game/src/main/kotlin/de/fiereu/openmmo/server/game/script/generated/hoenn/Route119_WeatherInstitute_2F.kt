package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_WEATHER_INST_2, Route119_WeatherInstitute_2F_Text_Grunt2Intro, Route119_WeatherInstitute_2F_Text_Grunt2Defeat
 * msgbox Route119_WeatherInstitute_2F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_WeatherInstitute_2F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_2F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_WEATHER_INST_3, Route119_WeatherInstitute_2F_Text_Grunt3Intro, Route119_WeatherInstitute_2F_Text_Grunt3Defeat
 * msgbox Route119_WeatherInstitute_2F_Text_Grunt3PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_WeatherInstitute_2F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_2F_EventScript_Grunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHELLY_WEATHER_INSTITUTE, Route119_WeatherInstitute_2F_Text_ShellyIntro, Route119_WeatherInstitute_2F_Text_ShellyDefeat, Route119_WeatherInstitute_2F_EventScript_ShellyDefeated
 * msgbox Route119_WeatherInstitute_2F_Text_ShellyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_WeatherInstitute_2F_EventScript_Shelly : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_2F_EventScript_Shelly")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_CASTFORM, Route119_WeatherInstitute_2F_EventScript_ScientistMentionWeather
 * goto Route119_WeatherInstitute_2F_EventScript_ReceiveCastform
 * end
 * ```
 */
internal object Route119_WeatherInstitute_2F_EventScript_WeatherScientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_2F_EventScript_WeatherScientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_WEATHER_INST_5, Route119_WeatherInstitute_2F_Text_Grunt5Intro, Route119_WeatherInstitute_2F_Text_Grunt5Defeat
 * msgbox Route119_WeatherInstitute_2F_Text_Grunt5PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_WeatherInstitute_2F_EventScript_Grunt5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route119_WeatherInstitute_2F_EventScript_Grunt5")
}

internal val Route119_WeatherInstitute_2FScripts: Map<String, Script> =
    mapOf(
        "Route119_WeatherInstitute_2F_EventScript_Grunt2" to
            Route119_WeatherInstitute_2F_EventScript_Grunt2,
        "Route119_WeatherInstitute_2F_EventScript_Grunt3" to
            Route119_WeatherInstitute_2F_EventScript_Grunt3,
        "Route119_WeatherInstitute_2F_EventScript_Shelly" to
            Route119_WeatherInstitute_2F_EventScript_Shelly,
        "Route119_WeatherInstitute_2F_EventScript_WeatherScientist" to
            Route119_WeatherInstitute_2F_EventScript_WeatherScientist,
        "Route119_WeatherInstitute_2F_EventScript_Grunt5" to
            Route119_WeatherInstitute_2F_EventScript_Grunt5,
    )
