package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route104
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route104_EventScript_BugCatcher : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route104.WhatsItLikeAtBottomOfSea)
}

internal object Route104_EventScript_Girl1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route104.BrineyLivesInSeasideCottage)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HALEY_1, Route104_Text_HaleyIntro, Route104_Text_HaleyDefeat, Route104_EventScript_TryRegisterHaleyAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route104_EventScript_RematchHaley
 * setvar VAR_0x8004, TRAINER_HALEY_1
 * specialvar VAR_RESULT, IsTrainerRegistered
 * goto_if_eq VAR_RESULT, FALSE, Route104_EventScript_TryRegisterHaley
 * msgbox Route104_Text_HaleyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route104_EventScript_Haley : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Haley")
}

internal object Route104_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route104.ThrowBallAtWeakenedPokemon)
}

internal object Route104_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route104.OnlyThrowBallAtWildPokemon)
}

internal object Route104_EventScript_Girl2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route104.ImNotATrainer)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_IVAN, Route104_Text_IvanIntro, Route104_Text_IvanDefeat
 * msgbox Route104_Text_IvanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route104_EventScript_Ivan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Ivan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_CHESTO_BERRY_ROUTE_104, Route104_EventScript_ReceivedBerry
 * msgbox Route104_Text_PlantBerriesInSoilTakeThis, MSGBOX_DEFAULT
 * giveitem ITEM_CHESTO_BERRY
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_CHESTO_BERRY_ROUTE_104
 * msgbox Route104_Text_TrainersOftenMakeMonHoldBerries, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route104_EventScript_ExpertF : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_ExpertF")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PP_UP
 * end
 * ```
 */
internal object Route104_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_ItemPPUp")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_WHITE_HERB, Route104_EventScript_ReceivedWhiteHerb
 * msgbox Route104_Text_DontNeedThisTakeIt, MSGBOX_DEFAULT
 * giveitem ITEM_WHITE_HERB
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_WHITE_HERB
 * release
 * end
 * ```
 */
internal object Route104_EventScript_WhiteHerbFlorist : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_WhiteHerbFlorist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GINA_AND_MIA_1, Route104_Text_GinaIntro, Route104_Text_GinaDefeat, Route104_Text_GinaNotEnoughMons
 * special GetPlayerBigGuyGirlString
 * msgbox Route104_Text_GinaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route104_EventScript_Gina : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Gina")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_GINA_AND_MIA_1, Route104_Text_MiaIntro, Route104_Text_MiaDefeat, Route104_Text_MiaNotEnoughMons
 * special GetPlayerBigGuyGirlString
 * msgbox Route104_Text_MiaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route104_EventScript_Mia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Mia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WINSTON_1, Route104_Text_WinstonIntro, Route104_Text_WinstonDefeat, Route104_EventScript_TryRegisterWinstonAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route104_EventScript_RematchWinston
 * setvar VAR_0x8004, TRAINER_WINSTON_1
 * specialvar VAR_RESULT, IsTrainerRegistered
 * goto_if_eq VAR_RESULT, FALSE, Route104_EventScript_TryRegisterWinston
 * msgbox Route104_Text_WinstonPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route104_EventScript_Winston : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Winston")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CINDY_1, Route104_Text_CindyIntro, Route104_Text_CindyDefeat, Route104_EventScript_TryRegisterCindyAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route104_EventScript_RematchCindy
 * setvar VAR_0x8004, TRAINER_CINDY_1
 * specialvar VAR_RESULT, IsTrainerRegistered
 * goto_if_eq VAR_RESULT, FALSE, Route104_EventScript_TryRegisterCindy
 * msgbox Route104_Text_CindyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route104_EventScript_Cindy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Cindy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_POKE_BALL
 * end
 * ```
 */
internal object Route104_EventScript_ItemPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_ItemPokeBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BILLY, Route104_Text_BillyIntro, Route104_Text_BillyDefeat
 * msgbox Route104_Text_BillyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route104_EventScript_Billy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Billy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_X_ACCURACY
 * end
 * ```
 */
