package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GENTLEMAN_ARTHUR, SSAnne_1F_Room5_Text_ArthurIntro, SSAnne_1F_Room5_Text_ArthurDefeat
 * msgbox SSAnne_1F_Room5_Text_ArthurPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_1F_Room5_EventScript_Arthur : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_1F_Room5_EventScript_Arthur")
}

internal val SSAnne_1F_Room5Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room5_EventScript_Arthur" to SSAnne_1F_Room5_EventScript_Arthur,
    )
