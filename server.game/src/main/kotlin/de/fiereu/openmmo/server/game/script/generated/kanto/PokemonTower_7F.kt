package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_MRFUJI, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * setflag FLAG_HIDE_TOWER_FUJI
 * clearflag FLAG_HIDE_POKEHOUSE_FUJI
 * setflag FLAG_RESCUED_MR_FUJI
 * msgbox PokemonTower_7F_Text_MrFujiThankYouFollowMe
 * closemessage
 * warp MAP_LAVENDER_TOWN_VOLUNTEER_POKEMON_HOUSE, 4, 7
 * waitstate
 * release
 * end
 * ```
 */
internal object PokemonTower_7F_EventScript_MrFuji : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_7F_EventScript_MrFuji")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_19, PokemonTower_7F_Text_Grunt1Intro, PokemonTower_7F_Text_Grunt1Defeat, PokemonTower_7F_EventScript_DefeatedGrunt1
 * msgbox PokemonTower_7F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_7F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_7F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_20, PokemonTower_7F_Text_Grunt2Intro, PokemonTower_7F_Text_Grunt2Defeat, PokemonTower_7F_EventScript_DefeatedGrunt2
 * msgbox PokemonTower_7F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_7F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_7F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_21, PokemonTower_7F_Text_Grunt3Intro, PokemonTower_7F_Text_Grunt3Defeat, PokemonTower_7F_EventScript_DefeatedGrunt3
 * msgbox PokemonTower_7F_Text_Grunt3PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonTower_7F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonTower_7F_EventScript_Grunt3")
}

internal val PokemonTower_7FScripts: Map<String, Script> =
    mapOf(
        "PokemonTower_7F_EventScript_MrFuji" to PokemonTower_7F_EventScript_MrFuji,
        "PokemonTower_7F_EventScript_Grunt1" to PokemonTower_7F_EventScript_Grunt1,
        "PokemonTower_7F_EventScript_Grunt2" to PokemonTower_7F_EventScript_Grunt2,
        "PokemonTower_7F_EventScript_Grunt3" to PokemonTower_7F_EventScript_Grunt3,
    )
