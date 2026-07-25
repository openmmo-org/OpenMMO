package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity
import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity_BikeShop
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox CeruleanCity_Text_PeopleHereWereRobbed
 * closemessage
 * applymovement LOCALID_CERULEAN_POLICEMAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object CeruleanCity_EventScript_Policeman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_EventScript_Policeman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_defeated TRAINER_TEAM_ROCKET_GRUNT_5, CeruleanCity_EventScript_GruntDefeated
 * message CeruleanCity_Text_GruntIntro
 * waitmessage
 * playbgm MUS_ENCOUNTER_ROCKET, 0
 * waitbuttonpress
 * trainerbattle_no_intro TRAINER_TEAM_ROCKET_GRUNT_5, CeruleanCity_Text_GruntDefeat
 * setvar VAR_MAP_SCENE_CERULEAN_CITY_ROCKET, 1
 * goto CeruleanCity_EventScript_GruntDefeated
 * end
 * ```
 */
internal object CeruleanCity_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_EventScript_Grunt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_SS_TICKET, CeruleanCity_EventScript_LittleBoySlowbroMoved
 * msgbox CeruleanCity_Text_IfSlowbroWasntThereCouldCutTree
 * release
 * end
 * ```
 */
internal object CeruleanCity_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_EventScript_LittleBoy")
}

internal object CeruleanCity_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity.PokemonEncyclopediaAmusing)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * random 4
 * copyvar VAR_0x8008, VAR_RESULT
 * call_if_eq VAR_0x8008, 0, CeruleanCity_EventScript_SlowbroText1
 * call_if_eq VAR_0x8008, 1, CeruleanCity_EventScript_SlowbroText2
 * call_if_eq VAR_0x8008, 2, CeruleanCity_EventScript_SlowbroText3
 * call_if_eq VAR_0x8008, 3, CeruleanCity_EventScript_SlowbroText4
 * release
 * end
 * ```
 */
internal object CeruleanCity_EventScript_Slowbro : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_EventScript_Slowbro")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * random 3
 * copyvar VAR_0x8008, VAR_RESULT
 * call_if_eq VAR_0x8008, 0, CeruleanCity_EventScript_SlowbroCommand1
 * call_if_eq VAR_0x8008, 1, CeruleanCity_EventScript_SlowbroCommand2
 * call_if_eq VAR_0x8008, 2, CeruleanCity_EventScript_SlowbroCommand3
 * waitmessage
 * delay 40
 * playse SE_PIN
 * applymovement LOCALID_CERULEAN_SLOWBRO, Common_Movement_QuestionMark
 * waitmovement 0
 * delay 30
 * call_if_eq VAR_0x8008, 0, CeruleanCity_EventScript_SlowbroFailed1
 * call_if_eq VAR_0x8008, 1, CeruleanCity_EventScript_SlowbroFailed2
 * call_if_eq VAR_0x8008, 2, CeruleanCity_EventScript_SlowbroFailed3
 * release
 * end
 * ```
 */
internal object CeruleanCity_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_EventScript_Lass")
}

internal object CeruleanCity_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity.TrainerLifeIsToughIsntIt)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox CeruleanCity_Text_WantBrightRedBicycle
 * closemessage
 * applymovement LOCALID_CERULEAN_WOMAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object CeruleanCity_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_EventScript_Woman")
}

internal object CeruleanCity_EventScript_CeruleanCaveGuard : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity.ThisIsCeruleanCave)
}

internal object CeruleanCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeruleanCity.CitySign)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_MISTY, 0
 * msgbox CeruleanCity_Text_GymSign
 * releaseall
 * end
 * ```
 */
internal object CeruleanCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_EventScript_GymSign")
}

internal object CeruleanCity_EventScript_BikeShopSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeruleanCity.BikeShopSign)
}

internal object CeruleanCity_EventScript_TrainerTips : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeruleanCity.TrainerTipsHeldItems)
}

internal object CeruleanCity_BikeShop_EventScript_Bicycle : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeruleanCity_BikeShop.ShinyNewBicycle)
}

internal val CeruleanCityScripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_EventScript_Policeman" to CeruleanCity_EventScript_Policeman,
        "CeruleanCity_EventScript_Grunt" to CeruleanCity_EventScript_Grunt,
        "CeruleanCity_EventScript_LittleBoy" to CeruleanCity_EventScript_LittleBoy,
        "CeruleanCity_EventScript_BaldingMan" to CeruleanCity_EventScript_BaldingMan,
        "CeruleanCity_EventScript_Slowbro" to CeruleanCity_EventScript_Slowbro,
        "CeruleanCity_EventScript_Lass" to CeruleanCity_EventScript_Lass,
        "CeruleanCity_EventScript_Youngster" to CeruleanCity_EventScript_Youngster,
        "CeruleanCity_EventScript_Woman" to CeruleanCity_EventScript_Woman,
        "CeruleanCity_EventScript_CeruleanCaveGuard" to CeruleanCity_EventScript_CeruleanCaveGuard,
        "CeruleanCity_EventScript_CitySign" to CeruleanCity_EventScript_CitySign,
        "CeruleanCity_EventScript_GymSign" to CeruleanCity_EventScript_GymSign,
        "CeruleanCity_EventScript_BikeShopSign" to CeruleanCity_EventScript_BikeShopSign,
        "CeruleanCity_EventScript_TrainerTips" to CeruleanCity_EventScript_TrainerTips,
        "CeruleanCity_BikeShop_EventScript_Bicycle" to CeruleanCity_BikeShop_EventScript_Bicycle,
    )
