package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * special GetLotadSizeRecordInfo
 * lock
 * faceplayer
 * msgbox SootopolisCity_LotadAndSeedotHouse_Text_PleaseShowMeBigLotad, MSGBOX_DEFAULT
 * special ChoosePartyMon
 * copyvar VAR_RESULT, VAR_0x8004
 * goto_if_eq VAR_RESULT, PARTY_NOTHING_CHOSEN, SootopolisCity_LotadAndSeedotHouse_EventScript_CancelShowLotad
 * special CompareLotadSize
 * goto_if_eq VAR_RESULT, COMPARE_SIZE_INCORRECT_SPECIES, SootopolisCity_LotadAndSeedotHouse_EventScript_NotLotad
 * goto_if_eq VAR_RESULT, COMPARE_SIZE_SMALLER, SootopolisCity_LotadAndSeedotHouse_EventScript_SmallLotad
 * goto_if_eq VAR_RESULT, COMPARE_SIZE_LARGER, SootopolisCity_LotadAndSeedotHouse_EventScript_BigLotad
 * release
 * end
 * ```
 */
internal object SootopolisCity_LotadAndSeedotHouse_EventScript_LotadBrother : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_LotadAndSeedotHouse_EventScript_LotadBrother")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * special GetSeedotSizeRecordInfo
 * lock
 * faceplayer
 * msgbox SootopolisCity_LotadAndSeedotHouse_Text_PleaseShowMeBigSeedot, MSGBOX_DEFAULT
 * special ChoosePartyMon
 * copyvar VAR_RESULT, VAR_0x8004
 * goto_if_eq VAR_RESULT, PARTY_NOTHING_CHOSEN, SootopolisCity_LotadAndSeedotHouse_EventScript_CancelShowSeedot
 * special CompareSeedotSize
 * goto_if_eq VAR_RESULT, COMPARE_SIZE_INCORRECT_SPECIES, SootopolisCity_LotadAndSeedotHouse_EventScript_NotSeedot
 * goto_if_eq VAR_RESULT, COMPARE_SIZE_SMALLER, SootopolisCity_LotadAndSeedotHouse_EventScript_SmallSeedot
 * goto_if_eq VAR_RESULT, COMPARE_SIZE_LARGER, SootopolisCity_LotadAndSeedotHouse_EventScript_BigSeedot
 * release
 * end
 * ```
 */
internal object SootopolisCity_LotadAndSeedotHouse_EventScript_SeedotBrother : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_LotadAndSeedotHouse_EventScript_SeedotBrother")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * special GetSeedotSizeRecordInfo
 * lockall
 * msgbox SootopolisCity_LotadAndSeedotHouse_Text_BiggestSeedotInHistory, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_LotadAndSeedotHouse_EventScript_SeedotSizeRecord : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_LotadAndSeedotHouse_EventScript_SeedotSizeRecord")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * special GetLotadSizeRecordInfo
 * lockall
 * msgbox SootopolisCity_LotadAndSeedotHouse_Text_BiggestLotadInHistory, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_LotadAndSeedotHouse_EventScript_LotadSizeRecord : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_LotadAndSeedotHouse_EventScript_LotadSizeRecord")
}

internal val SootopolisCity_LotadAndSeedotHouseScripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_LotadAndSeedotHouse_EventScript_LotadBrother" to
            SootopolisCity_LotadAndSeedotHouse_EventScript_LotadBrother,
        "SootopolisCity_LotadAndSeedotHouse_EventScript_SeedotBrother" to
            SootopolisCity_LotadAndSeedotHouse_EventScript_SeedotBrother,
        "SootopolisCity_LotadAndSeedotHouse_EventScript_SeedotSizeRecord" to
            SootopolisCity_LotadAndSeedotHouse_EventScript_SeedotSizeRecord,
        "SootopolisCity_LotadAndSeedotHouse_EventScript_LotadSizeRecord" to
            SootopolisCity_LotadAndSeedotHouse_EventScript_LotadSizeRecord,
    )
