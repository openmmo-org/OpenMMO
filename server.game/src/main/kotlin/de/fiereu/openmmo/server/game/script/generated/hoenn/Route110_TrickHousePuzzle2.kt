package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TED, Route110_TrickHousePuzzle2_Text_TedIntro, Route110_TrickHousePuzzle2_Text_TedDefeat
 * msgbox Route110_TrickHousePuzzle2_Text_TedPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle2_EventScript_Ted : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle2_EventScript_Ted")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PAUL, Route110_TrickHousePuzzle2_Text_PaulIntro, Route110_TrickHousePuzzle2_Text_PaulDefeat
 * msgbox Route110_TrickHousePuzzle2_Text_PaulPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle2_EventScript_Paul : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle2_EventScript_Paul")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GEORGIA, Route110_TrickHousePuzzle2_Text_GeorgiaIntro, Route110_TrickHousePuzzle2_Text_GeorgiaDefeat
 * msgbox Route110_TrickHousePuzzle2_Text_GeorgiaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle2_EventScript_Georgia : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle2_EventScript_Georgia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_WAVE_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle2_EventScript_ItemWaveMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle2_EventScript_ItemWaveMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HARBOR_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle2_EventScript_ItemHarborMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle2_EventScript_ItemHarborMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_2_STATE, 0, Route110_TrickHousePuzzle2_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle2_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle2_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle2Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle2_EventScript_Ted" to Route110_TrickHousePuzzle2_EventScript_Ted,
        "Route110_TrickHousePuzzle2_EventScript_Paul" to
            Route110_TrickHousePuzzle2_EventScript_Paul,
        "Route110_TrickHousePuzzle2_EventScript_Georgia" to
            Route110_TrickHousePuzzle2_EventScript_Georgia,
        "Route110_TrickHousePuzzle2_EventScript_ItemWaveMail" to
            Route110_TrickHousePuzzle2_EventScript_ItemWaveMail,
        "Route110_TrickHousePuzzle2_EventScript_ItemHarborMail" to
            Route110_TrickHousePuzzle2_EventScript_ItemHarborMail,
        "Route110_TrickHousePuzzle2_EventScript_Scroll" to
            Route110_TrickHousePuzzle2_EventScript_Scroll,
    )
