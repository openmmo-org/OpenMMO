package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RESCUED_MR_FUJI, LavenderTown_House1_EventScript_CooltrainerFGhostGone
 * msgbox LavenderTown_House1_Text_RocketsKilledCubonesMother
 * release
 * end
 * ```
 */
internal object LavenderTown_House1_EventScript_CooltrainerF : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavenderTown_House1_EventScript_CooltrainerF")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_CUBONE, CRY_MODE_NORMAL
 * msgbox LavenderTown_House1_Text_Cubone
 * waitmoncry
 * release
 * end
 * ```
 */
internal object LavenderTown_House1_EventScript_Cubone : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LavenderTown_House1_EventScript_Cubone")
}

internal val LavenderTown_House1Scripts: Map<String, Script> =
    mapOf(
        "LavenderTown_House1_EventScript_CooltrainerF" to
            LavenderTown_House1_EventScript_CooltrainerF,
        "LavenderTown_House1_EventScript_Cubone" to LavenderTown_House1_EventScript_Cubone,
    )
