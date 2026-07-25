package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route16
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BIKER_LAO, Route16_Text_LaoIntro, Route16_Text_LaoDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_LaoRematch
 * msgbox Route16_Text_LaoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Lao : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Lao")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CUE_BALL_KOJI, Route16_Text_KojiIntro, Route16_Text_KojiDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_KojiRematch
 * msgbox Route16_Text_KojiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Koji : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Koji")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CUE_BALL_LUKE, Route16_Text_LukeIntro, Route16_Text_LukeDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_LukeRematch
 * msgbox Route16_Text_LukePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Luke : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Luke")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BIKER_RUBEN, Route16_Text_RubenIntro, Route16_Text_RubenDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_RubenRematch
 * msgbox Route16_Text_RubenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Ruben : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Ruben")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BIKER_HIDEO, Route16_Text_HideoIntro, Route16_Text_HideoDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_HideoRematch
 * msgbox Route16_Text_HideoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Hideo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Hideo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CUE_BALL_CAMRON, Route16_Text_CamronIntro, Route16_Text_CamronDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_CamronRematch
 * msgbox Route16_Text_CamronPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Camron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Camron")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_YOUNG_COUPLE_LEA_JED, Route16_Text_LeaIntro, Route16_Text_LeaDefeat, Route16_Text_LeaNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_LeaRematch
 * msgbox Route16_Text_LeaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Lea : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Lea")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_YOUNG_COUPLE_LEA_JED, Route16_Text_JedIntro, Route16_Text_JedDefeat, Route16_Text_JedNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route16_EventScript_JedRematch
 * msgbox Route16_Text_JedPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route16_EventScript_Jed : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Jed")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_GOT_POKE_FLUTE, Route16_EventScript_SnorlaxNoPokeFlute
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * msgbox Text_WantToUsePokeFlute, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Route16_EventScript_DontUsePokeFlute
 * call EventScript_AwakenSnorlax
 * setwildbattle SPECIES_SNORLAX, 30
 * waitse
 * playmoncry SPECIES_SNORLAX, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setflag FLAG_HIDE_ROUTE_16_SNORLAX
 * setflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * dowildbattle
 * clearflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, Route16_EventScript_FoughtSnorlax
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, Route16_EventScript_FoughtSnorlax
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, Route16_EventScript_FoughtSnorlax
 * release
 * end
 * ```
 */
internal object Route16_EventScript_Snorlax : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_EventScript_Snorlax")
}

internal object Route16_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route16.RouteSign)
}

internal object Route16_EventScript_CyclingRoadSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route16.CyclingRoadSign)
}

internal val Route16Scripts: Map<String, Script> =
    mapOf(
        "Route16_EventScript_Lao" to Route16_EventScript_Lao,
        "Route16_EventScript_Koji" to Route16_EventScript_Koji,
        "Route16_EventScript_Luke" to Route16_EventScript_Luke,
        "Route16_EventScript_Ruben" to Route16_EventScript_Ruben,
        "Route16_EventScript_Hideo" to Route16_EventScript_Hideo,
        "Route16_EventScript_Camron" to Route16_EventScript_Camron,
        "Route16_EventScript_Lea" to Route16_EventScript_Lea,
        "Route16_EventScript_Jed" to Route16_EventScript_Jed,
        "Route16_EventScript_Snorlax" to Route16_EventScript_Snorlax,
        "Route16_EventScript_RouteSign" to Route16_EventScript_RouteSign,
        "Route16_EventScript_CyclingRoadSign" to Route16_EventScript_CyclingRoadSign,
    )
