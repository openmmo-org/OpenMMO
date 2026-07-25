package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * waitse
 * playmoncry SPECIES_RAYQUAZA, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setwildbattle SPECIES_RAYQUAZA, 70
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * special BattleSetup_StartLegendaryBattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, SkyPillar_Top_EventScript_DefeatedRayquaza
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, SkyPillar_Top_EventScript_RanFromRayquaza
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, SkyPillar_Top_EventScript_RanFromRayquaza
 * setflag FLAG_DEFEATED_RAYQUAZA
 * releaseall
 * end
 * ```
 */
internal object SkyPillar_Top_EventScript_Rayquaza : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SkyPillar_Top_EventScript_Rayquaza")
}

internal val SkyPillar_TopScripts: Map<String, Script> =
    mapOf(
        "SkyPillar_Top_EventScript_Rayquaza" to SkyPillar_Top_EventScript_Rayquaza,
    )
