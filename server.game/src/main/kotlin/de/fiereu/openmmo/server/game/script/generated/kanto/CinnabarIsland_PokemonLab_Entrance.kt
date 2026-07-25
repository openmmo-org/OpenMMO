package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CinnabarIsland_PokemonLab_Entrance
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CinnabarIsland_PokemonLab_Entrance_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CinnabarIsland_PokemonLab_Entrance.StudyMonsExtensively)
}

internal object CinnabarIsland_PokemonLab_Entrance_EventScript_DrFujiPhoto : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CinnabarIsland_PokemonLab_Entrance.PhotoOfLabFounderDrFuji)
}

internal object CinnabarIsland_PokemonLab_Entrance_EventScript_MeetingRoomSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CinnabarIsland_PokemonLab_Entrance.MeetingRoomSign)
}

internal object CinnabarIsland_PokemonLab_Entrance_EventScript_RAndDRoomSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CinnabarIsland_PokemonLab_Entrance.RAndDRoomSign)
}

internal object CinnabarIsland_PokemonLab_Entrance_EventScript_TestingRoomSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CinnabarIsland_PokemonLab_Entrance.TestingRoomSign)
}

internal val CinnabarIsland_PokemonLab_EntranceScripts: Map<String, Script> =
    mapOf(
        "CinnabarIsland_PokemonLab_Entrance_EventScript_Scientist" to
            CinnabarIsland_PokemonLab_Entrance_EventScript_Scientist,
        "CinnabarIsland_PokemonLab_Entrance_EventScript_DrFujiPhoto" to
            CinnabarIsland_PokemonLab_Entrance_EventScript_DrFujiPhoto,
        "CinnabarIsland_PokemonLab_Entrance_EventScript_MeetingRoomSign" to
            CinnabarIsland_PokemonLab_Entrance_EventScript_MeetingRoomSign,
        "CinnabarIsland_PokemonLab_Entrance_EventScript_RAndDRoomSign" to
            CinnabarIsland_PokemonLab_Entrance_EventScript_RAndDRoomSign,
        "CinnabarIsland_PokemonLab_Entrance_EventScript_TestingRoomSign" to
            CinnabarIsland_PokemonLab_Entrance_EventScript_TestingRoomSign,
    )