internal object Route104_EventScript_ItemXAccuracy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_ItemXAccuracy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_POTION
 * end
 * ```
 */
internal object Route104_EventScript_ItemPotion : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_ItemPotion")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_BULLET_SEED, Route104_EventScript_ReceivedBulletSeed
 * msgbox Route104_Text_LikeFillingMouthWithSeedsTakeThis, MSGBOX_DEFAULT
 * giveitem ITEM_TM_BULLET_SEED
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_BULLET_SEED
 * release
 * end
 * ```
 */
internal object Route104_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Boy2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DARIAN, Route104_Text_DarianIntro, Route104_Text_DarianDefeat
 * msgbox Route104_Text_DarianPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route104_EventScript_Darian : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Darian")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8008, 1
 * applymovement LOCALID_ROUTE104_RIVAL, Common_Movement_FacePlayer
 * waitmovement 0
 * goto Route104_EventScript_RivalEncounter
 * ```
 */
internal object Route104_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route104_EventScript_Rival")
}

internal object Route104_EventScript_BrineysCottageSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route104.MrBrineysCottage)
}

internal object Route104_EventScript_RouteSignPetalburg : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route104.RouteSignPetalburg)
}

internal object Route104_EventScript_RouteSignRustboro : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route104.RouteSignRustboro)
}

internal object Route104_EventScript_FlowerShopSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route104.PrettyPetalFlowShop)
}

internal object Route104_EventScript_TrainerTipsDoubleBattles : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route104.TrainerTipsDoubleBattles)
}

internal val Route104Scripts: Map<String, Script> =
    mapOf(
        "Route104_EventScript_BugCatcher" to Route104_EventScript_BugCatcher,
        "Route104_EventScript_Girl1" to Route104_EventScript_Girl1,
        "Route104_EventScript_Haley" to Route104_EventScript_Haley,
        "Route104_EventScript_Boy1" to Route104_EventScript_Boy1,
        "Route104_EventScript_Woman" to Route104_EventScript_Woman,
        "Route104_EventScript_Girl2" to Route104_EventScript_Girl2,
        "Route104_EventScript_Ivan" to Route104_EventScript_Ivan,
        "Route104_EventScript_ExpertF" to Route104_EventScript_ExpertF,
        "Route104_EventScript_ItemPPUp" to Route104_EventScript_ItemPPUp,
        "Route104_EventScript_WhiteHerbFlorist" to Route104_EventScript_WhiteHerbFlorist,
        "Route104_EventScript_Gina" to Route104_EventScript_Gina,
        "Route104_EventScript_Mia" to Route104_EventScript_Mia,
        "Route104_EventScript_Winston" to Route104_EventScript_Winston,
        "Route104_EventScript_Cindy" to Route104_EventScript_Cindy,
        "Route104_EventScript_ItemPokeBall" to Route104_EventScript_ItemPokeBall,
        "Route104_EventScript_Billy" to Route104_EventScript_Billy,
        "Route104_EventScript_ItemXAccuracy" to Route104_EventScript_ItemXAccuracy,
        "Route104_EventScript_ItemPotion" to Route104_EventScript_ItemPotion,
        "Route104_EventScript_Boy2" to Route104_EventScript_Boy2,
        "Route104_EventScript_Darian" to Route104_EventScript_Darian,
        "Route104_EventScript_Rival" to Route104_EventScript_Rival,
        "Route104_EventScript_BrineysCottageSign" to Route104_EventScript_BrineysCottageSign,
        "Route104_EventScript_RouteSignPetalburg" to Route104_EventScript_RouteSignPetalburg,
        "Route104_EventScript_RouteSignRustboro" to Route104_EventScript_RouteSignRustboro,
        "Route104_EventScript_FlowerShopSign" to Route104_EventScript_FlowerShopSign,
        "Route104_EventScript_TrainerTipsDoubleBattles" to
            Route104_EventScript_TrainerTipsDoubleBattles,
    )
