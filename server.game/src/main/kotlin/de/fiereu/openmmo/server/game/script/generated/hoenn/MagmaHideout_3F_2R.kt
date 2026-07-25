package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MAGMA_HIDEOUT_10, MagmaHideout_3F_2R_Text_Grunt10Intro, MagmaHideout_3F_2R_Text_Grunt10Defeat
 * msgbox MagmaHideout_3F_2R_Text_Grunt10PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MagmaHideout_3F_2R_EventScript_Grunt10 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MagmaHideout_3F_2R_EventScript_Grunt10")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PP_MAX
 * end
 * ```
 */
internal object MagmaHideout_3F_2R_EventScript_ItemPPMax : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MagmaHideout_3F_2R_EventScript_ItemPPMax")
}

internal val MagmaHideout_3F_2RScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_3F_2R_EventScript_Grunt10" to MagmaHideout_3F_2R_EventScript_Grunt10,
        "MagmaHideout_3F_2R_EventScript_ItemPPMax" to MagmaHideout_3F_2R_EventScript_ItemPPMax,
    )
