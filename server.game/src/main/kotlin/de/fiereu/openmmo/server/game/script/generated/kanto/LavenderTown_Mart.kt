package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.LavenderTown_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart LavenderTown_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object LavenderTown_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LavenderTown_Mart_EventScript_Clerk")
}

internal object LavenderTown_Mart_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LavenderTown_Mart.SearchingForStatRaiseItems)
}

internal object LavenderTown_Mart_EventScript_Rocker : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LavenderTown_Mart.DidYouBuyRevives)
}

internal object LavenderTown_Mart_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LavenderTown_Mart.TrainerDuosCanChallengeYou)
}

internal val LavenderTown_MartScripts: Map<String, Script> =
    mapOf(
        "LavenderTown_Mart_EventScript_Clerk" to LavenderTown_Mart_EventScript_Clerk,
        "LavenderTown_Mart_EventScript_BaldingMan" to LavenderTown_Mart_EventScript_BaldingMan,
        "LavenderTown_Mart_EventScript_Rocker" to LavenderTown_Mart_EventScript_Rocker,
        "LavenderTown_Mart_EventScript_Youngster" to LavenderTown_Mart_EventScript_Youngster,
    )
