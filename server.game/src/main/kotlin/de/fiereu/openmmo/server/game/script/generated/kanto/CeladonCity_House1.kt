package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_House1_EventScript_RocketChief : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_House1.SlotsReelInTheDough)
}

internal object CeladonCity_House1_EventScript_Rocket1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_House1.ShippedMonsAsSlotPrizes)
}

internal object CeladonCity_House1_EventScript_Rocket2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_House1.DontTouchGameCornerPoster)
}

internal val CeladonCity_House1Scripts: Map<String, Script> =
    mapOf(
        "CeladonCity_House1_EventScript_RocketChief" to CeladonCity_House1_EventScript_RocketChief,
        "CeladonCity_House1_EventScript_Rocket1" to CeladonCity_House1_EventScript_Rocket1,
        "CeladonCity_House1_EventScript_Rocket2" to CeladonCity_House1_EventScript_Rocket2,
    )
