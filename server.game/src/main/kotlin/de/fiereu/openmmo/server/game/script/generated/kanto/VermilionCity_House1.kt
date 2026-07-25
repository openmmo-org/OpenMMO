package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_OLD_ROD, VermilionCity_House1_EventScript_AlreadyGotOldRod
 * msgbox VermilionCity_House1_Text_ImFishingGuruDoYouLikeToFish, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, VermilionCity_House1_EventScript_GiveOldRod
 * msgbox VermilionCity_House1_Text_OhThatsSoDisappointing
 * release
 * end
 * ```
 */
internal object VermilionCity_House1_EventScript_FishingGuru : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_House1_EventScript_FishingGuru")
}

internal val VermilionCity_House1Scripts: Map<String, Script> =
    mapOf(
        "VermilionCity_House1_EventScript_FishingGuru" to
            VermilionCity_House1_EventScript_FishingGuru,
    )
