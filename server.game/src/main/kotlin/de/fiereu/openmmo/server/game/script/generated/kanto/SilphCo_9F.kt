package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_9F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_ED, SilphCo_9F_Text_EdIntro, SilphCo_9F_Text_EdDefeat
 * msgbox SilphCo_9F_Text_EdPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_9F_EventScript_Ed : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_9F_EventScript_Ed")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_38, SilphCo_9F_Text_Grunt2Intro, SilphCo_9F_Text_Grunt2Defeat
 * msgbox SilphCo_9F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_9F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_9F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_9F_EventScript_HealWomanRocketsGone
 * msgbox SilphCo_9F_Text_YouShouldTakeQuickNap
 * closemessage
 * call EventScript_OutOfCenterPartyHeal
 * msgbox SilphCo_9F_Text_DontGiveUp
 * release
 * end
 * ```
 */
internal object SilphCo_9F_EventScript_HealWoman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_9F_EventScript_HealWoman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_37, SilphCo_9F_Text_Grunt1Intro, SilphCo_9F_Text_Grunt1Defeat
 * msgbox SilphCo_9F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_9F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_9F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 15
 * setvar VAR_0x8004, FLAG_SILPH_9F_DOOR_1
 * goto_if_set FLAG_SILPH_9F_DOOR_1, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_9F_EventScript_Door1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_9F_EventScript_Door1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 16
 * setvar VAR_0x8004, FLAG_SILPH_9F_DOOR_2
 * goto_if_set FLAG_SILPH_9F_DOOR_2, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_9F_EventScript_Door2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_9F_EventScript_Door2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 17
 * setvar VAR_0x8004, FLAG_SILPH_9F_DOOR_3
 * goto_if_set FLAG_SILPH_9F_DOOR_3, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_9F_EventScript_Door3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_9F_EventScript_Door3")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 18
 * setvar VAR_0x8004, FLAG_SILPH_9F_DOOR_4
 * goto_if_set FLAG_SILPH_9F_DOOR_4, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_9F_EventScript_Door4 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_9F_EventScript_Door4")
}

internal object SilphCo_9F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_9F.FloorSign)
}

internal val SilphCo_9FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_9F_EventScript_Ed" to SilphCo_9F_EventScript_Ed,
        "SilphCo_9F_EventScript_Grunt2" to SilphCo_9F_EventScript_Grunt2,
        "SilphCo_9F_EventScript_HealWoman" to SilphCo_9F_EventScript_HealWoman,
        "SilphCo_9F_EventScript_Grunt1" to SilphCo_9F_EventScript_Grunt1,
        "SilphCo_9F_EventScript_Door1" to SilphCo_9F_EventScript_Door1,
        "SilphCo_9F_EventScript_Door2" to SilphCo_9F_EventScript_Door2,
        "SilphCo_9F_EventScript_Door3" to SilphCo_9F_EventScript_Door3,
        "SilphCo_9F_EventScript_Door4" to SilphCo_9F_EventScript_Door4,
        "SilphCo_9F_EventScript_FloorSign" to SilphCo_9F_EventScript_FloorSign,
    )
