package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.VictoryRoad_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_EDGAR, VictoryRoad_1F_Text_EdgarIntro, VictoryRoad_1F_Text_EdgarDefeat
 * msgbox VictoryRoad_1F_Text_EdgarPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_Edgar : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_Edgar")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HOPE, VictoryRoad_1F_Text_HopeIntro, VictoryRoad_1F_Text_HopeDefeat
 * msgbox VictoryRoad_1F_Text_HopePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_Hope : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_Hope")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ALBERT, VictoryRoad_1F_Text_AlbertIntro, VictoryRoad_1F_Text_AlbertDefeat
 * msgbox VictoryRoad_1F_Text_AlbertPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_Albert : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_Albert")
}

internal object VictoryRoad_1F_EventScript_EntranceWally : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(VictoryRoad_1F.WallyPostEntranceBattle)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ELIXIR
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_1F_EventScript_ItemMaxElixir")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PP_UP
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_ItemPPUp")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WALLY_VR_2, VictoryRoad_1F_Text_WallyIntro, VictoryRoad_1F_Text_WallyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, VictoryRoad_1F_EventScript_RematchWally
 * msgbox VictoryRoad_1F_Text_WallyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_ExitWally : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_ExitWally")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KATELYNN, VictoryRoad_1F_Text_KatelynnIntro, VictoryRoad_1F_Text_KatelynnDefeat
 * msgbox VictoryRoad_1F_Text_KatelynnPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_Katelynn : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_Katelynn")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_QUINCY, VictoryRoad_1F_Text_QuincyIntro, VictoryRoad_1F_Text_QuincyDefeat
 * msgbox VictoryRoad_1F_Text_QuincyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_1F_EventScript_Quincy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_1F_EventScript_Quincy")
}

internal val VictoryRoad_1FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_1F_EventScript_Edgar" to VictoryRoad_1F_EventScript_Edgar,
        "VictoryRoad_1F_EventScript_Hope" to VictoryRoad_1F_EventScript_Hope,
        "VictoryRoad_1F_EventScript_Albert" to VictoryRoad_1F_EventScript_Albert,
        "VictoryRoad_1F_EventScript_EntranceWally" to VictoryRoad_1F_EventScript_EntranceWally,
        "VictoryRoad_1F_EventScript_ItemMaxElixir" to VictoryRoad_1F_EventScript_ItemMaxElixir,
        "VictoryRoad_1F_EventScript_ItemPPUp" to VictoryRoad_1F_EventScript_ItemPPUp,
        "VictoryRoad_1F_EventScript_ExitWally" to VictoryRoad_1F_EventScript_ExitWally,
        "VictoryRoad_1F_EventScript_Katelynn" to VictoryRoad_1F_EventScript_Katelynn,
        "VictoryRoad_1F_EventScript_Quincy" to VictoryRoad_1F_EventScript_Quincy,
    )
