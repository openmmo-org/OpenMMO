package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ETHER
 * end
 * ```
 */
internal object Route2_EventScript_ItemEther : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route2_EventScript_ItemEther")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PARALYZE_HEAL
 * end
 * ```
 */
internal object Route2_EventScript_ItemParalyzeHeal : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route2_EventScript_ItemParalyzeHeal")
}

internal object Route2_EventScript_DiglettsCaveSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route2.DiglettsCave)
}

internal object Route2_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route2.RouteSign)
}

internal val Route2Scripts: Map<String, Script> =
    mapOf(
        "Route2_EventScript_ItemEther" to Route2_EventScript_ItemEther,
        "Route2_EventScript_ItemParalyzeHeal" to Route2_EventScript_ItemParalyzeHeal,
        "Route2_EventScript_DiglettsCaveSign" to Route2_EventScript_DiglettsCaveSign,
        "Route2_EventScript_RouteSign" to Route2_EventScript_RouteSign,
    )
