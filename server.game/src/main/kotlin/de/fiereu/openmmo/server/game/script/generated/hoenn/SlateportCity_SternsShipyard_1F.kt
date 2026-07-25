package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity_SternsShipyard_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_SYS_GAME_CLEAR, SlateportCity_SternsShipyard_1F_EventScript_FerryReady
 * goto_if_set FLAG_BADGE07_GET, SlateportCity_SternsShipyard_1F_EventScript_BrineyJoined
 * goto_if_set FLAG_DELIVERED_DEVON_GOODS, SlateportCity_SternsShipyard_1F_EventScript_NeedVeteran
 * goto_if_set FLAG_DOCK_REJECTED_DEVON_GOODS, SlateportCity_SternsShipyard_1F_EventScript_GoFindStern
 * msgbox SlateportCity_SternsShipyard_1F_Text_CantMakeHeadsOrTails, MSGBOX_DEFAULT
 * applymovement LOCALID_DOCK, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox SlateportCity_SternsShipyard_1F_Text_MeetDockDeliverToStern, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_DOCK, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * setflag FLAG_DOCK_REJECTED_DEVON_GOODS
 * setflag FLAG_HIDE_SLATEPORT_CITY_TEAM_AQUA
 * releaseall
 * end
 * ```
 */
internal object SlateportCity_SternsShipyard_1F_EventScript_Dock : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_SternsShipyard_1F_EventScript_Dock")
}

internal object SlateportCity_SternsShipyard_1F_EventScript_Scientist1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_SternsShipyard_1F.SeaIsLikeLivingThing)
}

internal object SlateportCity_SternsShipyard_1F_EventScript_Scientist2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_SternsShipyard_1F.GetSeasickEasily)
}

internal object SlateportCity_SternsShipyard_1F_EventScript_Briney : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_SternsShipyard_1F.DecidedToHelpDock)
}

internal val SlateportCity_SternsShipyard_1FScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_SternsShipyard_1F_EventScript_Dock" to
            SlateportCity_SternsShipyard_1F_EventScript_Dock,
        "SlateportCity_SternsShipyard_1F_EventScript_Scientist1" to
            SlateportCity_SternsShipyard_1F_EventScript_Scientist1,
        "SlateportCity_SternsShipyard_1F_EventScript_Scientist2" to
            SlateportCity_SternsShipyard_1F_EventScript_Scientist2,
        "SlateportCity_SternsShipyard_1F_EventScript_Briney" to
            SlateportCity_SternsShipyard_1F_EventScript_Briney,
    )
