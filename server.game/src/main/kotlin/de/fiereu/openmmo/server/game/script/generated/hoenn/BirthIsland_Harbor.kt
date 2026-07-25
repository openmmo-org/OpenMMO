package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BirthIsland_Harbor_Text_SailorReturn, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, BirthIsland_Harbor_EventScript_AsYouLike
 * msgbox EventTicket_Text_SailHome, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_WalkInPlaceFasterDown
 * waitmovement 0
 * delay 30
 * hideobjectat LOCALID_BIRTH_ISLAND_SAILOR, MAP_BIRTH_ISLAND_HARBOR
 * setvar VAR_0x8004, LOCALID_BIRTH_ISLAND_SS_TIDAL
 * call Common_EventScript_FerryDepartIsland
 * warp MAP_LILYCOVE_CITY_HARBOR, 8, 11
 * waitstate
 * release
 * end
 * ```
 */
internal object BirthIsland_Harbor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port BirthIsland_Harbor_EventScript_Sailor")
}

internal val BirthIsland_HarborScripts: Map<String, Script> =
    mapOf(
        "BirthIsland_Harbor_EventScript_Sailor" to BirthIsland_Harbor_EventScript_Sailor,
    )
