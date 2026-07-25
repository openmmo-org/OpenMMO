package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GENTLEMAN_THOMAS, SSAnne_1F_Room7_Text_ThomasIntro, SSAnne_1F_Room7_Text_ThomasDefeat
 * msgbox SSAnne_1F_Room7_Text_ThomasPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_1F_Room7_EventScript_Thomas : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_1F_Room7_EventScript_Thomas")
}

internal val SSAnne_1F_Room7Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room7_EventScript_Thomas" to SSAnne_1F_Room7_EventScript_Thomas,
    )
