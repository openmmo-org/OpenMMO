package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_10F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_TRAVIS, SilphCo_10F_Text_TravisIntro, SilphCo_10F_Text_TravisDefeat
 * msgbox SilphCo_10F_Text_TravisPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_10F_EventScript_Travis : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_10F_EventScript_Travis")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_10F_EventScript_WorkerFRocketsGone
 * msgbox SilphCo_10F_Text_WaaaImScared
 * release
 * end
 * ```
 */
internal object SilphCo_10F_EventScript_WorkerF : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_10F_EventScript_WorkerF")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_39, SilphCo_10F_Text_GruntIntro, SilphCo_10F_Text_GruntDefeat
 * msgbox SilphCo_10F_Text_GruntPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_10F_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_10F_EventScript_Grunt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_CARBOS
 * end
 * ```
 */
internal object SilphCo_10F_EventScript_ItemCarbos : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_10F_EventScript_ItemCarbos")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ULTRA_BALL
 * end
 * ```
 */
internal object SilphCo_10F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_10F_EventScript_ItemUltraBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object SilphCo_10F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_10F_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 19
 * setvar VAR_0x8004, FLAG_SILPH_10F_DOOR
 * goto_if_set FLAG_SILPH_10F_DOOR, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_10F_EventScript_Door : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_10F_EventScript_Door")
}

internal object SilphCo_10F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_10F.FloorSign)
}

internal val SilphCo_10FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_10F_EventScript_Travis" to SilphCo_10F_EventScript_Travis,
        "SilphCo_10F_EventScript_WorkerF" to SilphCo_10F_EventScript_WorkerF,
        "SilphCo_10F_EventScript_Grunt" to SilphCo_10F_EventScript_Grunt,
        "SilphCo_10F_EventScript_ItemCarbos" to SilphCo_10F_EventScript_ItemCarbos,
        "SilphCo_10F_EventScript_ItemUltraBall" to SilphCo_10F_EventScript_ItemUltraBall,
        "SilphCo_10F_EventScript_ItemRareCandy" to SilphCo_10F_EventScript_ItemRareCandy,
        "SilphCo_10F_EventScript_Door" to SilphCo_10F_EventScript_Door,
        "SilphCo_10F_EventScript_FloorSign" to SilphCo_10F_EventScript_FloorSign,
    )
