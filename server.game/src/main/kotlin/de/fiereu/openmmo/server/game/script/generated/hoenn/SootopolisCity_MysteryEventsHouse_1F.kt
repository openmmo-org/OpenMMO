package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * frontier_checkvisittrainer
 * goto_if_eq VAR_RESULT, 1, SootopolisCity_MysteryEventsHouse_1F_EventScript_InvalidVisitingTrainer
 * goto_if_eq VAR_TEMP_1, 1, SootopolisCity_MysteryEventsHouse_1F_EventScript_TrainerVisiting
 * msgbox SootopolisCity_MysteryEventsHouse_1F_Text_OnlyAmusementWatchingBattles, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SootopolisCity_MysteryEventsHouse_1F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_MysteryEventsHouse_1F_EventScript_OldMan")
}

internal val SootopolisCity_MysteryEventsHouse_1FScripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_MysteryEventsHouse_1F_EventScript_OldMan" to
            SootopolisCity_MysteryEventsHouse_1F_EventScript_OldMan,
    )
