package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route17
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BIKER_VIRGIL, Route17_Text_VirgilIntro, Route17_Text_VirgilDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_VirgilRematch
 * msgbox Route17_Text_VirgilPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_Virgil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_Virgil")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CUE_BALL_ISAIAH, Route17_Text_IsaiahIntro, Route17_Text_IsaiahDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_IsaiahRematch
 * msgbox Route17_Text_IsaiahPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_Isaiah : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_Isaiah")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CUE_BALL_RAUL, Route17_Text_RaulIntro, Route17_Text_RaulDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_RaulRematch
 * msgbox Route17_Text_RaulPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_Raul : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_Raul")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BIKER_NIKOLAS, Route17_Text_NikolasIntro, Route17_Text_NikolasDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_NikolasRematch
 * msgbox Route17_Text_NikolasPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_Nikolas : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_Nikolas")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BIKER_BILLY, Route17_Text_BillyIntro, Route17_Text_BillyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_BillyRematch
 * msgbox Route17_Text_BillyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_Billy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_Billy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CUE_BALL_JAMAL, Route17_Text_JamalIntro, Route17_Text_JamalDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_JamalRematch
 * msgbox Route17_Text_JamalPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_Jamal : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_Jamal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CUE_BALL_ZEEK, Route17_Text_ZeekIntro, Route17_Text_ZeekDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_ZeekRematch
 * msgbox Route17_Text_ZeekPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_Zeek : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_Zeek")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CUE_BALL_COREY, Route17_Text_CoreyIntro, Route17_Text_CoreyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_CoreyRematch
 * msgbox Route17_Text_CoreyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_Corey : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_Corey")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BIKER_WILLIAM, Route17_Text_WilliamIntro, Route17_Text_WilliamDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_WilliamRematch
 * msgbox Route17_Text_WilliamPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_William : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_William")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BIKER_JAXON, Route17_Text_JaxonIntro, Route17_Text_JaxonDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route17_EventScript_JaxonRematch
 * msgbox Route17_Text_JaxonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route17_EventScript_Jaxon : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route17_EventScript_Jaxon")
}

internal object Route17_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route17.RouteSign)
}

internal object Route17_EventScript_TrainerTips2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route17.PressBToStayInPlace)
}

internal object Route17_EventScript_TrainerTips1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route17.SameSpeciesGrowDifferentRates)
}

internal object Route17_EventScript_ItemsNotice : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route17.WatchOutForDiscardedItems)
}

internal object Route17_EventScript_BallsNotice : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route17.DontThrowGameThrowBalls)
}

internal object Route17_EventScript_CyclingRoadSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route17.CyclingRoadSign)
}

internal val Route17Scripts: Map<String, Script> =
    mapOf(
        "Route17_EventScript_Virgil" to Route17_EventScript_Virgil,
        "Route17_EventScript_Isaiah" to Route17_EventScript_Isaiah,
        "Route17_EventScript_Raul" to Route17_EventScript_Raul,
        "Route17_EventScript_Nikolas" to Route17_EventScript_Nikolas,
        "Route17_EventScript_Billy" to Route17_EventScript_Billy,
        "Route17_EventScript_Jamal" to Route17_EventScript_Jamal,
        "Route17_EventScript_Zeek" to Route17_EventScript_Zeek,
        "Route17_EventScript_Corey" to Route17_EventScript_Corey,
        "Route17_EventScript_William" to Route17_EventScript_William,
        "Route17_EventScript_Jaxon" to Route17_EventScript_Jaxon,
        "Route17_EventScript_RouteSign" to Route17_EventScript_RouteSign,
        "Route17_EventScript_TrainerTips2" to Route17_EventScript_TrainerTips2,
        "Route17_EventScript_TrainerTips1" to Route17_EventScript_TrainerTips1,
        "Route17_EventScript_ItemsNotice" to Route17_EventScript_ItemsNotice,
        "Route17_EventScript_BallsNotice" to Route17_EventScript_BallsNotice,
        "Route17_EventScript_CyclingRoadSign" to Route17_EventScript_CyclingRoadSign,
    )
