package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SALLY, Route110_TrickHousePuzzle1_Text_SallyIntro, Route110_TrickHousePuzzle1_Text_SallyDefeat
 * msgbox Route110_TrickHousePuzzle1_Text_SallyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle1_EventScript_Sally : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle1_EventScript_Sally")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_EDDIE, Route110_TrickHousePuzzle1_Text_EddieIntro, Route110_TrickHousePuzzle1_Text_EddieDefeat
 * msgbox Route110_TrickHousePuzzle1_Text_EddiePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle1_EventScript_Eddie : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle1_EventScript_Eddie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ROBIN, Route110_TrickHousePuzzle1_Text_RobinIntro, Route110_TrickHousePuzzle1_Text_RobinDefeat
 * msgbox Route110_TrickHousePuzzle1_Text_RobinPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle1_EventScript_Robin : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle1_EventScript_Robin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ORANGE_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle1_EventScript_ItemOrangeMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle1_EventScript_ItemOrangeMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_1_STATE, 0, Route110_TrickHousePuzzle1_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle1_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle1_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle1Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle1_EventScript_Sally" to
            Route110_TrickHousePuzzle1_EventScript_Sally,
        "Route110_TrickHousePuzzle1_EventScript_Eddie" to
            Route110_TrickHousePuzzle1_EventScript_Eddie,
        "Route110_TrickHousePuzzle1_EventScript_Robin" to
            Route110_TrickHousePuzzle1_EventScript_Robin,
        "Route110_TrickHousePuzzle1_EventScript_ItemOrangeMail" to
            Route110_TrickHousePuzzle1_EventScript_ItemOrangeMail,
        "Route110_TrickHousePuzzle1_EventScript_Scroll" to
            Route110_TrickHousePuzzle1_EventScript_Scroll,
    )
