package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER1
 * special Script_BufferFanClubTrainerName
 * goto_if_eq VAR_MAP_SCENE_SAFFRON_CITY_POKEMON_TRAINER_FAN_CLUB, 0, SaffronCity_PokemonTrainerFanClub_EventScript_CrushGirlPlayerNotChampion
 * specialvar VAR_RESULT, Script_IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, SaffronCity_PokemonTrainerFanClub_EventScript_CrushGirlPlayersFan
 * specialvar VAR_RESULT, Script_GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), SaffronCity_PokemonTrainerFanClub_EventScript_CrushGirlOnlyNonFan
 * msgbox SaffronCity_PokemonTrainerFanClub_Text_WasYourFanNotAnymore
 * release
 * end
 * ```
 */
internal object SaffronCity_PokemonTrainerFanClub_EventScript_CrushGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_PokemonTrainerFanClub_EventScript_CrushGirl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER4
 * special Script_BufferFanClubTrainerName
 * goto_if_eq VAR_MAP_SCENE_SAFFRON_CITY_POKEMON_TRAINER_FAN_CLUB, 0, SaffronCity_PokemonTrainerFanClub_EventScript_LittleGirlPlayerNotChampion
 * specialvar VAR_RESULT, Script_IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, SaffronCity_PokemonTrainerFanClub_EventScript_LittleGirlPlayersFan
 * specialvar VAR_RESULT, Script_GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), SaffronCity_PokemonTrainerFanClub_EventScript_LittleGirlOnlyNonFan
 * msgbox SaffronCity_PokemonTrainerFanClub_Text_WantToBeLikeSabrina
 * release
 * end
 * ```
 */
internal object SaffronCity_PokemonTrainerFanClub_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_PokemonTrainerFanClub_EventScript_LittleGirl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER2
 * special Script_BufferFanClubTrainerName
 * goto_if_eq VAR_MAP_SCENE_SAFFRON_CITY_POKEMON_TRAINER_FAN_CLUB, 0, SaffronCity_PokemonTrainerFanClub_EventScript_YoungsterPlayerNotChampion
 * specialvar VAR_RESULT, Script_IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, SaffronCity_PokemonTrainerFanClub_EventScript_YoungsterPlayersFan
 * specialvar VAR_RESULT, Script_GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), SaffronCity_PokemonTrainerFanClub_EventScript_YoungsterOnlyNonFan
 * msgbox SaffronCity_PokemonTrainerFanClub_Text_BrocksMyHero
 * release
 * end
 * ```
 */
internal object SaffronCity_PokemonTrainerFanClub_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_PokemonTrainerFanClub_EventScript_Youngster")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER3
 * special Script_BufferFanClubTrainerName
 * goto_if_eq VAR_MAP_SCENE_SAFFRON_CITY_POKEMON_TRAINER_FAN_CLUB, 0, SaffronCity_PokemonTrainerFanClub_EventScript_GentlemanPlayerNotChampion
 * specialvar VAR_RESULT, Script_IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, SaffronCity_PokemonTrainerFanClub_EventScript_GentlemanPlayersFan
 * specialvar VAR_RESULT, Script_GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), SaffronCity_PokemonTrainerFanClub_EventScript_GentlemanOnlyNonFan
 * msgbox SaffronCity_PokemonTrainerFanClub_Text_HmmAndYouAre
 * release
 * end
 * ```
 */
internal object SaffronCity_PokemonTrainerFanClub_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_PokemonTrainerFanClub_EventScript_Gentleman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER6
 * special Script_BufferFanClubTrainerName
 * specialvar VAR_RESULT, Script_IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, SaffronCity_PokemonTrainerFanClub_EventScript_WomanPlayersFan
 * specialvar VAR_RESULT, Script_GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), SaffronCity_PokemonTrainerFanClub_EventScript_WomanOnlyNonFan
 * msgbox SaffronCity_PokemonTrainerFanClub_Text_TrainerHasBeenOnFire
 * release
 * end
 * ```
 */
internal object SaffronCity_PokemonTrainerFanClub_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_PokemonTrainerFanClub_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER5
 * special Script_BufferFanClubTrainerName
 * specialvar VAR_RESULT, Script_IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, SaffronCity_PokemonTrainerFanClub_EventScript_RockerPlayersFan
 * specialvar VAR_RESULT, Script_GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), SaffronCity_PokemonTrainerFanClub_EventScript_RockerOnlyNonFan
 * msgbox SaffronCity_PokemonTrainerFanClub_Text_LoveWayTrainerTalks
 * release
 * end
 * ```
 */
