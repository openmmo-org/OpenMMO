package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_unset FLAG_MR_BRINEY_SAILING_INTRO, Route104_MrBrineysHouse_EventScript_SailingIntro
 * goto_if_unset FLAG_DELIVERED_STEVEN_LETTER, Route104_MrBrineysHouse_EventScript_SailBothDeliveries
 * goto_if_unset FLAG_DELIVERED_DEVON_GOODS, Route104_MrBrineysHouse_EventScript_SailDeliverPackage
 * goto Route104_MrBrineysHouse_EventScript_WhereAreWeBound
 * end
 * ```
 */
internal object Route104_MrBrineysHouse_EventScript_Briney : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route104_MrBrineysHouse_EventScript_Briney")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_WINGULL, CRY_MODE_NORMAL
 * msgbox Route104_MrBrineysHouse_Text_Peeko, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object Route104_MrBrineysHouse_EventScript_Peeko : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route104_MrBrineysHouse_EventScript_Peeko")
}

internal val Route104_MrBrineysHouseScripts: Map<String, Script> =
    mapOf(
        "Route104_MrBrineysHouse_EventScript_Briney" to Route104_MrBrineysHouse_EventScript_Briney,
        "Route104_MrBrineysHouse_EventScript_Peeko" to Route104_MrBrineysHouse_EventScript_Peeko,
    )
