package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_HM02, Route16_House_EventScript_AlreadyGotHM02
 * msgbox Route16_House_Text_FoundMySecretRetreat
 * checkitemspace ITEM_HM02
 * goto_if_eq VAR_RESULT, FALSE, Route16_House_EventScript_NoRoomForHM02
 * giveitem_msg Route16_House_Text_ReceivedHM02FromGirl, ITEM_HM02
 * msgbox Route16_House_Text_ExplainHM02
 * setflag FLAG_GOT_HM02
 * release
 * end
 * ```
 */
internal object Route16_House_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_House_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_FEAROW, CRY_MODE_NORMAL
 * msgbox Route16_House_Text_Fearow
 * waitmoncry
 * release
 * end
 * ```
 */
internal object Route16_House_EventScript_Fearow : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route16_House_EventScript_Fearow")
}

internal val Route16_HouseScripts: Map<String, Script> =
    mapOf(
        "Route16_House_EventScript_Woman" to Route16_House_EventScript_Woman,
        "Route16_House_EventScript_Fearow" to Route16_House_EventScript_Fearow,
    )
