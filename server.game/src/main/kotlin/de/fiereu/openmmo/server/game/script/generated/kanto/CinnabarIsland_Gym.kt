package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BURGLAR_QUINN, CinnabarIsland_Gym_Text_QuinnIntro, CinnabarIsland_Gym_Text_QuinnDefeat, CinnabarIsland_Gym_EventScript_DefeatedQuinn
 * msgbox CinnabarIsland_Gym_Text_QuinnPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quinn : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Gym_EventScript_Quinn")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SUPER_NERD_ERIK, CinnabarIsland_Gym_Text_ErikIntro, CinnabarIsland_Gym_Text_ErikDefeat
 * msgbox CinnabarIsland_Gym_Text_ErikPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Erik : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Gym_EventScript_Erik")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SUPER_NERD_AVERY, CinnabarIsland_Gym_Text_AveryIntro, CinnabarIsland_Gym_Text_AveryDefeat, CinnabarIsland_Gym_EventScript_DefeatedAvery
 * msgbox CinnabarIsland_Gym_Text_AveryPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Avery : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Gym_EventScript_Avery")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BURGLAR_RAMON, CinnabarIsland_Gym_Text_RamonIntro, CinnabarIsland_Gym_Text_RamonDefeat, CinnabarIsland_Gym_EventScript_DefeatedRamon
 * msgbox CinnabarIsland_Gym_Text_RamonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Ramon : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Gym_EventScript_Ramon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SUPER_NERD_DEREK, CinnabarIsland_Gym_Text_DerekIntro, CinnabarIsland_Gym_Text_DerekDefeat, CinnabarIsland_Gym_EventScript_DefeatedDerek
 * famechecker FAMECHECKER_BLAINE, 2
 * msgbox CinnabarIsland_Gym_Text_DerekPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Derek : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Gym_EventScript_Derek")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BURGLAR_DUSTY, CinnabarIsland_Gym_Text_DustyIntro, CinnabarIsland_Gym_Text_DustyDefeat, CinnabarIsland_Gym_EventScript_DefeatedDusty
 * msgbox CinnabarIsland_Gym_Text_DustyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Dusty : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Gym_EventScript_Dusty")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SUPER_NERD_ZAC, CinnabarIsland_Gym_Text_ZacIntro, CinnabarIsland_Gym_Text_ZacDefeat, CinnabarIsland_Gym_EventScript_DefeatedZac
 * msgbox CinnabarIsland_Gym_Text_ZacPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Zac : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Gym_EventScript_Zac")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * famechecker FAMECHECKER_BLAINE, FCPICKSTATE_COLORED, UpdatePickStateFromSpecialVar8005
 * trainerbattle_single TRAINER_LEADER_BLAINE, CinnabarIsland_Gym_Text_BlaineIntro, CinnabarIsland_Gym_Text_BlaineDefeat, CinnabarIsland_Gym_EventScript_DefeatedBlaine, NO_MUSIC
 * goto_if_unset FLAG_GOT_TM38_FROM_BLAINE, CinnabarIsland_Gym_EventScript_GiveTM38
 * msgbox CinnabarIsland_Gym_Text_BlainePostBattle
 * release
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Blaine : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Gym_EventScript_Blaine")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_BLAINE, CinnabarIsland_Gym_EventScript_GymGuyPostVictory
 * msgbox CinnabarIsland_Gym_Text_GymGuyAdvice
 * release
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CinnabarIsland_Gym_EventScript_GymGuy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE07_GET, CinnabarIsland_Gym_EventScript_GymStatuePostVictory
 * msgbox CinnabarIsland_Gym_Text_GymStatue
 * releaseall
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_GymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_GymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 0
 * goto CinnabarIsland_Gym_EventScript_Quiz1
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quz1Left : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quz1Left")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 1
 * goto CinnabarIsland_Gym_EventScript_Quiz1
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quz1Right : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quz1Right")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 0
 * goto CinnabarIsland_Gym_EventScript_Quiz2
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz2Left : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz2Left")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 1
 * goto CinnabarIsland_Gym_EventScript_Quiz2
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz2Right : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz2Right")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 0
 * goto CinnabarIsland_Gym_EventScript_Quiz3
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz3Left : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz3Left")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 1
 * goto CinnabarIsland_Gym_EventScript_Quiz3
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz3Right : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz3Right")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 0
 * goto CinnabarIsland_Gym_EventScript_Quiz4
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz4Left : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz4Left")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 1
 * goto CinnabarIsland_Gym_EventScript_Quiz4
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz4Right : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz4Right")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 0
 * goto CinnabarIsland_Gym_EventScript_Quiz5
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz5Left : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz5Left")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 1
 * goto CinnabarIsland_Gym_EventScript_Quiz5
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz5Right : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz5Right")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 0
 * goto CinnabarIsland_Gym_EventScript_Quiz6
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz6Left : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz6Left")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 1
 * goto CinnabarIsland_Gym_EventScript_Quiz6
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_Quiz6Right : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_Quiz6Right")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_BLAINE, 4
 * famechecker FAMECHECKER_MRFUJI, 4
 * msgbox CinnabarIsland_Gym_Text_PhotoOfBlaineAndFuji
 * releaseall
 * end
 * ```
 */
internal object CinnabarIsland_Gym_EventScript_BlaineFujiPhoto : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CinnabarIsland_Gym_EventScript_BlaineFujiPhoto")
}

internal val CinnabarIsland_GymScripts: Map<String, Script> =
    mapOf(
        "CinnabarIsland_Gym_EventScript_Quinn" to CinnabarIsland_Gym_EventScript_Quinn,
        "CinnabarIsland_Gym_EventScript_Erik" to CinnabarIsland_Gym_EventScript_Erik,
        "CinnabarIsland_Gym_EventScript_Avery" to CinnabarIsland_Gym_EventScript_Avery,
        "CinnabarIsland_Gym_EventScript_Ramon" to CinnabarIsland_Gym_EventScript_Ramon,
        "CinnabarIsland_Gym_EventScript_Derek" to CinnabarIsland_Gym_EventScript_Derek,
        "CinnabarIsland_Gym_EventScript_Dusty" to CinnabarIsland_Gym_EventScript_Dusty,
        "CinnabarIsland_Gym_EventScript_Zac" to CinnabarIsland_Gym_EventScript_Zac,
        "CinnabarIsland_Gym_EventScript_Blaine" to CinnabarIsland_Gym_EventScript_Blaine,
        "CinnabarIsland_Gym_EventScript_GymGuy" to CinnabarIsland_Gym_EventScript_GymGuy,
        "CinnabarIsland_Gym_EventScript_GymStatue" to CinnabarIsland_Gym_EventScript_GymStatue,
        "CinnabarIsland_Gym_EventScript_Quz1Left" to CinnabarIsland_Gym_EventScript_Quz1Left,
        "CinnabarIsland_Gym_EventScript_Quz1Right" to CinnabarIsland_Gym_EventScript_Quz1Right,
        "CinnabarIsland_Gym_EventScript_Quiz2Left" to CinnabarIsland_Gym_EventScript_Quiz2Left,
        "CinnabarIsland_Gym_EventScript_Quiz2Right" to CinnabarIsland_Gym_EventScript_Quiz2Right,
        "CinnabarIsland_Gym_EventScript_Quiz3Left" to CinnabarIsland_Gym_EventScript_Quiz3Left,
        "CinnabarIsland_Gym_EventScript_Quiz3Right" to CinnabarIsland_Gym_EventScript_Quiz3Right,
        "CinnabarIsland_Gym_EventScript_Quiz4Left" to CinnabarIsland_Gym_EventScript_Quiz4Left,
        "CinnabarIsland_Gym_EventScript_Quiz4Right" to CinnabarIsland_Gym_EventScript_Quiz4Right,
        "CinnabarIsland_Gym_EventScript_Quiz5Left" to CinnabarIsland_Gym_EventScript_Quiz5Left,
        "CinnabarIsland_Gym_EventScript_Quiz5Right" to CinnabarIsland_Gym_EventScript_Quiz5Right,
        "CinnabarIsland_Gym_EventScript_Quiz6Left" to CinnabarIsland_Gym_EventScript_Quiz6Left,
        "CinnabarIsland_Gym_EventScript_Quiz6Right" to CinnabarIsland_Gym_EventScript_Quiz6Right,
        "CinnabarIsland_Gym_EventScript_BlaineFujiPhoto" to
            CinnabarIsland_Gym_EventScript_BlaineFujiPhoto,
    )
