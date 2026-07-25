package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WATTSON_1, MauvilleCity_Gym_Text_WattsonIntro, MauvilleCity_Gym_Text_WattsonDefeat, MauvilleCity_Gym_EventScript_WattsonDefeated, NO_MUSIC
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MauvilleCity_Gym_EventScript_WattsonRematch
 * goto_if_unset FLAG_RECEIVED_TM_SHOCK_WAVE, MauvilleCity_Gym_EventScript_GiveShockWave2
 * goto_if_eq VAR_NEW_MAUVILLE_STATE, 2, MauvilleCity_Gym_EventScript_CompletedNewMauville
 * msgbox MauvilleCity_Gym_Text_WattsonPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_Wattson : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Gym_EventScript_Wattson")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHAWN, MauvilleCity_Gym_Text_ShawnIntro, MauvilleCity_Gym_Text_ShawnDefeat
 * msgbox MauvilleCity_Gym_Text_ShawnPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_Shawn : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Gym_EventScript_Shawn")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_VIVIAN, MauvilleCity_Gym_Text_VivianIntro, MauvilleCity_Gym_Text_VivianDefeat
 * msgbox MauvilleCity_Gym_Text_VivianPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_Vivian : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Gym_EventScript_Vivian")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BEN, MauvilleCity_Gym_Text_BenIntro, MauvilleCity_Gym_Text_BenDefeat
 * msgbox MauvilleCity_Gym_Text_BenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_Ben : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Gym_EventScript_Ben")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KIRK, MauvilleCity_Gym_Text_KirkIntro, MauvilleCity_Gym_Text_KirkDefeat
 * msgbox MauvilleCity_Gym_Text_KirkPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_Kirk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Gym_EventScript_Kirk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_MAUVILLE_GYM, MauvilleCity_Gym_EventScript_GymGuidePostVictory
 * msgbox MauvilleCity_Gym_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Gym_EventScript_GymGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ANGELO, MauvilleCity_Gym_Text_AngeloIntro, MauvilleCity_Gym_Text_AngeloDefeat
 * msgbox MauvilleCity_Gym_Text_AngeloPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_Angelo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MauvilleCity_Gym_EventScript_Angelo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE03_GET, MauvilleCity_Gym_EventScript_GymStatueCertified
 * goto MauvilleCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MauvilleCity_Gym_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE03_GET, MauvilleCity_Gym_EventScript_GymStatueCertified
 * goto MauvilleCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object MauvilleCity_Gym_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MauvilleCity_Gym_EventScript_RightGymStatue")
}

internal val MauvilleCity_GymScripts: Map<String, Script> =
    mapOf(
        "MauvilleCity_Gym_EventScript_Wattson" to MauvilleCity_Gym_EventScript_Wattson,
        "MauvilleCity_Gym_EventScript_Shawn" to MauvilleCity_Gym_EventScript_Shawn,
        "MauvilleCity_Gym_EventScript_Vivian" to MauvilleCity_Gym_EventScript_Vivian,
        "MauvilleCity_Gym_EventScript_Ben" to MauvilleCity_Gym_EventScript_Ben,
        "MauvilleCity_Gym_EventScript_Kirk" to MauvilleCity_Gym_EventScript_Kirk,
        "MauvilleCity_Gym_EventScript_GymGuide" to MauvilleCity_Gym_EventScript_GymGuide,
        "MauvilleCity_Gym_EventScript_Angelo" to MauvilleCity_Gym_EventScript_Angelo,
        "MauvilleCity_Gym_EventScript_LeftGymStatue" to MauvilleCity_Gym_EventScript_LeftGymStatue,
        "MauvilleCity_Gym_EventScript_RightGymStatue" to
            MauvilleCity_Gym_EventScript_RightGymStatue,
    )
