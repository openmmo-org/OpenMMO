package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_ExchangeServiceCorner
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, EXCHANGE_CORNER_DECOR1_CLERK
 * call BattleFrontier_ExchangeServiceCorner_EventScript_ClerkWelcome
 * goto BattleFrontier_ExchangeServiceCorner_EventScript_ChooseDecor1
 * end
 * ```
 */
internal object BattleFrontier_ExchangeServiceCorner_EventScript_DecorClerk1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_ExchangeServiceCorner_EventScript_DecorClerk1")
}

internal object BattleFrontier_ExchangeServiceCorner_EventScript_RichBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_ExchangeServiceCorner.WishIHadAllDolls)
}

internal object BattleFrontier_ExchangeServiceCorner_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_ExchangeServiceCorner.GetYouAnythingYouWant)
}

internal object BattleFrontier_ExchangeServiceCorner_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_ExchangeServiceCorner.ItemsWillGetMonTougher)
}

internal object BattleFrontier_ExchangeServiceCorner_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_ExchangeServiceCorner.GoGetYourOwnDoll)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, EXCHANGE_CORNER_DECOR2_CLERK
 * call BattleFrontier_ExchangeServiceCorner_EventScript_ClerkWelcome
 * goto BattleFrontier_ExchangeServiceCorner_EventScript_ChooseDecor2
 * end
 * ```
 */
internal object BattleFrontier_ExchangeServiceCorner_EventScript_DecorClerk2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_ExchangeServiceCorner_EventScript_DecorClerk2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, EXCHANGE_CORNER_VITAMIN_CLERK
 * call BattleFrontier_ExchangeServiceCorner_EventScript_ClerkWelcome
 * goto BattleFrontier_ExchangeServiceCorner_EventScript_ChooseVitamin
 * end
 * ```
 */
internal object BattleFrontier_ExchangeServiceCorner_EventScript_VitaminClerk : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_ExchangeServiceCorner_EventScript_VitaminClerk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, EXCHANGE_CORNER_HOLD_ITEM_CLERK
 * call BattleFrontier_ExchangeServiceCorner_EventScript_ClerkWelcome
 * goto BattleFrontier_ExchangeServiceCorner_EventScript_ChooseHoldItem
 * end
 * ```
 */
internal object BattleFrontier_ExchangeServiceCorner_EventScript_HoldItemClerk : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_ExchangeServiceCorner_EventScript_HoldItemClerk")
}

internal object BattleFrontier_ExchangeServiceCorner_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_ExchangeServiceCorner.MoreBattlePointsForRecord)
}

internal val BattleFrontier_ExchangeServiceCornerScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_ExchangeServiceCorner_EventScript_DecorClerk1" to
            BattleFrontier_ExchangeServiceCorner_EventScript_DecorClerk1,
        "BattleFrontier_ExchangeServiceCorner_EventScript_RichBoy" to
            BattleFrontier_ExchangeServiceCorner_EventScript_RichBoy,
        "BattleFrontier_ExchangeServiceCorner_EventScript_PokefanF" to
            BattleFrontier_ExchangeServiceCorner_EventScript_PokefanF,
        "BattleFrontier_ExchangeServiceCorner_EventScript_Sailor" to
            BattleFrontier_ExchangeServiceCorner_EventScript_Sailor,
        "BattleFrontier_ExchangeServiceCorner_EventScript_Man" to
            BattleFrontier_ExchangeServiceCorner_EventScript_Man,
        "BattleFrontier_ExchangeServiceCorner_EventScript_DecorClerk2" to
            BattleFrontier_ExchangeServiceCorner_EventScript_DecorClerk2,
        "BattleFrontier_ExchangeServiceCorner_EventScript_VitaminClerk" to
            BattleFrontier_ExchangeServiceCorner_EventScript_VitaminClerk,
        "BattleFrontier_ExchangeServiceCorner_EventScript_HoldItemClerk" to
            BattleFrontier_ExchangeServiceCorner_EventScript_HoldItemClerk,
        "BattleFrontier_ExchangeServiceCorner_EventScript_Girl" to
            BattleFrontier_ExchangeServiceCorner_EventScript_Girl,
    )
