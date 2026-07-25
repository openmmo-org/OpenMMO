package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FISHERMAN_RONALD, Route21_North_Text_RonaldIntro, Route21_North_Text_RonaldDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_North_EventScript_RonaldRematch
 * msgbox Route21_North_Text_RonaldPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_North_EventScript_Ronald : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_North_EventScript_Ronald")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FISHERMAN_WADE, Route21_North_Text_WadeIntro, Route21_North_Text_WadeDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_North_EventScript_WadeRematch
 * msgbox Route21_North_Text_WadePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_North_EventScript_Wade : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_North_EventScript_Wade")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SWIMMER_MALE_SPENCER, Route21_North_Text_SpencerIntro, Route21_North_Text_SpencerDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_North_EventScript_SpencerRematch
 * msgbox Route21_North_Text_SpencerPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_North_EventScript_Spencer : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_North_EventScript_Spencer")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_SIS_AND_BRO_LIL_IAN, Route21_North_Text_LilIntro, Route21_North_Text_LilDefeat, Route21_North_Text_LilNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_North_EventScript_LilRematch
 * msgbox Route21_North_Text_LilPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_North_EventScript_Lil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_North_EventScript_Lil")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_SIS_AND_BRO_LIL_IAN, Route21_North_Text_IanIntro, Route21_North_Text_IanDefeat, Route21_North_Text_IanNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_North_EventScript_IanRematch
 * msgbox Route21_North_Text_IanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_North_EventScript_Ian : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_North_EventScript_Ian")
}

internal val Route21_NorthScripts: Map<String, Script> =
    mapOf(
        "Route21_North_EventScript_Ronald" to Route21_North_EventScript_Ronald,
        "Route21_North_EventScript_Wade" to Route21_North_EventScript_Wade,
        "Route21_North_EventScript_Spencer" to Route21_North_EventScript_Spencer,
        "Route21_North_EventScript_Lil" to Route21_North_EventScript_Lil,
        "Route21_North_EventScript_Ian" to Route21_North_EventScript_Ian,
    )
