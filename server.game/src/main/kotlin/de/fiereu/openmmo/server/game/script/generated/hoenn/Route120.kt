package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Kecleon
import de.fiereu.openmmo.dialog.generated.hoenn.Route120
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COLIN, Route120_Text_ColinIntro, Route120_Text_ColinDefeat
 * msgbox Route120_Text_ColinPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Colin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Colin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ROBERT_1, Route120_Text_RobertIntro, Route120_Text_RobertDefeat, Route120_EventScript_RegisterRobert
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route120_EventScript_RematchRobert
 * msgbox Route120_Text_RobertPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route120_EventScript_Robert : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Robert")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LORENZO, Route120_Text_LorenzoIntro, Route120_Text_LorenzoDefeat
 * msgbox Route120_Text_LorenzoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Lorenzo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Lorenzo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JENNA, Route120_Text_JennaIntro, Route120_Text_JennaDefeat
 * msgbox Route120_Text_JennaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Jenna : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Jenna")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JEFFREY_1, Route120_Text_JeffreyIntro, Route120_Text_JeffreyDefeat, Route120_EventScript_RegisterJeffrey
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route120_EventScript_RematchJeffrey
 * msgbox Route120_Text_JeffreyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route120_EventScript_Jeffrey : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Jeffrey")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object Route120_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_ItemNugget")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_3, GabbyAndTy_Text_TyIntro, GabbyAndTy_Text_TyDefeat, GabbyAndTy_Text_TyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_TyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_TyBattle3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_TyBattle3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GABBY_AND_TY_3, GabbyAndTy_Text_GabbyIntro, GabbyAndTy_Text_GabbyDefeat, GabbyAndTy_Text_GabbyNotEnoughMons, GabbyAndTy_EventScript_RequestInterview
 * msgbox GabbyAndTy_Text_KeepingAnEyeOutForYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GabbyAndTy_EventScript_GabbyBattle3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GabbyAndTy_EventScript_GabbyBattle3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_HEAL
 * end
 * ```
 */
internal object Route120_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_ItemFullHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * goto_if_set FLAG_DAILY_ROUTE_120_RECEIVED_BERRY, Route120_EventScript_ReceivedBerry
 * msgbox Route120_Text_BerriesExpressionOfLoveIsntIt, MSGBOX_YESNO
 * call_if_eq VAR_RESULT, YES, Route120_EventScript_BerryLove
 * call_if_eq VAR_RESULT, NO, Route120_EventScript_BerryNotLove
 * specialvar VAR_RESULT, GetPlayerTrainerIdOnesDigit
 * switch VAR_RESULT
 * case 0, Route120_EventScript_GiveFigyBerry
 * case 5, Route120_EventScript_GiveFigyBerry
 * case 1, Route120_EventScript_GiveWikiBerry
 * case 6, Route120_EventScript_GiveWikiBerry
 * case 2, Route120_EventScript_GiveMagoBerry
 * case 7, Route120_EventScript_GiveMagoBerry
 * case 3, Route120_EventScript_GiveAguavBerry
 * case 8, Route120_EventScript_GiveAguavBerry
 * case 4, Route120_EventScript_GiveIapapaBerry
 * case 9, Route120_EventScript_GiveIapapaBerry
 * end
 * ```
 */
internal object Route120_EventScript_BerryBeauty : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_BerryBeauty")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JENNIFER, Route120_Text_JenniferIntro, Route120_Text_JenniferDefeat
 * msgbox Route120_Text_JenniferPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Jennifer : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Jennifer")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHIP, Route120_Text_ChipIntro, Route120_Text_ChipDefeat
 * msgbox Route120_Text_ChipPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Chip : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Chip")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CLARISSA, Route120_Text_ClarissaIntro, Route120_Text_ClarissaDefeat
 * msgbox Route120_Text_ClarissaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Clarissa : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Clarissa")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ANGELICA, Route120_Text_AngelicaIntro, Route120_Text_AngelicaDefeat
 * msgbox Route120_Text_AngelicaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Angelica : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Angelica")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NEST_BALL
 * end
 * ```
 */
internal object Route120_EventScript_ItemNestBall : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_ItemNestBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HYPER_POTION
 * end
 * ```
 */
internal object Route120_EventScript_ItemHyperPotion : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_ItemHyperPotion")
}

