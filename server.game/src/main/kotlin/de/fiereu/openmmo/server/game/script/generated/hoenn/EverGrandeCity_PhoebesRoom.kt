package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_ELITE_4_PHOEBE, EverGrandeCity_PhoebesRoom_EventScript_PostBattleSpeech
 * playbgm MUS_ENCOUNTER_ELITE_FOUR, FALSE
 * msgbox EverGrandeCity_PhoebesRoom_Text_IntroSpeech, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_PHOEBE, EverGrandeCity_PhoebesRoom_Text_Defeat
 * goto EverGrandeCity_PhoebesRoom_EventScript_Defeated
 * end
 * ```
 */
internal object EverGrandeCity_PhoebesRoom_EventScript_Phoebe : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port EverGrandeCity_PhoebesRoom_EventScript_Phoebe")
}

internal val EverGrandeCity_PhoebesRoomScripts: Map<String, Script> =
    mapOf(
        "EverGrandeCity_PhoebesRoom_EventScript_Phoebe" to
            EverGrandeCity_PhoebesRoom_EventScript_Phoebe,
    )
