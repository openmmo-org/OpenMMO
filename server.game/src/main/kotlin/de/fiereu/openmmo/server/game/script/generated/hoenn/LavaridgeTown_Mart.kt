package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LavaridgeTown_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart LavaridgeTown_Mart_Pokemart
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LavaridgeTown_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LavaridgeTown_Mart_EventScript_Clerk")
}

internal object LavaridgeTown_Mart_EventScript_ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LavaridgeTown_Mart.XSpeedFirstStrike)
}

internal object LavaridgeTown_Mart_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LavaridgeTown_Mart.LocalSpecialtyOnMtChimney)
}

internal val LavaridgeTown_MartScripts: Map<String, Script> =
    mapOf(
        "LavaridgeTown_Mart_EventScript_Clerk" to LavaridgeTown_Mart_EventScript_Clerk,
        "LavaridgeTown_Mart_EventScript_ExpertM" to LavaridgeTown_Mart_EventScript_ExpertM,
        "LavaridgeTown_Mart_EventScript_OldWoman" to LavaridgeTown_Mart_EventScript_OldWoman,
    )
