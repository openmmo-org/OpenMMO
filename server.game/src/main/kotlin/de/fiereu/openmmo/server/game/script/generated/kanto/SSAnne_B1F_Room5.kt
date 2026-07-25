package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SSAnne_B1F_Room5
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SSAnne_B1F_Room5_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SSAnne_B1F_Room5.MachokeHasStrengthToMoveRocks)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_MACHOKE, CRY_MODE_NORMAL
 * msgbox SSAnne_B1F_Room5_Text_Machoke
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SSAnne_B1F_Room5_EventScript_Machoke : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_B1F_Room5_EventScript_Machoke")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SUPER_POTION
 * end
 * ```
 */
internal object SSAnne_B1F_Room5_EventScript_ItemSuperPotion : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SSAnne_B1F_Room5_EventScript_ItemSuperPotion")
}

internal val SSAnne_B1F_Room5Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_B1F_Room5_EventScript_Boy" to SSAnne_B1F_Room5_EventScript_Boy,
        "SSAnne_B1F_Room5_EventScript_Machoke" to SSAnne_B1F_Room5_EventScript_Machoke,
        "SSAnne_B1F_Room5_EventScript_ItemSuperPotion" to
            SSAnne_B1F_Room5_EventScript_ItemSuperPotion,
    )
