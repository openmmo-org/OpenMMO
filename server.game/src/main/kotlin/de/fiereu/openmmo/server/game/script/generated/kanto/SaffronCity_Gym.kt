package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PSYCHIC_CAMERON, SaffronCity_Gym_Text_CameronIntro, SaffronCity_Gym_Text_CameronDefeat
 * msgbox SaffronCity_Gym_Text_CameronPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_Cameron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_Cameron")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PSYCHIC_JOHAN, SaffronCity_Gym_Text_JohanIntro, SaffronCity_Gym_Text_JohanDefeat
 * msgbox SaffronCity_Gym_Text_JohanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_Johan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_Johan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PSYCHIC_PRESTON, SaffronCity_Gym_Text_PrestonIntro, SaffronCity_Gym_Text_PrestonDefeat
 * msgbox SaffronCity_Gym_Text_PrestonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_Preston : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_Preston")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_AMANDA, SaffronCity_Gym_Text_AmandaIntro, SaffronCity_Gym_Text_AmandaDefeat
 * msgbox SaffronCity_Gym_Text_AmandaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_Amanda : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_Amanda")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_STACY, SaffronCity_Gym_Text_StacyIntro, SaffronCity_Gym_Text_StacyDefeat
 * msgbox SaffronCity_Gym_Text_StacyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_Stacy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_Stacy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHANNELER_TASHA, SaffronCity_Gym_Text_TashaIntro, SaffronCity_Gym_Text_TashaDefeat
 * msgbox SaffronCity_Gym_Text_TashaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_Tasha : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_Tasha")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * famechecker FAMECHECKER_SABRINA, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * trainerbattle_single TRAINER_LEADER_SABRINA, SaffronCity_Gym_Text_SabrinaIntro, SaffronCity_Gym_Text_SabrinaDefeat, SaffronCity_Gym_EventScript_DefeatedSabrina, NO_MUSIC
 * goto_if_unset FLAG_GOT_TM04_FROM_SABRINA, SaffronCity_Gym_EventScript_GiveTM04
 * msgbox SaffronCity_Gym_Text_ExplainTM04
 * release
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_Sabrina : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_Sabrina")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PSYCHIC_TYRON, SaffronCity_Gym_Text_TyronIntro, SaffronCity_Gym_Text_TyronDefeat
 * famechecker FAMECHECKER_SABRINA, 5
 * msgbox SaffronCity_Gym_Text_TyronPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_Tyron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_Tyron")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_SABRINA, SaffronCity_Gym_EventScript_GymGuyPostVictory
 * msgbox SaffronCity_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE06_GET, SaffronCity_Gym_EventScript_GymStatuePostVictory
 * msgbox SaffronCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object SaffronCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Gym_EventScript_GymStatue")
}

internal val SaffronCity_GymScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_Gym_EventScript_Cameron" to SaffronCity_Gym_EventScript_Cameron,
        "SaffronCity_Gym_EventScript_Johan" to SaffronCity_Gym_EventScript_Johan,
        "SaffronCity_Gym_EventScript_Preston" to SaffronCity_Gym_EventScript_Preston,
        "SaffronCity_Gym_EventScript_Amanda" to SaffronCity_Gym_EventScript_Amanda,
        "SaffronCity_Gym_EventScript_Stacy" to SaffronCity_Gym_EventScript_Stacy,
        "SaffronCity_Gym_EventScript_Tasha" to SaffronCity_Gym_EventScript_Tasha,
        "SaffronCity_Gym_EventScript_Sabrina" to SaffronCity_Gym_EventScript_Sabrina,
        "SaffronCity_Gym_EventScript_Tyron" to SaffronCity_Gym_EventScript_Tyron,
        "SaffronCity_Gym_EventScript_GymGuy" to SaffronCity_Gym_EventScript_GymGuy,
        "SaffronCity_Gym_EventScript_GymStatue" to SaffronCity_Gym_EventScript_GymStatue,
    )
