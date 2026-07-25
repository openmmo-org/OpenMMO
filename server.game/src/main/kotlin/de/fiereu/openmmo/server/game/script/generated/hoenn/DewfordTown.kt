package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.DewfordTown
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object DewfordTown_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(DewfordTown.TinyIslandCommunity)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_DELIVERED_STEVEN_LETTER, DewfordTown_EventScript_ReturnToPetalburgPrompt
 * message DewfordTown_Text_WhereAreWeBound
 * waitmessage
 * multichoicedefault 21, 6, MULTI_BRINEY_ON_DEWFORD, 2, FALSE
 * switch VAR_RESULT
 * case 0, DewfordTown_EventScript_ChoosePetalburg
 * case 1, DewfordTown_EventScript_ChooseSlateport
 * case 2, DewfordTown_EventScript_CancelSailSelect
 * case MULTI_B_PRESSED, DewfordTown_EventScript_CancelSailSelect
 * end
 * ```
 */
internal object DewfordTown_EventScript_Briney : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_EventScript_Briney")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_OLD_ROD, DewfordTown_EventScript_HowsFishing
 * msgbox DewfordTown_Text_GettingItchToFish, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, DewfordTown_EventScript_GiveOldRod
 * goto_if_eq VAR_RESULT, NO, DewfordTown_EventScript_NotGettingItchToFish
 * end
 * ```
 */
internal object DewfordTown_EventScript_OldRodFisherman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port DewfordTown_EventScript_OldRodFisherman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Common_EventScript_BufferTrendyPhrase
 * msgbox DewfordTown_Text_XIsTheBiggestHappeningThingRight, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, DewfordTown_EventScript_ConfirmTrendyPhrase
 * goto_if_eq VAR_RESULT, NO, DewfordTown_EventScript_RejectTrendyPhrase
 * end
 * ```
 */
internal object DewfordTown_EventScript_TrendyPhraseBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port DewfordTown_EventScript_TrendyPhraseBoy")
}

internal object DewfordTown_EventScript_TownSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(DewfordTown.TownSign)
}

internal object DewfordTown_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(DewfordTown.GymSign)
}

internal object DewfordTown_EventScript_HallSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(DewfordTown.HallSign)
}

internal val DewfordTownScripts: Map<String, Script> =
    mapOf(
        "DewfordTown_EventScript_Woman" to DewfordTown_EventScript_Woman,
        "DewfordTown_EventScript_Briney" to DewfordTown_EventScript_Briney,
        "DewfordTown_EventScript_OldRodFisherman" to DewfordTown_EventScript_OldRodFisherman,
        "DewfordTown_EventScript_TrendyPhraseBoy" to DewfordTown_EventScript_TrendyPhraseBoy,
        "DewfordTown_EventScript_TownSign" to DewfordTown_EventScript_TownSign,
        "DewfordTown_EventScript_GymSign" to DewfordTown_EventScript_GymSign,
        "DewfordTown_EventScript_HallSign" to DewfordTown_EventScript_HallSign,
    )
