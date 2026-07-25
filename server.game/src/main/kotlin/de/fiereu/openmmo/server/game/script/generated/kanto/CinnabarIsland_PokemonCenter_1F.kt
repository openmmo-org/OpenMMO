package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CinnabarIsland_PokemonCenter_1F
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
internal object CinnabarIsland_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_PokemonCenter_1F_EventScript_Nurse")
}

internal object CinnabarIsland_PokemonCenter_1F_EventScript_CooltrainerF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CinnabarIsland_PokemonCenter_1F.CinnabarGymLocked)
}

internal object CinnabarIsland_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CinnabarIsland_PokemonCenter_1F.VisitUnionRoom)
}

internal object CinnabarIsland_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CinnabarIsland_PokemonCenter_1F.EvolutionCanWaitForNewMoves)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_MRFUJI, 5
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureMrFuji
 * release
 * end
 * ```
 */
internal object CinnabarIsland_PokemonCenter_1F_EventScript_PokemonJournalMrFuji : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_PokemonCenter_1F_EventScript_PokemonJournalMrFuji")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox CinnabarIsland_PokemonCenter_1F_Text_ReadyToSailToOneIsland, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, CinnabarIsland_PokemonCenter_1F_EventScript_NotReadyToSail
 * msgbox CinnabarIsland_PokemonCenter_1F_Text_LetsGo
 * closemessage
 * playbgm MUS_FOLLOW_ME, 1
 * savebgm MUS_FOLLOW_ME
 * setflag FLAG_DONT_TRANSITION_MUSIC
 * setflag FLAG_HIDE_CINNABAR_POKECENTER_BILL
 * delay 20
 * call_if_eq VAR_FACING, DIR_SOUTH, CinnabarIsland_PokemonCenter_1F_EventScript_ExitWithBillSouth
 * call_if_eq VAR_FACING, DIR_EAST, CinnabarIsland_PokemonCenter_1F_EventScript_ExitWithBillEast
 * call_if_eq VAR_FACING, DIR_WEST, CinnabarIsland_PokemonCenter_1F_EventScript_ExitWithBillWest
 * removeobject LOCALID_CINNABAR_POKEMON_CENTER_BILL
 * setvar VAR_MAP_SCENE_CINNABAR_ISLAND_2, 1
 * clearflag FLAG_HIDE_CINNABAR_BILL
 * warp MAP_CINNABAR_ISLAND, 14, 11
 * waitstate
 * release
 * end
 * ```
 */
internal object CinnabarIsland_PokemonCenter_1F_EventScript_Bill : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_PokemonCenter_1F_EventScript_Bill")
}

internal val CinnabarIsland_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "CinnabarIsland_PokemonCenter_1F_EventScript_Nurse" to
            CinnabarIsland_PokemonCenter_1F_EventScript_Nurse,
        "CinnabarIsland_PokemonCenter_1F_EventScript_CooltrainerF" to
            CinnabarIsland_PokemonCenter_1F_EventScript_CooltrainerF,
        "CinnabarIsland_PokemonCenter_1F_EventScript_Gentleman" to
            CinnabarIsland_PokemonCenter_1F_EventScript_Gentleman,
        "CinnabarIsland_PokemonCenter_1F_EventScript_Youngster" to
            CinnabarIsland_PokemonCenter_1F_EventScript_Youngster,
        "CinnabarIsland_PokemonCenter_1F_EventScript_PokemonJournalMrFuji" to
            CinnabarIsland_PokemonCenter_1F_EventScript_PokemonJournalMrFuji,
        "CinnabarIsland_PokemonCenter_1F_EventScript_Bill" to
            CinnabarIsland_PokemonCenter_1F_EventScript_Bill,
    )
