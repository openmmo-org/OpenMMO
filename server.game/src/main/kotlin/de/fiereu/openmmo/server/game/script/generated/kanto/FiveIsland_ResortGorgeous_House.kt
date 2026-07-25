package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_ResortGorgeous_House
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * goto_if_set SHOWN_REQUESTED_MON, FiveIsland_ResortGorgeous_House_EventScript_JustFulfilledRequest
 * goto_if_eq VAR_RESORT_GORGEOUS_REQUESTED_MON, 0xFFFF, FiveIsland_ResortGorgeous_House_EventScript_RequestTookTooLong
 * goto_if_ne VAR_RESORT_GORGEOUS_REQUESTED_MON, SPECIES_NONE, FiveIsland_ResortGorgeous_House_EventScript_CheckForRequestedMon
 * msgbox FiveIsland_ResortGorgeous_House_Text_PleaseHearMyWish
 * goto FiveIsland_ResortGorgeous_House_EventScript_RequestMon
 * end
 * ```
 */
internal object FiveIsland_ResortGorgeous_House_EventScript_Selphy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_ResortGorgeous_House_EventScript_Selphy")
}

internal object FiveIsland_ResortGorgeous_House_EventScript_Butler : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FiveIsland_ResortGorgeous_House.LadySelphySmileHasBrillianceOfSun)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_BLAINE, 5
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureBlaine
 * releaseall
 * end
 * ```
 */
internal object FiveIsland_ResortGorgeous_House_EventScript_PokemonJournal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_ResortGorgeous_House_EventScript_PokemonJournal")
}

internal val FiveIsland_ResortGorgeous_HouseScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_ResortGorgeous_House_EventScript_Selphy" to
            FiveIsland_ResortGorgeous_House_EventScript_Selphy,
        "FiveIsland_ResortGorgeous_House_EventScript_Butler" to
            FiveIsland_ResortGorgeous_House_EventScript_Butler,
        "FiveIsland_ResortGorgeous_House_EventScript_PokemonJournal" to
            FiveIsland_ResortGorgeous_House_EventScript_PokemonJournal,
    )
