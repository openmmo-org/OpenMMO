package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PacifidlogTown_House5
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * specialvar VAR_RESULT, IsMirageIslandPresent
 * goto_if_eq VAR_RESULT, TRUE, PacifidlogTown_House5_EventScript_MirageIslandPresent
 * msgbox PacifidlogTown_House5_Text_CantSeeMirageIslandToday, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object PacifidlogTown_House5_EventScript_MirageIslandWatcher : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PacifidlogTown_House5_EventScript_MirageIslandWatcher")
}

internal object PacifidlogTown_House5_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PacifidlogTown_House5.MirageIslandAppearDependingOnWeather)
}

internal val PacifidlogTown_House5Scripts: Map<String, Script> =
    mapOf(
        "PacifidlogTown_House5_EventScript_MirageIslandWatcher" to
            PacifidlogTown_House5_EventScript_MirageIslandWatcher,
        "PacifidlogTown_House5_EventScript_Gentleman" to
            PacifidlogTown_House5_EventScript_Gentleman,
    )
