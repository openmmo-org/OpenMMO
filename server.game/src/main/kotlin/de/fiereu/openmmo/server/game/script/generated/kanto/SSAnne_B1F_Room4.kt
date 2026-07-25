package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SAILOR_DUNCAN, SSAnne_B1F_Room4_Text_DuncanIntro, SSAnne_B1F_Room4_Text_DuncanDefeat
 * msgbox SSAnne_B1F_Room4_Text_DuncanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_B1F_Room4_EventScript_Duncan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_B1F_Room4_EventScript_Duncan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SAILOR_LEONARD, SSAnne_B1F_Room4_Text_LeonardIntro, SSAnne_B1F_Room4_Text_LeonardDefeat
 * msgbox SSAnne_B1F_Room4_Text_LeonardPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_B1F_Room4_EventScript_Leonard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_B1F_Room4_EventScript_Leonard")
}

internal val SSAnne_B1F_Room4Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_B1F_Room4_EventScript_Duncan" to SSAnne_B1F_Room4_EventScript_Duncan,
        "SSAnne_B1F_Room4_EventScript_Leonard" to SSAnne_B1F_Room4_EventScript_Leonard,
    )
