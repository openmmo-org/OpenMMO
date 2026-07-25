package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SAILOR_HUEY, SSAnne_B1F_Room2_Text_HueyIntro, SSAnne_B1F_Room2_Text_HueyDefeat
 * msgbox SSAnne_B1F_Room2_Text_HueyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_B1F_Room2_EventScript_Huey : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_B1F_Room2_EventScript_Huey")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM44
 * end
 * ```
 */
internal object SSAnne_B1F_Room2_EventScript_ItemTM44 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_B1F_Room2_EventScript_ItemTM44")
}

internal val SSAnne_B1F_Room2Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_B1F_Room2_EventScript_Huey" to SSAnne_B1F_Room2_EventScript_Huey,
        "SSAnne_B1F_Room2_EventScript_ItemTM44" to SSAnne_B1F_Room2_EventScript_ItemTM44,
    )
