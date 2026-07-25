package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHELLY_SEAFLOOR_CAVERN, SeafloorCavern_Room3_Text_ShellyIntro, SeafloorCavern_Room3_Text_ShellyDefeat
 * msgbox SeafloorCavern_Room3_Text_ShellyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SeafloorCavern_Room3_EventScript_Shelly : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafloorCavern_Room3_EventScript_Shelly")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_SEAFLOOR_CAVERN_5, SeafloorCavern_Room3_Text_Grunt5Intro, SeafloorCavern_Room3_Text_Grunt5Defeat
 * msgbox SeafloorCavern_Room3_Text_Grunt5PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SeafloorCavern_Room3_EventScript_Grunt5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafloorCavern_Room3_EventScript_Grunt5")
}

internal val SeafloorCavern_Room3Scripts: Map<String, Script> =
    mapOf(
        "SeafloorCavern_Room3_EventScript_Shelly" to SeafloorCavern_Room3_EventScript_Shelly,
        "SeafloorCavern_Room3_EventScript_Grunt5" to SeafloorCavern_Room3_EventScript_Grunt5,
    )
