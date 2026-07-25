package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route124
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SPENCER, Route124_Text_SpencerIntro, Route124_Text_SpencerDefeat
 * msgbox Route124_Text_SpencerPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route124_EventScript_Spencer : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Spencer")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ROLAND, Route124_Text_RolandIntro, Route124_Text_RolandDefeat
 * msgbox Route124_Text_RolandPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route124_EventScript_Roland : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Roland")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JENNY_1, Route124_Text_JennyIntro, Route124_Text_JennyDefeat, Route124_EventScript_RegisterJenny
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route124_EventScript_RematchJenny
 * msgbox Route124_Text_JennyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route124_EventScript_Jenny : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Jenny")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRACE, Route124_Text_GraceIntro, Route124_Text_GraceDefeat
 * msgbox Route124_Text_GracePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route124_EventScript_Grace : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Grace")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHAD, Route124_Text_ChadIntro, Route124_Text_ChadDefeat
 * msgbox Route124_Text_ChadPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route124_EventScript_Chad : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Chad")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RED_SHARD
 * end
 * ```
 */
internal object Route124_EventScript_ItemRedShard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_ItemRedShard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BLUE_SHARD
 * end
 * ```
 */
internal object Route124_EventScript_ItemBlueShard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_ItemBlueShard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_YELLOW_SHARD
 * end
 * ```
 */
internal object Route124_EventScript_ItemYellowShard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_ItemYellowShard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LILA_AND_ROY_1, Route124_Text_LilaIntro, Route124_Text_LilaDefeat, Route124_Text_LilaNotEnoughMons, Route124_EventScript_RegisterLila
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route124_EventScript_RematchLila
 * msgbox Route124_Text_LilaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route124_EventScript_Lila : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Lila")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LILA_AND_ROY_1, Route124_Text_RoyIntro, Route124_Text_RoyDefeat, Route124_Text_RoyNotEnoughMons, Route124_EventScript_RegisterRoy
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route124_EventScript_RematchRoy
 * msgbox Route124_Text_RoyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route124_EventScript_Roy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Roy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DECLAN, Route124_Text_DeclanIntro, Route124_Text_DeclanDefeat
 * msgbox Route124_Text_DeclanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route124_EventScript_Declan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Declan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ISABELLA, Route124_Text_IsabellaIntro, Route124_Text_IsabellaDefeat
 * msgbox Route124_Text_IsabellaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route124_EventScript_Isabella : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route124_EventScript_Isabella")
}

internal object Route124_EventScript_HuntersHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route124.HuntersHouse)
}

internal val Route124Scripts: Map<String, Script> =
    mapOf(
        "Route124_EventScript_Spencer" to Route124_EventScript_Spencer,
        "Route124_EventScript_Roland" to Route124_EventScript_Roland,
        "Route124_EventScript_Jenny" to Route124_EventScript_Jenny,
        "Route124_EventScript_Grace" to Route124_EventScript_Grace,
        "Route124_EventScript_Chad" to Route124_EventScript_Chad,
        "Route124_EventScript_ItemRedShard" to Route124_EventScript_ItemRedShard,
        "Route124_EventScript_ItemBlueShard" to Route124_EventScript_ItemBlueShard,
        "Route124_EventScript_ItemYellowShard" to Route124_EventScript_ItemYellowShard,
        "Route124_EventScript_Lila" to Route124_EventScript_Lila,
        "Route124_EventScript_Roy" to Route124_EventScript_Roy,
        "Route124_EventScript_Declan" to Route124_EventScript_Declan,
        "Route124_EventScript_Isabella" to Route124_EventScript_Isabella,
        "Route124_EventScript_HuntersHouseSign" to Route124_EventScript_HuntersHouseSign,
    )
