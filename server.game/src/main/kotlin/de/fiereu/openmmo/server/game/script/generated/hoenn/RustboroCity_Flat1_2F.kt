package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_Flat1_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object RustboroCity_Flat1_2F_EventScript_WaldasMom : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_Flat1_2F.ComingUpWithMealsIsHard)
}

internal object RustboroCity_Flat1_2F_EventScript_PokeDoll : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(RustboroCity_Flat1_2F.ItsAPokemonPlushDoll)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * specialvar VAR_RESULT, TryBufferWaldaPhrase
 * goto_if_eq VAR_RESULT, FALSE, RustboroCity_Flat1_2F_EventScript_WaldasDadFirstPhrase
 * goto_if_eq VAR_RESULT, TRUE, RustboroCity_Flat1_2F_EventScript_WaldasDadNewPhrase
 * ```
 */
internal object RustboroCity_Flat1_2F_EventScript_WaldasDad : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_Flat1_2F_EventScript_WaldasDad")
}

internal val RustboroCity_Flat1_2FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_Flat1_2F_EventScript_WaldasMom" to
            RustboroCity_Flat1_2F_EventScript_WaldasMom,
        "RustboroCity_Flat1_2F_EventScript_PokeDoll" to RustboroCity_Flat1_2F_EventScript_PokeDoll,
        "RustboroCity_Flat1_2F_EventScript_WaldasDad" to
            RustboroCity_Flat1_2F_EventScript_WaldasDad,
    )
