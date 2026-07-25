package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * msgbox SixIsland_RuinValley_Text_CantFigureOutHowToGetInside
 * applymovement LOCALID_RUIN_VALLEY_SCIENTIST, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox SixIsland_RuinValley_Text_IFoundThisPlace
 * applymovement LOCALID_RUIN_VALLEY_SCIENTIST, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_RuinValley_EventScript_Scientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RUIN_MANIAC_STANLY, SixIsland_RuinValley_Text_StanlyIntro, SixIsland_RuinValley_Text_StanlyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SixIsland_RuinValley_EventScript_StanlyRematch
 * msgbox SixIsland_RuinValley_Text_StanlyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_Stanly : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_RuinValley_EventScript_Stanly")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RUIN_MANIAC_FOSTER, SixIsland_RuinValley_Text_FosterIntro, SixIsland_RuinValley_Text_FosterDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SixIsland_RuinValley_EventScript_FosterRematch
 * msgbox SixIsland_RuinValley_Text_FosterPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_Foster : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_RuinValley_EventScript_Foster")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RUIN_MANIAC_LARRY, SixIsland_RuinValley_Text_LarryIntro, SixIsland_RuinValley_Text_LarryDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SixIsland_RuinValley_EventScript_LarryRematch
 * msgbox SixIsland_RuinValley_Text_LarryPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_Larry : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SixIsland_RuinValley_EventScript_Larry")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_DARYL, SixIsland_RuinValley_Text_DarylIntro, SixIsland_RuinValley_Text_DarylDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SixIsland_RuinValley_EventScript_DarylRematch
 * msgbox SixIsland_RuinValley_Text_DarylPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_Daryl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SixIsland_RuinValley_EventScript_Daryl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_POKEMANIAC_HECTOR, SixIsland_RuinValley_Text_HectorIntro, SixIsland_RuinValley_Text_HectorDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SixIsland_RuinValley_EventScript_HectorRematch
 * msgbox SixIsland_RuinValley_Text_HectorPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_Hector : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_RuinValley_EventScript_Hector")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HP_UP
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_ItemHPUp : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_RuinValley_EventScript_ItemHPUp")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_RESTORE
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_RuinValley_EventScript_ItemFullRestore")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SUN_STONE
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_ItemSunStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_RuinValley_EventScript_ItemSunStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_USED_CUT_ON_RUIN_VALLEY_BRAILLE, SixIsland_RuinValley_EventScript_DottedHoleDoorOpen
 * msgbox SixIsland_RuinValley_Text_CheckDoorMoreThoroughly, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, SixIsland_RuinValley_EventScript_IgnoreDottedHoleDoor
 * msgbox SixIsland_RuinValley_Text_SeveralDotsOnTheDoor
 * braillemessage Braille_Text_Cut
 * waitbuttonpress
 * releaseall
 * end
 * ```
 */
internal object SixIsland_RuinValley_EventScript_DottedHoleDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SixIsland_RuinValley_EventScript_DottedHoleDoor")
}

internal val SixIsland_RuinValleyScripts: Map<String, Script> =
    mapOf(
        "SixIsland_RuinValley_EventScript_Scientist" to SixIsland_RuinValley_EventScript_Scientist,
        "SixIsland_RuinValley_EventScript_Stanly" to SixIsland_RuinValley_EventScript_Stanly,
        "SixIsland_RuinValley_EventScript_Foster" to SixIsland_RuinValley_EventScript_Foster,
        "SixIsland_RuinValley_EventScript_Larry" to SixIsland_RuinValley_EventScript_Larry,
        "SixIsland_RuinValley_EventScript_Daryl" to SixIsland_RuinValley_EventScript_Daryl,
        "SixIsland_RuinValley_EventScript_Hector" to SixIsland_RuinValley_EventScript_Hector,
        "SixIsland_RuinValley_EventScript_ItemHPUp" to SixIsland_RuinValley_EventScript_ItemHPUp,
        "SixIsland_RuinValley_EventScript_ItemFullRestore" to
            SixIsland_RuinValley_EventScript_ItemFullRestore,
        "SixIsland_RuinValley_EventScript_ItemSunStone" to
            SixIsland_RuinValley_EventScript_ItemSunStone,
        "SixIsland_RuinValley_EventScript_DottedHoleDoor" to
            SixIsland_RuinValley_EventScript_DottedHoleDoor,
    )
