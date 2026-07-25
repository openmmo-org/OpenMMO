package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ViridianForest
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object ViridianForest_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianForest.FriendsItchingToBattle)
}

internal object ViridianForest_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianForest.RanOutOfPokeBalls)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_RICK, ViridianForest_Text_RickIntro, ViridianForest_Text_RickDefeat
 * msgbox ViridianForest_Text_RickPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianForest_EventScript_Rick : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianForest_EventScript_Rick")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_DOUG, ViridianForest_Text_DougIntro, ViridianForest_Text_DougDefeat
 * msgbox ViridianForest_Text_DougPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianForest_EventScript_Doug : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianForest_EventScript_Doug")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_SAMMY, ViridianForest_Text_SammyIntro, ViridianForest_Text_SammyDefeat
 * msgbox ViridianForest_Text_SammyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianForest_EventScript_Sammy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianForest_EventScript_Sammy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_POKE_BALL
 * end
 * ```
 */
internal object ViridianForest_EventScript_ItemPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ViridianForest_EventScript_ItemPokeBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ANTIDOTE
 * end
 * ```
 */
internal object ViridianForest_EventScript_ItemAntidote : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ViridianForest_EventScript_ItemAntidote")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_POTION
 * end
 * ```
 */
internal object ViridianForest_EventScript_ItemPotion : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianForest_EventScript_ItemPotion")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_ANTHONY, ViridianForest_Text_AnthonyIntro, ViridianForest_Text_AnthonyDefeat
 * msgbox ViridianForest_Text_AnthonyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianForest_EventScript_Anthony : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianForest_EventScript_Anthony")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_CHARLIE, ViridianForest_Text_CharlieIntro, ViridianForest_Text_CharlieDefeat
 * msgbox ViridianForest_Text_CharliePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianForest_EventScript_Charlie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianForest_EventScript_Charlie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_POTION
 * end
 * ```
 */
internal object ViridianForest_EventScript_ItemPotion2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianForest_EventScript_ItemPotion2")
}

internal object ViridianForest_EventScript_TrainerTips1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.AvoidGrassyAreasWhenWeak)
}

internal object ViridianForest_EventScript_TrainerTips3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(ViridianForest.ContactOakViaPCToRatePokedex)
}

internal object ViridianForest_EventScript_TrainerTips4 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.CantCatchOwnedMons)
}

internal object ViridianForest_EventScript_ExitSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.LeavingViridianForest)
}

internal object ViridianForest_EventScript_TrainerTips2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.UseAntidoteForPoison)
}

internal object ViridianForest_EventScript_TrainerTips5 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianForest.WeakenMonsBeforeCapture)
}

internal val ViridianForestScripts: Map<String, Script> =
    mapOf(
        "ViridianForest_EventScript_Youngster" to ViridianForest_EventScript_Youngster,
        "ViridianForest_EventScript_Boy" to ViridianForest_EventScript_Boy,
        "ViridianForest_EventScript_Rick" to ViridianForest_EventScript_Rick,
        "ViridianForest_EventScript_Doug" to ViridianForest_EventScript_Doug,
        "ViridianForest_EventScript_Sammy" to ViridianForest_EventScript_Sammy,
        "ViridianForest_EventScript_ItemPokeBall" to ViridianForest_EventScript_ItemPokeBall,
        "ViridianForest_EventScript_ItemAntidote" to ViridianForest_EventScript_ItemAntidote,
        "ViridianForest_EventScript_ItemPotion" to ViridianForest_EventScript_ItemPotion,
        "ViridianForest_EventScript_Anthony" to ViridianForest_EventScript_Anthony,
        "ViridianForest_EventScript_Charlie" to ViridianForest_EventScript_Charlie,
        "ViridianForest_EventScript_ItemPotion2" to ViridianForest_EventScript_ItemPotion2,
        "ViridianForest_EventScript_TrainerTips1" to ViridianForest_EventScript_TrainerTips1,
        "ViridianForest_EventScript_TrainerTips3" to ViridianForest_EventScript_TrainerTips3,
        "ViridianForest_EventScript_TrainerTips4" to ViridianForest_EventScript_TrainerTips4,
        "ViridianForest_EventScript_ExitSign" to ViridianForest_EventScript_ExitSign,
        "ViridianForest_EventScript_TrainerTips2" to ViridianForest_EventScript_TrainerTips2,
        "ViridianForest_EventScript_TrainerTips5" to ViridianForest_EventScript_TrainerTips5,
    )
