package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CORA, Route110_TrickHousePuzzle4_Text_CoraIntro, Route110_TrickHousePuzzle4_Text_CoraDefeat
 * msgbox Route110_TrickHousePuzzle4_Text_CoraPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle4_EventScript_Cora : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle4_EventScript_Cora")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PAULA, Route110_TrickHousePuzzle4_Text_PaulaIntro, Route110_TrickHousePuzzle4_Text_PaulaDefeat
 * msgbox Route110_TrickHousePuzzle4_Text_PaulaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle4_EventScript_Paula : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle4_EventScript_Paula")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_YUJI, Route110_TrickHousePuzzle4_Text_YujiIntro, Route110_TrickHousePuzzle4_Text_YujiDefeat
 * msgbox Route110_TrickHousePuzzle4_Text_YujiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle4_EventScript_Yuji : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle4_EventScript_Yuji")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MECH_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle4_EventScript_ItemMechMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle4_EventScript_ItemMechMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_4_STATE, 0, Route110_TrickHousePuzzle4_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle4_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle4_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle4Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle4_EventScript_Cora" to
            Route110_TrickHousePuzzle4_EventScript_Cora,
        "Route110_TrickHousePuzzle4_EventScript_Paula" to
            Route110_TrickHousePuzzle4_EventScript_Paula,
        "Route110_TrickHousePuzzle4_EventScript_Yuji" to
            Route110_TrickHousePuzzle4_EventScript_Yuji,
        "Route110_TrickHousePuzzle4_EventScript_ItemMechMail" to
            Route110_TrickHousePuzzle4_EventScript_ItemMechMail,
        "Route110_TrickHousePuzzle4_EventScript_Scroll" to
            Route110_TrickHousePuzzle4_EventScript_Scroll,
    )
