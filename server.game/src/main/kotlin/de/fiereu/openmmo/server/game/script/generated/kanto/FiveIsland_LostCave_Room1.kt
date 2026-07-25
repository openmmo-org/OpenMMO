package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RUIN_MANIAC_LAWSON, FiveIsland_LostCave_Room1_Text_LawsonIntro, FiveIsland_LostCave_Room1_Text_LawsonDefeat
 * msgbox FiveIsland_LostCave_Room1_Text_LawsonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_LostCave_Room1_EventScript_Lawson : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_LostCave_Room1_EventScript_Lawson")
}

internal val FiveIsland_LostCave_Room1Scripts: Map<String, Script> =
    mapOf(
        "FiveIsland_LostCave_Room1_EventScript_Lawson" to
            FiveIsland_LostCave_Room1_EventScript_Lawson,
    )
