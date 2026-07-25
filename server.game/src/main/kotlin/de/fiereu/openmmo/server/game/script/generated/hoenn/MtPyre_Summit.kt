package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SOOTOPOLIS_ARCHIE_MAXIE_LEAVE, MtPyre_Summit_EventScript_OldManAfterRayquaza
 * msgbox MtPyre_Summit_Text_WillYouHearOutMyTale, MSGBOX_YESNO
 * call_if_eq VAR_RESULT, YES, MtPyre_Summit_EventScript_OldManTale
 * call_if_eq VAR_RESULT, NO, MtPyre_Summit_EventScript_DeclineOldManTale
 * release
 * end
 * ```
 */
internal object MtPyre_Summit_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_Summit_EventScript_OldMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RETURNED_RED_OR_BLUE_ORB, MtPyre_Summit_EventScript_OldLadyAfterOrbsReturned
 * call_if_ge VAR_MT_PYRE_STATE, 3, MtPyre_Summit_EventScript_OldLadyOrbsReturned
 * goto_if_set FLAG_KYOGRE_ESCAPED_SEAFLOOR_CAVERN, MtPyre_Summit_EventScript_OldLadyLegendariesAwake
 * msgbox MtPyre_Summit_Text_OrbsHaveBeenTaken, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MtPyre_Summit_EventScript_OldLady : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_Summit_EventScript_OldLady")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MT_PYRE_1, MtPyre_Summit_Text_Grunt1Intro, MtPyre_Summit_Text_Grunt1Defeat
 * msgbox MtPyre_Summit_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_Summit_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_Summit_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MT_PYRE_2, MtPyre_Summit_Text_Grunt2Intro, MtPyre_Summit_Text_Grunt2Defeat
 * msgbox MtPyre_Summit_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_Summit_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_Summit_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MT_PYRE_3, MtPyre_Summit_Text_Grunt3Intro, MtPyre_Summit_Text_Grunt3Defeat
 * msgbox MtPyre_Summit_Text_Grunt3PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_Summit_EventScript_Grunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_Summit_EventScript_Grunt3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MT_PYRE_4, MtPyre_Summit_Text_Grunt4Intro, MtPyre_Summit_Text_Grunt4Defeat
 * msgbox MtPyre_Summit_Text_Grunt4PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_Summit_EventScript_Grunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_Summit_EventScript_Grunt4")
}

internal val MtPyre_SummitScripts: Map<String, Script> =
    mapOf(
        "MtPyre_Summit_EventScript_OldMan" to MtPyre_Summit_EventScript_OldMan,
        "MtPyre_Summit_EventScript_OldLady" to MtPyre_Summit_EventScript_OldLady,
        "MtPyre_Summit_EventScript_Grunt1" to MtPyre_Summit_EventScript_Grunt1,
        "MtPyre_Summit_EventScript_Grunt2" to MtPyre_Summit_EventScript_Grunt2,
        "MtPyre_Summit_EventScript_Grunt3" to MtPyre_Summit_EventScript_Grunt3,
        "MtPyre_Summit_EventScript_Grunt4" to MtPyre_Summit_EventScript_Grunt4,
    )
