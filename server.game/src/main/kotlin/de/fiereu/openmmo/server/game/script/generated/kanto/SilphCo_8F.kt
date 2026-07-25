package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_8F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_32, SilphCo_8F_Text_Grunt1Intro, SilphCo_8F_Text_Grunt1Defeat
 * msgbox SilphCo_8F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_8F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_8F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_36, SilphCo_8F_Text_Grunt2Intro, SilphCo_8F_Text_Grunt2Defeat
 * msgbox SilphCo_8F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_8F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_8F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_PARKER, SilphCo_8F_Text_ParkerIntro, SilphCo_8F_Text_ParkerDefeat
 * msgbox SilphCo_8F_Text_ParkerPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_8F_EventScript_Parker : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_8F_EventScript_Parker")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_8F_EventScript_WorkerMRocketsGone
 * msgbox SilphCo_8F_Text_WonderIfSilphIsFinished
 * release
 * end
 * ```
 */
internal object SilphCo_8F_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_8F_EventScript_WorkerM")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_IRON
 * end
 * ```
 */
internal object SilphCo_8F_EventScript_ItemIron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_8F_EventScript_ItemIron")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_GIOVANNI, 2
 * msgbox SilphCo_8F_Text_ToRocketBossMonsAreTools
 * release
 * end
 * ```
 */
internal object SilphCo_8F_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_8F_EventScript_Scientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 14
 * setvar VAR_0x8004, FLAG_SILPH_8F_DOOR
 * goto_if_set FLAG_SILPH_8F_DOOR, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_8F_EventScript_Door : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_8F_EventScript_Door")
}

internal object SilphCo_8F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_8F.FloorSign)
}

internal val SilphCo_8FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_8F_EventScript_Grunt1" to SilphCo_8F_EventScript_Grunt1,
        "SilphCo_8F_EventScript_Grunt2" to SilphCo_8F_EventScript_Grunt2,
        "SilphCo_8F_EventScript_Parker" to SilphCo_8F_EventScript_Parker,
        "SilphCo_8F_EventScript_WorkerM" to SilphCo_8F_EventScript_WorkerM,
        "SilphCo_8F_EventScript_ItemIron" to SilphCo_8F_EventScript_ItemIron,
        "SilphCo_8F_EventScript_Scientist" to SilphCo_8F_EventScript_Scientist,
        "SilphCo_8F_EventScript_Door" to SilphCo_8F_EventScript_Door,
        "SilphCo_8F_EventScript_FloorSign" to SilphCo_8F_EventScript_FloorSign,
    )
