package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route15_WestEntrance_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Route15_WestEntrance_2F_EventScript_GetAideRequestInfo
 * goto_if_set FLAG_GOT_EXP_SHARE_FROM_OAKS_AIDE, Route15_WestEntrance_2F_EventScript_AlreadyGotExpShare
 * msgbox Route15_WestEntrance_2F_Text_GiveItemIfCaughtEnough, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Aide_EventScript_DeclineCheckMons
 * setvar VAR_0x8004, 0
 * specialvar VAR_RESULT, GetPokedexCount
 * buffernumberstring STR_VAR_3, VAR_0x8006
 * call Route15_WestEntrance_2F_EventScript_GetAideRequestInfo
 * goto_if_lt VAR_0x8006, REQUIRED_CAUGHT_MONS, Aide_EventScript_HaventCaughtEnough
 * msgbox Route15_WestEntrance_2F_Text_GreatHereYouGo
 * checkitemspace ITEM_EXP_SHARE
 * goto_if_eq VAR_RESULT, FALSE, Aide_EventScript_NoRoomForItem
 * giveitem_msg Route15_WestEntrance_2F_Text_ReceivedItemFromAide, ITEM_EXP_SHARE
 * setflag FLAG_GOT_EXP_SHARE_FROM_OAKS_AIDE
 * msgbox Route15_WestEntrance_2F_Text_ExplainExpShare
 * release
 * end
 * ```
 */
internal object Route15_WestEntrance_2F_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route15_WestEntrance_2F_EventScript_Aide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox Route15_WestEntrance_2F_Text_LargeShiningBird
 * showmonpic SPECIES_ARTICUNO, 10, 3
 * delay 20
 * waitbuttonpress
 * hidemonpic
 * setvar VAR_0x8004, SPECIES_ARTICUNO
 * special SetSeenMon
 * releaseall
 * end
 * ```
 */
internal object Route15_WestEntrance_2F_EventScript_LeftBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route15_WestEntrance_2F_EventScript_LeftBinoculars")
}

internal object Route15_WestEntrance_2F_EventScript_RightBinoculars : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(Route15_WestEntrance_2F.SmallIslandOnHorizon)
}

internal val Route15_WestEntrance_2FScripts: Map<String, Script> =
    mapOf(
        "Route15_WestEntrance_2F_EventScript_Aide" to Route15_WestEntrance_2F_EventScript_Aide,
        "Route15_WestEntrance_2F_EventScript_LeftBinoculars" to
            Route15_WestEntrance_2F_EventScript_LeftBinoculars,
        "Route15_WestEntrance_2F_EventScript_RightBinoculars" to
            Route15_WestEntrance_2F_EventScript_RightBinoculars,
    )
