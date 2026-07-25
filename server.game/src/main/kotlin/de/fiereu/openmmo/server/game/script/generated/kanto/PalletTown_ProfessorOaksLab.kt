package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PalletTown_ProfessorOaksLab
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, PalletTown_ProfessorOaksLab_EventScript_Aide1GameClear
 * msgbox PalletTown_ProfessorOaksLab_Text_StudyAsOaksAide
 * release
 * end
 * ```
 */
internal object PalletTown_ProfessorOaksLab_EventScript_Aide1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_ProfessorOaksLab_EventScript_Aide1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_OAK, 2
 * msgbox PalletTown_ProfessorOaksLab_Text_OakIsAuthorityOnMons
 * release
 * end
 * ```
 */
internal object PalletTown_ProfessorOaksLab_EventScript_Aide3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_ProfessorOaksLab_EventScript_Aide3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, PalletTown_ProfessorOaksLab_EventScript_Aide2GameClear
 * msgbox PalletTown_ProfessorOaksLab_Text_StudyAsOaksAide
 * release
 * end
 * ```
 */
internal object PalletTown_ProfessorOaksLab_EventScript_Aide2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_ProfessorOaksLab_EventScript_Aide2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set SHOWED_OAK_COMPLETE_DEX, PalletTown_ProfessorOaksLab_EventScript_OakJustShownCompleteDex
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 9, PalletTown_ProfessorOaksLab_EventScript_RatePokedex
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 8, PalletTown_ProfessorOaksLab_EventScript_MonsAroundWorldWait
 * goto_if_set FLAG_SYS_GAME_CLEAR, PalletTown_ProfessorOaksLab_EventScript_TryStartNationalDexScene
 * goto_if_eq VAR_MAP_SCENE_CERULEAN_CITY_RIVAL, 1, PalletTown_ProfessorOaksLab_EventScript_RatePokedex
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 6, PalletTown_ProfessorOaksLab_EventScript_RatePokedexOrTryGiveBalls
 * goto_if_ge VAR_MAP_SCENE_VIRIDIAN_CITY_MART, 1, PalletTown_ProfessorOaksLab_EventScript_ReceiveDexScene
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 4, PalletTown_ProfessorOaksLab_EventScript_OakBattleMonForItToGrow
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 3, PalletTown_ProfessorOaksLab_EventScript_OakCanReachNextTownWithMon
 * msgbox PalletTown_ProfessorOaksLab_Text_OakWhichOneWillYouChoose
 * release
 * end
 * ```
 */
internal object PalletTown_ProfessorOaksLab_EventScript_ProfOak : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_ProfessorOaksLab_EventScript_ProfOak")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar PLAYER_STARTER_NUM, 0
 * setvar PLAYER_STARTER_SPECIES, SPECIES_BULBASAUR
 * setvar RIVAL_STARTER_SPECIES, SPECIES_CHARMANDER
 * setvar RIVAL_STARTER_ID, LOCALID_CHARMANDER_BALL
 * goto_if_ge VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 3, PalletTown_ProfessorOaksLab_EventScript_LastPokeBall
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 2, PalletTown_ProfessorOaksLab_EventScript_ConfirmStarterChoice
 * msgbox PalletTown_ProfessorOaksLab_Text_ThoseArePokeBalls
 * release
 * end
 * ```
 */
internal object PalletTown_ProfessorOaksLab_EventScript_BulbasaurBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_ProfessorOaksLab_EventScript_BulbasaurBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar PLAYER_STARTER_NUM, 1
 * setvar PLAYER_STARTER_SPECIES, SPECIES_SQUIRTLE
 * setvar RIVAL_STARTER_SPECIES, SPECIES_BULBASAUR
 * setvar RIVAL_STARTER_ID, LOCALID_BULBASAUR_BALL
 * goto_if_ge VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 3, PalletTown_ProfessorOaksLab_EventScript_LastPokeBall
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 2, PalletTown_ProfessorOaksLab_EventScript_ConfirmStarterChoice
 * msgbox PalletTown_ProfessorOaksLab_Text_ThoseArePokeBalls
 * release
 * end
 * ```
 */
