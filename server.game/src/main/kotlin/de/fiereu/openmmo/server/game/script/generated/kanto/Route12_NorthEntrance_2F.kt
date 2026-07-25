package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route12_NorthEntrance_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_TM27, Route12_NorthEntrance_2F_EventScript_ExplainTM27
 * checkplayergender
 * call_if_eq VAR_RESULT, MALE, Route12_NorthEntrance_2F_EventScript_TakeTMMale
 * call_if_eq VAR_RESULT, FEMALE, Route12_NorthEntrance_2F_EventScript_TakeTMFemale
 * checkitemspace ITEM_TM27
 * goto_if_eq VAR_RESULT, FALSE, Route12_NorthEntrance_2F_EventScript_NoRoomForTM27
 * giveitem_msg Route12_NorthEntrance_2F_Text_ReceivedTM27FromLittleGirl, ITEM_TM27
 * msgbox Route12_NorthEntrance_2F_Text_ExplainTM27
 * setflag FLAG_GOT_TM27
 * release
 * end
 * ```
 */
internal object Route12_NorthEntrance_2F_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route12_NorthEntrance_2F_EventScript_Lass")
}

internal object Route12_NorthEntrance_2F_EventScript_LeftBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route12_NorthEntrance_2F.TheresManFishing)
}

internal object Route12_NorthEntrance_2F_EventScript_RightBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route12_NorthEntrance_2F.ItsPokemonTower)
}

internal val Route12_NorthEntrance_2FScripts: Map<String, Script> =
    mapOf(
        "Route12_NorthEntrance_2F_EventScript_Lass" to Route12_NorthEntrance_2F_EventScript_Lass,
        "Route12_NorthEntrance_2F_EventScript_LeftBinoculars" to
            Route12_NorthEntrance_2F_EventScript_LeftBinoculars,
        "Route12_NorthEntrance_2F_EventScript_RightBinoculars" to
            Route12_NorthEntrance_2F_EventScript_RightBinoculars,
    )
