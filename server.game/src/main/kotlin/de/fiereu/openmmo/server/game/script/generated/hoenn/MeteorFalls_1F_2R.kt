package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_NICOLAS_1, MeteorFalls_1F_2R_Text_NicolasIntro, MeteorFalls_1F_2R_Text_NicolasDefeat, MeteorFalls_1F_2R_EventScript_RegisterNicolas
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MeteorFalls_1F_2R_EventScript_RematchNicolas
 * msgbox MeteorFalls_1F_2R_Text_NicolasPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MeteorFalls_1F_2R_EventScript_Nicolas : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MeteorFalls_1F_2R_EventScript_Nicolas")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_JOHN_AND_JAY_1, MeteorFalls_1F_2R_Text_JohnIntro, MeteorFalls_1F_2R_Text_JohnDefeat, MeteorFalls_1F_2R_Text_JohnNotEnoughMons, MeteorFalls_1F_2R_EventScript_RegisterJohn
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MeteorFalls_1F_2R_EventScript_RematchJohn
 * msgbox MeteorFalls_1F_2R_Text_JohnPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MeteorFalls_1F_2R_EventScript_John : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MeteorFalls_1F_2R_EventScript_John")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_JOHN_AND_JAY_1, MeteorFalls_1F_2R_Text_JayIntro, MeteorFalls_1F_2R_Text_JayDefeat, MeteorFalls_1F_2R_Text_JayNotEnoughMons, MeteorFalls_1F_2R_EventScript_RegisterJay
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MeteorFalls_1F_2R_EventScript_RematchJay
 * msgbox MeteorFalls_1F_2R_Text_JayPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MeteorFalls_1F_2R_EventScript_Jay : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MeteorFalls_1F_2R_EventScript_Jay")
}

internal val MeteorFalls_1F_2RScripts: Map<String, Script> =
    mapOf(
        "MeteorFalls_1F_2R_EventScript_Nicolas" to MeteorFalls_1F_2R_EventScript_Nicolas,
        "MeteorFalls_1F_2R_EventScript_John" to MeteorFalls_1F_2R_EventScript_John,
        "MeteorFalls_1F_2R_EventScript_Jay" to MeteorFalls_1F_2R_EventScript_Jay,
    )
