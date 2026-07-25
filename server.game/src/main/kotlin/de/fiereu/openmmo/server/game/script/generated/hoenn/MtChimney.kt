package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtChimney
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_unset FLAG_EVIL_LEADER_PLEASE_STOP, MtChimney_EventScript_ArchieGoStopTeamMagma
 * call_if_set FLAG_EVIL_LEADER_PLEASE_STOP, MtChimney_EventScript_ArchieBusyFighting
 * closemessage
 * applymovement LOCALID_MT_CHIMNEY_ARCHIE, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * setflag FLAG_EVIL_LEADER_PLEASE_STOP
 * release
 * end
 * ```
 */
internal object MtChimney_EventScript_Archie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Archie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * playbgm MUS_ENCOUNTER_MAGMA, FALSE
 * msgbox MtChimney_Text_MeteoriteWillActivateVolcano, MSGBOX_DEFAULT
 * applymovement LOCALID_MT_CHIMNEY_MAXIE, Common_Movement_FacePlayer
 * waitmovement 0
 * playse SE_PIN
 * applymovement LOCALID_MT_CHIMNEY_MAXIE, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_MT_CHIMNEY_MAXIE, Common_Movement_Delay48
 * waitmovement 0
 * msgbox MtChimney_Text_MaxieIntro, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_MAXIE_MT_CHIMNEY, MtChimney_Text_MaxieDefeat
 * msgbox MtChimney_Text_MaxieYouHaventSeenLastOfMagma, MSGBOX_DEFAULT
 * closemessage
 * delay 30
 * fadescreen FADE_TO_BLACK
 * removeobject LOCALID_MT_CHIMNEY_MAXIE
 * removeobject LOCALID_MT_CHIMNEY_MAGMA_GRUNT_1
 * removeobject LOCALID_MT_CHIMNEY_TABITHA
 * removeobject LOCALID_MT_CHIMNEY_MAGMA_GRUNT_2
 * setflag FLAG_HIDE_MT_CHIMNEY_TEAM_MAGMA
 * fadescreen FADE_FROM_BLACK
 * setobjectxyperm LOCALID_MT_CHIMNEY_ARCHIE, 10, 12
 * addobject LOCALID_MT_CHIMNEY_ARCHIE
 * call_if_eq VAR_FACING, DIR_EAST, MtChimney_EventScript_ArchieApproachPlayerEast
 * call_if_eq VAR_FACING, DIR_NORTH, MtChimney_EventScript_ArchieApproachPlayerNorth
 * applymovement LOCALID_PLAYER, Common_Movement_WalkInPlaceFasterLeft
 * waitmovement 0
 * msgbox MtChimney_Text_ArchieThankYou, MSGBOX_DEFAULT
 * closemessage
 * call_if_eq VAR_FACING, DIR_EAST, MtChimney_EventScript_ArchieExitEast
 * call_if_eq VAR_FACING, DIR_NORTH, MtChimney_EventScript_ArchieExitNorth
 * removeobject LOCALID_MT_CHIMNEY_ARCHIE
 * setflag FLAG_HIDE_MT_CHIMNEY_TEAM_AQUA
 * setflag FLAG_DEFEATED_EVIL_TEAM_MT_CHIMNEY
 * clearflag FLAG_HIDE_FALLARBOR_HOUSE_PROF_COZMO
 * setflag FLAG_HIDE_METEOR_FALLS_1F_1R_COZMO
 * clearflag FLAG_HIDE_MT_CHIMNEY_LAVA_COOKIE_LADY
 * releaseall
 * end
 * ```
 */
internal object MtChimney_EventScript_Maxie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Maxie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TABITHA_MT_CHIMNEY, MtChimney_Text_TabithaIntro, MtChimney_Text_TabithaDefeat
 * msgbox MtChimney_Text_TabithaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtChimney_EventScript_Tabitha : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Tabitha")
}

internal object MtChimney_EventScript_BusyMagmaGrunt5 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.DouseThemInFire)
}

internal object MtChimney_EventScript_BusyMagmaGrunt4 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.AquasNameSimilar)
}

internal object MtChimney_EventScript_BusyAquaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.LessHabitatForWaterPokemon)
}

internal object MtChimney_EventScript_BusyAquaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.MagmaOutnumbersUs)
}

