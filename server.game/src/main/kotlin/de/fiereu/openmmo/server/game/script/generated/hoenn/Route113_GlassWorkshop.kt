package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route113_GlassWorkshop
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_GLASS_WORKSHOP_STATE, 10, Route113_GlassWorkshop_EventScript_GiveItemAfterNoRoom
 * goto_if_eq VAR_GLASS_WORKSHOP_STATE, 2, Route113_GlassWorkshop_EventScript_CheckCollectedAsh
 * goto_if_eq VAR_GLASS_WORKSHOP_STATE, 1, Route113_GlassWorkshop_EventScript_ExplainSootSack
 * msgbox Route113_GlassWorkshop_Text_GoCollectAshWithThis, MSGBOX_DEFAULT
 * giveitem ITEM_SOOT_SACK
 * setvar VAR_GLASS_WORKSHOP_STATE, 1
 * msgbox Route113_GlassWorkshop_Text_ExplainSootSack, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route113_GlassWorkshop_EventScript_GlassWorker : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route113_GlassWorkshop_EventScript_GlassWorker")
}

internal object Route113_GlassWorkshop_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route113_GlassWorkshop.FunToBlowGlassFlute)
}

internal val Route113_GlassWorkshopScripts: Map<String, Script> =
    mapOf(
        "Route113_GlassWorkshop_EventScript_GlassWorker" to
            Route113_GlassWorkshop_EventScript_GlassWorker,
        "Route113_GlassWorkshop_EventScript_NinjaBoy" to
            Route113_GlassWorkshop_EventScript_NinjaBoy,
    )
