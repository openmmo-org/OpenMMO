package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route4_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route4.TrippedOverGeodude)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LASS_CRISSY, Route4_Text_CrissyIntro, Route4_Text_CrissyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route4_EventScript_CrissyRematch
 * msgbox Route4_Text_CrissyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route4_EventScript_Crissy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route4_EventScript_Crissy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM05
 * end
 * ```
 */
internal object Route4_EventScript_ItemTM05 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route4_EventScript_ItemTM05")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BROCK, 3
 * msgbox Route4_Text_PeopleLikeAndRespectBrock
 * release
 * end
 * ```
 */
internal object Route4_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route4_EventScript_Boy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_MEGA_PUNCH, EventScript_MegaPunchTaught
 * msgbox Text_MegaPunchTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_MegaPunchDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_MegaPunchDeclined
 * msgbox Text_MegaPunchWhichMon
 * setvar VAR_0x8005, MOVETUTOR_MEGA_PUNCH
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_MegaPunchDeclined
 * setflag FLAG_TUTOR_MEGA_PUNCH
 * goto EventScript_MegaPunchTaught
 * end
 * ```
 */
internal object Route4_EventScript_MegaPunchTutor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route4_EventScript_MegaPunchTutor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_MEGA_KICK, EventScript_MegaKickTaught
 * msgbox Text_MegaKickTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_MegaKickDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_MegaKickDeclined
 * msgbox Text_MegaKickWhichMon
 * setvar VAR_0x8005, MOVETUTOR_MEGA_KICK
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_MegaKickDeclined
 * setflag FLAG_TUTOR_MEGA_KICK
 * goto EventScript_MegaKickTaught
 * end
 * ```
 */
internal object Route4_EventScript_MegaKickTutor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route4_EventScript_MegaKickTutor")
}

internal object Route4_EventScript_MtMoonSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route4.MtMoonEntrance)
}

internal object Route4_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route4.RouteSign)
}

internal val Route4Scripts: Map<String, Script> =
    mapOf(
        "Route4_EventScript_Woman" to Route4_EventScript_Woman,
        "Route4_EventScript_Crissy" to Route4_EventScript_Crissy,
        "Route4_EventScript_ItemTM05" to Route4_EventScript_ItemTM05,
        "Route4_EventScript_Boy" to Route4_EventScript_Boy,
        "Route4_EventScript_MegaPunchTutor" to Route4_EventScript_MegaPunchTutor,
        "Route4_EventScript_MegaKickTutor" to Route4_EventScript_MegaKickTutor,
        "Route4_EventScript_MtMoonSign" to Route4_EventScript_MtMoonSign,
        "Route4_EventScript_RouteSign" to Route4_EventScript_RouteSign,
    )
