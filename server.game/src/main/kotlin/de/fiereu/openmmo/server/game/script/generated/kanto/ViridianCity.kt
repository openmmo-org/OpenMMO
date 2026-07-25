package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ViridianCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto EventScript_DreamEaterTutor
 * end
 * ```
 */
internal object ViridianCity_EventScript_DreamEaterTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ViridianCity_EventScript_DreamEaterTutor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MAP_SCENE_VIRIDIAN_CITY_GYM_DOOR, 1, ViridianCity_EventScript_OldManGymLeaderReturned
 * msgbox ViridianCity_Text_GymClosedWonderWhoLeaderIs
 * closemessage
 * applymovement LOCALID_VIRIDIAN_OLD_MAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object ViridianCity_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_EventScript_OldMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_BADGE01_GET, ViridianCity_EventScript_AskIfTeachyTVHelpful
 * goto_if_ge VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN, 2, ViridianCity_EventScript_TutorialCompleted
 * goto_if_eq VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN, 1, ViridianCity_EventScript_TutorialStart
 * goto_if_eq VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN, 0, ViridianCity_EventScript_TutorialNotReady
 * end
 * ```
 */
internal object ViridianCity_EventScript_TutorialOldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ViridianCity_EventScript_TutorialOldMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN, 0, ViridianCity_EventScript_WomanRoadBlocked
 * msgbox ViridianCity_Text_GoShoppingInPewterOccasionally
 * release
 * end
 * ```
 */
internal object ViridianCity_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox ViridianCity_Text_WantToKnowAboutCaterpillarMons, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, ViridianCity_EventScript_YoungsterExplainCaterpillars
 * goto_if_eq VAR_RESULT, NO, ViridianCity_EventScript_YoungsterDeclineExplanation
 * end
 * ```
 */
internal object ViridianCity_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_EventScript_Youngster")
}

internal object ViridianCity_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianCity.CanCarryMonsAnywhere)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_POTION
 * end
 * ```
 */
internal object ViridianCity_EventScript_ItemPotion : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_EventScript_ItemPotion")
}

internal object ViridianCity_EventScript_TrainerTips1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.CatchMonsForEasierBattles)
}

internal object ViridianCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.GymSign)
}

internal object ViridianCity_EventScript_TrainerTips2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.MovesLimitedByPP)
}

internal object ViridianCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.CitySign)
}

internal object ViridianCity_EventScript_GymDoor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.GymDoorsAreLocked)
}

internal val ViridianCityScripts: Map<String, Script> =
    mapOf(
        "ViridianCity_EventScript_DreamEaterTutor" to ViridianCity_EventScript_DreamEaterTutor,
        "ViridianCity_EventScript_OldMan" to ViridianCity_EventScript_OldMan,
        "ViridianCity_EventScript_TutorialOldMan" to ViridianCity_EventScript_TutorialOldMan,
        "ViridianCity_EventScript_Woman" to ViridianCity_EventScript_Woman,
        "ViridianCity_EventScript_Youngster" to ViridianCity_EventScript_Youngster,
        "ViridianCity_EventScript_Boy" to ViridianCity_EventScript_Boy,
        "ViridianCity_EventScript_ItemPotion" to ViridianCity_EventScript_ItemPotion,
        "ViridianCity_EventScript_TrainerTips1" to ViridianCity_EventScript_TrainerTips1,
        "ViridianCity_EventScript_GymSign" to ViridianCity_EventScript_GymSign,
        "ViridianCity_EventScript_TrainerTips2" to ViridianCity_EventScript_TrainerTips2,
        "ViridianCity_EventScript_CitySign" to ViridianCity_EventScript_CitySign,
        "ViridianCity_EventScript_GymDoor" to ViridianCity_EventScript_GymDoor,
    )
