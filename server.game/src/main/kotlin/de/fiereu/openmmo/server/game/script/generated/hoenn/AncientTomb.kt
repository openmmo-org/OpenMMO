package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_REGISTEEL, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setwildbattle SPECIES_REGISTEEL, 40
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * special StartRegiBattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, AncientTomb_EventScript_DefeatedRegisteel
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, AncientTomb_EventScript_RanFromRegisteel
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, AncientTomb_EventScript_RanFromRegisteel
 * setflag FLAG_DEFEATED_REGISTEEL
 * release
 * end
 * ```
 */
internal object AncientTomb_EventScript_Registeel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AncientTomb_EventScript_Registeel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_SYS_REGISTEEL_PUZZLE_COMPLETED, AncientTomb_EventScript_BigHoleInWall
 * braillemsgbox AncientTomb_Braille_ShineInTheMiddle
 * releaseall
 * end
 * ```
 */
internal object AncientTomb_EventScript_CaveEntranceMiddle : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AncientTomb_EventScript_CaveEntranceMiddle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox AncientTomb_Braille_ShineInTheMiddle
 * releaseall
 * end
 * ```
 */
internal object AncientTomb_EventScript_CaveEntranceSide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AncientTomb_EventScript_CaveEntranceSide")
}

internal val AncientTombScripts: Map<String, Script> =
    mapOf(
        "AncientTomb_EventScript_Registeel" to AncientTomb_EventScript_Registeel,
        "AncientTomb_EventScript_CaveEntranceMiddle" to AncientTomb_EventScript_CaveEntranceMiddle,
        "AncientTomb_EventScript_CaveEntranceSide" to AncientTomb_EventScript_CaveEntranceSide,
    )
