package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route112
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * delay 40
 * applymovement LOCALID_ROUTE112_GRUNT_1, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * delay 20
 * msgbox Route112_Text_LeaderGoingToAwakenThing, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE112_GRUNT_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * delay 40
 * applymovement LOCALID_ROUTE112_GRUNT_2, Common_Movement_WalkInPlaceFasterLeft
 * waitmovement 0
 * delay 20
 * msgbox Route112_Text_YeahWeNeedMeteorite, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE112_GRUNT_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * delay 40
 * applymovement LOCALID_ROUTE112_GRUNT_1, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * delay 20
 * msgbox Route112_Text_OhThatsWhyCrewWentToFallarbor, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE112_GRUNT_1, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * delay 40
 * applymovement LOCALID_ROUTE112_GRUNT_2, Common_Movement_WalkInPlaceFasterLeft
 * waitmovement 0
 * delay 20
 * msgbox Route112_Text_CantLetAnyonePassUntilTheyreBack, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_ROUTE112_GRUNT_2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object Route112_EventScript_MagmaGrunts : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_MagmaGrunts")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BRICE, Route112_Text_BriceIntro, Route112_Text_BriceDefeat
 * msgbox Route112_Text_BricePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route112_EventScript_Brice : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_Brice")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LARRY, Route112_Text_LarryIntro, Route112_Text_LarryDefeat
 * msgbox Route112_Text_LarryPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route112_EventScript_Larry : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_Larry")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAROL, Route112_Text_CarolIntro, Route112_Text_CarolDefeat
 * msgbox Route112_Text_CarolPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route112_EventScript_Carol : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_Carol")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TRENT_1, Route112_Text_TrentIntro, Route112_Text_TrentDefeat, Route112_EventScript_RegisterTrent
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route112_EventScript_RematchTrent
 * msgbox Route112_Text_TrentPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route112_EventScript_Trent : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_Trent")
}

internal object Route112_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route112.NotEasyToGetBackToLavaridge)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object Route112_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_ItemNugget")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BRYANT, Route112_Text_BryantIntro, Route112_Text_BryantDefeat
 * msgbox Route112_Text_BryantPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route112_EventScript_Bryant : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_Bryant")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHAYLA, Route112_Text_ShaylaIntro, Route112_Text_ShaylaDefeat
 * msgbox Route112_Text_ShaylaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route112_EventScript_Shayla : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route112_EventScript_Shayla")
}

internal object Route112_EventScript_MtChimneySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route112.MtChimneySign)
}

internal object Route112_EventScript_MtChimneyCableCarSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route112.MtChimneyCableCarSign)
}

internal object Route112_EventScript_RouteSignLavaridge : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route112.RouteSignLavaridge)
}

internal val Route112Scripts: Map<String, Script> =
    mapOf(
        "Route112_EventScript_MagmaGrunts" to Route112_EventScript_MagmaGrunts,
        "Route112_EventScript_Brice" to Route112_EventScript_Brice,
        "Route112_EventScript_Larry" to Route112_EventScript_Larry,
        "Route112_EventScript_Carol" to Route112_EventScript_Carol,
        "Route112_EventScript_Trent" to Route112_EventScript_Trent,
        "Route112_EventScript_Hiker" to Route112_EventScript_Hiker,
        "Route112_EventScript_ItemNugget" to Route112_EventScript_ItemNugget,
        "Route112_EventScript_Bryant" to Route112_EventScript_Bryant,
        "Route112_EventScript_Shayla" to Route112_EventScript_Shayla,
        "Route112_EventScript_MtChimneySign" to Route112_EventScript_MtChimneySign,
        "Route112_EventScript_MtChimneyCableCarSign" to Route112_EventScript_MtChimneyCableCarSign,
        "Route112_EventScript_RouteSignLavaridge" to Route112_EventScript_RouteSignLavaridge,
    )
