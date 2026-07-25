package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox FarawayIsland_Entrance_Text_SailorReturn, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, FarawayIsland_Entrance_EventScript_AsYouLike
 * msgbox EventTicket_Text_SailHome, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_WalkInPlaceFasterDown
 * waitmovement 0
 * delay 30
 * hideobjectat LOCALID_FARAWAY_ISLAND_SAILOR, MAP_FARAWAY_ISLAND_ENTRANCE
 * setvar VAR_0x8004, LOCALID_FARAWAY_ISLAND_SS_TIDAL
 * call Common_EventScript_FerryDepartIsland
 * warp MAP_LILYCOVE_CITY_HARBOR, 8, 11
 * waitstate
 * release
 * end
 * ```
 */
internal object FarawayIsland_Entrance_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FarawayIsland_Entrance_EventScript_Sailor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * msgbox FarawayIsland_Entrance_Text_Sign, MSGBOX_SIGN
 * end
 * ```
 */
internal object FarawayIsland_Entrance_EventScript_Sign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FarawayIsland_Entrance_EventScript_Sign")
}

internal val FarawayIsland_EntranceScripts: Map<String, Script> =
    mapOf(
        "FarawayIsland_Entrance_EventScript_Sailor" to FarawayIsland_Entrance_EventScript_Sailor,
        "FarawayIsland_Entrance_EventScript_Sign" to FarawayIsland_Entrance_EventScript_Sign,
    )
