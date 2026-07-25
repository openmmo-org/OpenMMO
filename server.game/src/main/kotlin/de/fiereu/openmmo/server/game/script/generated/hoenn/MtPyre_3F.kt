package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WILLIAM, MtPyre_3F_Text_WilliamIntro, MtPyre_3F_Text_WilliamDefeat
 * msgbox MtPyre_3F_Text_WilliamPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_3F_EventScript_William : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_3F_EventScript_William")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KAYLA, MtPyre_3F_Text_KaylaIntro, MtPyre_3F_Text_KaylaDefeat
 * msgbox MtPyre_3F_Text_KaylaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_3F_EventScript_Kayla : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_3F_EventScript_Kayla")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SUPER_REPEL
 * end
 * ```
 */
internal object MtPyre_3F_EventScript_ItemSuperRepel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_3F_EventScript_ItemSuperRepel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GABRIELLE_1, MtPyre_3F_Text_GabrielleIntro, MtPyre_3F_Text_GabrielleDefeat, MtPyre_3F_EventScript_RegisterGabrielle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MtPyre_3F_EventScript_RematchGabrielle
 * msgbox MtPyre_3F_Text_GabriellePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MtPyre_3F_EventScript_Gabrielle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_3F_EventScript_Gabrielle")
}

internal val MtPyre_3FScripts: Map<String, Script> =
    mapOf(
        "MtPyre_3F_EventScript_William" to MtPyre_3F_EventScript_William,
        "MtPyre_3F_EventScript_Kayla" to MtPyre_3F_EventScript_Kayla,
        "MtPyre_3F_EventScript_ItemSuperRepel" to MtPyre_3F_EventScript_ItemSuperRepel,
        "MtPyre_3F_EventScript_Gabrielle" to MtPyre_3F_EventScript_Gabrielle,
    )
