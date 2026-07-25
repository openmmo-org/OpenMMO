package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FuchsiaCity_Mart
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
 * pokemart FuchsiaCity_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object FuchsiaCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Mart_EventScript_Clerk")
}

internal object FuchsiaCity_Mart_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FuchsiaCity_Mart.DontTheyHaveSafariZonePennants)
}

internal object FuchsiaCity_Mart_EventScript_CooltrainerF : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FuchsiaCity_Mart.DidYouTryXSpeed)
}

internal val FuchsiaCity_MartScripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_Mart_EventScript_Clerk" to FuchsiaCity_Mart_EventScript_Clerk,
        "FuchsiaCity_Mart_EventScript_Gentleman" to FuchsiaCity_Mart_EventScript_Gentleman,
        "FuchsiaCity_Mart_EventScript_CooltrainerF" to FuchsiaCity_Mart_EventScript_CooltrainerF,
    )
