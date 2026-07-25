package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SafariZone_Northeast
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SafariZone_Northeast_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_Northeast.Boy)
}

internal object SafariZone_Northeast_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_Northeast.Girl)
}

internal object SafariZone_Northeast_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SafariZone_Northeast.Woman)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object SafariZone_Northeast_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SafariZone_Northeast_EventScript_ItemNugget")
}

internal val SafariZone_NortheastScripts: Map<String, Script> =
    mapOf(
        "SafariZone_Northeast_EventScript_Boy" to SafariZone_Northeast_EventScript_Boy,
        "SafariZone_Northeast_EventScript_Girl" to SafariZone_Northeast_EventScript_Girl,
        "SafariZone_Northeast_EventScript_Woman" to SafariZone_Northeast_EventScript_Woman,
        "SafariZone_Northeast_EventScript_ItemNugget" to
            SafariZone_Northeast_EventScript_ItemNugget,
    )
