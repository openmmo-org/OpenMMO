package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CinnabarIsland_PokemonLab_ResearchRoom
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto EventScript_MetronomeTutor
 * end
 * ```
 */
internal object CinnabarIsland_PokemonLab_ResearchRoom_EventScript_MetronomeTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_PokemonLab_ResearchRoom_EventScript_MetronomeTutor")
}

internal object CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CinnabarIsland_PokemonLab_ResearchRoom.EeveeCanEvolveIntroThreeMons)
}

internal object CinnabarIsland_PokemonLab_ResearchRoom_EventScript_AmberPipe : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CinnabarIsland_PokemonLab_ResearchRoom.AnAmberPipe)
}

internal object CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Computer : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CinnabarIsland_PokemonLab_ResearchRoom.LegendaryBirdEmail)
}

internal val CinnabarIsland_PokemonLab_ResearchRoomScripts: Map<String, Script> =
    mapOf(
        "CinnabarIsland_PokemonLab_ResearchRoom_EventScript_MetronomeTutor" to
            CinnabarIsland_PokemonLab_ResearchRoom_EventScript_MetronomeTutor,
        "CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Scientist" to
            CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Scientist,
        "CinnabarIsland_PokemonLab_ResearchRoom_EventScript_AmberPipe" to
            CinnabarIsland_PokemonLab_ResearchRoom_EventScript_AmberPipe,
        "CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Computer" to
            CinnabarIsland_PokemonLab_ResearchRoom_EventScript_Computer,
    )
