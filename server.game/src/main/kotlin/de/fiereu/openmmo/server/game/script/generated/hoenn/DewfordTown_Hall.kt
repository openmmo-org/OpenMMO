package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Common_EventScript_BufferTrendyPhrase
 * special IsTrendyPhraseBoring
 * goto_if_eq VAR_RESULT, TRUE, DewfordTown_Hall_EventScript_GirlBoredOfTrend
 * msgbox DewfordTown_Hall_Text_CantImagineLifeWithoutTrend, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_Hall_EventScript_Girl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Common_EventScript_BufferTrendyPhrase
 * msgbox DewfordTown_Hall_Text_TeachingMonAboutTrend, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_Hall_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Common_EventScript_BufferTrendyPhrase
 * special BufferDeepLinkPhrase
 * msgbox DewfordTown_Hall_Text_DeepLinkBetweenXAndY, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, DewfordTown_Hall_EventScript_ConfirmTrendLink
 * goto_if_eq VAR_RESULT, NO, DewfordTown_Hall_EventScript_RejectTrendLink
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_Hall_EventScript_Man")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Common_EventScript_BufferTrendyPhrase
 * msgbox DewfordTown_Hall_Text_TVShowAboutTrend, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_DEWFORD_HALL_EXPERT_M, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_Hall_EventScript_ExpertM")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Common_EventScript_BufferTrendyPhrase
 * msgbox DewfordTown_Hall_Text_IsTrendMorePopularAcrossSea, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_DEWFORD_HALL_TWIN, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_Twin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_Hall_EventScript_Twin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Common_EventScript_BufferTrendyPhrase
 * msgbox DewfordTown_Hall_Text_CollectTrendMerchandise, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_Hall_EventScript_LittleBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8008, 0
 * goto DewfordTown_Hall_EventScript_DoTrendDebate
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_SchoolKidM : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port DewfordTown_Hall_EventScript_SchoolKidM")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8008, 1
 * goto DewfordTown_Hall_EventScript_DoTrendDebate
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_PsychicM : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_Hall_EventScript_PsychicM")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Common_EventScript_BufferTrendyPhrase
 * goto_if_set FLAG_RECEIVED_TM_SLUDGE_BOMB, DewfordTown_Hall_EventScript_ReceivedSludgeBomb
 * msgbox DewfordTown_Hall_Text_GiveYouSludgeBomb, MSGBOX_DEFAULT
 * giveitem ITEM_TM_SLUDGE_BOMB
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_SLUDGE_BOMB
 * release
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_SludgeBombMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port DewfordTown_Hall_EventScript_SludgeBombMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * call Common_EventScript_BufferTrendyPhrase
 * msgbox DewfordTown_Hall_Text_BooksAboutTrend, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_Bookshelf : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_Hall_EventScript_Bookshelf")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * call Common_EventScript_BufferTrendyPhrase
 * special GetDewfordHallPaintingNameIndex
 * switch VAR_RESULT
 * case 0, DewfordTown_Hall_EventScript_ScreamTitle
 * case 4, DewfordTown_Hall_EventScript_ScreamTitle
 * case 1, DewfordTown_Hall_EventScript_SmileTitle
 * case 5, DewfordTown_Hall_EventScript_ScreamTitle
 * case 2, DewfordTown_Hall_EventScript_LastTitle
 * case 6, DewfordTown_Hall_EventScript_LastTitle
 * case 3, DewfordTown_Hall_EventScript_BirthTitle
 * case 7, DewfordTown_Hall_EventScript_LastTitle
 * end
 * ```
 */
internal object DewfordTown_Hall_EventScript_Painting : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DewfordTown_Hall_EventScript_Painting")
}

internal val DewfordTown_HallScripts: Map<String, Script> =
    mapOf(
        "DewfordTown_Hall_EventScript_Girl" to DewfordTown_Hall_EventScript_Girl,
        "DewfordTown_Hall_EventScript_Woman" to DewfordTown_Hall_EventScript_Woman,
        "DewfordTown_Hall_EventScript_Man" to DewfordTown_Hall_EventScript_Man,
        "DewfordTown_Hall_EventScript_ExpertM" to DewfordTown_Hall_EventScript_ExpertM,
        "DewfordTown_Hall_EventScript_Twin" to DewfordTown_Hall_EventScript_Twin,
        "DewfordTown_Hall_EventScript_LittleBoy" to DewfordTown_Hall_EventScript_LittleBoy,
        "DewfordTown_Hall_EventScript_SchoolKidM" to DewfordTown_Hall_EventScript_SchoolKidM,
        "DewfordTown_Hall_EventScript_PsychicM" to DewfordTown_Hall_EventScript_PsychicM,
        "DewfordTown_Hall_EventScript_SludgeBombMan" to DewfordTown_Hall_EventScript_SludgeBombMan,
        "DewfordTown_Hall_EventScript_Bookshelf" to DewfordTown_Hall_EventScript_Bookshelf,
        "DewfordTown_Hall_EventScript_Painting" to DewfordTown_Hall_EventScript_Painting,
    )
