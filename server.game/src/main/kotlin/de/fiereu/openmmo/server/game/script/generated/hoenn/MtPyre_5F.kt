package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ATSUSHI, MtPyre_5F_Text_AtsushiIntro, MtPyre_5F_Text_AtsushiDefeat
 * msgbox MtPyre_5F_Text_AtsushiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_5F_EventScript_Atsushi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_5F_EventScript_Atsushi")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_LAX_INCENSE
 * end
 * ```
 */
internal object MtPyre_5F_EventScript_ItemLaxIncense : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_5F_EventScript_ItemLaxIncense")
}

internal val MtPyre_5FScripts: Map<String, Script> =
    mapOf(
        "MtPyre_5F_EventScript_Atsushi" to MtPyre_5F_EventScript_Atsushi,
        "MtPyre_5F_EventScript_ItemLaxIncense" to MtPyre_5F_EventScript_ItemLaxIncense,
    )
