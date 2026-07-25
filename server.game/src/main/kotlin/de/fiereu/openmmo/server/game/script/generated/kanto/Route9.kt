package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route9
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_ALICIA, Route9_Text_AliciaIntro, Route9_Text_AliciaDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route9_EventScript_AliciaRematch
 * msgbox Route9_Text_AliciaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route9_EventScript_Alicia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_Alicia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_JEREMY, Route9_Text_JeremyIntro, Route9_Text_JeremyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route9_EventScript_JeremyRematch
 * msgbox Route9_Text_JeremyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route9_EventScript_Jeremy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_Jeremy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_ALAN, Route9_Text_AlanIntro, Route9_Text_AlanDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route9_EventScript_AlanRematch
 * msgbox Route9_Text_AlanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route9_EventScript_Alan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_Alan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAMPER_CHRIS, Route9_Text_ChrisIntro, Route9_Text_ChrisDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route9_EventScript_ChrisRematch
 * msgbox Route9_Text_ChrisPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route9_EventScript_Chris : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_Chris")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_BRENT, Route9_Text_BrentIntro, Route9_Text_BrentDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route9_EventScript_BrentRematch
 * msgbox Route9_Text_BrentPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route9_EventScript_Brent : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_Brent")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_CONNER, Route9_Text_ConnerIntro, Route9_Text_ConnerDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route9_EventScript_ConnerRematch
 * msgbox Route9_Text_ConnerPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route9_EventScript_Conner : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_Conner")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_BRICE, Route9_Text_BriceIntro, Route9_Text_BriceDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route9_EventScript_BriceRematch
 * msgbox Route9_Text_BricePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route9_EventScript_Brice : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_Brice")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_CAITLIN, Route9_Text_CaitlinIntro, Route9_Text_CaitlinDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route9_EventScript_CaitlinRematch
 * msgbox Route9_Text_CaitlinPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route9_EventScript_Caitlin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_Caitlin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAMPER_DREW, Route9_Text_DrewIntro, Route9_Text_DrewDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route9_EventScript_DrewRematch
 * msgbox Route9_Text_DrewPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route9_EventScript_Drew : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_Drew")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM40
 * end
 * ```
 */
internal object Route9_EventScript_ItemTM40 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_ItemTM40")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BURN_HEAL
 * end
 * ```
 */
internal object Route9_EventScript_ItemBurnHeal : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route9_EventScript_ItemBurnHeal")
}

internal object Route9_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route9.RouteSign)
}

internal val Route9Scripts: Map<String, Script> =
    mapOf(
        "Route9_EventScript_Alicia" to Route9_EventScript_Alicia,
        "Route9_EventScript_Jeremy" to Route9_EventScript_Jeremy,
        "Route9_EventScript_Alan" to Route9_EventScript_Alan,
        "Route9_EventScript_Chris" to Route9_EventScript_Chris,
        "Route9_EventScript_Brent" to Route9_EventScript_Brent,
        "Route9_EventScript_Conner" to Route9_EventScript_Conner,
        "Route9_EventScript_Brice" to Route9_EventScript_Brice,
        "Route9_EventScript_Caitlin" to Route9_EventScript_Caitlin,
        "Route9_EventScript_Drew" to Route9_EventScript_Drew,
        "Route9_EventScript_ItemTM40" to Route9_EventScript_ItemTM40,
        "Route9_EventScript_ItemBurnHeal" to Route9_EventScript_ItemBurnHeal,
        "Route9_EventScript_RouteSign" to Route9_EventScript_RouteSign,
    )
