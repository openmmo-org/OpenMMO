package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SeafoamIslands_B4F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * setwildbattle SPECIES_ARTICUNO, 50
 * waitse
 * playmoncry SPECIES_ARTICUNO, CRY_MODE_ENCOUNTER
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
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, SeafoamIslands_B4F_EventScript_DefeatedArticuno
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, SeafoamIslands_B4F_EventScript_RanFromArticuno
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, SeafoamIslands_B4F_EventScript_RanFromArticuno
 * setflag FLAG_FOUGHT_ARTICUNO
 * release
 * end
 * ```
 */
internal object SeafoamIslands_B4F_EventScript_Articuno : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafoamIslands_B4F_EventScript_Articuno")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ULTRA_BALL
 * end
 * ```
 */
internal object SeafoamIslands_B4F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafoamIslands_B4F_EventScript_ItemUltraBall")
}

internal object SeafoamIslands_B4F_EventScript_FastCurrentSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SeafoamIslands_B4F.DangerFastCurrent)
}

internal object SeafoamIslands_B4F_EventScript_BoulderHintSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SeafoamIslands_B4F.BouldersMightChangeWaterFlow)
}

internal val SeafoamIslands_B4FScripts: Map<String, Script> =
    mapOf(
        "SeafoamIslands_B4F_EventScript_Articuno" to SeafoamIslands_B4F_EventScript_Articuno,
        "SeafoamIslands_B4F_EventScript_ItemUltraBall" to
            SeafoamIslands_B4F_EventScript_ItemUltraBall,
        "SeafoamIslands_B4F_EventScript_FastCurrentSign" to
            SeafoamIslands_B4F_EventScript_FastCurrentSign,
        "SeafoamIslands_B4F_EventScript_BoulderHintSign" to
            SeafoamIslands_B4F_EventScript_BoulderHintSign,
    )
