package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_Kitchen
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_Kitchen_EventScript_Chef1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_Kitchen.BusyOutOfTheWay)
}

internal object SSAnne_Kitchen_EventScript_Chef2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_Kitchen.SawOddBerryInTrash)
}

internal object SSAnne_Kitchen_EventScript_Chef3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_Kitchen.SoBusyImDizzy)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SSAnne_Kitchen_Text_IAmLeChefMainCourseIs
 * random 3
 * copyvar VAR_0x8008, VAR_RESULT
 * call_if_eq VAR_0x8008, 0, SSAnne_Kitchen_EventScript_SalmonDuSalad
 * call_if_eq VAR_0x8008, 1, SSAnne_Kitchen_EventScript_EelsAuBarbecue
 * call_if_eq VAR_0x8008, 2, SSAnne_Kitchen_EventScript_PrimeBeefsteak
 * release
 * end
 * ```
 */
internal object SSAnne_Kitchen_EventScript_Chef4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_Kitchen_EventScript_Chef4")
}

internal object SSAnne_Kitchen_EventScript_Chef5 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_Kitchen.PeelSpudsEveryDay)
}

internal object SSAnne_Kitchen_EventScript_Chef6 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_Kitchen.HearAboutSnorlaxItsAGlutton)
}

internal object SSAnne_Kitchen_EventScript_Chef7 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSAnne_Kitchen.OnlyGetToPeelOnions)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GREAT_BALL
 * end
 * ```
 */
internal object SSAnne_Kitchen_EventScript_ItemGreatBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SSAnne_Kitchen_EventScript_ItemGreatBall")
}

internal val SSAnne_KitchenScripts: Map<String, Script> =
    mapOf(
        "SSAnne_Kitchen_EventScript_Chef1" to SSAnne_Kitchen_EventScript_Chef1,
        "SSAnne_Kitchen_EventScript_Chef2" to SSAnne_Kitchen_EventScript_Chef2,
        "SSAnne_Kitchen_EventScript_Chef3" to SSAnne_Kitchen_EventScript_Chef3,
        "SSAnne_Kitchen_EventScript_Chef4" to SSAnne_Kitchen_EventScript_Chef4,
        "SSAnne_Kitchen_EventScript_Chef5" to SSAnne_Kitchen_EventScript_Chef5,
        "SSAnne_Kitchen_EventScript_Chef6" to SSAnne_Kitchen_EventScript_Chef6,
        "SSAnne_Kitchen_EventScript_Chef7" to SSAnne_Kitchen_EventScript_Chef7,
        "SSAnne_Kitchen_EventScript_ItemGreatBall" to SSAnne_Kitchen_EventScript_ItemGreatBall,
    )
