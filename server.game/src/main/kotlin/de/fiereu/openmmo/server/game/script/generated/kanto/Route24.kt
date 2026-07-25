package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MAP_SCENE_ROUTE24, 1, Route24_EventScript_RocketPostBattle
 * msgbox Route24_Text_JustEarnedFabulousPrize
 * checkitemspace ITEM_NUGGET
 * goto_if_eq VAR_RESULT, FALSE, Route24_EventScript_NoRoomForNugget
 * call Route24_EventScript_BattleRocket
 * release
 * end
 * ```
 */
internal object Route24_EventScript_Rocket : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route24_EventScript_Rocket")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAMPER_ETHAN, Route24_Text_EthanIntro, Route24_Text_EthanDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route24_EventScript_EthanRematch
 * msgbox Route24_Text_EthanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route24_EventScript_Ethan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route24_EventScript_Ethan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LASS_RELI, Route24_Text_ReliIntro, Route24_Text_ReliDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route24_EventScript_ReliRematch
 * msgbox Route24_Text_ReliPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route24_EventScript_Reli : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route24_EventScript_Reli")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_YOUNGSTER_TIMMY, Route24_Text_TimmyIntro, Route24_Text_TimmyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route24_EventScript_TimmyRematch
 * msgbox Route24_Text_TimmyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route24_EventScript_Timmy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route24_EventScript_Timmy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LASS_ALI, Route24_Text_AliIntro, Route24_Text_AliDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route24_EventScript_AliRematch
 * msgbox Route24_Text_AliPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route24_EventScript_Ali : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route24_EventScript_Ali")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_CALE, Route24_Text_CaleIntro, Route24_Text_CaleDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route24_EventScript_CaleRematch
 * msgbox Route24_Text_CalePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route24_EventScript_Cale : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route24_EventScript_Cale")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAMPER_SHANE, Route24_Text_ShaneIntro, Route24_Text_ShaneDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route24_EventScript_ShaneRematch
 * msgbox Route24_Text_ShanePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route24_EventScript_Shane : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route24_EventScript_Shane")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM45
 * end
 * ```
 */
internal object Route24_EventScript_ItemTM45 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route24_EventScript_ItemTM45")
}

internal val Route24Scripts: Map<String, Script> =
    mapOf(
        "Route24_EventScript_Rocket" to Route24_EventScript_Rocket,
        "Route24_EventScript_Ethan" to Route24_EventScript_Ethan,
        "Route24_EventScript_Reli" to Route24_EventScript_Reli,
        "Route24_EventScript_Timmy" to Route24_EventScript_Timmy,
        "Route24_EventScript_Ali" to Route24_EventScript_Ali,
        "Route24_EventScript_Cale" to Route24_EventScript_Cale,
        "Route24_EventScript_Shane" to Route24_EventScript_Shane,
        "Route24_EventScript_ItemTM45" to Route24_EventScript_ItemTM45,
    )
