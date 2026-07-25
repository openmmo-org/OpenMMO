package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_TATE_AND_LIZA_1, MossdeepCity_Gym_Text_TateAndLizaIntro, MossdeepCity_Gym_Text_TateAndLizaDefeat, MossdeepCity_Gym_Text_TateAndLizaNeedTwoMons, MossdeepCity_Gym_EventScript_TateAndLizaDefeated, NO_MUSIC
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MossdeepCity_Gym_EventScript_TateAndLizaRematch
 * goto_if_unset FLAG_RECEIVED_TM_CALM_MIND, MossdeepCity_Gym_EventScript_GiveCalmMind2
 * msgbox MossdeepCity_Gym_Text_TateAndLizaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_TateAndLiza : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_Gym_EventScript_TateAndLiza")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PRESTON, MossdeepCity_Gym_Text_PrestonIntro, MossdeepCity_Gym_Text_PrestonDefeat
 * msgbox MossdeepCity_Gym_Text_PrestonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Preston : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Preston")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLAKE, MossdeepCity_Gym_Text_BlakeIntro, MossdeepCity_Gym_Text_BlakeDefeat
 * msgbox MossdeepCity_Gym_Text_BlakePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Blake : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Blake")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MAURA, MossdeepCity_Gym_Text_MauraIntro, MossdeepCity_Gym_Text_MauraDefeat
 * msgbox MossdeepCity_Gym_Text_MauraPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Maura : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Maura")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SAMANTHA, MossdeepCity_Gym_Text_SamanthaIntro, MossdeepCity_Gym_Text_SamanthaDefeat
 * msgbox MossdeepCity_Gym_Text_SamanthaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Samantha : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Samantha")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_VIRGIL, MossdeepCity_Gym_Text_VirgilIntro, MossdeepCity_Gym_Text_VirgilDefeat
 * msgbox MossdeepCity_Gym_Text_VirgilPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Virgil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Virgil")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HANNAH, MossdeepCity_Gym_Text_HannahIntro, MossdeepCity_Gym_Text_HannahDefeat
 * msgbox MossdeepCity_Gym_Text_HannahPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Hannah : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Hannah")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_MOSSDEEP_GYM, MossdeepCity_Gym_EventScript_GymGuidePostVictory
 * msgbox MossdeepCity_Gym_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_GymGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_NATE, MossdeepCity_Gym_Text_NateIntro, MossdeepCity_Gym_Text_NateDefeat
 * msgbox MossdeepCity_Gym_Text_NatePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Nate : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Nate")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SYLVIA, MossdeepCity_Gym_Text_SylviaIntro, MossdeepCity_Gym_Text_SylviaDefeat
 * msgbox MossdeepCity_Gym_Text_SylviaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Sylvia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Sylvia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CLIFFORD, MossdeepCity_Gym_Text_CliffordIntro, MossdeepCity_Gym_Text_CliffordDefeat
 * msgbox MossdeepCity_Gym_Text_CliffordPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Clifford : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Clifford")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MACEY, MossdeepCity_Gym_Text_MaceyIntro, MossdeepCity_Gym_Text_MaceyDefeat
 * msgbox MossdeepCity_Gym_Text_MaceyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Macey : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Macey")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KATHLEEN, MossdeepCity_Gym_Text_KathleenIntro, MossdeepCity_Gym_Text_KathleenDefeat
 * msgbox MossdeepCity_Gym_Text_KathleenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Kathleen : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Kathleen")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_NICHOLAS, MossdeepCity_Gym_Text_NicholasIntro, MossdeepCity_Gym_Text_NicholasDefeat
 * msgbox MossdeepCity_Gym_Text_NicholasPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_Nicholas : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_Gym_EventScript_Nicholas")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE07_GET, MossdeepCity_Gym_EventScript_GymStatueCertified
 * goto MossdeepCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_Gym_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE07_GET, MossdeepCity_Gym_EventScript_GymStatueCertified
 * goto MossdeepCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object MossdeepCity_Gym_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_Gym_EventScript_RightGymStatue")
}

internal val MossdeepCity_GymScripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_Gym_EventScript_TateAndLiza" to MossdeepCity_Gym_EventScript_TateAndLiza,
        "MossdeepCity_Gym_EventScript_Preston" to MossdeepCity_Gym_EventScript_Preston,
        "MossdeepCity_Gym_EventScript_Blake" to MossdeepCity_Gym_EventScript_Blake,
        "MossdeepCity_Gym_EventScript_Maura" to MossdeepCity_Gym_EventScript_Maura,
        "MossdeepCity_Gym_EventScript_Samantha" to MossdeepCity_Gym_EventScript_Samantha,
        "MossdeepCity_Gym_EventScript_Virgil" to MossdeepCity_Gym_EventScript_Virgil,
        "MossdeepCity_Gym_EventScript_Hannah" to MossdeepCity_Gym_EventScript_Hannah,
        "MossdeepCity_Gym_EventScript_GymGuide" to MossdeepCity_Gym_EventScript_GymGuide,
        "MossdeepCity_Gym_EventScript_Nate" to MossdeepCity_Gym_EventScript_Nate,
        "MossdeepCity_Gym_EventScript_Sylvia" to MossdeepCity_Gym_EventScript_Sylvia,
        "MossdeepCity_Gym_EventScript_Clifford" to MossdeepCity_Gym_EventScript_Clifford,
        "MossdeepCity_Gym_EventScript_Macey" to MossdeepCity_Gym_EventScript_Macey,
        "MossdeepCity_Gym_EventScript_Kathleen" to MossdeepCity_Gym_EventScript_Kathleen,
        "MossdeepCity_Gym_EventScript_Nicholas" to MossdeepCity_Gym_EventScript_Nicholas,
        "MossdeepCity_Gym_EventScript_LeftGymStatue" to MossdeepCity_Gym_EventScript_LeftGymStatue,
        "MossdeepCity_Gym_EventScript_RightGymStatue" to
            MossdeepCity_Gym_EventScript_RightGymStatue,
    )
