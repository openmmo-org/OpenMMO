package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SootopolisCity_House4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SootopolisCity_House4_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SootopolisCity_House4.AncientTreasuresWaitingInSea)
}

internal object SootopolisCity_House4_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SootopolisCity_House4.StrollUnderwaterWithPokemon)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_AZUMARILL, CRY_MODE_NORMAL
 * msgbox SootopolisCity_House4_Text_Azumarill, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SootopolisCity_House4_EventScript_Azumarill : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_House4_EventScript_Azumarill")
}

internal val SootopolisCity_House4Scripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_House4_EventScript_Man" to SootopolisCity_House4_EventScript_Man,
        "SootopolisCity_House4_EventScript_Woman" to SootopolisCity_House4_EventScript_Woman,
        "SootopolisCity_House4_EventScript_Azumarill" to
            SootopolisCity_House4_EventScript_Azumarill,
    )
