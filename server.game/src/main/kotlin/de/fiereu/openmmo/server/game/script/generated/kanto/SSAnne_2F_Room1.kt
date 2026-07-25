package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message SSAnne_2F_Room1_Text_SleepingMonLookedLikeThis
 * waitmessage
 * setvar VAR_0x8004, SPECIES_SNORLAX
 * special SetSeenMon
 * showmonpic SPECIES_SNORLAX, 10, 3
 * waitbuttonpress
 * hidemonpic
 * release
 * end
 * ```
 */
internal object SSAnne_2F_Room1_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_2F_Room1_EventScript_Gentleman")
}

internal val SSAnne_2F_Room1Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_2F_Room1_EventScript_Gentleman" to SSAnne_2F_Room1_EventScript_Gentleman,
    )
