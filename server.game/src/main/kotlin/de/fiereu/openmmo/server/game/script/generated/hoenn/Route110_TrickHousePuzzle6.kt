package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SOPHIA, Route110_TrickHousePuzzle6_Text_SophiaIntro, Route110_TrickHousePuzzle6_Text_SophiaDefeat
 * msgbox Route110_TrickHousePuzzle6_Text_SophiaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle6_EventScript_Sophia : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle6_EventScript_Sophia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BENNY, Route110_TrickHousePuzzle6_Text_BennyIntro, Route110_TrickHousePuzzle6_Text_BennyDefeat
 * msgbox Route110_TrickHousePuzzle6_Text_BennyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle6_EventScript_Benny : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle6_EventScript_Benny")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SEBASTIAN, Route110_TrickHousePuzzle6_Text_SebastianIntro, Route110_TrickHousePuzzle6_Text_SebastianDefeat
 * msgbox Route110_TrickHousePuzzle6_Text_SebastianPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle6_EventScript_Sebastian : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle6_EventScript_Sebastian")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GLITTER_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle6_EventScript_ItemGlitterMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle6_EventScript_ItemGlitterMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_6_STATE, 0, Route110_TrickHousePuzzle6_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle6_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle6_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle6Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle6_EventScript_Sophia" to
            Route110_TrickHousePuzzle6_EventScript_Sophia,
        "Route110_TrickHousePuzzle6_EventScript_Benny" to
            Route110_TrickHousePuzzle6_EventScript_Benny,
        "Route110_TrickHousePuzzle6_EventScript_Sebastian" to
            Route110_TrickHousePuzzle6_EventScript_Sebastian,
        "Route110_TrickHousePuzzle6_EventScript_ItemGlitterMail" to
            Route110_TrickHousePuzzle6_EventScript_ItemGlitterMail,
        "Route110_TrickHousePuzzle6_EventScript_Scroll" to
            Route110_TrickHousePuzzle6_EventScript_Scroll,
    )
