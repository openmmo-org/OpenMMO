package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_SEAFLOOR_CAVERN_3, SeafloorCavern_Room4_Text_Grunt3Intro, SeafloorCavern_Room4_Text_Grunt3Defeat
 * msgbox SeafloorCavern_Room4_Text_Grunt3PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SeafloorCavern_Room4_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafloorCavern_Room4_EventScript_Grunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_SEAFLOOR_CAVERN_4, SeafloorCavern_Room4_Text_Grunt4Intro, SeafloorCavern_Room4_Text_Grunt4Defeat
 * msgbox SeafloorCavern_Room4_Text_Grunt4PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SeafloorCavern_Room4_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SeafloorCavern_Room4_EventScript_Grunt4")
}

internal val SeafloorCavern_Room4Scripts: Map<String, Script> =
    mapOf(
        "SeafloorCavern_Room4_EventScript_Grunt3" to SeafloorCavern_Room4_EventScript_Grunt3,
        "SeafloorCavern_Room4_EventScript_Grunt4" to SeafloorCavern_Room4_EventScript_Grunt4,
    )
