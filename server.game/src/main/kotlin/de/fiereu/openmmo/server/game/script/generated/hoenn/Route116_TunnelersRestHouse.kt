package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route116_TunnelersRestHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route116_TunnelersRestHouse_EventScript_Tunneler1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route116_TunnelersRestHouse.WeHadToStopBoring)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RUSTURF_TUNNEL_OPENED, Route116_TunnelersRestHouse_EventScript_TunnelOpened
 * msgbox Route116_TunnelersRestHouse_Text_GetToVerdanturfWithoutTunnel, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route116_TunnelersRestHouse_EventScript_Tunneler3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route116_TunnelersRestHouse_EventScript_Tunneler3")
}

internal object Route116_TunnelersRestHouse_EventScript_Tunneler2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route116_TunnelersRestHouse.ManDiggingHisWayToVerdanturf)
}

internal val Route116_TunnelersRestHouseScripts: Map<String, Script> =
    mapOf(
        "Route116_TunnelersRestHouse_EventScript_Tunneler1" to
            Route116_TunnelersRestHouse_EventScript_Tunneler1,
        "Route116_TunnelersRestHouse_EventScript_Tunneler3" to
            Route116_TunnelersRestHouse_EventScript_Tunneler3,
        "Route116_TunnelersRestHouse_EventScript_Tunneler2" to
            Route116_TunnelersRestHouse_EventScript_Tunneler2,
    )
