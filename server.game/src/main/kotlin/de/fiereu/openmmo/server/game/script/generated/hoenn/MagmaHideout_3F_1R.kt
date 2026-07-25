package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MAGMA_HIDEOUT_9, MagmaHideout_3F_1R_Text_Grunt9Intro, MagmaHideout_3F_1R_Text_Grunt9Defeat
 * msgbox MagmaHideout_3F_1R_Text_Grunt9PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MagmaHideout_3F_1R_EventScript_Grunt9 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MagmaHideout_3F_1R_EventScript_Grunt9")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MAGMA_HIDEOUT_16, MagmaHideout_3F_1R_Text_Grunt16Intro, MagmaHideout_3F_1R_Text_Grunt16Defeat
 * msgbox MagmaHideout_3F_1R_Text_Grunt16PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MagmaHideout_3F_1R_EventScript_Grunt16 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MagmaHideout_3F_1R_EventScript_Grunt16")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object MagmaHideout_3F_1R_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MagmaHideout_3F_1R_EventScript_ItemNugget")
}

internal val MagmaHideout_3F_1RScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_3F_1R_EventScript_Grunt9" to MagmaHideout_3F_1R_EventScript_Grunt9,
        "MagmaHideout_3F_1R_EventScript_Grunt16" to MagmaHideout_3F_1R_EventScript_Grunt16,
        "MagmaHideout_3F_1R_EventScript_ItemNugget" to MagmaHideout_3F_1R_EventScript_ItemNugget,
    )
