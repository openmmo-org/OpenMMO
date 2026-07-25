package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SafariZone_West
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GOLD_TEETH
 * end
 * ```
 */
internal object SafariZone_West_EventScript_ItemGoldTeeth : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SafariZone_West_EventScript_ItemGoldTeeth")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM32
 * end
 * ```
 */
internal object SafariZone_West_EventScript_ItemTM32 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SafariZone_West_EventScript_ItemTM32")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_POTION
 * end
 * ```
 */
internal object SafariZone_West_EventScript_ItemMaxPotion : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SafariZone_West_EventScript_ItemMaxPotion")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REVIVE
 * end
 * ```
 */
internal object SafariZone_West_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SafariZone_West_EventScript_ItemMaxRevive")
}

internal object SafariZone_West_EventScript_LostTeethNotice : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SafariZone_West.PleaseFindWardensLostTeeth)
}

internal object SafariZone_West_EventScript_TrainerTips : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SafariZone_West.SearchForSecretHouse)
}

internal object SafariZone_West_EventScript_AreaSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SafariZone_West.AreaSign)
}

internal object SafariZone_West_EventScript_RestHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SafariZone_West.RestHouse)
}

internal val SafariZone_WestScripts: Map<String, Script> =
    mapOf(
        "SafariZone_West_EventScript_ItemGoldTeeth" to SafariZone_West_EventScript_ItemGoldTeeth,
        "SafariZone_West_EventScript_ItemTM32" to SafariZone_West_EventScript_ItemTM32,
        "SafariZone_West_EventScript_ItemMaxPotion" to SafariZone_West_EventScript_ItemMaxPotion,
        "SafariZone_West_EventScript_ItemMaxRevive" to SafariZone_West_EventScript_ItemMaxRevive,
        "SafariZone_West_EventScript_LostTeethNotice" to
            SafariZone_West_EventScript_LostTeethNotice,
        "SafariZone_West_EventScript_TrainerTips" to SafariZone_West_EventScript_TrainerTips,
        "SafariZone_West_EventScript_AreaSign" to SafariZone_West_EventScript_AreaSign,
        "SafariZone_West_EventScript_RestHouseSign" to SafariZone_West_EventScript_RestHouseSign,
    )
