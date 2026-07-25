package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * specialvar VAR_0x8008, StickerManGetBragFlags
 * goto_if_unset FLAG_MET_STICKER_MAN, FourIsland_House2_EventScript_MeetStickerMan
 * goto_if_set FLAG_MET_STICKER_MAN, FourIsland_House2_EventScript_StickerManAskForBrag
 * end
 * ```
 */
internal object FourIsland_House2_EventScript_StickerMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FourIsland_House2_EventScript_StickerMan")
}

internal val FourIsland_House2Scripts: Map<String, Script> =
    mapOf(
        "FourIsland_House2_EventScript_StickerMan" to FourIsland_House2_EventScript_StickerMan,
    )
