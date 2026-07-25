package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SWIMMER_MALE_LUIS, CeruleanCity_Gym_Text_LuisIntro, CeruleanCity_Gym_Text_LuisDefeat
 * famechecker FAMECHECKER_MISTY, 2
 * msgbox CeruleanCity_Gym_Text_LuisPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object CeruleanCity_Gym_EventScript_Luis : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_Gym_EventScript_Luis")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_DIANA, CeruleanCity_Gym_Text_DianaIntro, CeruleanCity_Gym_Text_DianaDefeat
 * msgbox CeruleanCity_Gym_Text_DianaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object CeruleanCity_Gym_EventScript_Diana : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_Gym_EventScript_Diana")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * famechecker FAMECHECKER_MISTY, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * trainerbattle_single TRAINER_LEADER_MISTY, CeruleanCity_Gym_Text_MistyIntro, CeruleanCity_Gym_Text_MistyDefeat, CeruleanCity_Gym_EventScript_MistyDefeated, NO_MUSIC
 * goto_if_unset FLAG_GOT_TM03_FROM_MISTY, CeruleanCity_Gym_EventScript_GiveTM03
 * msgbox CeruleanCity_Gym_Text_ExplainTM03
 * release
 * end
 * ```
 */
internal object CeruleanCity_Gym_EventScript_Misty : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_Gym_EventScript_Misty")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_MISTY, CeruleanCity_Gym_EventScript_GymGuyPostVictory
 * msgbox CeruleanCity_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object CeruleanCity_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE02_GET, CeruleanCity_Gym_EventScript_GymStatuePostVictory
 * msgbox CeruleanCity_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object CeruleanCity_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_Gym_EventScript_GymStatue")
}

internal val CeruleanCity_GymScripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_Gym_EventScript_Luis" to CeruleanCity_Gym_EventScript_Luis,
        "CeruleanCity_Gym_EventScript_Diana" to CeruleanCity_Gym_EventScript_Diana,
        "CeruleanCity_Gym_EventScript_Misty" to CeruleanCity_Gym_EventScript_Misty,
        "CeruleanCity_Gym_EventScript_GymGuy" to CeruleanCity_Gym_EventScript_GymGuy,
        "CeruleanCity_Gym_EventScript_GymStatue" to CeruleanCity_Gym_EventScript_GymStatue,
    )
