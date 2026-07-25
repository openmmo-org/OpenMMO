package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message BattleFrontier_BattleTowerMultiPartnerRoom_Text_QuitLookingForPartner
 * waitmessage
 * multichoicedefault 20, 8, MULTI_YESNO, 1, FALSE
 * goto_if_eq VAR_RESULT, 0, BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_QuitChallenge
 * msgbox BattleFrontier_BattleTowerMultiPartnerRoom_Text_PleaseFindPartner2, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Attendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Attendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, 0
 * goto BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_TalkToPotentialPartner
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, 1
 * goto BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_TalkToPotentialPartner
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, 2
 * goto BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_TalkToPotentialPartner
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, 3
 * goto BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_TalkToPotentialPartner
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner4")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, 4
 * goto BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_TalkToPotentialPartner
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, 5
 * goto BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_TalkToPotentialPartner
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner6 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner6")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, 6
 * goto BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_TalkToPotentialPartner
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner7 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner7")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_TEMP_2, 7
 * goto BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_TalkToPotentialPartner
 * end
 * ```
 */
internal object BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner8 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner8")
}

internal val BattleFrontier_BattleTowerMultiPartnerRoomScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Attendant" to
            BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Attendant,
        "BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner1" to
            BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner1,
        "BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner2" to
            BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner2,
        "BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner3" to
            BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner3,
        "BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner4" to
            BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner4,
        "BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner5" to
            BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner5,
        "BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner6" to
            BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner6,
        "BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner7" to
            BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner7,
        "BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner8" to
            BattleFrontier_BattleTowerMultiPartnerRoom_EventScript_Partner8,
    )
