package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_VINCENT, Route110_TrickHousePuzzle8_Text_VincentIntro, Route110_TrickHousePuzzle8_Text_VincentDefeat
 * msgbox Route110_TrickHousePuzzle8_Text_VincentPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle8_EventScript_Vincent : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle8_EventScript_Vincent")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LEROY, Route110_TrickHousePuzzle8_Text_LeroyIntro, Route110_TrickHousePuzzle8_Text_LeroyDefeat
 * msgbox Route110_TrickHousePuzzle8_Text_LeroyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle8_EventScript_Leroy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle8_EventScript_Leroy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KEIRA, Route110_TrickHousePuzzle8_Text_KeiraIntro, Route110_TrickHousePuzzle8_Text_KeiraDefeat
 * msgbox Route110_TrickHousePuzzle8_Text_KeiraPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle8_EventScript_Keira : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle8_EventScript_Keira")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BEAD_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle8_EventScript_ItemBeadMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle8_EventScript_ItemBeadMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_8_STATE, 0, Route110_TrickHousePuzzle8_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle8_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle8_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle8Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle8_EventScript_Vincent" to
            Route110_TrickHousePuzzle8_EventScript_Vincent,
        "Route110_TrickHousePuzzle8_EventScript_Leroy" to
            Route110_TrickHousePuzzle8_EventScript_Leroy,
        "Route110_TrickHousePuzzle8_EventScript_Keira" to
            Route110_TrickHousePuzzle8_EventScript_Keira,
        "Route110_TrickHousePuzzle8_EventScript_ItemBeadMail" to
            Route110_TrickHousePuzzle8_EventScript_ItemBeadMail,
        "Route110_TrickHousePuzzle8_EventScript_Scroll" to
            Route110_TrickHousePuzzle8_EventScript_Scroll,
    )
