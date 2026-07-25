package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SaffronCity_House
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_PIDGEY, CRY_MODE_NORMAL
 * msgbox SaffronCity_House_Text_Pidgey
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SaffronCity_House_EventScript_Pidgey : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_House_EventScript_Pidgey")
}

internal object SaffronCity_House_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity_House.DontLookAtMyLetter)
}

internal object SaffronCity_House_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity_House.GettingCopycatPokeDoll)
}

internal object SaffronCity_House_EventScript_Letter : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity_House.ExplainPPUp)
}

internal val SaffronCity_HouseScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_House_EventScript_Pidgey" to SaffronCity_House_EventScript_Pidgey,
        "SaffronCity_House_EventScript_Lass" to SaffronCity_House_EventScript_Lass,
        "SaffronCity_House_EventScript_Youngster" to SaffronCity_House_EventScript_Youngster,
        "SaffronCity_House_EventScript_Letter" to SaffronCity_House_EventScript_Letter,
    )
