package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_REVIVED_FOSSIL_MON, Route114_FossilManiacsTunnel_EventScript_PlayerRevivedFossil
 * checkitem ITEM_ROOT_FOSSIL
 * goto_if_eq VAR_RESULT, TRUE, Route114_FossilManiacsTunnel_EventScript_PlayerHasFossil
 * checkitem ITEM_CLAW_FOSSIL
 * goto_if_eq VAR_RESULT, TRUE, Route114_FossilManiacsTunnel_EventScript_PlayerHasFossil
 * msgbox Route114_FossilManiacsTunnel_Text_LookInDesertForFossils, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route114_FossilManiacsTunnel_EventScript_FossilManiac : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route114_FossilManiacsTunnel_EventScript_FossilManiac")
}

internal val Route114_FossilManiacsTunnelScripts: Map<String, Script> =
    mapOf(
        "Route114_FossilManiacsTunnel_EventScript_FossilManiac" to
            Route114_FossilManiacsTunnel_EventScript_FossilManiac,
    )
