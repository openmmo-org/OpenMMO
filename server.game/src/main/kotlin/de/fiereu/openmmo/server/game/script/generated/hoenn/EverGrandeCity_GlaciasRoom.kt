package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_ELITE_4_GLACIA, EverGrandeCity_GlaciasRoom_EventScript_PostBattleSpeech
 * playbgm MUS_ENCOUNTER_ELITE_FOUR, FALSE
 * msgbox EverGrandeCity_GlaciasRoom_Text_IntroSpeech, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_GLACIA, EverGrandeCity_GlaciasRoom_Text_Defeat
 * goto EverGrandeCity_GlaciasRoom_EventScript_Defeated
 * end
 * ```
 */
internal object EverGrandeCity_GlaciasRoom_EventScript_Glacia : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port EverGrandeCity_GlaciasRoom_EventScript_Glacia")
}

internal val EverGrandeCity_GlaciasRoomScripts: Map<String, Script> =
    mapOf(
        "EverGrandeCity_GlaciasRoom_EventScript_Glacia" to
            EverGrandeCity_GlaciasRoom_EventScript_Glacia,
    )
