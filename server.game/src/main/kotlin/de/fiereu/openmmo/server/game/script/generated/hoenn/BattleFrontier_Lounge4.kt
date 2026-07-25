package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_Lounge4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object BattleFrontier_Lounge4_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge4.WonderIfInterviewsAiring)
}

internal object BattleFrontier_Lounge4_EventScript_Cook : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge4.IfIOpenedRestaurantHere)
}

internal object BattleFrontier_Lounge4_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge4.NeedBreatherAfterBattles)
}

internal val BattleFrontier_Lounge4Scripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_Lounge4_EventScript_Woman" to BattleFrontier_Lounge4_EventScript_Woman,
        "BattleFrontier_Lounge4_EventScript_Cook" to BattleFrontier_Lounge4_EventScript_Cook,
        "BattleFrontier_Lounge4_EventScript_Man" to BattleFrontier_Lounge4_EventScript_Man,
    )
