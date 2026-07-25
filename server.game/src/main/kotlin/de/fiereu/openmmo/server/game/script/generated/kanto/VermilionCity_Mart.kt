package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VermilionCity_Mart
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
 * pokemart VermilionCity_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object VermilionCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VermilionCity_Mart_EventScript_Clerk")
}

internal object VermilionCity_Mart_EventScript_CooltrainerF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VermilionCity_Mart.MonsGoodOrBadDependingOnTrainer)
}

internal object VermilionCity_Mart_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VermilionCity_Mart.TeamRocketAreWickedPeople)
}

internal val VermilionCity_MartScripts: Map<String, Script> =
    mapOf(
        "VermilionCity_Mart_EventScript_Clerk" to VermilionCity_Mart_EventScript_Clerk,
        "VermilionCity_Mart_EventScript_CooltrainerF" to
            VermilionCity_Mart_EventScript_CooltrainerF,
        "VermilionCity_Mart_EventScript_BaldingMan" to VermilionCity_Mart_EventScript_BaldingMan,
    )
