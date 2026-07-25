package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SootopolisCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_SOOTOPOLIS_CITY_STATE, 6, SootopolisCity_EventScript_ExpertPostLegendaries
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_ExpertLegendaries
 * goto_if_ge VAR_SOOTOPOLIS_CITY_STATE, 2, SootopolisCity_EventScript_ExpertLeadToCave
 * msgbox SootopolisCity_Text_CaveOfOriginPleaseLeave, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SootopolisCity_EventScript_CaveOfOriginExpert : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_EventScript_CaveOfOriginExpert")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_SOOTOPOLIS_WOMAN_2, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_Woman2Rayquaza
 * msgbox SootopolisCity_Text_WeatherWentWild, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SOOTOPOLIS_WOMAN_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Woman2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Woman2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_le VAR_SOOTOPOLIS_CITY_STATE, 1, SootopolisCity_EventScript_KiriGiveBerry
 * goto_if_ge VAR_SOOTOPOLIS_CITY_STATE, 6, SootopolisCity_EventScript_KiriGiveBerry
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_KiriRayquaza
 * msgbox SootopolisCity_Text_BigPokemonFighting, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_KIRI, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Kiri : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Kiri")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_SOOTOPOLIS_NINJA_BOY, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_NinjaBoyRayquaza
 * goto_if_ge VAR_SOOTOPOLIS_CITY_STATE, 6, SootopolisCity_EventScript_NinjaBoyNormal
 * goto_if_le VAR_SOOTOPOLIS_CITY_STATE, 1, SootopolisCity_EventScript_NinjaBoyNormal
 * msgbox SootopolisCity_Text_ThisIsWicked, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SOOTOPOLIS_NINJA_BOY, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SootopolisCity_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_NinjaBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_SOOTOPOLIS_BOY_1, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_Boy1Rayquaza
 * goto_if_set FLAG_SYS_GAME_CLEAR, SootopolisCity_EventScript_Boy1GameClear
 * goto_if_ge VAR_SOOTOPOLIS_CITY_STATE, 6, SootopolisCity_EventScript_Boy1Normal
 * goto_if_le VAR_SOOTOPOLIS_CITY_STATE, 1, SootopolisCity_EventScript_Boy1Normal
 * msgbox SootopolisCity_Text_GiantPokemonSuddenlyAppeared, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SOOTOPOLIS_BOY_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Boy1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_SOOTOPOLIS_CITY_STATE, 6, SootopolisCity_EventScript_ManPostLegendaries
 * msgbox SootopolisCity_Text_NoOrdinaryTourist, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Man")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_SOOTOPOLIS_STEVEN, Common_Movement_FacePlayer
 * waitmovement 0
 * call_if_unset FLAG_STEVEN_GUIDES_TO_CAVE_OF_ORIGIN, SootopolisCity_EventScript_StevenLeadPlayerCaveOfOrigin
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 2, SootopolisCity_EventScript_StevenHelpWallace
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 3, SootopolisCity_EventScript_StevenHelpedWallace
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 4, SootopolisCity_EventScript_StevenHelpedWallace
 * goto_if_set FLAG_SOOTOPOLIS_ARCHIE_MAXIE_LEAVE, SootopolisCity_EventScript_StevenMaxieArchieLeft
 * msgbox SootopolisCity_Text_SoThatsRayquaza, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Steven : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Steven")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_SOOTOPOLIS_CITY_STATE, 6, SootopolisCity_EventScript_Woman1PostLegendaries
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_Woman1Rayquaza
 * goto_if_ge VAR_SOOTOPOLIS_CITY_STATE, 2, SootopolisCity_EventScript_Woman1Legendaries
 * msgbox SootopolisCity_Text_SootopolisSkyBeautiful, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Woman1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Woman1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_SOOTOPOLIS_MANIAC, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_ManiacRayquaza
 * msgbox SootopolisCity_Text_SeeingLegendWithOwnEyes, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SOOTOPOLIS_MANIAC, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Maniac : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Maniac")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_SOOTOPOLIS_GIRL, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_GirlRayquaza
 * msgbox SootopolisCity_Text_SootopolisWillBeWrecked, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SOOTOPOLIS_GIRL, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Girl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_BlackBeltRayquaza
 * msgbox SootopolisCity_Text_GoRedAndBlueMon, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SOOTOPOLIS_BLACK_BELT, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox SootopolisCity_Text_DoYouKnowMonNames, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SOOTOPOLIS_BLACK_BELT, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_BlackBelt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_SOOTOPOLIS_BOY_2, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_Boy2Rayquaza
 * msgbox SootopolisCity_Text_TwoPokemonArentAngry, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_SOOTOPOLIS_BOY_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Boy2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_MaxieRayquaza
 * msgbox SootopolisCity_Text_GroudonPleaseStop, MSGBOX_DEFAULT
 * closemessage
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Maxie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Maxie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 5, SootopolisCity_EventScript_ArchieRayquaza
 * msgbox SootopolisCity_Text_KyogreCalmDown, MSGBOX_DEFAULT
 * closemessage
 * releaseall
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Archie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Archie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 4, SootopolisCity_EventScript_GoToSkyPillar
 * goto_if_set FLAG_RECEIVED_HM_WATERFALL, SootopolisCity_EventScript_GoToGym
 * goto_if_set FLAG_SOOTOPOLIS_ARCHIE_MAXIE_LEAVE, SootopolisCity_EventScript_GiveWaterfall
 * msgbox SootopolisCity_Text_AquaMagmaDidntMeanHarm, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SootopolisCity_EventScript_Wallace : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SootopolisCity_EventScript_Wallace")
}

internal object SootopolisCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SootopolisCity.GymSign)
}

internal object SootopolisCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SootopolisCity.CitySign)
}

internal val SootopolisCityScripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_EventScript_CaveOfOriginExpert" to
            SootopolisCity_EventScript_CaveOfOriginExpert,
        "SootopolisCity_EventScript_Woman2" to SootopolisCity_EventScript_Woman2,
        "SootopolisCity_EventScript_Kiri" to SootopolisCity_EventScript_Kiri,
        "SootopolisCity_EventScript_NinjaBoy" to SootopolisCity_EventScript_NinjaBoy,
        "SootopolisCity_EventScript_Boy1" to SootopolisCity_EventScript_Boy1,
        "SootopolisCity_EventScript_Man" to SootopolisCity_EventScript_Man,
        "SootopolisCity_EventScript_Steven" to SootopolisCity_EventScript_Steven,
        "SootopolisCity_EventScript_Woman1" to SootopolisCity_EventScript_Woman1,
        "SootopolisCity_EventScript_Maniac" to SootopolisCity_EventScript_Maniac,
        "SootopolisCity_EventScript_Girl" to SootopolisCity_EventScript_Girl,
        "SootopolisCity_EventScript_BlackBelt" to SootopolisCity_EventScript_BlackBelt,
        "SootopolisCity_EventScript_Boy2" to SootopolisCity_EventScript_Boy2,
        "SootopolisCity_EventScript_Maxie" to SootopolisCity_EventScript_Maxie,
        "SootopolisCity_EventScript_Archie" to SootopolisCity_EventScript_Archie,
        "SootopolisCity_EventScript_Wallace" to SootopolisCity_EventScript_Wallace,
        "SootopolisCity_EventScript_GymSign" to SootopolisCity_EventScript_GymSign,
        "SootopolisCity_EventScript_CitySign" to SootopolisCity_EventScript_CitySign,
    )
