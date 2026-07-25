package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route10
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_CAROL, Route10_Text_CarolIntro, Route10_Text_CarolDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route10_EventScript_CarolRematch
 * msgbox Route10_Text_CarolPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route10_EventScript_Carol : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route10_EventScript_Carol")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_CLARK, Route10_Text_ClarkIntro, Route10_Text_ClarkDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route10_EventScript_ClarkRematch
 * msgbox Route10_Text_ClarkPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route10_EventScript_Clark : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route10_EventScript_Clark")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_POKEMANIAC_HERMAN, Route10_Text_HermanIntro, Route10_Text_HermanDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route10_EventScript_HermanRematch
 * msgbox Route10_Text_HermanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route10_EventScript_Herman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route10_EventScript_Herman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_TRENT, Route10_Text_TrentIntro, Route10_Text_TrentDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route10_EventScript_TrentRematch
 * msgbox Route10_Text_TrentPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route10_EventScript_Trent : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route10_EventScript_Trent")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_POKEMANIAC_MARK, Route10_Text_MarkIntro, Route10_Text_MarkDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route10_EventScript_MarkRematch
 * msgbox Route10_Text_MarkPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route10_EventScript_Mark : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route10_EventScript_Mark")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_HEIDI, Route10_Text_HeidiIntro, Route10_Text_HeidiDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route10_EventScript_HeidiRematch
 * msgbox Route10_Text_HeidiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route10_EventScript_Heidi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route10_EventScript_Heidi")
}

internal object Route10_EventScript_SouthRockTunnelSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route10.RockTunnel)
}

internal object Route10_EventScript_PowerPlantSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route10.PowerPlant)
}

internal object Route10_EventScript_NorthRockTunnelSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route10.RockTunnelDetourToLavender)
}

internal val Route10Scripts: Map<String, Script> =
    mapOf(
        "Route10_EventScript_Carol" to Route10_EventScript_Carol,
        "Route10_EventScript_Clark" to Route10_EventScript_Clark,
        "Route10_EventScript_Herman" to Route10_EventScript_Herman,
        "Route10_EventScript_Trent" to Route10_EventScript_Trent,
        "Route10_EventScript_Mark" to Route10_EventScript_Mark,
        "Route10_EventScript_Heidi" to Route10_EventScript_Heidi,
        "Route10_EventScript_SouthRockTunnelSign" to Route10_EventScript_SouthRockTunnelSign,
        "Route10_EventScript_PowerPlantSign" to Route10_EventScript_PowerPlantSign,
        "Route10_EventScript_NorthRockTunnelSign" to Route10_EventScript_NorthRockTunnelSign,
    )
