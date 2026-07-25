package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox EventTicket_Text_SouthernIslandSailBack, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, SouthernIsland_Exterior_EventScript_AsYouLike
 * msgbox EventTicket_Text_SailHome, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_WalkInPlaceFasterDown
 * waitmovement 0
 * delay 30
 * hideobjectat LOCALID_SOUTHERN_ISLAND_SAILOR, MAP_SOUTHERN_ISLAND_EXTERIOR
 * setvar VAR_0x8004, LOCALID_SOUTHERN_ISLAND_SS_TIDAL
 * call Common_EventScript_FerryDepartIsland
 * warp MAP_LILYCOVE_CITY_HARBOR, 8, 11
 * waitstate
 * release
 * end
 * ```
 */
internal object SouthernIsland_Exterior_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SouthernIsland_Exterior_EventScript_Sailor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * msgbox SouthernIsland_Exterior_Text_Sign, MSGBOX_SIGN
 * end
 * ```
 */
internal object SouthernIsland_Exterior_EventScript_Sign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SouthernIsland_Exterior_EventScript_Sign")
}

internal val SouthernIsland_ExteriorScripts: Map<String, Script> =
    mapOf(
        "SouthernIsland_Exterior_EventScript_Sailor" to SouthernIsland_Exterior_EventScript_Sailor,
        "SouthernIsland_Exterior_EventScript_Sign" to SouthernIsland_Exterior_EventScript_Sign,
    )
