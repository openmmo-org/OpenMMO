package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_GIOVANNI, 0
 * message RocketHideout_B4F_Text_GiovanniIntro
 * waitmessage
 * playbgm MUS_ENCOUNTER_ROCKET, 0
 * waitbuttonpress
 * trainerbattle_no_intro TRAINER_BOSS_GIOVANNI, RocketHideout_B4F_Text_GiovanniDefeat
 * msgbox RocketHideout_B4F_Text_GiovanniPostBattle
 * fadescreen FADE_TO_BLACK
 * closemessage
 * removeobject LOCALID_HIDEOUT_GIOVANNI
 * addobject LOCALID_SILPH_SCOPE
 * clearflag FLAG_HIDE_SILPH_SCOPE
 * setflag FLAG_HIDE_CELADON_ROCKETS
 * famechecker FAMECHECKER_GIOVANNI, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * fadescreen FADE_FROM_BLACK
 * release
 * end
 * ```
 */
internal object RocketHideout_B4F_EventScript_Giovanni : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B4F_EventScript_Giovanni")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * removeobject LOCALID_SILPH_SCOPE
 * giveitem ITEM_SILPH_SCOPE
 * goto_if_eq VAR_RESULT, FALSE, EventScript_BagIsFull
 * release
 * end
 * ```
 */
internal object RocketHideout_B4F_EventScript_SilphScope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B4F_EventScript_SilphScope")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_18, RocketHideout_B4F_Text_Grunt1Intro, RocketHideout_B4F_Text_Grunt1Defeat, RocketHideout_B4F_EventScript_DefeatedGrunt1
 * msgbox RocketHideout_B4F_Text_Grunt1PostBattle
 * release
 * end
 * ```
 */
internal object RocketHideout_B4F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B4F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setflag FLAG_CAN_USE_ROCKET_HIDEOUT_LIFT
 * removeobject LOCALID_LIFT_KEY
 * giveitem ITEM_LIFT_KEY
 * goto_if_eq VAR_RESULT, FALSE, EventScript_BagIsFull
 * release
 * end
 * ```
 */
internal object RocketHideout_B4F_EventScript_LiftKey : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B4F_EventScript_LiftKey")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_17, RocketHideout_B4F_Text_Grunt3Intro, RocketHideout_B4F_Text_Grunt3Defeat, RocketHideout_B4F_EventScript_DefeatedGrunt3
 * msgbox RocketHideout_B4F_Text_Grunt3PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RocketHideout_B4F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B4F_EventScript_Grunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_16, RocketHideout_B4F_Text_Grunt2Intro, RocketHideout_B4F_Text_Grunt2Defeat, RocketHideout_B4F_EventScript_DefeatedGrunt2
 * msgbox RocketHideout_B4F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RocketHideout_B4F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B4F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM49
 * end
 * ```
 */
internal object RocketHideout_B4F_EventScript_ItemTM49 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RocketHideout_B4F_EventScript_ItemTM49")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ETHER
 * end
 * ```
 */
internal object RocketHideout_B4F_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B4F_EventScript_ItemMaxEther")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_CALCIUM
 * end
 * ```
 */
internal object RocketHideout_B4F_EventScript_ItemCalcium : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RocketHideout_B4F_EventScript_ItemCalcium")
}

internal val RocketHideout_B4FScripts: Map<String, Script> =
    mapOf(
        "RocketHideout_B4F_EventScript_Giovanni" to RocketHideout_B4F_EventScript_Giovanni,
        "RocketHideout_B4F_EventScript_SilphScope" to RocketHideout_B4F_EventScript_SilphScope,
        "RocketHideout_B4F_EventScript_Grunt1" to RocketHideout_B4F_EventScript_Grunt1,
        "RocketHideout_B4F_EventScript_LiftKey" to RocketHideout_B4F_EventScript_LiftKey,
        "RocketHideout_B4F_EventScript_Grunt3" to RocketHideout_B4F_EventScript_Grunt3,
        "RocketHideout_B4F_EventScript_Grunt2" to RocketHideout_B4F_EventScript_Grunt2,
        "RocketHideout_B4F_EventScript_ItemTM49" to RocketHideout_B4F_EventScript_ItemTM49,
        "RocketHideout_B4F_EventScript_ItemMaxEther" to RocketHideout_B4F_EventScript_ItemMaxEther,
        "RocketHideout_B4F_EventScript_ItemCalcium" to RocketHideout_B4F_EventScript_ItemCalcium,
    )
