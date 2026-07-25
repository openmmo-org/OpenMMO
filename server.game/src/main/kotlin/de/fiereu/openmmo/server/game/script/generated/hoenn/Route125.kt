package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_NOLEN, Route125_Text_NolenIntro, Route125_Text_NolenDefeat
 * msgbox Route125_Text_NolenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route125_EventScript_Nolen : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Nolen")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_STAN, Route125_Text_StanIntro, Route125_Text_StanDefeat
 * msgbox Route125_Text_StanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route125_EventScript_Stan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Stan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TANYA, Route125_Text_TanyaIntro, Route125_Text_TanyaDefeat
 * msgbox Route125_Text_TanyaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route125_EventScript_Tanya : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Tanya")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHARON, Route125_Text_SharonIntro, Route125_Text_SharonDefeat
 * msgbox Route125_Text_SharonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route125_EventScript_Sharon : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Sharon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ERNEST_1, Route125_Text_ErnestIntro, Route125_Text_ErnestDefeat, Route125_EventScript_RegisterErnest
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route125_EventScript_RematchErnest
 * msgbox Route125_Text_ErnestPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route125_EventScript_Ernest : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Ernest")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_KIM_AND_IRIS, Route125_Text_KimIntro, Route125_Text_KimDefeat, Route125_Text_KimNotEnoughMons
 * msgbox Route125_Text_KimPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route125_EventScript_Kim : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Kim")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_KIM_AND_IRIS, Route125_Text_IrisIntro, Route125_Text_IrisDefeat, Route125_Text_IrisNotEnoughMons
 * msgbox Route125_Text_IrisPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route125_EventScript_Iris : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Iris")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PRESLEY, Route125_Text_PresleyIntro, Route125_Text_PresleyDefeat
 * msgbox Route125_Text_PresleyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route125_EventScript_Presley : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Presley")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_AURON, Route125_Text_AuronIntro, Route125_Text_AuronDefeat
 * msgbox Route125_Text_AuronPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route125_EventScript_Auron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_Auron")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BIG_PEARL
 * end
 * ```
 */
internal object Route125_EventScript_ItemBigPearl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route125_EventScript_ItemBigPearl")
}

internal val Route125Scripts: Map<String, Script> =
    mapOf(
        "Route125_EventScript_Nolen" to Route125_EventScript_Nolen,
        "Route125_EventScript_Stan" to Route125_EventScript_Stan,
        "Route125_EventScript_Tanya" to Route125_EventScript_Tanya,
        "Route125_EventScript_Sharon" to Route125_EventScript_Sharon,
        "Route125_EventScript_Ernest" to Route125_EventScript_Ernest,
        "Route125_EventScript_Kim" to Route125_EventScript_Kim,
        "Route125_EventScript_Iris" to Route125_EventScript_Iris,
        "Route125_EventScript_Presley" to Route125_EventScript_Presley,
        "Route125_EventScript_Auron" to Route125_EventScript_Auron,
        "Route125_EventScript_ItemBigPearl" to Route125_EventScript_ItemBigPearl,
    )
