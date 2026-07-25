package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route110_TrickHouseEnd_Text_YouveMadeItToMe, MSGBOX_DEFAULT
 * setvar VAR_TEMP_2, 1
 * switch VAR_TRICK_HOUSE_LEVEL
 * case 0, Route110_TrickHouseEnd_EventScript_CompletedPuzzle1
 * case 1, Route110_TrickHouseEnd_EventScript_CompletedPuzzle2
 * case 2, Route110_TrickHouseEnd_EventScript_CompletedPuzzle3
 * case 3, Route110_TrickHouseEnd_EventScript_CompletedPuzzle4
 * case 4, Route110_TrickHouseEnd_EventScript_CompletedPuzzle5
 * case 5, Route110_TrickHouseEnd_EventScript_CompletedPuzzle6
 * case 6, Route110_TrickHouseEnd_EventScript_CompletedPuzzle7
 * case 7, Route110_TrickHouseEnd_EventScript_CompletedPuzzle8
 * end
 * ```
 */
internal object Route110_TrickHouseEnd_EventScript_TrickMaster : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route110_TrickHouseEnd_EventScript_TrickMaster")
}

internal val Route110_TrickHouseEndScripts: Map<String, Script> =
    mapOf(
        "Route110_TrickHouseEnd_EventScript_TrickMaster" to
            Route110_TrickHouseEnd_EventScript_TrickMaster,
    )
