package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_SEAFLOOR_CAVERN_1, SeafloorCavern_Room1_Text_Grunt1Intro, SeafloorCavern_Room1_Text_Grunt1Defeat
 * msgbox SeafloorCavern_Room1_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SeafloorCavern_Room1_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafloorCavern_Room1_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_SEAFLOOR_CAVERN_2, SeafloorCavern_Room1_Text_Grunt2Intro, SeafloorCavern_Room1_Text_Grunt2Defeat
 * msgbox SeafloorCavern_Room1_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SeafloorCavern_Room1_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafloorCavern_Room1_EventScript_Grunt2")
}

internal val SeafloorCavern_Room1Scripts: Map<String, Script> =
    mapOf(
        "SeafloorCavern_Room1_EventScript_Grunt1" to SeafloorCavern_Room1_EventScript_Grunt1,
        "SeafloorCavern_Room1_EventScript_Grunt2" to SeafloorCavern_Room1_EventScript_Grunt2,
    )
