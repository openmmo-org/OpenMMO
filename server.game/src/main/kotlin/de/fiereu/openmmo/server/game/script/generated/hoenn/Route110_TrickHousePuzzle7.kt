package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JOSHUA, Route110_TrickHousePuzzle7_Text_JoshuaIntro, Route110_TrickHousePuzzle7_Text_JoshuaDefeat
 * msgbox Route110_TrickHousePuzzle7_Text_JoshuaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_Joshua : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_Joshua")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ALEXIS, Route110_TrickHousePuzzle7_Text_AlexisIntro, Route110_TrickHousePuzzle7_Text_AlexisDefeat
 * msgbox Route110_TrickHousePuzzle7_Text_AlexisPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_Alexis : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_Alexis")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PATRICIA, Route110_TrickHousePuzzle7_Text_PatriciaIntro, Route110_TrickHousePuzzle7_Text_PatriciaDefeat
 * msgbox Route110_TrickHousePuzzle7_Text_PatriciaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_Patricia : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_Patricia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TROPIC_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_ItemTropicMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_ItemTropicMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ALVARO, Route110_TrickHousePuzzle7_Text_AlvaroIntro, Route110_TrickHousePuzzle7_Text_AlvaroDefeat
 * msgbox Route110_TrickHousePuzzle7_Text_AlvaroPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_Alvaro : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_Alvaro")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MARIELA, Route110_TrickHousePuzzle7_Text_MarielaIntro, Route110_TrickHousePuzzle7_Text_MarielaDefeat
 * msgbox Route110_TrickHousePuzzle7_Text_MarielaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_Mariela : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_Mariela")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_EVERETT, Route110_TrickHousePuzzle7_Text_EverettIntro, Route110_TrickHousePuzzle7_Text_EverettDefeat
 * msgbox Route110_TrickHousePuzzle7_Text_EverettPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_Everett : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_Everett")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_7_STATE, 0, Route110_TrickHousePuzzle7_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle7_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle7_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle7Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle7_EventScript_Joshua" to
            Route110_TrickHousePuzzle7_EventScript_Joshua,
        "Route110_TrickHousePuzzle7_EventScript_Alexis" to
            Route110_TrickHousePuzzle7_EventScript_Alexis,
        "Route110_TrickHousePuzzle7_EventScript_Patricia" to
            Route110_TrickHousePuzzle7_EventScript_Patricia,
        "Route110_TrickHousePuzzle7_EventScript_ItemTropicMail" to
            Route110_TrickHousePuzzle7_EventScript_ItemTropicMail,
        "Route110_TrickHousePuzzle7_EventScript_Alvaro" to
            Route110_TrickHousePuzzle7_EventScript_Alvaro,
        "Route110_TrickHousePuzzle7_EventScript_Mariela" to
            Route110_TrickHousePuzzle7_EventScript_Mariela,
        "Route110_TrickHousePuzzle7_EventScript_Everett" to
            Route110_TrickHousePuzzle7_EventScript_Everett,
        "Route110_TrickHousePuzzle7_EventScript_Scroll" to
            Route110_TrickHousePuzzle7_EventScript_Scroll,
    )
