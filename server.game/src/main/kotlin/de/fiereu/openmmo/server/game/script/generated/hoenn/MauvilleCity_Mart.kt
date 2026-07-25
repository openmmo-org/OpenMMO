package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MauvilleCity_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart MauvilleCity_Mart_Pokemart
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MauvilleCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Mart_EventScript_Clerk")
}

internal object MauvilleCity_Mart_EventScript_ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MauvilleCity_Mart.ItemsToTemporarilyElevateStats)
}

internal object MauvilleCity_Mart_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MauvilleCity_Mart.DecisionsDetermineBattle)
}

internal val MauvilleCity_MartScripts: Map<String, Script> =
    mapOf(
        "MauvilleCity_Mart_EventScript_Clerk" to MauvilleCity_Mart_EventScript_Clerk,
        "MauvilleCity_Mart_EventScript_ExpertM" to MauvilleCity_Mart_EventScript_ExpertM,
        "MauvilleCity_Mart_EventScript_Man" to MauvilleCity_Mart_EventScript_Man,
    )
