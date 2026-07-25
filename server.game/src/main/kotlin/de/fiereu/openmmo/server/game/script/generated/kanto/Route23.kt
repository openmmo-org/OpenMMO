package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route23
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, 2
 * bufferstdstring STR_VAR_1, STDSTRING_CASCADE_BADGE
 * goto Route23_EventScript_BadgeGuard
 * end
 * ```
 */
internal object Route23_EventScript_CascadeBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route23_EventScript_CascadeBadgeGuard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, 3
 * bufferstdstring STR_VAR_1, STDSTRING_THUNDER_BADGE
 * goto Route23_EventScript_BadgeGuard
 * end
 * ```
 */
internal object Route23_EventScript_ThunderBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route23_EventScript_ThunderBadgeGuard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, 4
 * bufferstdstring STR_VAR_1, STDSTRING_RAINBOW_BADGE
 * goto Route23_EventScript_BadgeGuard
 * end
 * ```
 */
internal object Route23_EventScript_RainbowBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route23_EventScript_RainbowBadgeGuard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, 5
 * bufferstdstring STR_VAR_1, STDSTRING_SOUL_BADGE
 * goto Route23_EventScript_BadgeGuard
 * end
 * ```
 */
internal object Route23_EventScript_SoulBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route23_EventScript_SoulBadgeGuard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, 6
 * bufferstdstring STR_VAR_1, STDSTRING_MARSH_BADGE
 * goto Route23_EventScript_BadgeGuard
 * end
 * ```
 */
internal object Route23_EventScript_MarshBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route23_EventScript_MarshBadgeGuard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, 7
 * bufferstdstring STR_VAR_1, STDSTRING_VOLCANO_BADGE
 * goto Route23_EventScript_BadgeGuard
 * end
 * ```
 */
internal object Route23_EventScript_VolcanoBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route23_EventScript_VolcanoBadgeGuard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_1, 8
 * bufferstdstring STR_VAR_1, STDSTRING_EARTH_BADGE
 * goto Route23_EventScript_BadgeGuard
 * end
 * ```
 */
internal object Route23_EventScript_EarthBadgeGuard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route23_EventScript_EarthBadgeGuard")
}

internal object Route23_EventScript_VictoryRoadGateSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route23.VictoryRoadGateSign)
}

internal val Route23Scripts: Map<String, Script> =
    mapOf(
        "Route23_EventScript_CascadeBadgeGuard" to Route23_EventScript_CascadeBadgeGuard,
        "Route23_EventScript_ThunderBadgeGuard" to Route23_EventScript_ThunderBadgeGuard,
        "Route23_EventScript_RainbowBadgeGuard" to Route23_EventScript_RainbowBadgeGuard,
        "Route23_EventScript_SoulBadgeGuard" to Route23_EventScript_SoulBadgeGuard,
        "Route23_EventScript_MarshBadgeGuard" to Route23_EventScript_MarshBadgeGuard,
        "Route23_EventScript_VolcanoBadgeGuard" to Route23_EventScript_VolcanoBadgeGuard,
        "Route23_EventScript_EarthBadgeGuard" to Route23_EventScript_EarthBadgeGuard,
        "Route23_EventScript_VictoryRoadGateSign" to Route23_EventScript_VictoryRoadGateSign,
    )
