package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WINONA_1, FortreeCity_Gym_Text_WinonaIntro, FortreeCity_Gym_Text_WinonaDefeat, FortreeCity_Gym_EventScript_WinonaDefeated, NO_MUSIC
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FortreeCity_Gym_EventScript_WinonaRematch
 * goto_if_unset FLAG_RECEIVED_TM_AERIAL_ACE, FortreeCity_Gym_EventScript_GiveAerialAce2
 * msgbox FortreeCity_Gym_Text_WinonaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_Winona : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_Winona")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JARED, FortreeCity_Gym_Text_JaredIntro, FortreeCity_Gym_Text_JaredDefeat
 * msgbox FortreeCity_Gym_Text_JaredPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_Jared : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_Jared")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FLINT, FortreeCity_Gym_Text_FlintIntro, FortreeCity_Gym_Text_FlintDefeat
 * msgbox FortreeCity_Gym_Text_FlintPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_Flint : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_Flint")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ASHLEY, FortreeCity_Gym_Text_AshleyIntro, FortreeCity_Gym_Text_AshleyDefeat
 * msgbox FortreeCity_Gym_Text_AshleyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_Ashley : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_Ashley")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_EDWARDO, FortreeCity_Gym_Text_EdwardoIntro, FortreeCity_Gym_Text_EdwardoDefeat
 * msgbox FortreeCity_Gym_Text_EdwardoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_Edwardo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_Edwardo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_FORTREE_GYM, FortreeCity_Gym_EventScript_GymGuidePostVictory
 * msgbox FortreeCity_Gym_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_GymGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HUMBERTO, FortreeCity_Gym_Text_HumbertoIntro, FortreeCity_Gym_Text_HumbertoDefeat
 * msgbox FortreeCity_Gym_Text_HumbertoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_Humberto : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_Humberto")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DARIUS, FortreeCity_Gym_Text_DariusIntro, FortreeCity_Gym_Text_DariusDefeat
 * msgbox FortreeCity_Gym_Text_DariusPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_Darius : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_Gym_EventScript_Darius")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE06_GET, FortreeCity_Gym_EventScript_GymStatueCertified
 * goto FortreeCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FortreeCity_Gym_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE06_GET, FortreeCity_Gym_EventScript_GymStatueCertified
 * goto FortreeCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object FortreeCity_Gym_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FortreeCity_Gym_EventScript_RightGymStatue")
}

internal val FortreeCity_GymScripts: Map<String, Script> =
    mapOf(
        "FortreeCity_Gym_EventScript_Winona" to FortreeCity_Gym_EventScript_Winona,
        "FortreeCity_Gym_EventScript_Jared" to FortreeCity_Gym_EventScript_Jared,
        "FortreeCity_Gym_EventScript_Flint" to FortreeCity_Gym_EventScript_Flint,
        "FortreeCity_Gym_EventScript_Ashley" to FortreeCity_Gym_EventScript_Ashley,
        "FortreeCity_Gym_EventScript_Edwardo" to FortreeCity_Gym_EventScript_Edwardo,
        "FortreeCity_Gym_EventScript_GymGuide" to FortreeCity_Gym_EventScript_GymGuide,
        "FortreeCity_Gym_EventScript_Humberto" to FortreeCity_Gym_EventScript_Humberto,
        "FortreeCity_Gym_EventScript_Darius" to FortreeCity_Gym_EventScript_Darius,
        "FortreeCity_Gym_EventScript_LeftGymStatue" to FortreeCity_Gym_EventScript_LeftGymStatue,
        "FortreeCity_Gym_EventScript_RightGymStatue" to FortreeCity_Gym_EventScript_RightGymStatue,
    )
