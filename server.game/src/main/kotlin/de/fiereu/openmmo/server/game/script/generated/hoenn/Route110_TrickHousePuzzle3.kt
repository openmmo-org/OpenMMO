package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JUSTIN, Route110_TrickHousePuzzle3_Text_JustinIntro, Route110_TrickHousePuzzle3_Text_JustinDefeat
 * msgbox Route110_TrickHousePuzzle3_Text_JustinPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle3_EventScript_Justin : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle3_EventScript_Justin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MARTHA, Route110_TrickHousePuzzle3_Text_MarthaIntro, Route110_TrickHousePuzzle3_Text_MarthaDefeat
 * msgbox Route110_TrickHousePuzzle3_Text_MarthaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle3_EventScript_Martha : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle3_EventScript_Martha")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ALAN, Route110_TrickHousePuzzle3_Text_AlanIntro, Route110_TrickHousePuzzle3_Text_AlanDefeat
 * msgbox Route110_TrickHousePuzzle3_Text_AlanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle3_EventScript_Alan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle3_EventScript_Alan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_WOOD_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle3_EventScript_ItemWoodMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle3_EventScript_ItemWoodMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SHADOW_MAIL
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle3_EventScript_ItemShadowMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle3_EventScript_ItemShadowMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_TRICK_HOUSE_PUZZLE_3_STATE, 0, Route110_TrickHousePuzzle3_EventScript_FoundScroll
 * goto Route110_TrickHousePuzzle_EventScript_ReadScrollAgain
 * end
 * ```
 */
internal object Route110_TrickHousePuzzle3_EventScript_Scroll : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHousePuzzle3_EventScript_Scroll")
}

internal val Route110_TrickHousePuzzle3Scripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHousePuzzle3_EventScript_Justin" to
            Route110_TrickHousePuzzle3_EventScript_Justin,
        "Route110_TrickHousePuzzle3_EventScript_Martha" to
            Route110_TrickHousePuzzle3_EventScript_Martha,
        "Route110_TrickHousePuzzle3_EventScript_Alan" to
            Route110_TrickHousePuzzle3_EventScript_Alan,
        "Route110_TrickHousePuzzle3_EventScript_ItemWoodMail" to
            Route110_TrickHousePuzzle3_EventScript_ItemWoodMail,
        "Route110_TrickHousePuzzle3_EventScript_ItemShadowMail" to
            Route110_TrickHousePuzzle3_EventScript_ItemShadowMail,
        "Route110_TrickHousePuzzle3_EventScript_Scroll" to
            Route110_TrickHousePuzzle3_EventScript_Scroll,
    )
