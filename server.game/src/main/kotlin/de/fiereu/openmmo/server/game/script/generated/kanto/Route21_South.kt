package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FISHERMAN_CLAUDE, Route21_South_Text_ClaudeIntro, Route21_South_Text_ClaudeDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_South_EventScript_ClaudeRematch
 * msgbox Route21_South_Text_ClaudePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_South_EventScript_Claude : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_South_EventScript_Claude")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FISHERMAN_NOLAN, Route21_South_Text_NolanIntro, Route21_South_Text_NolanDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_South_EventScript_NolanRematch
 * msgbox Route21_South_Text_NolanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_South_EventScript_Nolan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_South_EventScript_Nolan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SWIMMER_MALE_JACK, Route21_South_Text_JackIntro, Route21_South_Text_JackDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_South_EventScript_JackRematch
 * msgbox Route21_South_Text_JackPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_South_EventScript_Jack : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_South_EventScript_Jack")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SWIMMER_MALE_JEROME, Route21_South_Text_JeromeIntro, Route21_South_Text_JeromeDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_South_EventScript_JeromeRematch
 * msgbox Route21_South_Text_JeromePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_South_EventScript_Jerome : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_South_EventScript_Jerome")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SWIMMER_MALE_ROLAND, Route21_South_Text_RolandIntro, Route21_South_Text_RolandDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route21_South_EventScript_RolandRematch
 * msgbox Route21_South_Text_RolandPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route21_South_EventScript_Roland : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route21_South_EventScript_Roland")
}

internal val Route21_SouthScripts: Map<String, Script> =
    mapOf(
        "Route21_South_EventScript_Claude" to Route21_South_EventScript_Claude,
        "Route21_South_EventScript_Nolan" to Route21_South_EventScript_Nolan,
        "Route21_South_EventScript_Jack" to Route21_South_EventScript_Jack,
        "Route21_South_EventScript_Jerome" to Route21_South_EventScript_Jerome,
        "Route21_South_EventScript_Roland" to Route21_South_EventScript_Roland,
    )
