package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.TwoIsland_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call EventScript_PkmnCenterNurse
 * release
 * end
 * ```
 */
internal object TwoIsland_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TwoIsland_PokemonCenter_1F_EventScript_Nurse")
}

internal object TwoIsland_PokemonCenter_1F_EventScript_GBAKid : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(TwoIsland_PokemonCenter_1F.HaveYouVisitedGameCorner)
}

internal object TwoIsland_PokemonCenter_1F_EventScript_BugCatcher : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(TwoIsland_PokemonCenter_1F.OldLadyLivesOutOnCape)
}

internal val TwoIsland_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "TwoIsland_PokemonCenter_1F_EventScript_Nurse" to
            TwoIsland_PokemonCenter_1F_EventScript_Nurse,
        "TwoIsland_PokemonCenter_1F_EventScript_GBAKid" to
            TwoIsland_PokemonCenter_1F_EventScript_GBAKid,
        "TwoIsland_PokemonCenter_1F_EventScript_BugCatcher" to
            TwoIsland_PokemonCenter_1F_EventScript_BugCatcher,
    )
