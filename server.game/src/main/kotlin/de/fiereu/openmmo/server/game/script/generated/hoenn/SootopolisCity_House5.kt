package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SootopolisCity_House5
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SootopolisCity_House5_EventScript_Maniac : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SootopolisCity_House5.SootopolisMtPyreConnection)
}

internal object SootopolisCity_House5_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SootopolisCity_House5.BrotherUsedToStudySea)
}

internal val SootopolisCity_House5Scripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_House5_EventScript_Maniac" to SootopolisCity_House5_EventScript_Maniac,
        "SootopolisCity_House5_EventScript_Girl" to SootopolisCity_House5_EventScript_Girl,
    )
