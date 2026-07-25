package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * delay 20
 * playse SE_THUNDERSTORM_STOP
 * setvar VAR_0x8004, 0  @ vertical pan
 * setvar VAR_0x8005, 3  @ horizontal pan
 * setvar VAR_0x8006, 4  @ num shakes
 * setvar VAR_0x8007, 2  @ shake delay
 * special ShakeCamera
 * delay 30
 * playse SE_THUNDERSTORM_STOP
 * setvar VAR_0x8004, 0  @ vertical pan
 * setvar VAR_0x8005, 3  @ horizontal pan
 * setvar VAR_0x8006, 4  @ num shakes
 * setvar VAR_0x8007, 2  @ shake delay
 * special ShakeCamera
 * delay 30
 * delay 50
 * waitse
 * playmoncry SPECIES_LUGIA, CRY_MODE_ENCOUNTER
 * waitmoncry
 * delay 20
 * seteventmon SPECIES_LUGIA, 70
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * special BattleSetup_StartLegendaryBattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, NavelRock_Bottom_EventScript_DefeatedLugia
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, NavelRock_Bottom_EventScript_RanFromLugia
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, NavelRock_Bottom_EventScript_RanFromLugia
 * setflag FLAG_CAUGHT_LUGIA
 * release
 * end
 * ```
 */
internal object NavelRock_Bottom_EventScript_Lugia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port NavelRock_Bottom_EventScript_Lugia")
}

internal val NavelRock_BottomScripts: Map<String, Script> =
    mapOf(
        "NavelRock_Bottom_EventScript_Lugia" to NavelRock_Bottom_EventScript_Lugia,
    )