internal object Route120_EventScript_BridgeKecleon : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Kecleon.SomethingUnseeable)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_NOT_READY_FOR_BATTLE_ROUTE_120, Route120_EventScript_StevenAskReadyForBattle
 * msgbox Route120_Text_StevenGreeting, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Route120_EventScript_StevenNotReady
 * goto Route120_EventScript_StevenBattleKecleon
 * end
 * ```
 */
internal object Route120_EventScript_Steven : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Steven")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KEIGO, Route120_Text_KeigoIntro, Route120_Text_KeigoDefeat
 * msgbox Route120_Text_KeigoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Keigo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Keigo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RILEY, Route120_Text_RileyIntro, Route120_Text_RileyDefeat
 * msgbox Route120_Text_RileyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Riley : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Riley")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8009, 1
 * goto EventScript_Kecleon
 * end
 * ```
 */
internal object Route120_EventScript_Kecleon1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Kecleon1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8009, 2
 * goto EventScript_Kecleon
 * end
 * ```
 */
internal object Route120_EventScript_Kecleon2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Kecleon2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8009, 3
 * goto EventScript_Kecleon
 * end
 * ```
 */
internal object Route120_EventScript_Kecleon3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Kecleon3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8009, 5
 * goto EventScript_Kecleon
 * end
 * ```
 */
internal object Route120_EventScript_Kecleon5 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Kecleon5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8009, 4
 * goto EventScript_Kecleon
 * end
 * ```
 */
internal object Route120_EventScript_Kecleon4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Kecleon4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CALLIE, Route120_Text_CallieIntro, Route120_Text_CallieDefeat
 * msgbox Route120_Text_CalliePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Callie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Callie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LEONEL, Route120_Text_LeonelIntro, Route120_Text_LeonelDefeat
 * msgbox Route120_Text_LeonelPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route120_EventScript_Leonel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_Leonel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_REVIVE
 * end
 * ```
 */
internal object Route120_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route120_EventScript_ItemRevive")
}

internal object Route120_EventScript_RouteSignFortree : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route120.RouteSignFortree)
}

internal object Route120_EventScript_RouteSign121 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route120.RouteSign121)
}

internal val Route120Scripts: Map<String, Script> =
    mapOf(
        "Route120_EventScript_Colin" to Route120_EventScript_Colin,
        "Route120_EventScript_Robert" to Route120_EventScript_Robert,
        "Route120_EventScript_Lorenzo" to Route120_EventScript_Lorenzo,
        "Route120_EventScript_Jenna" to Route120_EventScript_Jenna,
        "Route120_EventScript_Jeffrey" to Route120_EventScript_Jeffrey,
        "Route120_EventScript_ItemNugget" to Route120_EventScript_ItemNugget,
        "GabbyAndTy_EventScript_TyBattle3" to GabbyAndTy_EventScript_TyBattle3,
        "GabbyAndTy_EventScript_GabbyBattle3" to GabbyAndTy_EventScript_GabbyBattle3,
        "Route120_EventScript_ItemFullHeal" to Route120_EventScript_ItemFullHeal,
        "Route120_EventScript_BerryBeauty" to Route120_EventScript_BerryBeauty,
        "Route120_EventScript_Jennifer" to Route120_EventScript_Jennifer,
        "Route120_EventScript_Chip" to Route120_EventScript_Chip,
        "Route120_EventScript_Clarissa" to Route120_EventScript_Clarissa,
        "Route120_EventScript_Angelica" to Route120_EventScript_Angelica,
        "Route120_EventScript_ItemNestBall" to Route120_EventScript_ItemNestBall,
        "Route120_EventScript_ItemHyperPotion" to Route120_EventScript_ItemHyperPotion,
        "Route120_EventScript_BridgeKecleon" to Route120_EventScript_BridgeKecleon,
        "Route120_EventScript_Steven" to Route120_EventScript_Steven,
        "Route120_EventScript_Keigo" to Route120_EventScript_Keigo,
        "Route120_EventScript_Riley" to Route120_EventScript_Riley,
        "Route120_EventScript_Kecleon1" to Route120_EventScript_Kecleon1,
        "Route120_EventScript_Kecleon2" to Route120_EventScript_Kecleon2,
        "Route120_EventScript_Kecleon3" to Route120_EventScript_Kecleon3,
        "Route120_EventScript_Kecleon5" to Route120_EventScript_Kecleon5,
        "Route120_EventScript_Kecleon4" to Route120_EventScript_Kecleon4,
        "Route120_EventScript_Callie" to Route120_EventScript_Callie,
        "Route120_EventScript_Leonel" to Route120_EventScript_Leonel,
        "Route120_EventScript_ItemRevive" to Route120_EventScript_ItemRevive,
        "Route120_EventScript_RouteSignFortree" to Route120_EventScript_RouteSignFortree,
        "Route120_EventScript_RouteSign121" to Route120_EventScript_RouteSign121,
    )
