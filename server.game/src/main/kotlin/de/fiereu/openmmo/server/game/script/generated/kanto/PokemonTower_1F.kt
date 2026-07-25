package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonTower_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PokemonTower_1F_EventScript_WorkerF : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonTower_1F.ErectedInMemoryOfDeadMons)
}

internal object PokemonTower_1F_EventScript_Channeler : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonTower_1F.SenseSpiritsUpToMischief)
}

internal object PokemonTower_1F_EventScript_Woman1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PokemonTower_1F.GrowlitheWhyDidYouDie)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, PokemonTower_1F_EventScript_Woman2MalePlayer
 * msgbox PokemonTower_1F_Text_ComeToPayRespectsGirl
 * release
 * end
 * ```
 */
internal object PokemonTower_1F_EventScript_Woman2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_1F_EventScript_Woman2")
}

internal object PokemonTower_1F_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PokemonTower_1F.CameToPrayForDepartedClefairy)
}

internal val PokemonTower_1FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_1F_EventScript_WorkerF" to PokemonTower_1F_EventScript_WorkerF,
        "PokemonTower_1F_EventScript_Channeler" to PokemonTower_1F_EventScript_Channeler,
        "PokemonTower_1F_EventScript_Woman1" to PokemonTower_1F_EventScript_Woman1,
        "PokemonTower_1F_EventScript_Woman2" to PokemonTower_1F_EventScript_Woman2,
        "PokemonTower_1F_EventScript_BaldingMan" to PokemonTower_1F_EventScript_BaldingMan,
    )
