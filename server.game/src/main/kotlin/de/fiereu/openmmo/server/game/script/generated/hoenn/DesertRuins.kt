package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_REGIROCK, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setwildbattle SPECIES_REGIROCK, 40
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * special StartRegiBattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, DesertRuins_EventScript_DefeatedRegirock
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, DesertRuins_EventScript_RanFromRegirock
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, DesertRuins_EventScript_RanFromRegirock
 * setflag FLAG_DEFEATED_REGIROCK
 * release
 * end
 * ```
 */
internal object DesertRuins_EventScript_Regirock : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port DesertRuins_EventScript_Regirock")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_SYS_REGIROCK_PUZZLE_COMPLETED, DesertRuins_EventScript_BigHoleInWall
 * braillemsgbox DesertRuins_Braille_UseRockSmash
 * releaseall
 * end
 * ```
 */
internal object DesertRuins_EventScript_CaveEntranceMiddle : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port DesertRuins_EventScript_CaveEntranceMiddle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * braillemsgbox DesertRuins_Braille_UseRockSmash
 * releaseall
 * end
 * ```
 */
internal object DesertRuins_EventScript_CaveEntranceSide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port DesertRuins_EventScript_CaveEntranceSide")
}

internal val DesertRuinsScripts: Map<String, Script> =
    mapOf(
        "DesertRuins_EventScript_Regirock" to DesertRuins_EventScript_Regirock,
        "DesertRuins_EventScript_CaveEntranceMiddle" to DesertRuins_EventScript_CaveEntranceMiddle,
        "DesertRuins_EventScript_CaveEntranceSide" to DesertRuins_EventScript_CaveEntranceSide,
    )
