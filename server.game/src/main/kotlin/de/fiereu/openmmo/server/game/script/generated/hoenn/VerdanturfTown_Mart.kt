package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.VerdanturfTown_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart VerdanturfTown_Mart_Pokemart
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object VerdanturfTown_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VerdanturfTown_Mart_EventScript_Clerk")
}

internal object VerdanturfTown_Mart_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(VerdanturfTown_Mart.XSpecialIsCrucial)
}

internal object VerdanturfTown_Mart_EventScript_ExpertF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VerdanturfTown_Mart.NoStrategyGuidesForBattleTent)
}

internal object VerdanturfTown_Mart_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VerdanturfTown_Mart.NestBallOnWeakenedPokemon)
}

internal val VerdanturfTown_MartScripts: Map<String, Script> =
    mapOf(
        "VerdanturfTown_Mart_EventScript_Clerk" to VerdanturfTown_Mart_EventScript_Clerk,
        "VerdanturfTown_Mart_EventScript_Boy" to VerdanturfTown_Mart_EventScript_Boy,
        "VerdanturfTown_Mart_EventScript_ExpertF" to VerdanturfTown_Mart_EventScript_ExpertF,
        "VerdanturfTown_Mart_EventScript_Lass" to VerdanturfTown_Mart_EventScript_Lass,
    )
