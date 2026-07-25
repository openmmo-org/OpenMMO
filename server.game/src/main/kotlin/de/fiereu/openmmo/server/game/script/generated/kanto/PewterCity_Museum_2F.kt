package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PewterCity_Museum_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PewterCity_Museum_2F_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_Museum_1F.RunningSpaceExhibitThisMonth)
}

internal object PewterCity_Museum_2F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_Museum_1F.WhatsSpecialAboutMoonStone)
}

internal object PewterCity_Museum_2F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_Museum_1F.BoughtColorTVForMoonLanding)
}

internal object PewterCity_Museum_2F_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_Museum_1F.AskedDaddyToCatchPikachu)
}

internal object PewterCity_Museum_2F_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PewterCity_Museum_1F.PikachuSoonIPromise)
}

internal object PewterCity_Museum_2F_EventScript_MoonStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(PewterCity_Museum_1F.MeteoriteThatFellOnMtMoon)
}

internal object PewterCity_Museum_2F_EventScript_SpaceShuttle : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PewterCity_Museum_1F.SpaceShuttle)
}

internal val PewterCity_Museum_2FScripts: Map<String, Script> =
    mapOf(
        "PewterCity_Museum_2F_EventScript_Scientist" to PewterCity_Museum_2F_EventScript_Scientist,
        "PewterCity_Museum_2F_EventScript_Man" to PewterCity_Museum_2F_EventScript_Man,
        "PewterCity_Museum_2F_EventScript_OldMan" to PewterCity_Museum_2F_EventScript_OldMan,
        "PewterCity_Museum_2F_EventScript_LittleGirl" to
            PewterCity_Museum_2F_EventScript_LittleGirl,
        "PewterCity_Museum_2F_EventScript_BaldingMan" to
            PewterCity_Museum_2F_EventScript_BaldingMan,
        "PewterCity_Museum_2F_EventScript_MoonStone" to PewterCity_Museum_2F_EventScript_MoonStone,
        "PewterCity_Museum_2F_EventScript_SpaceShuttle" to
            PewterCity_Museum_2F_EventScript_SpaceShuttle,
    )
