package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MossdeepCity_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart MossdeepCity_Mart_Pokemart
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Mart_EventScript_Clerk")
}

internal object MossdeepCity_Mart_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity_Mart.ReviveIsFantastic)
}

internal object MossdeepCity_Mart_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity_Mart.MaxRepelLastsLongest)
}

internal object MossdeepCity_Mart_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity_Mart.NetAndDiveBallsRare)
}

internal val MossdeepCity_MartScripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_Mart_EventScript_Clerk" to MossdeepCity_Mart_EventScript_Clerk,
        "MossdeepCity_Mart_EventScript_Woman" to MossdeepCity_Mart_EventScript_Woman,
        "MossdeepCity_Mart_EventScript_Boy" to MossdeepCity_Mart_EventScript_Boy,
        "MossdeepCity_Mart_EventScript_Sailor" to MossdeepCity_Mart_EventScript_Sailor,
    )
