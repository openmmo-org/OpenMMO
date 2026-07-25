package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BARRY, Route126_Text_BarryIntro, Route126_Text_BarryDefeat
 * msgbox Route126_Text_BarryPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route126_EventScript_Barry : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_Barry")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DEAN, Route126_Text_DeanIntro, Route126_Text_DeanDefeat
 * msgbox Route126_Text_DeanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route126_EventScript_Dean : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_Dean")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_NIKKI, Route126_Text_NikkiIntro, Route126_Text_NikkiDefeat
 * msgbox Route126_Text_NikkiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route126_EventScript_Nikki : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_Nikki")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BRENDA, Route126_Text_BrendaIntro, Route126_Text_BrendaDefeat
 * msgbox Route126_Text_BrendaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route126_EventScript_Brenda : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_Brenda")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GREEN_SHARD
 * end
 * ```
 */
internal object Route126_EventScript_ItemGreenShard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_ItemGreenShard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SIENNA, Route126_Text_SiennaIntro, Route126_Text_SiennaDefeat
 * msgbox Route126_Text_SiennaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route126_EventScript_Sienna : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_Sienna")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PABLO_1, Route126_Text_PabloIntro, Route126_Text_PabloDefeat, Route126_EventScript_RegisterPablo
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route126_EventScript_RematchPablo
 * msgbox Route126_Text_PabloPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route126_EventScript_Pablo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_Pablo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ISOBEL, Route126_Text_IsobelIntro, Route126_Text_IsobelDefeat
 * msgbox Route126_Text_IsobelPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route126_EventScript_Isobel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_Isobel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LEONARDO, Route126_Text_LeonardoIntro, Route126_Text_LeonardoDefeat
 * msgbox Route126_Text_LeonardoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route126_EventScript_Leonardo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route126_EventScript_Leonardo")
}

internal val Route126Scripts: Map<String, Script> =
    mapOf(
        "Route126_EventScript_Barry" to Route126_EventScript_Barry,
        "Route126_EventScript_Dean" to Route126_EventScript_Dean,
        "Route126_EventScript_Nikki" to Route126_EventScript_Nikki,
        "Route126_EventScript_Brenda" to Route126_EventScript_Brenda,
        "Route126_EventScript_ItemGreenShard" to Route126_EventScript_ItemGreenShard,
        "Route126_EventScript_Sienna" to Route126_EventScript_Sienna,
        "Route126_EventScript_Pablo" to Route126_EventScript_Pablo,
        "Route126_EventScript_Isobel" to Route126_EventScript_Isobel,
        "Route126_EventScript_Leonardo" to Route126_EventScript_Leonardo,
    )
