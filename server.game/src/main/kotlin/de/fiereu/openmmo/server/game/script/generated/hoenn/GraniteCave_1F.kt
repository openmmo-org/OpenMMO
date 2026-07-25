package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_HM_FLASH, GraniteCave_1F_EventScript_ReceivedFlash
 * msgbox GraniteCave_1F_Text_GetsDarkAheadHereYouGo, MSGBOX_DEFAULT
 * giveitem ITEM_HM_FLASH
 * setflag FLAG_RECEIVED_HM_FLASH
 * msgbox GraniteCave_1F_Text_ExplainFlash, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object GraniteCave_1F_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port GraniteCave_1F_EventScript_Hiker")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object GraniteCave_1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port GraniteCave_1F_EventScript_ItemEscapeRope")
}

internal val GraniteCave_1FScripts: Map<String, Script> =
    mapOf(
        "GraniteCave_1F_EventScript_Hiker" to GraniteCave_1F_EventScript_Hiker,
        "GraniteCave_1F_EventScript_ItemEscapeRope" to GraniteCave_1F_EventScript_ItemEscapeRope,
    )