internal object PalletTown_ProfessorOaksLab_EventScript_SquirtleBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_ProfessorOaksLab_EventScript_SquirtleBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar PLAYER_STARTER_NUM, 2
 * setvar PLAYER_STARTER_SPECIES, SPECIES_CHARMANDER
 * setvar RIVAL_STARTER_SPECIES, SPECIES_SQUIRTLE
 * setvar RIVAL_STARTER_ID, LOCALID_SQUIRTLE_BALL
 * goto_if_ge VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 3, PalletTown_ProfessorOaksLab_EventScript_LastPokeBall
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 2, PalletTown_ProfessorOaksLab_EventScript_ConfirmStarterChoice
 * msgbox PalletTown_ProfessorOaksLab_Text_ThoseArePokeBalls
 * release
 * end
 * ```
 */
internal object PalletTown_ProfessorOaksLab_EventScript_CharmanderBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_ProfessorOaksLab_EventScript_CharmanderBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 3, PalletTown_ProfessorOaksLab_EventScript_RivalChoseStarter
 * goto_if_eq VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 2, PalletTown_ProfessorOaksLab_EventScript_RivalWaitingForStarter
 * msgbox PalletTown_ProfessorOaksLab_Text_RivalGrampsIsntAround
 * release
 * end
 * ```
 */
internal object PalletTown_ProfessorOaksLab_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_ProfessorOaksLab_EventScript_Rival")
}

internal object PalletTown_ProfessorOaksLab_EventScript_Pokedex : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PalletTown_ProfessorOaksLab.BlankEncyclopedia)
}

internal object PalletTown_ProfessorOaksLab_EventScript_Computer : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PalletTown_ProfessorOaksLab.EmailMessage)
}

internal object PalletTown_ProfessorOaksLab_EventScript_LeftSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(PalletTown_ProfessorOaksLab.PressStartToOpenMenu)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_ge VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 6, PalletTown_ProfessorOaksLab_EventScript_RightSignAlt
 * msgbox PalletTown_ProfessorOaksLab_Text_SaveOptionInMenu
 * releaseall
 * end
 * ```
 */
internal object PalletTown_ProfessorOaksLab_EventScript_RightSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_ProfessorOaksLab_EventScript_RightSign")
}

internal val PalletTown_ProfessorOaksLabScripts: Map<String, Script> =
    mapOf(
        "PalletTown_ProfessorOaksLab_EventScript_Aide1" to
            PalletTown_ProfessorOaksLab_EventScript_Aide1,
        "PalletTown_ProfessorOaksLab_EventScript_Aide3" to
            PalletTown_ProfessorOaksLab_EventScript_Aide3,
        "PalletTown_ProfessorOaksLab_EventScript_Aide2" to
            PalletTown_ProfessorOaksLab_EventScript_Aide2,
        "PalletTown_ProfessorOaksLab_EventScript_ProfOak" to
            PalletTown_ProfessorOaksLab_EventScript_ProfOak,
        "PalletTown_ProfessorOaksLab_EventScript_BulbasaurBall" to
            PalletTown_ProfessorOaksLab_EventScript_BulbasaurBall,
        "PalletTown_ProfessorOaksLab_EventScript_SquirtleBall" to
            PalletTown_ProfessorOaksLab_EventScript_SquirtleBall,
        "PalletTown_ProfessorOaksLab_EventScript_CharmanderBall" to
            PalletTown_ProfessorOaksLab_EventScript_CharmanderBall,
        "PalletTown_ProfessorOaksLab_EventScript_Rival" to
            PalletTown_ProfessorOaksLab_EventScript_Rival,
        "PalletTown_ProfessorOaksLab_EventScript_Pokedex" to
            PalletTown_ProfessorOaksLab_EventScript_Pokedex,
        "PalletTown_ProfessorOaksLab_EventScript_Computer" to
            PalletTown_ProfessorOaksLab_EventScript_Computer,
        "PalletTown_ProfessorOaksLab_EventScript_LeftSign" to
            PalletTown_ProfessorOaksLab_EventScript_LeftSign,
        "PalletTown_ProfessorOaksLab_EventScript_RightSign" to
            PalletTown_ProfessorOaksLab_EventScript_RightSign,
    )
