package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route119
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GREG, Route119_Text_GregIntro, Route119_Text_GregDefeat
 * msgbox Route119_Text_GregPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Greg : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Greg")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TAYLOR, Route119_Text_TaylorIntro, Route119_Text_TaylorDefeat
 * msgbox Route119_Text_TaylorPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Taylor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Taylor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DONALD, Route119_Text_DonaldIntro, Route119_Text_DonaldDefeat
 * msgbox Route119_Text_DonaldPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Donald : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Donald")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JACKSON_1, Route119_Text_JacksonIntro, Route119_Text_JacksonDefeat, Route119_EventScript_RegisterJackson
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route119_EventScript_RematchJackson
 * msgbox Route119_Text_JacksonPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route119_EventScript_Jackson : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Jackson")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BRENT, Route119_Text_BrentIntro, Route119_Text_BrentDefeat
 * msgbox Route119_Text_BrentPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Brent : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Brent")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CATHERINE_1, Route119_Text_CatherineIntro, Route119_Text_CatherineDefeat, Route119_EventScript_RegisterCatherine
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route119_EventScript_RematchCatherine
 * msgbox Route119_Text_CatherinePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route119_EventScript_Catherine : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Catherine")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DOUG, Route119_Text_DougIntro, Route119_Text_DougDefeat
 * msgbox Route119_Text_DougPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Doug : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Doug")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KENT, Route119_Text_KentIntro, Route119_Text_KentDefeat
 * msgbox Route119_Text_KentPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Kent : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Kent")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_YASU, Route119_Text_YasuIntro, Route119_Text_YasuDefeat
 * msgbox Route119_Text_YasuPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Yasu : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Yasu")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TAKASHI, Route119_Text_TakashiIntro, Route119_Text_TakashiDefeat
 * msgbox Route119_Text_TakashiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Takashi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Takashi")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HUGH, Route119_Text_HughIntro, Route119_Text_HughDefeat
 * msgbox Route119_Text_HughPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Hugh : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Hugh")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PHIL, Route119_Text_PhilIntro, Route119_Text_PhilDefeat
 * msgbox Route119_Text_PhilPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Phil : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Phil")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SUPER_REPEL
 * end
 * ```
 */
internal object Route119_EventScript_ItemSuperRepel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_ItemSuperRepel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ZINC
 * end
 * ```
 */
internal object Route119_EventScript_ItemZinc : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_ItemZinc")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ELIXIR
 * end
 * ```
 */
internal object Route119_EventScript_ItemElixir : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_ItemElixir")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_LEAF_STONE
 * end
 * ```
 */
internal object Route119_EventScript_ItemLeafStone : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_ItemLeafStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object Route119_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HYPER_POTION
 * end
 * ```
 */
internal object Route119_EventScript_ItemHyperPotion : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_ItemHyperPotion")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route119_Text_StayAwayFromWeatherInstitute, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route119_EventScript_BridgeAquaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_BridgeAquaGrunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox Route119_Text_DontGoNearWeatherInstitute, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object Route119_EventScript_BridgeAquaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_BridgeAquaGrunt2")
}

internal object Route119_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route119.ThoughtFlyByCatchingBirdMons)
}

internal object Route119_EventScript_CyclingTriathleteM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route119.TallGrassSnaresBikeTires)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HYPER_POTION
 * end
 * ```
 */
internal object Route119_EventScript_ItemHyperPotion2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_ItemHyperPotion2")
}

internal object Route119_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route119.CanYourMonMakeSecretBase)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIDEO, Route119_Text_HideoIntro, Route119_Text_HideoDefeat
 * msgbox Route119_Text_HideoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Hideo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Hideo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8009, 6
 * goto EventScript_Kecleon
 * end
 * ```
 */
internal object Route119_EventScript_Kecleon1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Kecleon1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8009, 7
 * goto EventScript_Kecleon
 * end
 * ```
 */
