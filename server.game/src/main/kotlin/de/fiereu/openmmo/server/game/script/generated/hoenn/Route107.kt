package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DARRIN, Route107_Text_DarrinIntro, Route107_Text_DarrinDefeated
 * msgbox Route107_Text_DarrinPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route107_EventScript_Darrin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Darrin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TONY_1, Route107_Text_TonyIntro, Route107_Text_TonyDefeated, Route107_EventScript_TonyRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route107_EventScript_TonyRematch
 * msgbox Route107_Text_TonyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route107_EventScript_Tony : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Tony")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DENISE, Route107_Text_DeniseIntro, Route107_Text_DeniseDefeated
 * msgbox Route107_Text_DenisePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route107_EventScript_Denise : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Denise")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BETH, Route107_Text_BethIntro, Route107_Text_BethDefeated
 * msgbox Route107_Text_BethPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route107_EventScript_Beth : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Beth")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LISA_AND_RAY, Route107_Text_LisaIntro, Route107_Text_LisaDefeated, Route107_Text_LisaNotEnoughPokemon
 * msgbox Route107_Text_LisaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route107_EventScript_Lisa : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Lisa")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LISA_AND_RAY, Route107_Text_RayIntro, Route107_Text_RayDefeated, Route107_Text_RayNotEnoughPokemon
 * msgbox Route107_Text_RayPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route107_EventScript_Ray : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Ray")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAMRON, Route107_Text_CamronIntro, Route107_Text_CamronDefeated
 * msgbox Route107_Text_CamronPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route107_EventScript_Camron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route107_EventScript_Camron")
}

internal val Route107Scripts: Map<String, Script> =
    mapOf(
        "Route107_EventScript_Darrin" to Route107_EventScript_Darrin,
        "Route107_EventScript_Tony" to Route107_EventScript_Tony,
        "Route107_EventScript_Denise" to Route107_EventScript_Denise,
        "Route107_EventScript_Beth" to Route107_EventScript_Beth,
        "Route107_EventScript_Lisa" to Route107_EventScript_Lisa,
        "Route107_EventScript_Ray" to Route107_EventScript_Ray,
        "Route107_EventScript_Camron" to Route107_EventScript_Camron,
    )
