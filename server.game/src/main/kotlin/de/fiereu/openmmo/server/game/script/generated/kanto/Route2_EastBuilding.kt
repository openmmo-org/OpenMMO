package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route2_EastBuilding
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Route2_EastBuilding_EventScript_GetAideRequestInfo
 * goto_if_set FLAG_GOT_HM05, Route2_EastBuilding_EventScript_AlreadyGotHM05
 * msgbox Route2_EastBuilding_Text_GiveHM05IfSeen10Mons, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Aide_EventScript_DeclineCheckMons
 * setvar VAR_0x8004, 0
 * specialvar VAR_RESULT, GetPokedexCount
 * buffernumberstring STR_VAR_3, VAR_0x8006
 * call Route2_EastBuilding_EventScript_GetAideRequestInfo
 * goto_if_lt VAR_0x8006, REQUIRED_SEEN_MONS, Aide_EventScript_HaventCaughtEnough
 * msgbox Route2_EastBuilding_Text_GreatHereYouGo
 * checkitemspace ITEM_HM05
 * goto_if_eq VAR_RESULT, FALSE, Aide_EventScript_NoRoomForItem
 * giveitem_msg Route2_EastBuilding_Text_ReceivedHM05FromAide, ITEM_HM05
 * setflag FLAG_GOT_HM05
 * msgbox Route2_EastBuilding_Text_ExplainHM05
 * release
 * end
 * ```
 */
internal object Route2_EastBuilding_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route2_EastBuilding_EventScript_Aide")
}

internal object Route2_EastBuilding_EventScript_Rocker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route2_EastBuilding.CanGetThroughRockTunnel)
}

internal val Route2_EastBuildingScripts: Map<String, Script> =
    mapOf(
        "Route2_EastBuilding_EventScript_Aide" to Route2_EastBuilding_EventScript_Aide,
        "Route2_EastBuilding_EventScript_Rocker" to Route2_EastBuilding_EventScript_Rocker,
    )
