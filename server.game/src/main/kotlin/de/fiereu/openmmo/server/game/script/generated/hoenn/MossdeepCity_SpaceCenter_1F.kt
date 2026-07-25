package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MossdeepCity_SpaceCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_1F_EventScript_RocketLaunchCounterMagma
 * dotimebasedevents
 * specialvar VAR_RESULT, GetWeekCount
 * buffernumberstring STR_VAR_1, VAR_RESULT
 * call_if_eq VAR_RESULT, 0, MossdeepCity_SpaceCenter_1F_EventScript_NoLaunchesYet
 * call_if_ge VAR_RESULT, 1, MossdeepCity_SpaceCenter_1F_EventScript_ShowLaunchNumber
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_RocketLaunchCounter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_RocketLaunchCounter")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_1F_EventScript_ScientistMagma
 * msgbox MossdeepCity_SpaceCenter_1F_Text_RocketLaunchDemandsPerfection, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_Scientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_1F_EventScript_SunStoneManMagma
 * goto_if_set FLAG_RECEIVED_SUN_STONE_MOSSDEEP, MossdeepCity_SpaceCenter_1F_EventScript_GaveSunStone
 * msgbox MossdeepCity_SpaceCenter_1F_Text_FoundThisYouCanHaveIt, MSGBOX_DEFAULT
 * giveitem ITEM_SUN_STONE
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_SUN_STONE_MOSSDEEP
 * msgbox MossdeepCity_SpaceCenter_1F_Text_HoennFamousForMeteorShowers, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_SunStoneMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_SunStoneMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, MossdeepCity_SpaceCenter_1F_EventScript_OldManNormal
 * goto_if_eq VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_1F_EventScript_OldManMagma
 * goto_if_lt VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_1F_EventScript_OldManNormal
 * goto MossdeepCity_SpaceCenter_1F_EventScript_OldManMagma
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_OldMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, MossdeepCity_SpaceCenter_1F_EventScript_WomanNormal
 * goto_if_eq VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_1F_EventScript_WomanMagma
 * goto_if_lt VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_1F_EventScript_WomanNormal
 * goto MossdeepCity_SpaceCenter_1F_EventScript_WomanMagma
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_SPACE_CENTER_3, MossdeepCity_SpaceCenter_1F_Text_Grunt3Intro, MossdeepCity_SpaceCenter_1F_Text_Grunt3Defeat
 * msgbox MossdeepCity_SpaceCenter_1F_Text_Grunt3PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_Grunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_SPACE_CENTER_1, MossdeepCity_SpaceCenter_1F_Text_Grunt1Intro, MossdeepCity_SpaceCenter_1F_Text_Grunt1Defeat
 * msgbox MossdeepCity_SpaceCenter_1F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_SPACE_CENTER_4, MossdeepCity_SpaceCenter_1F_Text_Grunt4Intro, MossdeepCity_SpaceCenter_1F_Text_Grunt4Defeat
 * msgbox MossdeepCity_SpaceCenter_1F_Text_Grunt4PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_Grunt4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_GRUNT_SPACE_CENTER_1F, MossdeepCity_SpaceCenter_1F_EventScript_Grunt2Defeated
 * msgbox MossdeepCity_SpaceCenter_1F_Text_Grunt2Intro, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_GRUNT_SPACE_CENTER_2, MossdeepCity_SpaceCenter_1F_Text_Grunt2Defeat
 * setflag FLAG_DEFEATED_GRUNT_SPACE_CENTER_1F
 * copyobjectxytoperm LOCALID_SPACE_CENTER_1F_STAIR_GRUNT
 * switch VAR_FACING
 * case DIR_WEST, MossdeepCity_SpaceCenter_1F_EventScript_MoveGruntFromStairsWest
 * #ifdef BUGFIX
 * case DIR_EAST, MossdeepCity_SpaceCenter_1F_EventScript_MoveGruntFromStairsEast
 * #else
 * case DIR_WEST, MossdeepCity_SpaceCenter_1F_EventScript_MoveGruntFromStairsEast
 * #endif
 * applymovement LOCALID_SPACE_CENTER_1F_STAIR_GRUNT, MossdeepCity_SpaceCenter_1F_Movement_MoveGruntFromStairs
 * waitmovement 0
 * setvar VAR_MOSSDEEP_SPACE_CENTER_STAIR_GUARD_STATE, 2
 * release
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_Grunt2")
}

internal object MossdeepCity_SpaceCenter_1F_EventScript_MagmaNote : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MossdeepCity_SpaceCenter_1F.MagmaIntentToStealNotice)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox MossdeepCity_SpaceCenter_1F_Text_StevenMagmaCantBeAllowedToTakeFuel, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_1F_EventScript_Steven : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_1F_EventScript_Steven")
}

internal val MossdeepCity_SpaceCenter_1FScripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_SpaceCenter_1F_EventScript_RocketLaunchCounter" to
            MossdeepCity_SpaceCenter_1F_EventScript_RocketLaunchCounter,
        "MossdeepCity_SpaceCenter_1F_EventScript_Scientist" to
            MossdeepCity_SpaceCenter_1F_EventScript_Scientist,
        "MossdeepCity_SpaceCenter_1F_EventScript_SunStoneMan" to
            MossdeepCity_SpaceCenter_1F_EventScript_SunStoneMan,
        "MossdeepCity_SpaceCenter_1F_EventScript_OldMan" to
            MossdeepCity_SpaceCenter_1F_EventScript_OldMan,
        "MossdeepCity_SpaceCenter_1F_EventScript_Woman" to
            MossdeepCity_SpaceCenter_1F_EventScript_Woman,
        "MossdeepCity_SpaceCenter_1F_EventScript_Grunt3" to
            MossdeepCity_SpaceCenter_1F_EventScript_Grunt3,
        "MossdeepCity_SpaceCenter_1F_EventScript_Grunt1" to
            MossdeepCity_SpaceCenter_1F_EventScript_Grunt1,
        "MossdeepCity_SpaceCenter_1F_EventScript_Grunt4" to
            MossdeepCity_SpaceCenter_1F_EventScript_Grunt4,
        "MossdeepCity_SpaceCenter_1F_EventScript_Grunt2" to
            MossdeepCity_SpaceCenter_1F_EventScript_Grunt2,
        "MossdeepCity_SpaceCenter_1F_EventScript_MagmaNote" to
            MossdeepCity_SpaceCenter_1F_EventScript_MagmaNote,
        "MossdeepCity_SpaceCenter_1F_EventScript_Steven" to
            MossdeepCity_SpaceCenter_1F_EventScript_Steven,
    )
