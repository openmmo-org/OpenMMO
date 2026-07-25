package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_set DID_CHANSEY_DANCE, SevenIsland_SevaultCanyon_House_EventScript_AlreadyDanced
 * playbgm MUS_SCHOOL, 0
 * applymovement LOCALID_CHANSEY_DANCE_MAN, SevenIsland_SevaultCanyon_House_Movement_ChanseyDance
 * waitmovement 0
 * fadedefaultbgm
 * delay 30
 * applymovement LOCALID_CHANSEY_DANCE_MAN, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox SevenIsland_SevaultCanyon_House_Text_ChanseyDanceJoinIn
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox SevenIsland_SevaultCanyon_House_Text_WouldYouLikeToDance, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, SevenIsland_SevaultCanyon_House_EventScript_DeclineDance
 * msgbox SevenIsland_SevaultCanyon_House_Text_DancedChanseyDance
 * closemessage
 * call EventScript_RestorePrevTextColor
 * call_if_ne VAR_FACING, DIR_SOUTH, SevenIsland_SevaultCanyon_House_EventScript_PlayerFaceDown
 * delay 30
 * playbgm MUS_SCHOOL, 0
 * applymovement LOCALID_PLAYER, SevenIsland_SevaultCanyon_House_Movement_ChanseyDance
 * waitmovement 0
 * fadedefaultbgm
 * delay 30
 * call EventScript_OutOfCenterPartyHeal
 * msgbox SevenIsland_SevaultCanyon_House_Text_YoureAllChipperNow
 * setflag DID_CHANSEY_DANCE
 * release
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_House_EventScript_ChanseyDanceMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_House_EventScript_ChanseyDanceMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_CHANSEY, CRY_MODE_NORMAL
 * msgbox SevenIsland_SevaultCanyon_House_Text_Chansey
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_House_EventScript_Chansey : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_House_EventScript_Chansey")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_LUCKY_PUNCH
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_House_EventScript_ItemLuckyPunch : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_House_EventScript_ItemLuckyPunch")
}

internal val SevenIsland_SevaultCanyon_HouseScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_SevaultCanyon_House_EventScript_ChanseyDanceMan" to
            SevenIsland_SevaultCanyon_House_EventScript_ChanseyDanceMan,
        "SevenIsland_SevaultCanyon_House_EventScript_Chansey" to
            SevenIsland_SevaultCanyon_House_EventScript_Chansey,
        "SevenIsland_SevaultCanyon_House_EventScript_ItemLuckyPunch" to
            SevenIsland_SevaultCanyon_House_EventScript_ItemLuckyPunch,
    )
