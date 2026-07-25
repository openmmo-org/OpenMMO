package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_POTION
 * end
 * ```
 */
internal object PowerPlant_EventScript_ItemMaxPotion : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PowerPlant_EventScript_ItemMaxPotion")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM17
 * end
 * ```
 */
internal object PowerPlant_EventScript_ItemTM17 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PowerPlant_EventScript_ItemTM17")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM25
 * end
 * ```
 */
internal object PowerPlant_EventScript_ItemTM25 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PowerPlant_EventScript_ItemTM25")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_THUNDER_STONE
 * end
 * ```
 */
internal object PowerPlant_EventScript_ItemThunderStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PowerPlant_EventScript_ItemThunderStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ELIXIR
 * end
 * ```
 */
internal object PowerPlant_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PowerPlant_EventScript_ItemElixir")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * setwildbattle SPECIES_ZAPDOS, 50
 * waitse
 * playmoncry SPECIES_ZAPDOS, CRY_MODE_ENCOUNTER
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
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, PowerPlant_EventScript_DefeatedZapdos
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, PowerPlant_EventScript_RanFromZapdos
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, PowerPlant_EventScript_RanFromZapdos
 * setflag FLAG_FOUGHT_ZAPDOS
 * release
 * end
 * ```
 */
internal object PowerPlant_EventScript_Zapdos : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PowerPlant_EventScript_Zapdos")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * setwildbattle SPECIES_ELECTRODE, 34
 * waitse
 * playmoncry SPECIES_ELECTRODE, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * dowildbattle
 * clearflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * special QuestLog_CutRecording
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, PowerPlant_EventScript_FoughtElectrode2
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, PowerPlant_EventScript_FoughtElectrode2
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, PowerPlant_EventScript_FoughtElectrode2
 * setflag FLAG_FOUGHT_POWER_PLANT_ELECTRODE_2
 * release
 * end
 * ```
 */
internal object PowerPlant_EventScript_Electrode2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PowerPlant_EventScript_Electrode2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * setwildbattle SPECIES_ELECTRODE, 34
 * waitse
 * playmoncry SPECIES_ELECTRODE, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * dowildbattle
 * clearflag FLAG_SYS_SPECIAL_WILD_BATTLE
 * special QuestLog_CutRecording
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, PowerPlant_EventScript_FoughtElectrode1
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, PowerPlant_EventScript_FoughtElectrode1
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, PowerPlant_EventScript_FoughtElectrode1
 * setflag FLAG_FOUGHT_POWER_PLANT_ELECTRODE_1
 * release
 * end
 * ```
 */
internal object PowerPlant_EventScript_Electrode1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PowerPlant_EventScript_Electrode1")
}

internal val PowerPlantScripts: Map<String, Script> =
    mapOf(
        "PowerPlant_EventScript_ItemMaxPotion" to PowerPlant_EventScript_ItemMaxPotion,
        "PowerPlant_EventScript_ItemTM17" to PowerPlant_EventScript_ItemTM17,
        "PowerPlant_EventScript_ItemTM25" to PowerPlant_EventScript_ItemTM25,
        "PowerPlant_EventScript_ItemThunderStone" to PowerPlant_EventScript_ItemThunderStone,
        "PowerPlant_EventScript_ItemElixir" to PowerPlant_EventScript_ItemElixir,
        "PowerPlant_EventScript_Zapdos" to PowerPlant_EventScript_Zapdos,
        "PowerPlant_EventScript_Electrode2" to PowerPlant_EventScript_Electrode2,
        "PowerPlant_EventScript_Electrode1" to PowerPlant_EventScript_Electrode1,
    )
