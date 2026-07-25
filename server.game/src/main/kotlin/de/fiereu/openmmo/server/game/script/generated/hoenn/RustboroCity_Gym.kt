package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ROXANNE_1, RustboroCity_Gym_Text_RoxanneIntro, RustboroCity_Gym_Text_RoxanneDefeat, RustboroCity_Gym_EventScript_RoxanneDefeated, NO_MUSIC
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, RustboroCity_Gym_EventScript_RoxanneRematch
 * goto_if_unset FLAG_RECEIVED_TM_ROCK_TOMB, RustboroCity_Gym_EventScript_GiveRockTomb
 * msgbox RustboroCity_Gym_Text_RoxannePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_Roxanne : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_Gym_EventScript_Roxanne")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JOSH, RustboroCity_Gym_Text_JoshIntro, RustboroCity_Gym_Text_JoshDefeat
 * msgbox RustboroCity_Gym_Text_JoshPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_Josh : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_Gym_EventScript_Josh")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TOMMY, RustboroCity_Gym_Text_TommyIntro, RustboroCity_Gym_Text_TommyDefeat
 * msgbox RustboroCity_Gym_Text_TommyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_Tommy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_Gym_EventScript_Tommy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_RUSTBORO_GYM, RustboroCity_Gym_EventScript_GymGuidePostVictory
 * msgbox RustboroCity_Gym_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_Gym_EventScript_GymGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MARC, RustboroCity_Gym_Text_MarcIntro, RustboroCity_Gym_Text_MarcDefeat
 * msgbox RustboroCity_Gym_Text_MarcPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_Marc : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_Gym_EventScript_Marc")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE01_GET, RustboroCity_Gym_EventScript_GymStatueCertified
 * goto RustboroCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_Gym_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE01_GET, RustboroCity_Gym_EventScript_GymStatueCertified
 * goto RustboroCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_Gym_EventScript_RightGymStatue")
}

internal val RustboroCity_GymScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_Gym_EventScript_Roxanne" to RustboroCity_Gym_EventScript_Roxanne,
        "RustboroCity_Gym_EventScript_Josh" to RustboroCity_Gym_EventScript_Josh,
        "RustboroCity_Gym_EventScript_Tommy" to RustboroCity_Gym_EventScript_Tommy,
        "RustboroCity_Gym_EventScript_GymGuide" to RustboroCity_Gym_EventScript_GymGuide,
        "RustboroCity_Gym_EventScript_Marc" to RustboroCity_Gym_EventScript_Marc,
        "RustboroCity_Gym_EventScript_LeftGymStatue" to RustboroCity_Gym_EventScript_LeftGymStatue,
        "RustboroCity_Gym_EventScript_RightGymStatue" to
            RustboroCity_Gym_EventScript_RightGymStatue,
    )
