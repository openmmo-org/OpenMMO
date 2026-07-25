package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_House2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object ThreeIsland_House2_EventScript_Rocker : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ThreeIsland_House2.CantMakeThisSpoonBend)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_SABRINA, 1
 * msgbox ThreeIsland_House2_Text_IAdmireSabrina
 * release
 * end
 * ```
 */
internal object ThreeIsland_House2_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ThreeIsland_House2_EventScript_Man")
}

internal val ThreeIsland_House2Scripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_House2_EventScript_Rocker" to ThreeIsland_House2_EventScript_Rocker,
        "ThreeIsland_House2_EventScript_Man" to ThreeIsland_House2_EventScript_Man,
    )
