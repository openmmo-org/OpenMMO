package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_Lounge3
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MET_BATTLE_FRONTIER_GAMBLER, BattleFrontier_Lounge3_EventScript_AlreadyMetGambler
 * call BattleFrontier_Lounge3_EventScript_CountSilverSymbols
 * goto_if_le VAR_0x8004, 2, BattleFrontier_Lounge3_EventScript_NotEnoughSilverSymbols
 * setflag FLAG_MET_BATTLE_FRONTIER_GAMBLER
 * msgbox BattleFrontier_Lounge3_Text_YouLookToughExplainGambling, MSGBOX_DEFAULT
 * goto BattleFrontier_Lounge3_EventScript_AskToEnterChallenge
 * end
 * ```
 */
internal object BattleFrontier_Lounge3_EventScript_Gambler : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_Lounge3_EventScript_Gambler")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_Lounge3_Text_TrainerGoodButRattled, MSGBOX_DEFAULT
 * goto BattleFrontier_Lounge3_EventScript_FaceOriginalDirection
 * end
 * ```
 */
internal object BattleFrontier_Lounge3_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_Lounge3_EventScript_FatMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_Lounge3_Text_BackedWrongTrainer, MSGBOX_DEFAULT
 * goto BattleFrontier_Lounge3_EventScript_FaceOriginalDirection
 * end
 * ```
 */
internal object BattleFrontier_Lounge3_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_Lounge3_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_Lounge3_Text_KnowWinnerWhenISeeOne, MSGBOX_DEFAULT
 * goto BattleFrontier_Lounge3_EventScript_FaceOriginalDirection
 * end
 * ```
 */
internal object BattleFrontier_Lounge3_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_Lounge3_EventScript_PokefanF")
}

internal object BattleFrontier_Lounge3_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge3.ShouldBeTakingChallenges)
}

internal val BattleFrontier_Lounge3Scripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_Lounge3_EventScript_Gambler" to BattleFrontier_Lounge3_EventScript_Gambler,
        "BattleFrontier_Lounge3_EventScript_FatMan" to BattleFrontier_Lounge3_EventScript_FatMan,
        "BattleFrontier_Lounge3_EventScript_Woman" to BattleFrontier_Lounge3_EventScript_Woman,
        "BattleFrontier_Lounge3_EventScript_PokefanF" to
            BattleFrontier_Lounge3_EventScript_PokefanF,
        "BattleFrontier_Lounge3_EventScript_Man" to BattleFrontier_Lounge3_EventScript_Man,
    )
