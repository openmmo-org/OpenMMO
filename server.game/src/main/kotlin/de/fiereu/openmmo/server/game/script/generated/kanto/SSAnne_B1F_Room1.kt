package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FISHERMAN_BARNY, SSAnne_B1F_Room1_Text_BarnyIntro, SSAnne_B1F_Room1_Text_BarnyDefeat
 * msgbox SSAnne_B1F_Room1_Text_BarnyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_B1F_Room1_EventScript_Barny : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_B1F_Room1_EventScript_Barny")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SAILOR_PHILLIP, SSAnne_B1F_Room1_Text_PhillipIntro, SSAnne_B1F_Room1_Text_PhillipDefeat
 * msgbox SSAnne_B1F_Room1_Text_PhillipPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSAnne_B1F_Room1_EventScript_Phillip : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSAnne_B1F_Room1_EventScript_Phillip")
}

internal val SSAnne_B1F_Room1Scripts: Map<String, Script> =
    mapOf(
        "SSAnne_B1F_Room1_EventScript_Barny" to SSAnne_B1F_Room1_EventScript_Barny,
        "SSAnne_B1F_Room1_EventScript_Phillip" to SSAnne_B1F_Room1_EventScript_Phillip,
    )
