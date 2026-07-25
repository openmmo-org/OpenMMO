package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ERIC, JaggedPass_Text_EricIntro, JaggedPass_Text_EricDefeat
 * msgbox JaggedPass_Text_EricPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object JaggedPass_EventScript_Eric : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port JaggedPass_EventScript_Eric")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ETHAN_1, JaggedPass_Text_EthanIntro, JaggedPass_Text_EthanDefeat, JaggedPass_EventScript_RegisterEthan
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, JaggedPass_EventScript_EthanRematch
 * msgbox JaggedPass_Text_EthanPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object JaggedPass_EventScript_Ethan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port JaggedPass_EventScript_Ethan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BURN_HEAL
 * end
 * ```
 */
internal object JaggedPass_EventScript_ItemBurnHeal : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port JaggedPass_EventScript_ItemBurnHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DIANA_1, JaggedPass_Text_DianaIntro, JaggedPass_Text_DianaDefeat, JaggedPass_EventScript_RegisterDiana
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, JaggedPass_EventScript_DianaRematch
 * msgbox JaggedPass_Text_DianaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object JaggedPass_EventScript_Diana : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port JaggedPass_EventScript_Diana")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BEAT_MAGMA_GRUNT_JAGGED_PASS, JaggedPass_EventScript_GuardDefeated
 * waitse
 * playse SE_PIN
 * applymovement LOCALID_MAGMA_HIDEOUT_GUARD, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_MAGMA_HIDEOUT_GUARD, Common_Movement_Delay48
 * waitmovement 0
 * applymovement LOCALID_MAGMA_HIDEOUT_GUARD, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox JaggedPass_Text_GruntIntro, MSGBOX_DEFAULT
 * closemessage
 * trainerbattle_no_intro TRAINER_GRUNT_JAGGED_PASS, JaggedPass_Text_GruntDefeat
 * setflag FLAG_BEAT_MAGMA_GRUNT_JAGGED_PASS
 * applymovement LOCALID_MAGMA_HIDEOUT_GUARD, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object JaggedPass_EventScript_MagmaHideoutGuard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port JaggedPass_EventScript_MagmaHideoutGuard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_AUTUMN, JaggedPass_Text_AutumnIntro, JaggedPass_Text_AutumnDefeat
 * msgbox JaggedPass_Text_AutumnPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object JaggedPass_EventScript_Autumn : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port JaggedPass_EventScript_Autumn")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JULIO, JaggedPass_Text_JulioIntro, JaggedPass_Text_JulioDefeat
 * msgbox JaggedPass_Text_JulioPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object JaggedPass_EventScript_Julio : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port JaggedPass_EventScript_Julio")
}

internal val JaggedPassScripts: Map<String, Script> =
    mapOf(
        "JaggedPass_EventScript_Eric" to JaggedPass_EventScript_Eric,
        "JaggedPass_EventScript_Ethan" to JaggedPass_EventScript_Ethan,
        "JaggedPass_EventScript_ItemBurnHeal" to JaggedPass_EventScript_ItemBurnHeal,
        "JaggedPass_EventScript_Diana" to JaggedPass_EventScript_Diana,
        "JaggedPass_EventScript_MagmaHideoutGuard" to JaggedPass_EventScript_MagmaHideoutGuard,
        "JaggedPass_EventScript_Autumn" to JaggedPass_EventScript_Autumn,
        "JaggedPass_EventScript_Julio" to JaggedPass_EventScript_Julio,
    )
