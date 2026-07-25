package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route119_House
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route119_House_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route119_House.RumorAboutCaveOfOrigin)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_WINGULL, CRY_MODE_NORMAL
 * msgbox Route119_House_Text_Wingull, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object Route119_House_EventScript_Wingull : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_House_EventScript_Wingull")
}

internal val Route119_HouseScripts: Map<String, Script> =
    mapOf(
        "Route119_House_EventScript_Woman" to Route119_House_EventScript_Woman,
        "Route119_House_EventScript_Wingull" to Route119_House_EventScript_Wingull,
    )
