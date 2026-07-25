package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_Flat2_3F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object RustboroCity_Flat2_3F_EventScript_DevonEmployee : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_Flat2_3F.PresidentCollectsRareStones)
}

internal object RustboroCity_Flat2_3F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_Flat2_3F.PresidentsSonAlsoCollectsRareStones)
}

internal val RustboroCity_Flat2_3FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_Flat2_3F_EventScript_DevonEmployee" to
            RustboroCity_Flat2_3F_EventScript_DevonEmployee,
        "RustboroCity_Flat2_3F_EventScript_Woman" to RustboroCity_Flat2_3F_EventScript_Woman,
    )
