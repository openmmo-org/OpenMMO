package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_Lounge8
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object BattleFrontier_Lounge8_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge8.ToldMeIHaveTalentForBattling)
}

internal object BattleFrontier_Lounge8_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BattleFrontier_Lounge8.WhatATrainerNeeds)
}

internal object BattleFrontier_Lounge8_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge8.KnowAboutFrontierBrains)
}

internal val BattleFrontier_Lounge8Scripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_Lounge8_EventScript_NinjaBoy" to
            BattleFrontier_Lounge8_EventScript_NinjaBoy,
        "BattleFrontier_Lounge8_EventScript_Man" to BattleFrontier_Lounge8_EventScript_Man,
        "BattleFrontier_Lounge8_EventScript_Woman" to BattleFrontier_Lounge8_EventScript_Woman,
    )
