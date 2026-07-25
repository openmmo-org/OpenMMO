package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PetalburgCity_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PetalburgCity_House1_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity_House1.GoOnAdventure)
}

internal object PetalburgCity_House1_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity_House1.TravelingIsWonderful)
}

internal val PetalburgCity_House1Scripts: Map<String, Script> =
    mapOf(
        "PetalburgCity_House1_EventScript_Woman" to PetalburgCity_House1_EventScript_Woman,
        "PetalburgCity_House1_EventScript_Man" to PetalburgCity_House1_EventScript_Man,
    )
