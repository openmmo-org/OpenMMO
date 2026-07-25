package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JUGGLER_KAYDEN, FuchsiaCity_Gym_Text_KaydenIntro, FuchsiaCity_Gym_Text_KaydenDefeat
 * msgbox FuchsiaCity_Gym_Text_KaydenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_Kayden : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Gym_EventScript_Kayden")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JUGGLER_SHAWN, FuchsiaCity_Gym_Text_ShawnIntro, FuchsiaCity_Gym_Text_ShawnDefeat
 * msgbox FuchsiaCity_Gym_Text_ShawnPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_Shawn : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Gym_EventScript_Shawn")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JUGGLER_KIRK, FuchsiaCity_Gym_Text_KirkIntro, FuchsiaCity_Gym_Text_KirkDefeat
 * famechecker FAMECHECKER_KOGA, 2
 * msgbox FuchsiaCity_Gym_Text_KirkPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_Kirk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Gym_EventScript_Kirk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TAMER_EDGAR, FuchsiaCity_Gym_Text_EdgarIntro, FuchsiaCity_Gym_Text_EdgarDefeat
 * msgbox FuchsiaCity_Gym_Text_EdgarPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_Edgar : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Gym_EventScript_Edgar")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TAMER_PHIL, FuchsiaCity_Gym_Text_PhilIntro, FuchsiaCity_Gym_Text_PhilDefeat
 * msgbox FuchsiaCity_Gym_Text_PhilPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_Phil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Gym_EventScript_Phil")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JUGGLER_NATE, FuchsiaCity_Gym_Text_NateIntro, FuchsiaCity_Gym_Text_NateDefeat
 * msgbox FuchsiaCity_Gym_Text_NatePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_Nate : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Gym_EventScript_Nate")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * famechecker FAMECHECKER_KOGA, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * trainerbattle_single TRAINER_LEADER_KOGA, FuchsiaCity_Gym_Text_KogaIntro, FuchsiaCity_Gym_Text_KogaDefeat, FuchsiaCity_Gym_EventScript_DefeatedKoga, NO_MUSIC
 * goto_if_unset FLAG_GOT_TM06_FROM_KOGA, FuchsiaCity_Gym_EventScript_GiveTM06
 * msgbox FuchsiaCity_Gym_Text_KogaPostBattle
 * release
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_Koga : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Gym_EventScript_Koga")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_KOGA, FuchsiaCity_Gym_EventScript_GymGuyPostVictory
 * msgbox FuchsiaCity_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE05_GET, FuchsiaCity_Gym_EventScript_GymStatuePostVictory
 * msgbox FuchsiaCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object FuchsiaCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FuchsiaCity_Gym_EventScript_GymStatue")
}

internal val FuchsiaCity_GymScripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_Gym_EventScript_Kayden" to FuchsiaCity_Gym_EventScript_Kayden,
        "FuchsiaCity_Gym_EventScript_Shawn" to FuchsiaCity_Gym_EventScript_Shawn,
        "FuchsiaCity_Gym_EventScript_Kirk" to FuchsiaCity_Gym_EventScript_Kirk,
        "FuchsiaCity_Gym_EventScript_Edgar" to FuchsiaCity_Gym_EventScript_Edgar,
        "FuchsiaCity_Gym_EventScript_Phil" to FuchsiaCity_Gym_EventScript_Phil,
        "FuchsiaCity_Gym_EventScript_Nate" to FuchsiaCity_Gym_EventScript_Nate,
        "FuchsiaCity_Gym_EventScript_Koga" to FuchsiaCity_Gym_EventScript_Koga,
        "FuchsiaCity_Gym_EventScript_GymGuy" to FuchsiaCity_Gym_EventScript_GymGuy,
        "FuchsiaCity_Gym_EventScript_GymStatue" to FuchsiaCity_Gym_EventScript_GymStatue,
    )
