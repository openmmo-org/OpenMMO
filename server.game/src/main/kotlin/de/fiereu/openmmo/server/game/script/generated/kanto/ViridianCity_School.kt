package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox ViridianCity_School_Text_ReadBlackboardCarefully
 * closemessage
 * applymovement LOCALID_SCHOOL_WOMAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object ViridianCity_School_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_School_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox ViridianCity_School_Text_TryingToMemorizeNotes
 * closemessage
 * applymovement LOCALID_SCHOOL_LASS, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object ViridianCity_School_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_School_EventScript_Lass")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox ViridianCity_School_Text_NotebookFirstPage
 * msgbox ViridianCity_School_Text_TurnThePage, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, ViridianCity_School_EventScript_StopReadingNotebook
 * msgbox ViridianCity_School_Text_NotebookSecondPage
 * msgbox ViridianCity_School_Text_TurnThePage, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, ViridianCity_School_EventScript_StopReadingNotebook
 * msgbox ViridianCity_School_Text_NotebookThirdPage
 * msgbox ViridianCity_School_Text_TurnThePage, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, ViridianCity_School_EventScript_StopReadingNotebook
 * msgbox ViridianCity_School_Text_NotebookFourthPage
 * applymovement LOCALID_SCHOOL_LASS, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * textcolor NPC_TEXT_COLOR_FEMALE
 * msgbox ViridianCity_School_Text_HeyDontLookAtMyNotes
 * releaseall
 * end
 * ```
 */
internal object ViridianCity_School_EventScript_Notebook : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ViridianCity_School_EventScript_Notebook")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox ViridianCity_School_Text_BlackboardListsStatusProblems
 * goto ViridianCity_School_EventScript_ChooseBlackboardTopic
 * end
 * ```
 */
internal object ViridianCity_School_EventScript_Blackboard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ViridianCity_School_EventScript_Blackboard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_OAK, 3
 * famechecker FAMECHECKER_DAISY, 3
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureProfOak
 * release
 * end
 * ```
 */
internal object ViridianCity_School_EventScript_PokemonJournal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ViridianCity_School_EventScript_PokemonJournal")
}

internal val ViridianCity_SchoolScripts: Map<String, Script> =
    mapOf(
        "ViridianCity_School_EventScript_Woman" to ViridianCity_School_EventScript_Woman,
        "ViridianCity_School_EventScript_Lass" to ViridianCity_School_EventScript_Lass,
        "ViridianCity_School_EventScript_Notebook" to ViridianCity_School_EventScript_Notebook,
        "ViridianCity_School_EventScript_Blackboard" to ViridianCity_School_EventScript_Blackboard,
        "ViridianCity_School_EventScript_PokemonJournal" to
            ViridianCity_School_EventScript_PokemonJournal,
    )
