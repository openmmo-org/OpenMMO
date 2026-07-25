package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity_OceanicMuseum_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_OceanicMuseum_2F_Text_ThankYouForTheParts, MSGBOX_DEFAULT
 * closemessage
 * playbgm MUS_ENCOUNTER_AQUA, TRUE
 * addobject LOCALID_OCEANIC_MUSEUM_2F_GRUNT_1
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_1, SlateportCity_OceanicMuseum_2F_Movement_FirstGruntEnter
 * waitmovement 0
 * addobject LOCALID_OCEANIC_MUSEUM_2F_GRUNT_2
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_2, SlateportCity_OceanicMuseum_2F_Movement_SecondGruntEnter
 * waitmovement 0
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_1, SlateportCity_OceanicMuseum_2F_Movement_FirstGruntApproach
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_2, SlateportCity_OceanicMuseum_2F_Movement_SecondGruntApproach
 * waitmovement 0
 * call_if_eq VAR_FACING, DIR_SOUTH, SlateportCity_OceanicMuseum_2F_EventScript_PlayerFaceGrunts
 * call_if_eq VAR_FACING, DIR_EAST, SlateportCity_OceanicMuseum_2F_EventScript_PlayerFaceGrunts
 * msgbox SlateportCity_OceanicMuseum_2F_Text_WellTakeThoseParts, MSGBOX_DEFAULT
 * call_if_ne VAR_FACING, DIR_EAST, SlateportCity_OceanicMuseum_2F_EventScript_SternFaceGrunts
 * msgbox SlateportCity_OceanicMuseum_2F_Text_SternWhoAreYou, MSGBOX_DEFAULT
 * msgbox SlateportCity_OceanicMuseum_2F_Text_WereTeamAqua, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_2, SlateportCity_OceanicMuseum_2F_Movement_GruntApproachToBattle
 * waitmovement 0
 * call_if_eq VAR_FACING, DIR_SOUTH, SlateportCity_OceanicMuseum_2F_EventScript_PlayerApproachGruntSouth
 * call_if_eq VAR_FACING, DIR_WEST, SlateportCity_OceanicMuseum_2F_EventScript_PlayerApproachGruntWest
 * trainerbattle_no_intro TRAINER_GRUNT_MUSEUM_1, SlateportCity_OceanicMuseum_2F_Text_Grunt1Defeat
 * msgbox SlateportCity_OceanicMuseum_2F_Text_BossGoingToBeFurious, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_2, SlateportCity_OceanicMuseum_2F_Movement_GruntDefeated
 * waitmovement 0
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_1, SlateportCity_OceanicMuseum_2F_Movement_GruntApproachToBattle
 * waitmovement 0
 * msgbox SlateportCity_OceanicMuseum_2F_Text_LetMeTakeCareOfThis, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_GRUNT_MUSEUM_2, SlateportCity_OceanicMuseum_2F_Text_Grunt2Defeat
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_1, SlateportCity_OceanicMuseum_2F_Movement_GruntDefeated
 * waitmovement 0
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_1, Common_Movement_WalkInPlaceFasterDown
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_2, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * msgbox SlateportCity_OceanicMuseum_2F_Text_MeddlingKid, MSGBOX_DEFAULT
 * closemessage
 * delay 35
 * addobject LOCALID_OCEANIC_MUSEUM_2F_ARCHIE
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_ARCHIE, SlateportCity_OceanicMuseum_2F_Movement_ArchieEnter
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_GRUNT_2, SlateportCity_OceanicMuseum_2F_Movement_GruntMoveForArchie
 * waitmovement 0
 * msgbox SlateportCity_OceanicMuseum_2F_Text_CameToSeeWhatsTakingSoLong, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_OCEANIC_MUSEUM_2F_ARCHIE, SlateportCity_OceanicMuseum_2F_Movement_ArchieApproachPlayer
 * waitmovement 0
 * msgbox SlateportCity_OceanicMuseum_2F_Text_ArchieWarning, MSGBOX_DEFAULT
 * closemessage
 * savebgm MUS_DUMMY
 * fadedefaultbgm
 * fadescreen FADE_TO_BLACK
 * removeobject LOCALID_OCEANIC_MUSEUM_2F_ARCHIE
 * removeobject LOCALID_OCEANIC_MUSEUM_2F_GRUNT_1
 * removeobject LOCALID_OCEANIC_MUSEUM_2F_GRUNT_2
 * fadescreen FADE_FROM_BLACK
 * delay 30
 * setflag FLAG_HIDE_SLATEPORT_CITY_OCEANIC_MUSEUM_AQUA_GRUNTS
 * applymovement LOCALID_PLAYER, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * msgbox SlateportCity_OceanicMuseum_2F_Text_SternThankYouForSavingUs, MSGBOX_DEFAULT
 * setvar VAR_0x8004, ITEM_DEVON_GOODS
 * call Common_EventScript_PlayerHandedOverTheItem
 * msgbox SlateportCity_OceanicMuseum_2F_Text_SternIveGotToGo, MSGBOX_DEFAULT
 * closemessage
 * fadescreen FADE_TO_BLACK
 * playfanfare MUS_HEAL
 * waitfanfare
 * special HealPlayerParty
 * removeobject LOCALID_OCEANIC_MUSEUM_2F_CAPT_STERN
 * setflag FLAG_HIDE_ROUTE_110_TEAM_AQUA
 * call_if_eq VAR_REGISTER_BIRCH_STATE, 0, SlateportCity_OceanicMuseum_2F_EventScript_ReadyRegisterBirch
 * setflag FLAG_DELIVERED_DEVON_GOODS
 * clearflag FLAG_HIDE_ROUTE_116_DEVON_EMPLOYEE
 * setflag FLAG_HIDE_RUSTBORO_CITY_DEVON_CORP_3F_EMPLOYEE
 * setvar VAR_SLATEPORT_OUTSIDE_MUSEUM_STATE, 1
 * fadescreen FADE_FROM_BLACK
 * release
 * end
 * ```
 */
internal object SlateportCity_OceanicMuseum_2F_EventScript_CaptStern : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_OceanicMuseum_2F_EventScript_CaptStern")
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_2F.RemindsMeOfAbandonedShip)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_2F.DontRunInMuseum)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SlateportCity_OceanicMuseum_2F_Text_WantToRideSubmarine, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron3")
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.WaterQualitySample1)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.WaterQualitySample2)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SubmersibleReplica : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SumbersibleReplica)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SubmarineReplica : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SubmarineReplica)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SSTidalReplica : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SSTidalReplica)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SSAnneReplica : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SSAnneReplica)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_SurfaceSeawaterDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.SurfaceSeawaterDisplay)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_DeepSeawaterDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.DeepSeawaterDisplay)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_HoennModel : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SlateportCity_OceanicMuseum_2F.HoennModel)
}

internal object SlateportCity_OceanicMuseum_2F_EventScript_PressureExperiment : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_2F.PressureExperiment)
}

internal val SlateportCity_OceanicMuseum_2FScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_OceanicMuseum_2F_EventScript_CaptStern" to
            SlateportCity_OceanicMuseum_2F_EventScript_CaptStern,
        "SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron1" to
            SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron1,
        "SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron2" to
            SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron2,
        "SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron3" to
            SlateportCity_OceanicMuseum_2F_EventScript_MuseumPatron3,
        "SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample1" to
            SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample1,
        "SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample2" to
            SlateportCity_OceanicMuseum_2F_EventScript_WaterQualitySample2,
        "SlateportCity_OceanicMuseum_2F_EventScript_SubmersibleReplica" to
            SlateportCity_OceanicMuseum_2F_EventScript_SubmersibleReplica,
        "SlateportCity_OceanicMuseum_2F_EventScript_SubmarineReplica" to
            SlateportCity_OceanicMuseum_2F_EventScript_SubmarineReplica,
        "SlateportCity_OceanicMuseum_2F_EventScript_SSTidalReplica" to
            SlateportCity_OceanicMuseum_2F_EventScript_SSTidalReplica,
        "SlateportCity_OceanicMuseum_2F_EventScript_SSAnneReplica" to
            SlateportCity_OceanicMuseum_2F_EventScript_SSAnneReplica,
        "SlateportCity_OceanicMuseum_2F_EventScript_SurfaceSeawaterDisplay" to
            SlateportCity_OceanicMuseum_2F_EventScript_SurfaceSeawaterDisplay,
        "SlateportCity_OceanicMuseum_2F_EventScript_DeepSeawaterDisplay" to
            SlateportCity_OceanicMuseum_2F_EventScript_DeepSeawaterDisplay,
        "SlateportCity_OceanicMuseum_2F_EventScript_HoennModel" to
            SlateportCity_OceanicMuseum_2F_EventScript_HoennModel,
        "SlateportCity_OceanicMuseum_2F_EventScript_PressureExperiment" to
            SlateportCity_OceanicMuseum_2F_EventScript_PressureExperiment,
    )
