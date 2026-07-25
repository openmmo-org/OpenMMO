package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, SSAnne_1F_Room4_EventScript_WomanPlayerMale
 * msgbox SSAnne_1F_Room4_Text_WaitressCherryPiePlease
 * release
 * end
 * ```
 */
internal object SSAnne_1F_Room4_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_1F_Room4_EventScript_Woman")
}

internal val SSAnne_1F_Room4Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_1F_Room4_EventScript_Woman" to SSAnne_1F_Room4_EventScript_Woman,
    )
