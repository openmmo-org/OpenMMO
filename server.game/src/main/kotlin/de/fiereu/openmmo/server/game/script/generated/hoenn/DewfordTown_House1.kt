package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.DewfordTown_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object DewfordTown_House1_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(DewfordTown_House1.LifeGoesSlowlyOnIsland)
}

internal object DewfordTown_House1_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(DewfordTown_House1.LotToBeSaidForLivingOnIsland)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_ZIGZAGOON, CRY_MODE_NORMAL
 * msgbox DewfordTown_House1_Text_Zigzagoon, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object DewfordTown_House1_EventScript_Zigzagoon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port DewfordTown_House1_EventScript_Zigzagoon")
}

internal val DewfordTown_House1Scripts: Map<String, Script> =
    mapOf(
        "DewfordTown_House1_EventScript_Woman" to DewfordTown_House1_EventScript_Woman,
        "DewfordTown_House1_EventScript_Man" to DewfordTown_House1_EventScript_Man,
        "DewfordTown_House1_EventScript_Zigzagoon" to DewfordTown_House1_EventScript_Zigzagoon,
    )
