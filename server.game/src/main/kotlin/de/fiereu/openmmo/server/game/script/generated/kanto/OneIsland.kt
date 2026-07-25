package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.OneIsland
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_CAN_LINK_WITH_RS, OneIsland_EventScript_OldManLinkHoenn
 * goto_if_set FLAG_SEVII_DETOUR_FINISHED, OneIsland_EventScript_OldManLinkKanto
 * msgbox OneIsland_Text_LuckyToHaveCelioHere
 * release
 * end
 * ```
 */
internal object OneIsland_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port OneIsland_EventScript_OldMan")
}

internal object OneIsland_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(OneIsland.IsntWarmClimateHereGreat)
}

internal object OneIsland_EventScript_IslandSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(OneIsland.IslandSign)
}

internal object OneIsland_EventScript_PokemonNetCenterSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(OneIsland.PokemonNetCenterSign)
}

internal val OneIslandScripts: Map<String, Script> =
    mapOf(
        "OneIsland_EventScript_OldMan" to OneIsland_EventScript_OldMan,
        "OneIsland_EventScript_BaldingMan" to OneIsland_EventScript_BaldingMan,
        "OneIsland_EventScript_IslandSign" to OneIsland_EventScript_IslandSign,
        "OneIsland_EventScript_PokemonNetCenterSign" to OneIsland_EventScript_PokemonNetCenterSign,
    )
