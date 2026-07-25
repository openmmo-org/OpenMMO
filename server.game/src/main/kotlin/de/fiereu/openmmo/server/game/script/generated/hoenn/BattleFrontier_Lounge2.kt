package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_Lounge2
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MET_BATTLE_FRONTIER_MANIAC, BattleFrontier_Lounge2_EventScript_AlreadyMetManiac
 * setflag FLAG_MET_BATTLE_FRONTIER_MANIAC
 * msgbox BattleFrontier_Lounge2_Text_FrontierManiacIntro, MSGBOX_DEFAULT
 * goto BattleFrontier_Lounge2_EventScript_GiveAdvice
 * end
 * ```
 */
internal object BattleFrontier_Lounge2_EventScript_FrontierManiac : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_Lounge2_EventScript_FrontierManiac")
}

internal object BattleFrontier_Lounge2_EventScript_Maniac1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BattleFrontier_Lounge2.NewsGatheringPower)
}

internal object BattleFrontier_Lounge2_EventScript_Maniac2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge2.AmazingPowersOfObservation)
}

internal object BattleFrontier_Lounge2_EventScript_TriathleteF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge2.ThisPlaceIsScaringMe)
}

internal object BattleFrontier_Lounge2_EventScript_Maniac3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge2.AmazingPowerOfPersuasion)
}

internal val BattleFrontier_Lounge2Scripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_Lounge2_EventScript_FrontierManiac" to
            BattleFrontier_Lounge2_EventScript_FrontierManiac,
        "BattleFrontier_Lounge2_EventScript_Maniac1" to BattleFrontier_Lounge2_EventScript_Maniac1,
        "BattleFrontier_Lounge2_EventScript_Maniac2" to BattleFrontier_Lounge2_EventScript_Maniac2,
        "BattleFrontier_Lounge2_EventScript_TriathleteF" to
            BattleFrontier_Lounge2_EventScript_TriathleteF,
        "BattleFrontier_Lounge2_EventScript_Maniac3" to BattleFrontier_Lounge2_EventScript_Maniac3,
    )
