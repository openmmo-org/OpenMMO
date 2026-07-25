package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_6F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_30, SilphCo_6F_Text_Grunt1Intro, SilphCo_6F_Text_Grunt1Defeat
 * msgbox SilphCo_6F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_6F_EventScript_WorkerM1RocketsGone
 * msgbox SilphCo_6F_Text_HelpMePlease
 * release
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_WorkerM1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_WorkerM1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_6F_EventScript_WorkerF1RocketsGone
 * msgbox SilphCo_6F_Text_ThatManIsSuchACoward
 * release
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_WorkerF1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_WorkerF1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_6F_EventScript_WorkerM2RocketsGone
 * msgbox SilphCo_6F_Text_TargetedSilphForOurMonProducts
 * release
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_WorkerM2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_WorkerM2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_6F_EventScript_WorkerM3RocketsGone
 * msgbox SilphCo_6F_Text_RocketsTookOverBuilding
 * release
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_WorkerM3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_WorkerM3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_6F_EventScript_WorkerF2RocketsGone
 * msgbox SilphCo_6F_Text_RocketsTryingToConquerWorld
 * release
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_WorkerF2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_WorkerF2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_TAYLOR, SilphCo_6F_Text_TaylorIntro, SilphCo_6F_Text_TaylorDefeat
 * msgbox SilphCo_6F_Text_TaylorPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_Taylor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_Taylor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HP_UP
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_ItemHPUp : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_ItemHPUp")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_X_SPECIAL
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_ItemXSpecial : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_ItemXSpecial")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_31, SilphCo_6F_Text_Grunt2Intro, SilphCo_6F_Text_Grunt2Defeat
 * msgbox SilphCo_6F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 10
 * setvar VAR_0x8004, FLAG_SILPH_6F_DOOR
 * goto_if_set FLAG_SILPH_6F_DOOR, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_6F_EventScript_Door : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_6F_EventScript_Door")
}

internal object SilphCo_6F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_6F.FloorSign)
}

internal val SilphCo_6FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_6F_EventScript_Grunt1" to SilphCo_6F_EventScript_Grunt1,
        "SilphCo_6F_EventScript_WorkerM1" to SilphCo_6F_EventScript_WorkerM1,
        "SilphCo_6F_EventScript_WorkerF1" to SilphCo_6F_EventScript_WorkerF1,
        "SilphCo_6F_EventScript_WorkerM2" to SilphCo_6F_EventScript_WorkerM2,
        "SilphCo_6F_EventScript_WorkerM3" to SilphCo_6F_EventScript_WorkerM3,
        "SilphCo_6F_EventScript_WorkerF2" to SilphCo_6F_EventScript_WorkerF2,
        "SilphCo_6F_EventScript_Taylor" to SilphCo_6F_EventScript_Taylor,
        "SilphCo_6F_EventScript_ItemHPUp" to SilphCo_6F_EventScript_ItemHPUp,
        "SilphCo_6F_EventScript_ItemXSpecial" to SilphCo_6F_EventScript_ItemXSpecial,
        "SilphCo_6F_EventScript_Grunt2" to SilphCo_6F_EventScript_Grunt2,
        "SilphCo_6F_EventScript_Door" to SilphCo_6F_EventScript_Door,
        "SilphCo_6F_EventScript_FloorSign" to SilphCo_6F_EventScript_FloorSign,
    )
