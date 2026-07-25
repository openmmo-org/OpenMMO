package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ULTRA_BALL
 * end
 * ```
 */
internal object CeruleanCave_B1F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCave_B1F_EventScript_ItemUltraBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REVIVE
 * end
 * ```
 */
internal object CeruleanCave_B1F_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCave_B1F_EventScript_ItemMaxRevive")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_MEWTWO, CRY_MODE_ENCOUNTER
 * message CeruleanCave_B1F_Text_Mew
 * waitmessage
 * waitmoncry
 * delay 20
 * playbgm MUS_ENCOUNTER_GYM_LEADER, 0
 * waitbuttonpress
 * setwildbattle SPECIES_MEWTWO, 70
 * setflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * special StartLegendaryBattle
 * waitstate
 * clearflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, CeruleanCave_B1F_EventScript_DefeatedMewtwo
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, CeruleanCave_B1F_EventScript_RanFromMewtwo
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, CeruleanCave_B1F_EventScript_RanFromMewtwo
 * setflag FLAG_FOUGHT_MEWTWO
 * release
 * end
 * ```
 */
internal object CeruleanCave_B1F_EventScript_Mewtwo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCave_B1F_EventScript_Mewtwo")
}

internal val CeruleanCave_B1FScripts: Map<String, Script> =
    mapOf(
        "CeruleanCave_B1F_EventScript_ItemUltraBall" to CeruleanCave_B1F_EventScript_ItemUltraBall,
        "CeruleanCave_B1F_EventScript_ItemMaxRevive" to CeruleanCave_B1F_EventScript_ItemMaxRevive,
        "CeruleanCave_B1F_EventScript_Mewtwo" to CeruleanCave_B1F_EventScript_Mewtwo,
    )
