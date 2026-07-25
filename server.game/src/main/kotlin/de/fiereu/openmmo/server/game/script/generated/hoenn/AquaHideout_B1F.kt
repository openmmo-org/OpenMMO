package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_AQUA_HIDEOUT_2, AquaHideout_B1F_Text_Grunt2Intro, AquaHideout_B1F_Text_Grunt2Defeat, AquaHideout_B1F_EventScript_Grunt2Defeated
 * msgbox AquaHideout_B1F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object AquaHideout_B1F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_B1F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_AQUA_HIDEOUT_3, AquaHideout_B1F_Text_Grunt3Intro, AquaHideout_B1F_Text_Grunt3Defeat, AquaHideout_B1F_EventScript_Grunt3Defeated
 * msgbox AquaHideout_B1F_Text_Grunt3PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object AquaHideout_B1F_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_B1F_EventScript_Grunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ELIXIR
 * end
 * ```
 */
internal object AquaHideout_B1F_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AquaHideout_B1F_EventScript_ItemMaxElixir")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_AQUA_HIDEOUT_5, AquaHideout_B1F_Text_Grunt5Intro, AquaHideout_B1F_Text_Grunt5Defeat
 * msgbox AquaHideout_B1F_Text_Grunt5PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object AquaHideout_B1F_EventScript_Grunt5 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_B1F_EventScript_Grunt5")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MASTER_BALL
 * end
 * ```
 */
internal object AquaHideout_B1F_EventScript_ItemMasterBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AquaHideout_B1F_EventScript_ItemMasterBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setwildbattle SPECIES_ELECTRODE, 30
 * waitse
 * playmoncry SPECIES_ELECTRODE, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * dowildbattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, AquaHideout_B1F_EventScript_DefeatedElectrode1
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, AquaHideout_B1F_EventScript_DefeatedElectrode1
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, AquaHideout_B1F_EventScript_DefeatedElectrode1
 * setflag FLAG_DEFEATED_ELECTRODE_1_AQUA_HIDEOUT
 * release
 * end
 * ```
 */
internal object AquaHideout_B1F_EventScript_Electrode1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_B1F_EventScript_Electrode1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object AquaHideout_B1F_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_B1F_EventScript_ItemNugget")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setwildbattle SPECIES_ELECTRODE, 30
 * waitse
 * playmoncry SPECIES_ELECTRODE, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * dowildbattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, AquaHideout_B1F_EventScript_DefeatedElectrode2
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, AquaHideout_B1F_EventScript_DefeatedElectrode2
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, AquaHideout_B1F_EventScript_DefeatedElectrode2
 * setflag FLAG_DEFEATED_ELECTRODE_2_AQUA_HIDEOUT
 * release
 * end
 * ```
 */
internal object AquaHideout_B1F_EventScript_Electrode2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_B1F_EventScript_Electrode2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_AQUA_HIDEOUT_7, AquaHideout_B1F_Text_Grunt7Intro, AquaHideout_B1F_Text_Grunt7Defeat
 * msgbox AquaHideout_B1F_Text_Grunt7PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object AquaHideout_B1F_EventScript_Grunt7 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_B1F_EventScript_Grunt7")
}

internal val AquaHideout_B1FScripts: Map<String, Script> =
    mapOf(
        "AquaHideout_B1F_EventScript_Grunt2" to AquaHideout_B1F_EventScript_Grunt2,
        "AquaHideout_B1F_EventScript_Grunt3" to AquaHideout_B1F_EventScript_Grunt3,
        "AquaHideout_B1F_EventScript_ItemMaxElixir" to AquaHideout_B1F_EventScript_ItemMaxElixir,
        "AquaHideout_B1F_EventScript_Grunt5" to AquaHideout_B1F_EventScript_Grunt5,
        "AquaHideout_B1F_EventScript_ItemMasterBall" to AquaHideout_B1F_EventScript_ItemMasterBall,
        "AquaHideout_B1F_EventScript_Electrode1" to AquaHideout_B1F_EventScript_Electrode1,
        "AquaHideout_B1F_EventScript_ItemNugget" to AquaHideout_B1F_EventScript_ItemNugget,
        "AquaHideout_B1F_EventScript_Electrode2" to AquaHideout_B1F_EventScript_Electrode2,
        "AquaHideout_B1F_EventScript_Grunt7" to AquaHideout_B1F_EventScript_Grunt7,
    )
