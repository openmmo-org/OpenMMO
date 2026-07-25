package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_ELITE_4_SIDNEY, EverGrandeCity_SidneysRoom_EventScript_PostBattleSpeech
 * playbgm MUS_ENCOUNTER_ELITE_FOUR, FALSE
 * msgbox EverGrandeCity_SidneysRoom_Text_IntroSpeech, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_SIDNEY, EverGrandeCity_SidneysRoom_Text_Defeat
 * goto EverGrandeCity_SidneysRoom_EventScript_Defeated
 * end
 * ```
 */
internal object EverGrandeCity_SidneysRoom_EventScript_Sidney : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port EverGrandeCity_SidneysRoom_EventScript_Sidney")
}

internal val EverGrandeCity_SidneysRoomScripts: Map<String, Script> =
    mapOf(
        "EverGrandeCity_SidneysRoom_EventScript_Sidney" to
            EverGrandeCity_SidneysRoom_EventScript_Sidney,
    )
