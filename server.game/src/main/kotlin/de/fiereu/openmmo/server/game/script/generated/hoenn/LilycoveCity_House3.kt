package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * switch VAR_TEMP_1
 * case 0, LilycoveCity_House3_EventScript_WereDoingMultiBattle
 * case 1, LilycoveCity_House3_EventScript_WereMixingRecords
 * case 2, LilycoveCity_House3_EventScript_WereBlendingBerries
 * case 3, LilycoveCity_House3_EventScript_WereDoingContest
 * end
 * ```
 */
internal object LilycoveCity_House3_EventScript_GameBoyKid4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_House3_EventScript_GameBoyKid4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_House3_Text_LearnFromMasterOfPokeblocks, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, LilycoveCity_House3_EventScript_DeclinePokeblockLearn
 * msgbox LilycoveCity_House3_Text_ExplainPokeblocks, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_House3_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_House3_EventScript_PokefanF")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * switch VAR_TEMP_1
 * case 0, LilycoveCity_House3_EventScript_WereDoingMultiBattle
 * case 1, LilycoveCity_House3_EventScript_WereMixingRecords
 * case 2, LilycoveCity_House3_EventScript_WereBlendingBerries
 * case 3, LilycoveCity_House3_EventScript_WereDoingContest
 * end
 * ```
 */
internal object LilycoveCity_House3_EventScript_GameBoyKid2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_House3_EventScript_GameBoyKid2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * switch VAR_TEMP_1
 * case 0, LilycoveCity_House3_EventScript_WereDoingMultiBattle
 * case 1, LilycoveCity_House3_EventScript_WereMixingRecords
 * case 2, LilycoveCity_House3_EventScript_WereBlendingBerries
 * case 3, LilycoveCity_House3_EventScript_WereDoingContest
 * end
 * ```
 */
internal object LilycoveCity_House3_EventScript_GameBoyKid3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_House3_EventScript_GameBoyKid3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * switch VAR_TEMP_1
 * case 0, LilycoveCity_House3_EventScript_WereDoingMultiBattle
 * case 1, LilycoveCity_House3_EventScript_WereMixingRecords
 * case 2, LilycoveCity_House3_EventScript_WereBlendingBerries
 * case 3, LilycoveCity_House3_EventScript_WereDoingContest
 * end
 * ```
 */
internal object LilycoveCity_House3_EventScript_GameBoyKid1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_House3_EventScript_GameBoyKid1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LilycoveCity_House3_Text_HappyToHaveQuadruplets, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object LilycoveCity_House3_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LilycoveCity_House3_EventScript_Man")
}

internal val LilycoveCity_House3Scripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_House3_EventScript_GameBoyKid4" to
            LilycoveCity_House3_EventScript_GameBoyKid4,
        "LilycoveCity_House3_EventScript_PokefanF" to LilycoveCity_House3_EventScript_PokefanF,
        "LilycoveCity_House3_EventScript_GameBoyKid2" to
            LilycoveCity_House3_EventScript_GameBoyKid2,
        "LilycoveCity_House3_EventScript_GameBoyKid3" to
            LilycoveCity_House3_EventScript_GameBoyKid3,
        "LilycoveCity_House3_EventScript_GameBoyKid1" to
            LilycoveCity_House3_EventScript_GameBoyKid1,
        "LilycoveCity_House3_EventScript_Man" to LilycoveCity_House3_EventScript_Man,
    )