internal object Route119_EventScript_Kecleon2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Kecleon2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHRIS, Route119_Text_ChrisIntro, Route119_Text_ChrisDefeat
 * msgbox Route119_Text_ChrisPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Chris : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Chris")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RACHEL, Route119_Text_RachelIntro, Route119_Text_RachelDefeat
 * msgbox Route119_Text_RachelPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Rachel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Rachel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DAYTON, Route119_Text_DaytonIntro, Route119_Text_DaytonDefeat
 * msgbox Route119_Text_DaytonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Dayton : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Dayton")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FABIAN, Route119_Text_FabianIntro, Route119_Text_FabianDefeat
 * msgbox Route119_Text_FabianPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route119_EventScript_Fabian : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_Fabian")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object Route119_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_ItemNugget")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ELIXIR
 * end
 * ```
 */
internal object Route119_EventScript_ItemElixir2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route119_EventScript_ItemElixir2")
}

internal object Route119_EventScript_WeatherInstituteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route119.WeatherInstitute)
}

internal object Route119_EventScript_RouteSignFortree : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route119.RouteSignFortree)
}

internal object Route119_EventScript_TrainerTipsDecoration : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route119.TrainerTipsDecoration)
}

internal val Route119Scripts: Map<String, Script> =
    mapOf(
        "Route119_EventScript_Greg" to Route119_EventScript_Greg,
        "Route119_EventScript_Taylor" to Route119_EventScript_Taylor,
        "Route119_EventScript_Donald" to Route119_EventScript_Donald,
        "Route119_EventScript_Jackson" to Route119_EventScript_Jackson,
        "Route119_EventScript_Brent" to Route119_EventScript_Brent,
        "Route119_EventScript_Catherine" to Route119_EventScript_Catherine,
        "Route119_EventScript_Doug" to Route119_EventScript_Doug,
        "Route119_EventScript_Kent" to Route119_EventScript_Kent,
        "Route119_EventScript_Yasu" to Route119_EventScript_Yasu,
        "Route119_EventScript_Takashi" to Route119_EventScript_Takashi,
        "Route119_EventScript_Hugh" to Route119_EventScript_Hugh,
        "Route119_EventScript_Phil" to Route119_EventScript_Phil,
        "Route119_EventScript_ItemSuperRepel" to Route119_EventScript_ItemSuperRepel,
        "Route119_EventScript_ItemZinc" to Route119_EventScript_ItemZinc,
        "Route119_EventScript_ItemElixir" to Route119_EventScript_ItemElixir,
        "Route119_EventScript_ItemLeafStone" to Route119_EventScript_ItemLeafStone,
        "Route119_EventScript_ItemRareCandy" to Route119_EventScript_ItemRareCandy,
        "Route119_EventScript_ItemHyperPotion" to Route119_EventScript_ItemHyperPotion,
        "Route119_EventScript_BridgeAquaGrunt1" to Route119_EventScript_BridgeAquaGrunt1,
        "Route119_EventScript_BridgeAquaGrunt2" to Route119_EventScript_BridgeAquaGrunt2,
        "Route119_EventScript_Boy1" to Route119_EventScript_Boy1,
        "Route119_EventScript_CyclingTriathleteM" to Route119_EventScript_CyclingTriathleteM,
        "Route119_EventScript_ItemHyperPotion2" to Route119_EventScript_ItemHyperPotion2,
        "Route119_EventScript_Boy2" to Route119_EventScript_Boy2,
        "Route119_EventScript_Hideo" to Route119_EventScript_Hideo,
        "Route119_EventScript_Kecleon1" to Route119_EventScript_Kecleon1,
        "Route119_EventScript_Kecleon2" to Route119_EventScript_Kecleon2,
        "Route119_EventScript_Chris" to Route119_EventScript_Chris,
        "Route119_EventScript_Rachel" to Route119_EventScript_Rachel,
        "Route119_EventScript_Dayton" to Route119_EventScript_Dayton,
        "Route119_EventScript_Fabian" to Route119_EventScript_Fabian,
        "Route119_EventScript_ItemNugget" to Route119_EventScript_ItemNugget,
        "Route119_EventScript_ItemElixir2" to Route119_EventScript_ItemElixir2,
        "Route119_EventScript_WeatherInstituteSign" to Route119_EventScript_WeatherInstituteSign,
        "Route119_EventScript_RouteSignFortree" to Route119_EventScript_RouteSignFortree,
        "Route119_EventScript_TrainerTipsDecoration" to Route119_EventScript_TrainerTipsDecoration,
    )
