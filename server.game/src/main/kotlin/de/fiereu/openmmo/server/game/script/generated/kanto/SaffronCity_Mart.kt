package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SaffronCity_Mart
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
 * pokemart SaffronCity_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object SaffronCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Mart_EventScript_Clerk")
}

internal object SaffronCity_Mart_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SaffronCity_Mart.MaxRepelMoreEffectiveThanSuper)
}

internal object SaffronCity_Mart_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SaffronCity_Mart.ReviveIsCostly)
}

internal val SaffronCity_MartScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_Mart_EventScript_Clerk" to SaffronCity_Mart_EventScript_Clerk,
        "SaffronCity_Mart_EventScript_Youngster" to SaffronCity_Mart_EventScript_Youngster,
        "SaffronCity_Mart_EventScript_Lass" to SaffronCity_Mart_EventScript_Lass,
    )
