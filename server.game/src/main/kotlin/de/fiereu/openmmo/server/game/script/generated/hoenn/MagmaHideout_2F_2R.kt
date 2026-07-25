package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MAGMA_HIDEOUT_8, MagmaHideout_2F_2R_Text_Grunt8Intro, MagmaHideout_2F_2R_Text_Grunt8Defeat
 * msgbox MagmaHideout_2F_2R_Text_Grunt8PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MagmaHideout_2F_2R_EventScript_Grunt8 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MagmaHideout_2F_2R_EventScript_Grunt8")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MAGMA_HIDEOUT_7, MagmaHideout_2F_2R_Text_Grunt7Intro, MagmaHideout_2F_2R_Text_Grunt7Defeat
 * msgbox MagmaHideout_2F_2R_Text_Grunt7PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MagmaHideout_2F_2R_EventScript_Grunt7 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MagmaHideout_2F_2R_EventScript_Grunt7")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ELIXIR
 * end
 * ```
 */
internal object MagmaHideout_2F_2R_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MagmaHideout_2F_2R_EventScript_ItemMaxElixir")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MAGMA_HIDEOUT_6, MagmaHideout_2F_2R_Text_Grunt6Intro, MagmaHideout_2F_2R_Text_Grunt6Defeat
 * msgbox MagmaHideout_2F_2R_Text_Grunt6PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MagmaHideout_2F_2R_EventScript_Grunt6 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MagmaHideout_2F_2R_EventScript_Grunt6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MAGMA_HIDEOUT_15, MagmaHideout_2F_2R_Text_Grunt15Intro, MagmaHideout_2F_2R_Text_Grunt15Defeat
 * msgbox MagmaHideout_2F_2R_Text_Grunt15PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MagmaHideout_2F_2R_EventScript_Grunt15 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MagmaHideout_2F_2R_EventScript_Grunt15")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_RESTORE
 * end
 * ```
 */
internal object MagmaHideout_2F_2R_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MagmaHideout_2F_2R_EventScript_ItemFullRestore")
}

internal val MagmaHideout_2F_2RScripts: Map<String, Script> =
    mapOf(
        "MagmaHideout_2F_2R_EventScript_Grunt8" to MagmaHideout_2F_2R_EventScript_Grunt8,
        "MagmaHideout_2F_2R_EventScript_Grunt7" to MagmaHideout_2F_2R_EventScript_Grunt7,
        "MagmaHideout_2F_2R_EventScript_ItemMaxElixir" to
            MagmaHideout_2F_2R_EventScript_ItemMaxElixir,
        "MagmaHideout_2F_2R_EventScript_Grunt6" to MagmaHideout_2F_2R_EventScript_Grunt6,
        "MagmaHideout_2F_2R_EventScript_Grunt15" to MagmaHideout_2F_2R_EventScript_Grunt15,
        "MagmaHideout_2F_2R_EventScript_ItemFullRestore" to
            MagmaHideout_2F_2R_EventScript_ItemFullRestore,
    )
