package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ULTRA_BALL
 * end
 * ```
 */
internal object NewMauville_Inside_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port NewMauville_Inside_EventScript_ItemUltraBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object NewMauville_Inside_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port NewMauville_Inside_EventScript_ItemEscapeRope")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_THUNDER_STONE
 * end
 * ```
 */
internal object NewMauville_Inside_EventScript_ItemThunderStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port NewMauville_Inside_EventScript_ItemThunderStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_HEAL
 * end
 * ```
 */
internal object NewMauville_Inside_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port NewMauville_Inside_EventScript_ItemFullHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PARALYZE_HEAL
 * end
 * ```
 */
internal object NewMauville_Inside_EventScript_ItemParalyzeHeal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port NewMauville_Inside_EventScript_ItemParalyzeHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setwildbattle SPECIES_VOLTORB, 25
 * waitse
 * playmoncry SPECIES_VOLTORB, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * dowildbattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, NewMauville_Inside_EventScript_DefeatedVoltorb1
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, NewMauville_Inside_EventScript_DefeatedVoltorb1
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, NewMauville_Inside_EventScript_DefeatedVoltorb1
 * setflag FLAG_DEFEATED_VOLTORB_1_NEW_MAUVILLE
 * release
 * end
 * ```
 */
internal object NewMauville_Inside_EventScript_Voltorb1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port NewMauville_Inside_EventScript_Voltorb1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setwildbattle SPECIES_VOLTORB, 25
 * waitse
 * playmoncry SPECIES_VOLTORB, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * dowildbattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, NewMauville_Inside_EventScript_DefeatedVoltorb2
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, NewMauville_Inside_EventScript_DefeatedVoltorb2
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, NewMauville_Inside_EventScript_DefeatedVoltorb2
 * setflag FLAG_DEFEATED_VOLTORB_2_NEW_MAUVILLE
 * release
 * end
 * ```
 */
internal object NewMauville_Inside_EventScript_Voltorb2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port NewMauville_Inside_EventScript_Voltorb2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setwildbattle SPECIES_VOLTORB, 25
 * waitse
 * playmoncry SPECIES_VOLTORB, CRY_MODE_ENCOUNTER
 * delay 40
 * waitmoncry
 * setflag FLAG_SYS_CTRL_OBJ_DELETE
 * dowildbattle
 * clearflag FLAG_SYS_CTRL_OBJ_DELETE
 * specialvar VAR_RESULT, GetBattleOutcome
 * goto_if_eq VAR_RESULT, B_OUTCOME_WON, NewMauville_Inside_EventScript_DefeatedVoltorb3
 * goto_if_eq VAR_RESULT, B_OUTCOME_RAN, NewMauville_Inside_EventScript_DefeatedVoltorb3
 * goto_if_eq VAR_RESULT, B_OUTCOME_PLAYER_TELEPORTED, NewMauville_Inside_EventScript_DefeatedVoltorb3
 * setflag FLAG_DEFEATED_VOLTORB_3_NEW_MAUVILLE
 * release
 * end
 * ```
 */
internal object NewMauville_Inside_EventScript_Voltorb3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port NewMauville_Inside_EventScript_Voltorb3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_NEW_MAUVILLE_STATE, 2, NewMauville_Inside_EventScript_GeneratorOff
 * msgbox NewMauville_Inside_Text_GeneratorRadiatingHeat, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object NewMauville_Inside_EventScript_Generator : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port NewMauville_Inside_EventScript_Generator")
}

internal val NewMauville_InsideScripts: Map<String, Script> =
    mapOf(
        "NewMauville_Inside_EventScript_ItemUltraBall" to
            NewMauville_Inside_EventScript_ItemUltraBall,
        "NewMauville_Inside_EventScript_ItemEscapeRope" to
            NewMauville_Inside_EventScript_ItemEscapeRope,
        "NewMauville_Inside_EventScript_ItemThunderStone" to
            NewMauville_Inside_EventScript_ItemThunderStone,
        "NewMauville_Inside_EventScript_ItemFullHeal" to
            NewMauville_Inside_EventScript_ItemFullHeal,
        "NewMauville_Inside_EventScript_ItemParalyzeHeal" to
            NewMauville_Inside_EventScript_ItemParalyzeHeal,
        "NewMauville_Inside_EventScript_Voltorb1" to NewMauville_Inside_EventScript_Voltorb1,
        "NewMauville_Inside_EventScript_Voltorb2" to NewMauville_Inside_EventScript_Voltorb2,
        "NewMauville_Inside_EventScript_Voltorb3" to NewMauville_Inside_EventScript_Voltorb3,
        "NewMauville_Inside_EventScript_Generator" to NewMauville_Inside_EventScript_Generator,
    )
