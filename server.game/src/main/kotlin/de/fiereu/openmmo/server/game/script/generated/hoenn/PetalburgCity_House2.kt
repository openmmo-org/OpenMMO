package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PetalburgCity_House2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PetalburgCity_House2_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity_House2.NormanBecameGymLeader)
}

internal object PetalburgCity_House2_EventScript_SchoolKid : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity_House2.BattledNormanOnce)
}

internal val PetalburgCity_House2Scripts: Map<String, Script> =
    mapOf(
        "PetalburgCity_House2_EventScript_Woman" to PetalburgCity_House2_EventScript_Woman,
        "PetalburgCity_House2_EventScript_SchoolKid" to PetalburgCity_House2_EventScript_SchoolKid,
    )
