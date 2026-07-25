package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_ELITE_4_DRAKE, EverGrandeCity_DrakesRoom_EventScript_PostBattleSpeech
 * playbgm MUS_ENCOUNTER_ELITE_FOUR, FALSE
 * msgbox EverGrandeCity_DrakesRoom_Text_IntroSpeech, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_DRAKE, EverGrandeCity_DrakesRoom_Text_Defeat
 * goto EverGrandeCity_DrakesRoom_EventScript_Defeated
 * end
 * ```
 */
internal object EverGrandeCity_DrakesRoom_EventScript_Drake : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port EverGrandeCity_DrakesRoom_EventScript_Drake")
}

internal val EverGrandeCity_DrakesRoomScripts: Map<String, Script> =
    mapOf(
        "EverGrandeCity_DrakesRoom_EventScript_Drake" to
            EverGrandeCity_DrakesRoom_EventScript_Drake,
    )
