package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PHILLIP, SSTidalLowerDeck_Text_PhillipIntro, SSTidalLowerDeck_Text_PhillipDefeat
 * msgbox SSTidalLowerDeck_Text_PhillipPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSTidalLowerDeck_EventScript_Phillip : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalLowerDeck_EventScript_Phillip")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LEONARD, SSTidalLowerDeck_Text_LeonardIntro, SSTidalLowerDeck_Text_LeonardDefeat
 * msgbox SSTidalLowerDeck_Text_LeonardPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSTidalLowerDeck_EventScript_Leonard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalLowerDeck_EventScript_Leonard")
}

internal val SSTidalLowerDeckScripts: Map<String, Script> =
    mapOf(
        "SSTidalLowerDeck_EventScript_Phillip" to SSTidalLowerDeck_EventScript_Phillip,
        "SSTidalLowerDeck_EventScript_Leonard" to SSTidalLowerDeck_EventScript_Leonard,
    )
