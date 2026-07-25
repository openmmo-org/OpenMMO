package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FISHERMAN_DALE, SSAnne_2F_Room2_Text_DaleIntro, SSAnne_2F_Room2_Text_DaleDefeat
 * msgbox SSAnne_2F_Room2_Text_DalePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_2F_Room2_EventScript_Dale : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_2F_Room2_EventScript_Dale")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GENTLEMAN_BROOKS, SSAnne_2F_Room2_Text_BrooksIntro, SSAnne_2F_Room2_Text_BrooksDefeat
 * msgbox SSAnne_2F_Room2_Text_BrooksPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_2F_Room2_EventScript_Brooks : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_2F_Room2_EventScript_Brooks")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_STARDUST
 * end
 * ```
 */
internal object SSAnne_2F_Room2_EventScript_ItemStardust : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SSAnne_2F_Room2_EventScript_ItemStardust")
}

internal val SSAnne_2F_Room2Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_2F_Room2_EventScript_Dale" to SSAnne_2F_Room2_EventScript_Dale,
        "SSAnne_2F_Room2_EventScript_Brooks" to SSAnne_2F_Room2_EventScript_Brooks,
        "SSAnne_2F_Room2_EventScript_ItemStardust" to SSAnne_2F_Room2_EventScript_ItemStardust,
    )
