package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GROUDON_AWAKENED_MAGMA_HIDEOUT, AquaHideout_1F_EventScript_SlateportHint1
 * goto_if_set FLAG_RECEIVED_RED_OR_BLUE_ORB, AquaHideout_1F_EventScript_MagmaHideoutHint1
 * msgbox AquaHideout_1F_Text_OurBossIsSnatchingSomething, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object AquaHideout_1F_EventScript_HideoutEntranceGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AquaHideout_1F_EventScript_HideoutEntranceGrunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GROUDON_AWAKENED_MAGMA_HIDEOUT, AquaHideout_1F_EventScript_SlateportHint2
 * goto_if_set FLAG_RECEIVED_RED_OR_BLUE_ORB, AquaHideout_1F_EventScript_MagmaHideoutHint2
 * msgbox AquaHideout_1F_Text_BossIsOnRoute122, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object AquaHideout_1F_EventScript_HideoutEntranceGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AquaHideout_1F_EventScript_HideoutEntranceGrunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_AQUA_HIDEOUT_1, AquaHideout_1F_Text_Grunt1Intro, AquaHideout_1F_Text_Grunt1Defeat, AquaHideout_1F_EventScript_Grunt1Defeated
 * msgbox AquaHideout_1F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object AquaHideout_1F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port AquaHideout_1F_EventScript_Grunt1")
}

internal val AquaHideout_1FScripts: Map<String, Script> =
    mapOf(
        "AquaHideout_1F_EventScript_HideoutEntranceGrunt1" to
            AquaHideout_1F_EventScript_HideoutEntranceGrunt1,
        "AquaHideout_1F_EventScript_HideoutEntranceGrunt2" to
            AquaHideout_1F_EventScript_HideoutEntranceGrunt2,
        "AquaHideout_1F_EventScript_Grunt1" to AquaHideout_1F_EventScript_Grunt1,
    )
