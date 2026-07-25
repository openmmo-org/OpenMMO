package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * setvar VAR_0x8004, SPECIES_HERACROSS
 * specialvar VAR_RESULT, DoesPlayerPartyContainSpecies
 * goto_if_eq VAR_RESULT, FALSE, SixIsland_WaterPath_House1_EventScript_NoHeracrossInParty
 * special GetHeracrossSizeRecordInfo
 * msgbox SixIsland_WaterPath_House1_Text_MayIMeasureHeracross
 * special ChoosePartyMon
 * waitstate
 * copyvar VAR_RESULT, VAR_0x8004
 * goto_if_ge VAR_RESULT, PARTY_SIZE, SixIsland_WaterPath_House1_EventScript_DontShowMon
 * special CompareHeracrossSize
 * goto_if_eq VAR_RESULT, 1, SixIsland_WaterPath_House1_EventScript_ShownNonHeracross
 * goto_if_eq VAR_RESULT, 2, SixIsland_WaterPath_House1_EventScript_ShownSmallHeracross
 * goto_if_eq VAR_RESULT, 3, SixIsland_WaterPath_House1_EventScript_ShownBigHeracross
 * goto_if_eq VAR_RESULT, 4, SixIsland_WaterPath_House1_EventScript_ShownTiedHeracross
 * release
 * end
 * ```
 */
internal object SixIsland_WaterPath_House1_EventScript_Beauty : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_WaterPath_House1_EventScript_Beauty")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_GOT_NEST_BALL_FROM_WATER_PATH_HOUSE_1, SixIsland_WaterPath_House1_EventScript_SizeRecordNonEmpty
 * msgbox SixIsland_WaterPath_House1_Text_BlankChartOfSomeSort
 * releaseall
 * end
 * ```
 */
internal object SixIsland_WaterPath_House1_EventScript_SizeRecord : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_WaterPath_House1_EventScript_SizeRecord")
}

internal val SixIsland_WaterPath_House1Scripts: Map<String, Script> =
    mapOf(
        "SixIsland_WaterPath_House1_EventScript_Beauty" to
            SixIsland_WaterPath_House1_EventScript_Beauty,
        "SixIsland_WaterPath_House1_EventScript_SizeRecord" to
            SixIsland_WaterPath_House1_EventScript_SizeRecord,
    )
