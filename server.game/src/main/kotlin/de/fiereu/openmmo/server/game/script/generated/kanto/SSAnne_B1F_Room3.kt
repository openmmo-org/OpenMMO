package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SAILOR_DYLAN, SSAnne_B1F_Room3_Text_DylanIntro, SSAnne_B1F_Room3_Text_DylanDefeat
 * msgbox SSAnne_B1F_Room3_Text_DylanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_B1F_Room3_EventScript_Dylan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_B1F_Room3_EventScript_Dylan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ETHER
 * end
 * ```
 */
internal object SSAnne_B1F_Room3_EventScript_ItemEther : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_B1F_Room3_EventScript_ItemEther")
}

internal val SSAnne_B1F_Room3Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_B1F_Room3_EventScript_Dylan" to SSAnne_B1F_Room3_EventScript_Dylan,
        "SSAnne_B1F_Room3_EventScript_ItemEther" to SSAnne_B1F_Room3_EventScript_ItemEther,
    )
