package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route103
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route103_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route103.ShortcutToOldale)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * checkplayergender
 * goto_if_eq VAR_RESULT, MALE, Route103_EventScript_RivalMay
 * goto_if_eq VAR_RESULT, FEMALE, Route103_EventScript_RivalBrendan
 * end
 * ```
 */
internal object Route103_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Rival")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DAISY, Route103_Text_DaisyIntro, Route103_Text_DaisyDefeated
 * msgbox Route103_Text_DaisyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Daisy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Daisy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_AMY_AND_LIV_1, Route103_Text_LivIntro, Route103_Text_LivDefeated, Route103_Text_LivNotEnoughPokemon, Route102_EventScript_LivRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route102_EventScript_LivRematch
 * msgbox Route103_Text_LivPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Liv : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Liv")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_AMY_AND_LIV_1, Route103_Text_AmyIntro, Route103_Text_AmyDefeated, Route103_Text_AmyNotEnoughPokemon, Route102_EventScript_AmyRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route102_EventScript_AmyRematch
 * msgbox Route103_Text_AmyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Amy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Amy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ANDREW, Route103_Text_AndrewIntro, Route103_Text_AndrewDefeated
 * msgbox Route103_Text_AndrewPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Andrew : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Andrew")
}

internal object Route103_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route103.ShouldHaveBroughtPotion)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MIGUEL_1, Route103_Text_MiguelIntro, Route103_Text_MiguelDefeated, Route102_EventScript_MiguelRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route103_EventScript_MiguelRematch
 * msgbox Route103_Text_MiguelPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route103_EventScript_Miguel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Miguel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GUARD_SPEC
 * end
 * ```
 */
internal object Route103_EventScript_ItemGuardSpec : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_ItemGuardSpec")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RHETT, Route103_Text_RhettIntro, Route103_Text_RhettDefeated
 * msgbox Route103_Text_RhettPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Rhett : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Rhett")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MARCOS, Route103_Text_MarcosIntro, Route103_Text_MarcosDefeated
 * msgbox Route103_Text_MarcosPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Marcos : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Marcos")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ISABELLE, Route103_Text_IsabelleIntro, Route103_Text_IsabelleDefeated
 * msgbox Route103_Text_IsabellePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Isabelle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Isabelle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PETE, Route103_Text_PeteIntro, Route103_Text_PeteDefeated
 * msgbox Route103_Text_PetePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route103_EventScript_Pete : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_Pete")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PP_UP
 * end
 * ```
 */
internal object Route103_EventScript_ItemPPUp : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route103_EventScript_ItemPPUp")
}

internal object Route103_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route103.RouteSign)
}

internal val Route103Scripts: Map<String, Script> =
    mapOf(
        "Route103_EventScript_Man" to Route103_EventScript_Man,
        "Route103_EventScript_Rival" to Route103_EventScript_Rival,
        "Route103_EventScript_Daisy" to Route103_EventScript_Daisy,
        "Route103_EventScript_Liv" to Route103_EventScript_Liv,
        "Route103_EventScript_Amy" to Route103_EventScript_Amy,
        "Route103_EventScript_Andrew" to Route103_EventScript_Andrew,
        "Route103_EventScript_Boy" to Route103_EventScript_Boy,
        "Route103_EventScript_Miguel" to Route103_EventScript_Miguel,
        "Route103_EventScript_ItemGuardSpec" to Route103_EventScript_ItemGuardSpec,
        "Route103_EventScript_Rhett" to Route103_EventScript_Rhett,
        "Route103_EventScript_Marcos" to Route103_EventScript_Marcos,
        "Route103_EventScript_Isabelle" to Route103_EventScript_Isabelle,
        "Route103_EventScript_Pete" to Route103_EventScript_Pete,
        "Route103_EventScript_ItemPPUp" to Route103_EventScript_ItemPPUp,
        "Route103_EventScript_RouteSign" to Route103_EventScript_RouteSign,
    )
