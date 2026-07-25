package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_GOOD_ROD, FuchsiaCity_House2_EventScript_AlreadyGotGoodRod
 * msgbox FuchsiaCity_House2_Text_DoYouLikeToFish, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, FuchsiaCity_House2_EventScript_GiveGoodRod
 * msgbox FuchsiaCity_House2_Text_OhThatsDisappointing
 * release
 * end
 * ```
 */
internal object FuchsiaCity_House2_EventScript_FishingGurusBrother : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FuchsiaCity_House2_EventScript_FishingGurusBrother")
}

internal val FuchsiaCity_House2Scripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_House2_EventScript_FishingGurusBrother" to
            FuchsiaCity_House2_EventScript_FishingGurusBrother,
    )
