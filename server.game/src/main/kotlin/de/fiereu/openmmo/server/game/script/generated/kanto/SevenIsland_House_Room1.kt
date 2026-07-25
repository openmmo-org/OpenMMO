package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SevenIsland_House_Room1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * special ValidateEReaderTrainer
 * call_if_eq VAR_RESULT, 1, SevenIsland_House_Room1_EventScript_InvalidVisitingTrainer
 * goto_if_eq TRAINER_VISITING, TRUE, SevenIsland_House_Room1_EventScript_TrainerVisiting
 * msgbox SevenIsland_House_Room1_Text_OnlyEnjoymentWatchingBattles
 * release
 * end
 * ```
 */
internal object SevenIsland_House_Room1_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_House_Room1_EventScript_OldWoman")
}

internal object SevenIsland_House_Room1_EventScript_Box : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SevenIsland_House_Room1.SlightBreezeAroundBox)
}

internal val SevenIsland_House_Room1Scripts: Map<String, Script> =
    mapOf(
        "SevenIsland_House_Room1_EventScript_OldWoman" to
            SevenIsland_House_Room1_EventScript_OldWoman,
        "SevenIsland_House_Room1_EventScript_Box" to SevenIsland_House_Room1_EventScript_Box,
    )
