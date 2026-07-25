package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RODNEY, Route130_Text_RodneyIntro, Route130_Text_RodneyDefeat
 * msgbox Route130_Text_RodneyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route130_EventScript_Rodney : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route130_EventScript_Rodney")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KATIE, Route130_Text_KatieIntro, Route130_Text_KatieDefeat
 * msgbox Route130_Text_KatiePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route130_EventScript_Katie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route130_EventScript_Katie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SANTIAGO, Route130_Text_SantiagoIntro, Route130_Text_SantiagoDefeat
 * msgbox Route130_Text_SantiagoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route130_EventScript_Santiago : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route130_EventScript_Santiago")
}

internal val Route130Scripts: Map<String, Script> =
    mapOf(
        "Route130_EventScript_Rodney" to Route130_EventScript_Rodney,
        "Route130_EventScript_Katie" to Route130_EventScript_Katie,
        "Route130_EventScript_Santiago" to Route130_EventScript_Santiago,
    )
