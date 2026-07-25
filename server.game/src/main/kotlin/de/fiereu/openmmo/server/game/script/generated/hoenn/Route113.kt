package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route113
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route113_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route113.FunWalkingThroughAsh)
}

internal object Route113_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route113.AshCanBeFashionedIntoGlass)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JAYLEN, Route113_Text_JaylenIntro, Route113_Text_JaylenDefeat
 * msgbox Route113_Text_JaylenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route113_EventScript_Jaylen : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Jaylen")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DILLON, Route113_Text_DillonIntro, Route113_Text_DillonDefeat
 * msgbox Route113_Text_DillonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route113_EventScript_Dillon : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Dillon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MADELINE_1, Route113_Text_MadelineIntro, Route113_Text_MadelineDefeat, Route113_EventScript_RegisterMadeline
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route113_EventScript_RematchMadeline
 * msgbox Route113_Text_MadelinePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route113_EventScript_Madeline : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Madeline")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ETHER
 * end
 * ```
 */
internal object Route113_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_ItemMaxEther")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SUPER_REPEL
 * end
 * ```
 */
internal object Route113_EventScript_ItemSuperRepel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_ItemSuperRepel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LAO_1, Route113_Text_LaoIntro, Route113_Text_LaoDefeat, Route113_EventScript_RegisterLao
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route113_EventScript_RematchLao
 * msgbox Route113_Text_LaoPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route113_EventScript_Lao : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Lao")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LUNG, Route113_Text_LungIntro, Route113_Text_LungDefeat
 * msgbox Route113_Text_LungPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route113_EventScript_Lung : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Lung")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_TORI_AND_TIA, Route113_Text_ToriIntro, Route113_Text_ToriDefeat, Route113_Text_ToriNotEnoughMons
 * msgbox Route113_Text_ToriPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route113_EventScript_Tori : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Tori")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_TORI_AND_TIA, Route113_Text_TiaIntro, Route113_Text_TiaDefeat, Route113_Text_TiaNotEnoughMons
 * msgbox Route113_Text_TiaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route113_EventScript_Tia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Tia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HYPER_POTION
 * end
 * ```
 */
internal object Route113_EventScript_ItemHyperPotion : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_ItemHyperPotion")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WYATT, Route113_Text_WyattIntro, Route113_Text_WyattDefeat
 * msgbox Route113_Text_WyattPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route113_EventScript_Wyatt : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Wyatt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LAWRENCE, Route113_Text_LawrenceIntro, Route113_Text_LawrenceDefeat
 * msgbox Route113_Text_LawrencePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route113_EventScript_Lawrence : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Lawrence")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SOPHIE, Route113_Text_SophieIntro, Route113_Text_SophieDefeat
 * msgbox Route113_Text_SophiePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route113_EventScript_Sophie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Sophie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COBY, Route113_Text_CobyIntro, Route113_Text_CobyDefeat
 * msgbox Route113_Text_CobyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route113_EventScript_Coby : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route113_EventScript_Coby")
}

internal object Route113_EventScript_RouteSign111 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route113.RouteSign111)
}

internal object Route113_EventScript_RouteSignFallarbor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route113.RouteSignFallarbor)
}

internal object Route113_EventScript_TrainerTipsRegisterKeyItems : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route113.TrainerTipsRegisterKeyItems)
}

internal object Route113_EventScript_GlassWorkshopSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route113.GlassWorkshopSign)
}

internal val Route113Scripts: Map<String, Script> =
    mapOf(
        "Route113_EventScript_NinjaBoy" to Route113_EventScript_NinjaBoy,
        "Route113_EventScript_Gentleman" to Route113_EventScript_Gentleman,
        "Route113_EventScript_Jaylen" to Route113_EventScript_Jaylen,
        "Route113_EventScript_Dillon" to Route113_EventScript_Dillon,
        "Route113_EventScript_Madeline" to Route113_EventScript_Madeline,
        "Route113_EventScript_ItemMaxEther" to Route113_EventScript_ItemMaxEther,
        "Route113_EventScript_ItemSuperRepel" to Route113_EventScript_ItemSuperRepel,
        "Route113_EventScript_Lao" to Route113_EventScript_Lao,
        "Route113_EventScript_Lung" to Route113_EventScript_Lung,
        "Route113_EventScript_Tori" to Route113_EventScript_Tori,
        "Route113_EventScript_Tia" to Route113_EventScript_Tia,
        "Route113_EventScript_ItemHyperPotion" to Route113_EventScript_ItemHyperPotion,
        "Route113_EventScript_Wyatt" to Route113_EventScript_Wyatt,
        "Route113_EventScript_Lawrence" to Route113_EventScript_Lawrence,
        "Route113_EventScript_Sophie" to Route113_EventScript_Sophie,
        "Route113_EventScript_Coby" to Route113_EventScript_Coby,
        "Route113_EventScript_RouteSign111" to Route113_EventScript_RouteSign111,
        "Route113_EventScript_RouteSignFallarbor" to Route113_EventScript_RouteSignFallarbor,
        "Route113_EventScript_TrainerTipsRegisterKeyItems" to
            Route113_EventScript_TrainerTipsRegisterKeyItems,
        "Route113_EventScript_GlassWorkshopSign" to Route113_EventScript_GlassWorkshopSign,
    )
