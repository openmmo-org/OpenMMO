package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_4F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_27, SilphCo_4F_Text_Grunt2Intro, SilphCo_4F_Text_Grunt2Defeat
 * msgbox SilphCo_4F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_26, SilphCo_4F_Text_Grunt1Intro, SilphCo_4F_Text_Grunt1Defeat
 * msgbox SilphCo_4F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_RODNEY, SilphCo_4F_Text_RodneyIntro, SilphCo_4F_Text_RodneyDefeat
 * msgbox SilphCo_4F_Text_RodneyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_Rodney : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_Rodney")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_4F_EventScript_WorkerMRocketsGone
 * msgbox SilphCo_4F_Text_CantYouSeeImHiding
 * release
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_WorkerM")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REVIVE
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_ItemMaxRevive")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_ItemEscapeRope")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_HEAL
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_ItemFullHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM41
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_ItemTM41 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_ItemTM41")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 5
 * setvar VAR_0x8004, FLAG_SILPH_4F_DOOR_1
 * goto_if_set FLAG_SILPH_4F_DOOR_1, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_Door1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_Door1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 6
 * setvar VAR_0x8004, FLAG_SILPH_4F_DOOR_2
 * goto_if_set FLAG_SILPH_4F_DOOR_2, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_4F_EventScript_Door2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_4F_EventScript_Door2")
}

internal object SilphCo_4F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_4F.FloorSign)
}

internal val SilphCo_4FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_4F_EventScript_Grunt2" to SilphCo_4F_EventScript_Grunt2,
        "SilphCo_4F_EventScript_Grunt1" to SilphCo_4F_EventScript_Grunt1,
        "SilphCo_4F_EventScript_Rodney" to SilphCo_4F_EventScript_Rodney,
        "SilphCo_4F_EventScript_WorkerM" to SilphCo_4F_EventScript_WorkerM,
        "SilphCo_4F_EventScript_ItemMaxRevive" to SilphCo_4F_EventScript_ItemMaxRevive,
        "SilphCo_4F_EventScript_ItemEscapeRope" to SilphCo_4F_EventScript_ItemEscapeRope,
        "SilphCo_4F_EventScript_ItemFullHeal" to SilphCo_4F_EventScript_ItemFullHeal,
        "SilphCo_4F_EventScript_ItemTM41" to SilphCo_4F_EventScript_ItemTM41,
        "SilphCo_4F_EventScript_Door1" to SilphCo_4F_EventScript_Door1,
        "SilphCo_4F_EventScript_Door2" to SilphCo_4F_EventScript_Door2,
        "SilphCo_4F_EventScript_FloorSign" to SilphCo_4F_EventScript_FloorSign,
    )
