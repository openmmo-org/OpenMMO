package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.InsideOfTruck
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object InsideOfTruck_EventScript_MovingBox : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(InsideOfTruck.BoxPrintedWithMonLogo)
}

internal val InsideOfTruckScripts: Map<String, Script> =
    mapOf(
        "InsideOfTruck_EventScript_MovingBox" to InsideOfTruck_EventScript_MovingBox,
    )
