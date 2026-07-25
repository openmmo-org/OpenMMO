package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, 1
 * bufferstdstring STR_VAR_1, STDSTRING_BOULDER_BADGE
 * goto Route22_NorthEntrance_EventScript_BadgeGuard
 * end
 * ```
 */
internal object Route22_NorthEntrance_EventScript_BoulderBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route22_NorthEntrance_EventScript_BoulderBadgeGuard")
}

internal val Route22_NorthEntranceScripts: Map<String, Script> =
    mapOf(
        "Route22_NorthEntrance_EventScript_BoulderBadgeGuard" to
            Route22_NorthEntrance_EventScript_BoulderBadgeGuard,
    )
