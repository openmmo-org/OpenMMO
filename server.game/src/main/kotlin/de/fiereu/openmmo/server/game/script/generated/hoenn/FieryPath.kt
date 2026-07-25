package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_TOXIC
 * end
 * ```
 */
internal object FieryPath_EventScript_ItemTMToxic : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FieryPath_EventScript_ItemTMToxic")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_unset FLAG_BADGE04_GET, EventScript_CantStrength
 * goto_if_set FLAG_SYS_USE_STRENGTH, EventScript_CheckActivatedBoulder
 * checkpartymove MOVE_STRENGTH
 * goto_if_eq VAR_RESULT, PARTY_SIZE, EventScript_CantStrength
 * setfieldeffectargument 0, VAR_RESULT
 * msgbox Text_WantToStrength, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_CancelStrength
 * closemessage
 * dofieldeffect FLDEFF_USE_STRENGTH
 * waitstate
 * goto EventScript_ActivateStrength
 * end
 * ```
 */
internal object EventScript_StrengthBoulder : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port EventScript_StrengthBoulder")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FIRE_STONE
 * end
 * ```
 */
internal object FieryPath_EventScript_ItemFireStone : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FieryPath_EventScript_ItemFireStone")
}

internal val FieryPathScripts: Map<String, Script> =
    mapOf(
        "FieryPath_EventScript_ItemTMToxic" to FieryPath_EventScript_ItemTMToxic,
        "EventScript_StrengthBoulder" to EventScript_StrengthBoulder,
        "FieryPath_EventScript_ItemFireStone" to FieryPath_EventScript_ItemFireStone,
    )
