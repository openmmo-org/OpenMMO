package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route121
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route121_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route121.AheadLoomsMtPyre)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_KATE_AND_JOY, Route121_Text_KateIntro, Route121_Text_KateDefeat, Route121_Text_KateNotEnoughMons
 * msgbox Route121_Text_KatePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route121_EventScript_Kate : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Kate")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_KATE_AND_JOY, Route121_Text_JoyIntro, Route121_Text_JoyDefeat, Route121_Text_JoyNotEnoughMons
 * msgbox Route121_Text_JoyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route121_EventScript_Joy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Joy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_VANESSA, Route121_Text_VanessaIntro, Route121_Text_VanessaDefeat
 * msgbox Route121_Text_VanessaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route121_EventScript_Vanessa : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Vanessa")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WALTER_1, Route121_Text_WalterIntro, Route121_Text_WalterDefeat, Route121_EventScript_RegisterWalter
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route121_EventScript_RematchWalter
 * msgbox Route121_Text_WalterPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route121_EventScript_Walter : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Walter")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TAMMY, Route121_Text_TammyIntro, Route121_Text_TammyDefeat
 * msgbox Route121_Text_TammyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route121_EventScript_Tammy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Tammy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JESSICA_1, Route121_Text_JessicaIntro, Route121_Text_JessicaDefeat, Route121_EventScript_RegisterJessica
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route121_EventScript_RematchJessica
 * msgbox Route121_Text_JessicaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route121_EventScript_Jessica : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Jessica")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_CARBOS
 * end
 * ```
 */
internal object Route121_EventScript_ItemCarbos : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_ItemCarbos")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CALE, Route121_Text_CaleIntro, Route121_Text_CaleDefeat
 * msgbox Route121_Text_CalePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route121_EventScript_Cale : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Cale")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MYLES, Route121_Text_MylesIntro, Route121_Text_MylesDefeat
 * msgbox Route121_Text_MylesPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route121_EventScript_Myles : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Myles")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PAT, Route121_Text_PatIntro, Route121_Text_PatDefeat
 * msgbox Route121_Text_PatPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route121_EventScript_Pat : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Pat")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MARCEL, Route121_Text_MarcelIntro, Route121_Text_MarcelDefeat
 * msgbox Route121_Text_MarcelPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route121_EventScript_Marcel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Marcel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CRISTIN_1, Route121_Text_CristinIntro, Route121_Text_CristinDefeat, Route121_EventScript_RegisterCristin
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route121_EventScript_RematchCristin
 * msgbox Route121_Text_CristinPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route121_EventScript_Cristin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_Cristin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_REVIVE
 * end
 * ```
 */
internal object Route121_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_ItemRevive")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ZINC
 * end
 * ```
 */
internal object Route121_EventScript_ItemZinc : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route121_EventScript_ItemZinc")
}

internal object Route121_EventScript_MtPyrePierSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route121.MtPyrePierSign)
}

internal object Route121_EventScript_SafariZoneSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route121.SafariZoneSign)
}

internal val Route121Scripts: Map<String, Script> =
    mapOf(
        "Route121_EventScript_Woman" to Route121_EventScript_Woman,
        "Route121_EventScript_Kate" to Route121_EventScript_Kate,
        "Route121_EventScript_Joy" to Route121_EventScript_Joy,
        "Route121_EventScript_Vanessa" to Route121_EventScript_Vanessa,
        "Route121_EventScript_Walter" to Route121_EventScript_Walter,
        "Route121_EventScript_Tammy" to Route121_EventScript_Tammy,
        "Route121_EventScript_Jessica" to Route121_EventScript_Jessica,
        "Route121_EventScript_ItemCarbos" to Route121_EventScript_ItemCarbos,
        "Route121_EventScript_Cale" to Route121_EventScript_Cale,
        "Route121_EventScript_Myles" to Route121_EventScript_Myles,
        "Route121_EventScript_Pat" to Route121_EventScript_Pat,
        "Route121_EventScript_Marcel" to Route121_EventScript_Marcel,
        "Route121_EventScript_Cristin" to Route121_EventScript_Cristin,
        "Route121_EventScript_ItemRevive" to Route121_EventScript_ItemRevive,
        "Route121_EventScript_ItemZinc" to Route121_EventScript_ItemZinc,
        "Route121_EventScript_MtPyrePierSign" to Route121_EventScript_MtPyrePierSign,
        "Route121_EventScript_SafariZoneSign" to Route121_EventScript_SafariZoneSign,
    )
