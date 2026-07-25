package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity_Harbor
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, SlateportCity_Harbor_EventScript_AskForTicket
 * msgbox SlateportCity_Harbor_Text_FerryServiceUnavailable, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_Harbor_EventScript_FerryAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_Harbor_EventScript_FerryAttendant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_SYS_GAME_CLEAR, SlateportCity_Harbor_EventScript_SailorNoAbnormalWeather
 * setvar VAR_0x8004, 0
 * call_if_set FLAG_DEFEATED_KYOGRE, SlateportCity_Harbor_EventScript_CountDefeatedLegendary
 * call_if_set FLAG_DEFEATED_GROUDON, SlateportCity_Harbor_EventScript_CountDefeatedLegendary
 * goto_if_eq VAR_0x8004, 2, SlateportCity_Harbor_EventScript_SailorNoAbnormalWeather  @ Defeated both
 * msgbox SlateportCity_Harbor_Text_AbnormalWeather, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SlateportCity_Harbor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_Harbor_EventScript_Sailor")
}

internal object SlateportCity_Harbor_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SlateportCity_Harbor.SubTooSmallForMe)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_BADGE07_GET, SlateportCity_Harbor_EventScript_CaptSternFerryOrScannerComment
 * goto_if_set FLAG_EVIL_TEAM_ESCAPED_STERN_SPOKE, SlateportCity_Harbor_EventScript_NeedDive
 * goto_if_set FLAG_TEAM_AQUA_ESCAPED_IN_SUBMARINE, SlateportCity_Harbor_EventScript_TeamAquaLeftNeedDive
 * goto_if_eq VAR_SLATEPORT_HARBOR_STATE, 2, SlateportCity_Harbor_EventScript_WhyStealSubmarine
 * msgbox SlateportCity_Harbor_Text_SameThugsTriedToRobAtMuseum, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_Harbor_EventScript_CaptStern : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_Harbor_EventScript_CaptStern")
}

internal val SlateportCity_HarborScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_Harbor_EventScript_FerryAttendant" to
            SlateportCity_Harbor_EventScript_FerryAttendant,
        "SlateportCity_Harbor_EventScript_Sailor" to SlateportCity_Harbor_EventScript_Sailor,
        "SlateportCity_Harbor_EventScript_FatMan" to SlateportCity_Harbor_EventScript_FatMan,
        "SlateportCity_Harbor_EventScript_CaptStern" to SlateportCity_Harbor_EventScript_CaptStern,
    )