internal object SaffronCity_PokemonTrainerFanClub_EventScript_Rocker : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_PokemonTrainerFanClub_EventScript_Rocker")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER7
 * special Script_BufferFanClubTrainerName
 * specialvar VAR_RESULT, Script_IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, SaffronCity_PokemonTrainerFanClub_EventScript_BeautyPlayersFan
 * specialvar VAR_RESULT, Script_GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), SaffronCity_PokemonTrainerFanClub_EventScript_BeautyOnlyNonFan
 * msgbox SaffronCity_PokemonTrainerFanClub_Text_WhyCantOthersSeeMastersDignity
 * release
 * end
 * ```
 */
internal object SaffronCity_PokemonTrainerFanClub_EventScript_Beauty : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_PokemonTrainerFanClub_EventScript_Beauty")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8004, FANCLUB_MEMBER8
 * special Script_BufferFanClubTrainerName
 * specialvar VAR_RESULT, Script_IsFanClubMemberFanOfPlayer
 * goto_if_eq VAR_RESULT, TRUE, SaffronCity_PokemonTrainerFanClub_EventScript_BlackBeltPlayersFan
 * specialvar VAR_RESULT, Script_GetNumFansOfPlayerInTrainerFanClub
 * goto_if_eq VAR_RESULT, (NUM_TRAINER_FAN_CLUB_MEMBERS - 1), SaffronCity_PokemonTrainerFanClub_EventScript_BlackBeltOnlyNonFan
 * msgbox SaffronCity_PokemonTrainerFanClub_Text_OnlyMasterHasMyRespect
 * release
 * end
 * ```
 */
internal object SaffronCity_PokemonTrainerFanClub_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_PokemonTrainerFanClub_EventScript_BlackBelt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BRUNO, 2
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureBruno
 * release
 * end
 * ```
 */
internal object SaffronCity_PokemonTrainerFanClub_EventScript_PokemonJournalBruno : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_PokemonTrainerFanClub_EventScript_PokemonJournalBruno")
}

internal val SaffronCity_PokemonTrainerFanClubScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_PokemonTrainerFanClub_EventScript_CrushGirl" to
            SaffronCity_PokemonTrainerFanClub_EventScript_CrushGirl,
        "SaffronCity_PokemonTrainerFanClub_EventScript_LittleGirl" to
            SaffronCity_PokemonTrainerFanClub_EventScript_LittleGirl,
        "SaffronCity_PokemonTrainerFanClub_EventScript_Youngster" to
            SaffronCity_PokemonTrainerFanClub_EventScript_Youngster,
        "SaffronCity_PokemonTrainerFanClub_EventScript_Gentleman" to
            SaffronCity_PokemonTrainerFanClub_EventScript_Gentleman,
        "SaffronCity_PokemonTrainerFanClub_EventScript_Woman" to
            SaffronCity_PokemonTrainerFanClub_EventScript_Woman,
        "SaffronCity_PokemonTrainerFanClub_EventScript_Rocker" to
            SaffronCity_PokemonTrainerFanClub_EventScript_Rocker,
        "SaffronCity_PokemonTrainerFanClub_EventScript_Beauty" to
            SaffronCity_PokemonTrainerFanClub_EventScript_Beauty,
        "SaffronCity_PokemonTrainerFanClub_EventScript_BlackBelt" to
            SaffronCity_PokemonTrainerFanClub_EventScript_BlackBelt,
        "SaffronCity_PokemonTrainerFanClub_EventScript_PokemonJournalBruno" to
            SaffronCity_PokemonTrainerFanClub_EventScript_PokemonJournalBruno,
    )
