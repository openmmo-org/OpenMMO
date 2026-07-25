package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SafariZone_Center
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object SafariZone_Center_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SafariZone_Center_EventScript_ItemNugget")
}

internal object SafariZone_Center_EventScript_RestHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SafariZone_Center.RestHouse)
}

internal object SafariZone_Center_EventScript_TrainerTips : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SafariZone_Center.PressStartToCheckTime)
}

internal object SafariZone_Center_EventScript_AreaSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SafariZone_Center.CenterArea)
}

internal val SafariZone_CenterScripts: Map<String, Script> =
    mapOf(
        "SafariZone_Center_EventScript_ItemNugget" to SafariZone_Center_EventScript_ItemNugget,
        "SafariZone_Center_EventScript_RestHouseSign" to
            SafariZone_Center_EventScript_RestHouseSign,
        "SafariZone_Center_EventScript_TrainerTips" to SafariZone_Center_EventScript_TrainerTips,
        "SafariZone_Center_EventScript_AreaSign" to SafariZone_Center_EventScript_AreaSign,
    )
