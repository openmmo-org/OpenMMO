package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FLANNERY_1, LavaridgeTown_Gym_1F_Text_FlanneryIntro, LavaridgeTown_Gym_1F_Text_FlanneryDefeat, LavaridgeTown_Gym_1F_EventScript_FlanneryDefeated, NO_MUSIC
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, LavaridgeTown_Gym_1F_EventScript_FlanneryRematch
 * goto_if_unset FLAG_RECEIVED_TM_OVERHEAT, LavaridgeTown_Gym_1F_EventScript_GiveOverheat2
 * msgbox LavaridgeTown_Gym_1F_Text_FlanneryPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LavaridgeTown_Gym_1F_EventScript_Flannery : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavaridgeTown_Gym_1F_EventScript_Flannery")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle TRAINER_BATTLE_CONTINUE_SCRIPT, TRAINER_COLE, LOCALID_COLE, LavaridgeTown_Gym_1F_Text_ColeIntro, LavaridgeTown_Gym_1F_Text_ColeDefeat, LavaridgeTown_Gym_EventScript_CheckTrainerScript
 * msgbox LavaridgeTown_Gym_1F_Text_ColePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object LavaridgeTown_Gym_1F_EventScript_Cole : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LavaridgeTown_Gym_1F_EventScript_Cole")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle TRAINER_BATTLE_CONTINUE_SCRIPT, TRAINER_GERALD, LOCALID_GERALD, LavaridgeTown_Gym_1F_Text_GeraldIntro, LavaridgeTown_Gym_1F_Text_GeraldDefeat, LavaridgeTown_Gym_EventScript_CheckTrainerScript
 * msgbox LavaridgeTown_Gym_1F_Text_GeraldPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object LavaridgeTown_Gym_1F_EventScript_Gerald : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavaridgeTown_Gym_1F_EventScript_Gerald")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle TRAINER_BATTLE_CONTINUE_SCRIPT, TRAINER_AXLE, LOCALID_AXLE, LavaridgeTown_Gym_1F_Text_AxleIntro, LavaridgeTown_Gym_1F_Text_AxleDefeat, LavaridgeTown_Gym_EventScript_CheckTrainerScript
 * msgbox LavaridgeTown_Gym_1F_Text_AxlePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object LavaridgeTown_Gym_1F_EventScript_Axle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LavaridgeTown_Gym_1F_EventScript_Axle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle TRAINER_BATTLE_CONTINUE_SCRIPT, TRAINER_DANIELLE, LOCALID_DANIELLE, LavaridgeTown_Gym_1F_Text_DanielleIntro, LavaridgeTown_Gym_1F_Text_DanielleDefeat, LavaridgeTown_Gym_EventScript_CheckTrainerScript
 * msgbox LavaridgeTown_Gym_1F_Text_DaniellePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object LavaridgeTown_Gym_1F_EventScript_Danielle : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavaridgeTown_Gym_1F_EventScript_Danielle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_LAVARIDGE_GYM, LavaridgeTown_Gym_1F_EventScript_GymGuidePostVictory
 * msgbox LavaridgeTown_Gym_1F_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LavaridgeTown_Gym_1F_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavaridgeTown_Gym_1F_EventScript_GymGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE04_GET, LavaridgeTown_Gym_1F_EventScript_GymStatueCertified
 * goto LavaridgeTown_Gym_1F_EventScript_GymStatue
 * end
 * ```
 */
internal object LavaridgeTown_Gym_1F_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavaridgeTown_Gym_1F_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE04_GET, LavaridgeTown_Gym_1F_EventScript_GymStatueCertified
 * goto LavaridgeTown_Gym_1F_EventScript_GymStatue
 * end
 * ```
 */
internal object LavaridgeTown_Gym_1F_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavaridgeTown_Gym_1F_EventScript_RightGymStatue")
}

internal val LavaridgeTown_Gym_1FScripts: Map<String, Script> =
    mapOf(
        "LavaridgeTown_Gym_1F_EventScript_Flannery" to LavaridgeTown_Gym_1F_EventScript_Flannery,
        "LavaridgeTown_Gym_1F_EventScript_Cole" to LavaridgeTown_Gym_1F_EventScript_Cole,
        "LavaridgeTown_Gym_1F_EventScript_Gerald" to LavaridgeTown_Gym_1F_EventScript_Gerald,
        "LavaridgeTown_Gym_1F_EventScript_Axle" to LavaridgeTown_Gym_1F_EventScript_Axle,
        "LavaridgeTown_Gym_1F_EventScript_Danielle" to LavaridgeTown_Gym_1F_EventScript_Danielle,
        "LavaridgeTown_Gym_1F_EventScript_GymGuide" to LavaridgeTown_Gym_1F_EventScript_GymGuide,
        "LavaridgeTown_Gym_1F_EventScript_LeftGymStatue" to
            LavaridgeTown_Gym_1F_EventScript_LeftGymStatue,
        "LavaridgeTown_Gym_1F_EventScript_RightGymStatue" to
            LavaridgeTown_Gym_1F_EventScript_RightGymStatue,
    )
