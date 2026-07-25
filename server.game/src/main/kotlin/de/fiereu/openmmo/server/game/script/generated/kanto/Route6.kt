package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route6
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_KEIGO, Route6_Text_KeigoIntro, Route6_Text_KeigoDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route6_EventScript_KeigoRematch
 * msgbox Route6_Text_KeigoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route6_EventScript_Keigo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route6_EventScript_Keigo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAMPER_RICKY, Route6_Text_RickyIntro, Route6_Text_RickyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route6_EventScript_RickyRematch
 * msgbox Route6_Text_RickyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route6_EventScript_Ricky : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route6_EventScript_Ricky")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_NANCY, Route6_Text_NancyIntro, Route6_Text_NancyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route6_EventScript_NancyRematch
 * msgbox Route6_Text_NancyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route6_EventScript_Nancy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route6_EventScript_Nancy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_ELIJAH, Route6_Text_ElijahIntro, Route6_Text_ElijahDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route6_EventScript_ElijahRematch
 * msgbox Route6_Text_ElijahPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route6_EventScript_Elijah : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route6_EventScript_Elijah")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_ISABELLE, Route6_Text_IsabelleIntro, Route6_Text_IsabelleDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route6_EventScript_IsabelleRematch
 * msgbox Route6_Text_IsabellePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route6_EventScript_Isabelle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route6_EventScript_Isabelle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAMPER_JEFF, Route6_Text_JeffIntro, Route6_Text_JeffDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route6_EventScript_JeffRematch
 * msgbox Route6_Text_JeffPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route6_EventScript_Jeff : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route6_EventScript_Jeff")
}

internal object Route6_EventScript_UndergroundPathSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route6.UndergroundPathSign)
}

internal val Route6Scripts: Map<String, Script> =
    mapOf(
        "Route6_EventScript_Keigo" to Route6_EventScript_Keigo,
        "Route6_EventScript_Ricky" to Route6_EventScript_Ricky,
        "Route6_EventScript_Nancy" to Route6_EventScript_Nancy,
        "Route6_EventScript_Elijah" to Route6_EventScript_Elijah,
        "Route6_EventScript_Isabelle" to Route6_EventScript_Isabelle,
        "Route6_EventScript_Jeff" to Route6_EventScript_Jeff,
        "Route6_EventScript_UndergroundPathSign" to Route6_EventScript_UndergroundPathSign,
    )
