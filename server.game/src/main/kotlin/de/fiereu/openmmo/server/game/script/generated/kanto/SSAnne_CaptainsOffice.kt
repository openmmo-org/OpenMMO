package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_CaptainsOffice
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_set FLAG_GOT_HM01, SSAnne_CaptainsOffice_EventScript_AlreadyGotCut
 * msgbox SSAnne_CaptainsOffice_Text_CaptainIFeelSeasick
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * message SSAnne_CaptainsOffice_Text_RubbedCaptainsBack
 * waitmessage
 * playfanfare MUS_HEAL
 * waitfanfare
 * call EventScript_RestorePrevTextColor
 * delay 50
 * applymovement LOCALID_SS_ANNE_CAPTAIN, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox SSAnne_CaptainsOffice_Text_ThankYouHaveHMForCut
 * giveitem_msg SSAnne_CaptainsOffice_Text_ObtainedHM01FromCaptain, ITEM_HM01, 1, MUS_OBTAIN_KEY_ITEM
 * msgbox SSAnne_CaptainsOffice_Text_ExplainCut
 * setflag FLAG_GOT_HM01
 * setvar VAR_MAP_SCENE_VERMILION_CITY, 1
 * release
 * end
 * ```
 */
internal object SSAnne_CaptainsOffice_EventScript_Captain : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SSAnne_CaptainsOffice_EventScript_Captain")
}

internal object SSAnne_CaptainsOffice_EventScript_Book : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SSAnne_CaptainsOffice.HowToConquerSeasickness)
}

internal object SSAnne_CaptainsOffice_EventScript_TrashCan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SSAnne_CaptainsOffice.YuckShouldntHaveLooked)
}

internal val SSAnne_CaptainsOfficeScripts: Map<String, Script> =
    mapOf(
        "SSAnne_CaptainsOffice_EventScript_Captain" to SSAnne_CaptainsOffice_EventScript_Captain,
        "SSAnne_CaptainsOffice_EventScript_Book" to SSAnne_CaptainsOffice_EventScript_Book,
        "SSAnne_CaptainsOffice_EventScript_TrashCan" to SSAnne_CaptainsOffice_EventScript_TrashCan,
    )
