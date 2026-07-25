package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLACK_BELT_TAKASHI, ViridianCity_Gym_Text_TakashiIntro, ViridianCity_Gym_Text_TakashiDefeat
 * msgbox ViridianCity_Gym_Text_TakashiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_Takashi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_Takashi")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_YUJI, ViridianCity_Gym_Text_YujiIntro, ViridianCity_Gym_Text_YujiDefeat
 * msgbox ViridianCity_Gym_Text_YujiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_Yuji : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_Yuji")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLACK_BELT_ATSUSHI, ViridianCity_Gym_Text_AtsushiIntro, ViridianCity_Gym_Text_AtsushiDefeat
 * msgbox ViridianCity_Gym_Text_AtsushiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_Atsushi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_Atsushi")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TAMER_JASON, ViridianCity_Gym_Text_JasonIntro, ViridianCity_Gym_Text_JasonDefeat
 * msgbox ViridianCity_Gym_Text_JasonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_Jason : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_Jason")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TAMER_COLE, ViridianCity_Gym_Text_ColeIntro, ViridianCity_Gym_Text_ColeDefeat
 * msgbox ViridianCity_Gym_Text_ColePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_Cole : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_Cole")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLACK_BELT_KIYO, ViridianCity_Gym_Text_KiyoIntro, ViridianCity_Gym_Text_KiyoDefeat
 * msgbox ViridianCity_Gym_Text_KiyoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_Kiyo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_Kiyo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_SAMUEL, ViridianCity_Gym_Text_SamuelIntro, ViridianCity_Gym_Text_SamuelDefeat
 * msgbox ViridianCity_Gym_Text_SamuelPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_Samuel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_Samuel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * famechecker FAMECHECKER_GIOVANNI, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * trainerbattle_single TRAINER_LEADER_GIOVANNI, ViridianCity_Gym_Text_GiovanniIntro, ViridianCity_Gym_Text_GiovanniDefeat, ViridianCity_Gym_EventScript_DefeatedGiovanni, NO_MUSIC
 * goto_if_unset FLAG_GOT_TM26_FROM_GIOVANNI, ViridianCity_Gym_EventScript_GiveTM26
 * msgbox ViridianCity_Gym_Text_GiovanniPostBattle
 * closemessage
 * fadescreen FADE_TO_BLACK
 * removeobject LOCALID_VIRIDIAN_GIOVANNI
 * fadescreen FADE_FROM_BLACK
 * release
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_Giovanni : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_Giovanni")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_WARREN, ViridianCity_Gym_Text_WarrenIntro, ViridianCity_Gym_Text_WarrenDefeat
 * msgbox ViridianCity_Gym_Text_WarrenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_Warren : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_Warren")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_LEADER_GIOVANNI, ViridianCity_Gym_EventScript_GymGuyPostVictory
 * msgbox ViridianCity_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE08_GET, ViridianCity_Gym_EventScript_GymStatuePostVictory
 * msgbox ViridianCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object ViridianCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_Gym_EventScript_GymStatue")
}

internal val ViridianCity_GymScripts: Map<String, Script> =
    mapOf(
        "ViridianCity_Gym_EventScript_Takashi" to ViridianCity_Gym_EventScript_Takashi,
        "ViridianCity_Gym_EventScript_Yuji" to ViridianCity_Gym_EventScript_Yuji,
        "ViridianCity_Gym_EventScript_Atsushi" to ViridianCity_Gym_EventScript_Atsushi,
        "ViridianCity_Gym_EventScript_Jason" to ViridianCity_Gym_EventScript_Jason,
        "ViridianCity_Gym_EventScript_Cole" to ViridianCity_Gym_EventScript_Cole,
        "ViridianCity_Gym_EventScript_Kiyo" to ViridianCity_Gym_EventScript_Kiyo,
        "ViridianCity_Gym_EventScript_Samuel" to ViridianCity_Gym_EventScript_Samuel,
        "ViridianCity_Gym_EventScript_Giovanni" to ViridianCity_Gym_EventScript_Giovanni,
        "ViridianCity_Gym_EventScript_Warren" to ViridianCity_Gym_EventScript_Warren,
        "ViridianCity_Gym_EventScript_GymGuy" to ViridianCity_Gym_EventScript_GymGuy,
        "ViridianCity_Gym_EventScript_GymStatue" to ViridianCity_Gym_EventScript_GymStatue,
    )
