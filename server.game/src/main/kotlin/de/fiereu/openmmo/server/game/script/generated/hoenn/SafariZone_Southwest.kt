package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SafariZone_Southwest
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SafariZone_Southwest_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_Southwest.Woman)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REVIVE
 * end
 * ```
 */
internal object SafariZone_Southwest_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SafariZone_Southwest_EventScript_ItemMaxRevive")
}

internal object SafariZone_Southwest_EventScript_RestHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SafariZone_Southwest.RestHouseSign)
}

internal val SafariZone_SouthwestScripts: Map<String, Script> =
    mapOf(
        "SafariZone_Southwest_EventScript_Woman" to SafariZone_Southwest_EventScript_Woman,
        "SafariZone_Southwest_EventScript_ItemMaxRevive" to
            SafariZone_Southwest_EventScript_ItemMaxRevive,
        "SafariZone_Southwest_EventScript_RestHouseSign" to
            SafariZone_Southwest_EventScript_RestHouseSign,
    )
