package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_TOGEPI_EGG, FiveIsland_WaterLabyrinth_EventScript_PostEggComment
 * goto_if_set FLAG_NO_ROOM_FOR_TOGEPI_EGG, FiveIsland_WaterLabyrinth_EventScript_ReturnForEgg
 * msgbox FiveIsland_WaterLabyrinth_Text_LetMeTakeLookAtMons
 * specialvar VAR_RESULT, GetLeadMonFriendship
 * goto_if_eq VAR_RESULT, 6, FiveIsland_WaterLabyrinth_EventScript_LeadMonMaxFriendship
 * msgbox FiveIsland_WaterLabyrinth_Text_HmmISeeIsee
 * release
 * end
 * ```
 */
internal object FiveIsland_WaterLabyrinth_EventScript_EggGentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_WaterLabyrinth_EventScript_EggGentleman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PKMN_BREEDER_ALIZE, FiveIsland_WaterLabyrinth_Text_AlizeIntro, FiveIsland_WaterLabyrinth_Text_AlizeDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FiveIsland_WaterLabyrinth_EventScript_AlizeRematch
 * msgbox FiveIsland_WaterLabyrinth_Text_AlizePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_WaterLabyrinth_EventScript_Alize : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_WaterLabyrinth_EventScript_Alize")
}

internal val FiveIsland_WaterLabyrinthScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_WaterLabyrinth_EventScript_EggGentleman" to
            FiveIsland_WaterLabyrinth_EventScript_EggGentleman,
        "FiveIsland_WaterLabyrinth_EventScript_Alize" to
            FiveIsland_WaterLabyrinth_EventScript_Alize,
    )
