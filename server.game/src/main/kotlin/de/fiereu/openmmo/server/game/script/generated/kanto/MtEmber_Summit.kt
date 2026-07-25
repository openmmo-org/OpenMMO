package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * setwildbattle SPECIES_MOLTRES, 50
 * waitse
 * playmoncry SPECIES_MOLTRES, CRY_MODE_ENCOUNTER
 * message Text_Gyaoo
 * waitmessage
 * waitmoncry
 * delay 10
 * playbgm MUS_ENCOUNTER_GYM_LEADER, 0
 * waitbuttonpress
 * setflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * special StartLegendaryBattle
 * waitstate
 * clearflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, MtEmber_Summit_EventScript_DefeatedMoltres
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, MtEmber_Summit_EventScript_RanFromMoltres
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, MtEmber_Summit_EventScript_RanFromMoltres
 * setflag FLAG_FOUGHT_MOLTRES
 * release
 * end
 * ```
 */
internal object MtEmber_Summit_EventScript_Moltres : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtEmber_Summit_EventScript_Moltres")
}

internal val MtEmber_SummitScripts: Map<String, Script> =
    mapOf(
        "MtEmber_Summit_EventScript_Moltres" to MtEmber_Summit_EventScript_Moltres,
    )
