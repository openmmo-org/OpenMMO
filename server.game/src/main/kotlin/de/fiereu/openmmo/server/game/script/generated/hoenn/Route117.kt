package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route117
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route117_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route117.ArentTheseFlowersPretty)
}

internal object Route117_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route117.AirIsTastyHere)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * special GetDaycareMonNicknames
 * specialvar VAR_RESULT, GetDaycareState
 * goto_if_eq VAR_RESULT, DAYCARE_EGG_WAITING, Route117_EventScript_DaycareEggWaiting
 * goto_if_eq VAR_RESULT, DAYCARE_ONE_MON, Route117_EventScript_CheckOnOneMon
 * goto_if_eq VAR_RESULT, DAYCARE_TWO_MONS, Route117_EventScript_CheckOnTwoMons
 * msgbox Route117_Text_SeeWifeIfYoudLikeMeToRaiseMon, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route117_EventScript_DaycareMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_DaycareMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DYLAN_1, Route117_Text_DylanIntro, Route117_Text_DylanDefeat, Route117_EventScript_RegisterDylan
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route117_EventScript_RematchDylan
 * msgbox Route117_Text_DylanPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route117_EventScript_Dylan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Dylan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LYDIA_1, Route117_Text_LydiaIntro, Route117_Text_LydiaDefeat, Route117_EventScript_RegisterLydia
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route117_EventScript_RematchLydia
 * msgbox Route117_Text_LydiaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route117_EventScript_Lydia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Lydia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ISAAC_1, Route117_Text_IsaacIntro, Route117_Text_IsaacDefeat, Route117_EventScript_RegisterIsaac
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route117_EventScript_RematchIsaac
 * msgbox Route117_Text_IsaacPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route117_EventScript_Isaac : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Isaac")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GREAT_BALL
 * end
 * ```
 */
internal object Route117_EventScript_ItemGreatBall : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_ItemGreatBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_REVIVE
 * end
 * ```
 */
internal object Route117_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_ItemRevive")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MARIA_1, Route117_Text_MariaIntro, Route117_Text_MariaDefeat, Route117_EventScript_RegisterMaria
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route117_EventScript_RematchMaria
 * msgbox Route117_Text_MariaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route117_EventScript_Maria : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Maria")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DEREK, Route117_Text_DerekIntro, Route117_Text_DerekDefeat
 * msgbox Route117_Text_DerekPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route117_EventScript_Derek : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Derek")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_ANNA_AND_MEG_1, Route117_Text_MegIntro, Route117_Text_MegDefeat, Route117_Text_MegNotEnoughMons, Route117_EventScript_RegisterMeg
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route117_EventScript_RematchMeg
 * msgbox Route117_Text_MegPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route117_EventScript_Meg : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Meg")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_ANNA_AND_MEG_1, Route117_Text_AnnaIntro, Route117_Text_AnnaDefeat, Route117_Text_AnnaNotEnoughMons, Route117_EventScript_RegisterAnna
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route117_EventScript_RematchAnna
 * msgbox Route117_Text_AnnaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route117_EventScript_Anna : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Anna")
}

internal object Route117_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route117.DayCarePokemonHadNewMove)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BRANDI, Route117_Text_BrandiIntro, Route117_Text_BrandiDefeat
 * msgbox Route117_Text_BrandiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route117_EventScript_Brandi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Brandi")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_AISHA, Route117_Text_AishaIntro, Route117_Text_AishaDefeat
 * msgbox Route117_Text_AishaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route117_EventScript_Aisha : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Aisha")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MELINA, Route117_Text_MelinaIntro, Route117_Text_MelinaDefeat
 * msgbox Route117_Text_MelinaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route117_EventScript_Melina : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route117_EventScript_Melina")
}

internal object Route117_EventScript_RouteSignVerdanturf : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route117.RouteSignVerdanturf)
}

internal object Route117_EventScript_RouteSignMauville : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route117.RouteSignMauville)
}

internal object Route117_EventScript_DayCareSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route117.DayCareSign)
}

internal val Route117Scripts: Map<String, Script> =
    mapOf(
        "Route117_EventScript_Woman" to Route117_EventScript_Woman,
        "Route117_EventScript_LittleBoy" to Route117_EventScript_LittleBoy,
        "Route117_EventScript_DaycareMan" to Route117_EventScript_DaycareMan,
        "Route117_EventScript_Dylan" to Route117_EventScript_Dylan,
        "Route117_EventScript_Lydia" to Route117_EventScript_Lydia,
        "Route117_EventScript_Isaac" to Route117_EventScript_Isaac,
        "Route117_EventScript_ItemGreatBall" to Route117_EventScript_ItemGreatBall,
        "Route117_EventScript_ItemRevive" to Route117_EventScript_ItemRevive,
        "Route117_EventScript_Maria" to Route117_EventScript_Maria,
        "Route117_EventScript_Derek" to Route117_EventScript_Derek,
        "Route117_EventScript_Meg" to Route117_EventScript_Meg,
        "Route117_EventScript_Anna" to Route117_EventScript_Anna,
        "Route117_EventScript_Girl" to Route117_EventScript_Girl,
        "Route117_EventScript_Brandi" to Route117_EventScript_Brandi,
        "Route117_EventScript_Aisha" to Route117_EventScript_Aisha,
        "Route117_EventScript_Melina" to Route117_EventScript_Melina,
        "Route117_EventScript_RouteSignVerdanturf" to Route117_EventScript_RouteSignVerdanturf,
        "Route117_EventScript_RouteSignMauville" to Route117_EventScript_RouteSignMauville,
        "Route117_EventScript_DayCareSign" to Route117_EventScript_DayCareSign,
    )
