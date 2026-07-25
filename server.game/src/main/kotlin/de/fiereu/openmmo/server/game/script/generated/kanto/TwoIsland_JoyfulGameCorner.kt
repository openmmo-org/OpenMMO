package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.TwoIsland_JoyfulGameCorner
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MAP_SCENE_TWO_ISLAND_JOYFUL_GAME_CORNER, 4, JoyfulGameCorner_EventScript_MinigameAttendant
 * goto_if_set FLAG_GOT_MOON_STONE_FROM_JOYFUL_GAME_CORNER, TwoIsland_JoyfulGameCorner_EventScript_GetGameCornerRunning
 * goto_if_eq VAR_MAP_SCENE_TWO_ISLAND_JOYFUL_GAME_CORNER, 3, TwoIsland_JoyfulGameCorner_EventScript_GiveDaddyMeteorite
 * msgbox TwoIsland_JoyfulGameCorner_Text_PleaseGoToThreeIsland
 * release
 * end
 * ```
 */
internal object TwoIsland_JoyfulGameCorner_EventScript_LostellesDaddy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TwoIsland_JoyfulGameCorner_EventScript_LostellesDaddy")
}

internal object TwoIsland_JoyfulGameCorner_EventScript_Lostelle : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(TwoIsland_JoyfulGameCorner.PleaseShowMeYouBeingCool)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MAP_SCENE_TWO_ISLAND_JOYFUL_GAME_CORNER, 4, JoyfulGameCorner_EventScript_InfoMan2
 * goto_if_set FLAG_RESCUED_LOSTELLE, TwoIsland_JoyfulGameCorner_EventScript_GetGamesGoingSoon
 * msgbox TwoIsland_JoyfulGameCorner_Text_NotRunningAnyGamesToday
 * release
 * end
 * ```
 */
internal object TwoIsland_JoyfulGameCorner_EventScript_InfoMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TwoIsland_JoyfulGameCorner_EventScript_InfoMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lockall
 * special ShowPokemonJumpRecords
 * waitstate
 * releaseall
 * end
 * ```
 */
internal object TwoIsland_JoyfulGameCorner_EventScript_ShowPokemonJumpRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TwoIsland_JoyfulGameCorner_EventScript_ShowPokemonJumpRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lockall
 * special ShowDodrioBerryPickingRecords
 * waitstate
 * releaseall
 * end
 * ```
 */
internal object TwoIsland_JoyfulGameCorner_EventScript_ShowDodrioBerryPickingRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TwoIsland_JoyfulGameCorner_EventScript_ShowDodrioBerryPickingRecords")
}

internal val TwoIsland_JoyfulGameCornerScripts: Map<String, Script> =
    mapOf(
        "TwoIsland_JoyfulGameCorner_EventScript_LostellesDaddy" to
            TwoIsland_JoyfulGameCorner_EventScript_LostellesDaddy,
        "TwoIsland_JoyfulGameCorner_EventScript_Lostelle" to
            TwoIsland_JoyfulGameCorner_EventScript_Lostelle,
        "TwoIsland_JoyfulGameCorner_EventScript_InfoMan" to
            TwoIsland_JoyfulGameCorner_EventScript_InfoMan,
        "TwoIsland_JoyfulGameCorner_EventScript_ShowPokemonJumpRecords" to
            TwoIsland_JoyfulGameCorner_EventScript_ShowPokemonJumpRecords,
        "TwoIsland_JoyfulGameCorner_EventScript_ShowDodrioBerryPickingRecords" to
            TwoIsland_JoyfulGameCorner_EventScript_ShowDodrioBerryPickingRecords,
    )