internal object MtChimney_EventScript_BusyAquaGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.MagmasNameSimilar)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * showmoneybox 0, 0
 * msgbox MtChimney_Text_LavaCookiesJust200, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MtChimney_EventScript_DeclineLavaCookie
 * checkmoney 200
 * goto_if_eq VAR_RESULT, FALSE, MtChimney_EventScript_NotEnoughMoney
 * msgbox MtChimney_Text_ThankYouDear, MSGBOX_DEFAULT
 * checkitemspace ITEM_LAVA_COOKIE
 * call_if_eq VAR_RESULT, TRUE, MtChimney_EventScript_RemoveMoney
 * giveitem ITEM_LAVA_COOKIE
 * goto_if_eq VAR_RESULT, FALSE, MtChimney_EventScript_BagIsFull
 * hidemoneybox
 * release
 * end
 * ```
 */
internal object MtChimney_EventScript_LavaCookieLady : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_LavaCookieLady")
}

internal object MtChimney_EventScript_BusyMagmaGrunt6 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.KeepMakingMoreLand)
}

internal object MtChimney_EventScript_AquaPoochyena : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.Bushaa)
}

internal object MtChimney_EventScript_MagmaPoochyena : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.Bufoh)
}

internal object MtChimney_EventScript_BusyMagmaGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.MeteoritesPackAmazingPower)
}

internal object MtChimney_EventScript_BusyMagmaGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.YouBetterNotMessWithUs)
}

internal object MtChimney_EventScript_BusyMagmaGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.TeamAquaAlwaysMessingWithPlans)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MT_CHIMNEY_2, MtChimney_Text_Grunt2Intro, MtChimney_Text_Grunt2Defeat
 * msgbox MtChimney_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtChimney_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHELBY_1, MtChimney_Text_ShelbyIntro, MtChimney_Text_ShelbyDefeat, MtChimney_EventScript_DefeatedShelby
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MtChimney_EventScript_RematchShelby
 * msgbox MtChimney_Text_ShelbyPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MtChimney_EventScript_Shelby : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Shelby")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MELISSA, MtChimney_Text_MelissaIntro, MtChimney_Text_MelissaDefeat
 * msgbox MtChimney_Text_MelissaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtChimney_EventScript_Melissa : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Melissa")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHEILA, MtChimney_Text_SheilaIntro, MtChimney_Text_SheilaDefeat
 * msgbox MtChimney_Text_SheilaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtChimney_EventScript_Sheila : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Sheila")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHIRLEY, MtChimney_Text_ShirleyIntro, MtChimney_Text_ShirleyDefeat
 * msgbox MtChimney_Text_ShirleyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtChimney_EventScript_Shirley : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Shirley")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GRUNT_MT_CHIMNEY_1, MtChimney_Text_Grunt1Intro, MtChimney_Text_Grunt1Defeat
 * msgbox MtChimney_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtChimney_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SAWYER_1, MtChimney_Text_SawyerIntro, MtChimney_Text_SawyerDefeat, MtChimney_EventScript_SawyerDefeated
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, MtChimney_EventScript_SawyerRematch
 * msgbox MtChimney_Text_SawyerPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MtChimney_EventScript_Sawyer : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_Sawyer")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_unset FLAG_DEFEATED_EVIL_TEAM_MT_CHIMNEY, MtChimney_EventScript_MachineOn
 * goto_if_set FLAG_RECEIVED_METEORITE, MtChimney_EventScript_MachineOff
 * msgbox MtChimney_Text_RemoveTheMeteorite, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MtChimney_EventScript_LeaveMeteoriteAlone
 * msgbox MtChimney_Text_PlayerRemovedMeteorite, MSGBOX_DEFAULT
 * giveitem ITEM_METEORITE
 * setflag FLAG_RECEIVED_METEORITE
 * releaseall
 * end
 * ```
 */
internal object MtChimney_EventScript_MeteoriteMachine : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtChimney_EventScript_MeteoriteMachine")
}

internal object MtChimney_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtChimney.RouteSign)
}

internal val MtChimneyScripts: Map<String, Script> =
    mapOf(
        "MtChimney_EventScript_Archie" to MtChimney_EventScript_Archie,
        "MtChimney_EventScript_Maxie" to MtChimney_EventScript_Maxie,
        "MtChimney_EventScript_Tabitha" to MtChimney_EventScript_Tabitha,
        "MtChimney_EventScript_BusyMagmaGrunt5" to MtChimney_EventScript_BusyMagmaGrunt5,
        "MtChimney_EventScript_BusyMagmaGrunt4" to MtChimney_EventScript_BusyMagmaGrunt4,
        "MtChimney_EventScript_BusyAquaGrunt2" to MtChimney_EventScript_BusyAquaGrunt2,
        "MtChimney_EventScript_BusyAquaGrunt1" to MtChimney_EventScript_BusyAquaGrunt1,
        "MtChimney_EventScript_BusyAquaGrunt3" to MtChimney_EventScript_BusyAquaGrunt3,
        "MtChimney_EventScript_LavaCookieLady" to MtChimney_EventScript_LavaCookieLady,
        "MtChimney_EventScript_BusyMagmaGrunt6" to MtChimney_EventScript_BusyMagmaGrunt6,
        "MtChimney_EventScript_AquaPoochyena" to MtChimney_EventScript_AquaPoochyena,
        "MtChimney_EventScript_MagmaPoochyena" to MtChimney_EventScript_MagmaPoochyena,
        "MtChimney_EventScript_BusyMagmaGrunt2" to MtChimney_EventScript_BusyMagmaGrunt2,
        "MtChimney_EventScript_BusyMagmaGrunt3" to MtChimney_EventScript_BusyMagmaGrunt3,
        "MtChimney_EventScript_BusyMagmaGrunt1" to MtChimney_EventScript_BusyMagmaGrunt1,
        "MtChimney_EventScript_Grunt2" to MtChimney_EventScript_Grunt2,
        "MtChimney_EventScript_Shelby" to MtChimney_EventScript_Shelby,
        "MtChimney_EventScript_Melissa" to MtChimney_EventScript_Melissa,
        "MtChimney_EventScript_Sheila" to MtChimney_EventScript_Sheila,
        "MtChimney_EventScript_Shirley" to MtChimney_EventScript_Shirley,
        "MtChimney_EventScript_Grunt1" to MtChimney_EventScript_Grunt1,
        "MtChimney_EventScript_Sawyer" to MtChimney_EventScript_Sawyer,
        "MtChimney_EventScript_MeteoriteMachine" to MtChimney_EventScript_MeteoriteMachine,
        "MtChimney_EventScript_RouteSign" to MtChimney_EventScript_RouteSign,
    )
