package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_WALLY_VICTORY_ROAD, PetalburgCity_WallysHouse_EventScript_DefeatedWallyInVictoryRoad
 * goto_if_set FLAG_RECEIVED_HM_SURF, PetalburgCity_WallysHouse_EventScript_ReceievedHMSurf
 * goto_if_set FLAG_THANKED_FOR_PLAYING_WITH_WALLY, PetalburgCity_WallysHouse_EventScript_PlayedWithWally
 * msgbox PetalburgCity_WallysHouse_Text_ThanksForPlayingWithWally, MSGBOX_DEFAULT
 * setflag FLAG_THANKED_FOR_PLAYING_WITH_WALLY
 * release
 * end
 * ```
 */
internal object PetalburgCity_WallysHouse_EventScript_WallysDad : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_WallysHouse_EventScript_WallysDad")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_HM_SURF, PetalburgCity_WallysHouse_EventScript_ReceivedHMSurf
 * msgbox PetalburgCity_WallysHouse_Text_WallyWasReallyHappy, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object PetalburgCity_WallysHouse_EventScript_WallysMom : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_WallysHouse_EventScript_WallysMom")
}

internal val PetalburgCity_WallysHouseScripts: Map<String, Script> =
    mapOf(
        "PetalburgCity_WallysHouse_EventScript_WallysDad" to
            PetalburgCity_WallysHouse_EventScript_WallysDad,
        "PetalburgCity_WallysHouse_EventScript_WallysMom" to
            PetalburgCity_WallysHouse_EventScript_WallysMom,
    )
