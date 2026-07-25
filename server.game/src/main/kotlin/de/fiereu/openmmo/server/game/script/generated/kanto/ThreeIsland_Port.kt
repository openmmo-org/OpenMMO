package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_Port
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_TWO_ISLAND_JOYFUL_GAME_CORNER, 2, ThreeIsland_Port_EventScript_WomanLostelleFound
 * goto_if_ge VAR_MAP_SCENE_THREE_ISLAND, 4, ThreeIsland_Port_EventScript_WomanBikersGone
 * msgbox ThreeIsland_Port_Text_IllCallThePolice
 * release
 * end
 * ```
 */
internal object ThreeIsland_Port_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ThreeIsland_Port_EventScript_Woman")
}

internal object ThreeIsland_Port_EventScript_Biker1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ThreeIsland_Port.WereKantoRiderFederation)
}

internal object ThreeIsland_Port_EventScript_Biker2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ThreeIsland_Port.ForkOverMoney)
}

internal val ThreeIsland_PortScripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_Port_EventScript_Woman" to ThreeIsland_Port_EventScript_Woman,
        "ThreeIsland_Port_EventScript_Biker1" to ThreeIsland_Port_EventScript_Biker1,
        "ThreeIsland_Port_EventScript_Biker2" to ThreeIsland_Port_EventScript_Biker2,
    )
