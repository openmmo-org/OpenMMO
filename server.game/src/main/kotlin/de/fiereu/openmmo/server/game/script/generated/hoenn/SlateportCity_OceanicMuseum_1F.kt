package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SlateportCity_OceanicMuseum_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SlateportCity_OceanicMuseum_1F_EventScript_EntranceAttendant : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.PleaseEnjoyYourself)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.WouldStuffHereMakeMeRich)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.CanLearnForNefariousDeeds)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.OurBossIsntHere)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.AquaExistForGoodOfAll)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.RustboroBungled)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt6 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.DidntHaveMoney)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.LearnAboutSeaForBattling)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.SternIsRoleModel)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.MustBePokemonWeDontKnow)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SlateportCity_OceanicMuseum_1F.WantSeaPokemon)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * delay 8
 * playse SE_PIN
 * applymovement LOCALID_OCEANIC_MUSEUM_FAMILIAR_GRUNT, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_OCEANIC_MUSEUM_FAMILIAR_GRUNT, Common_Movement_Delay48
 * waitmovement 0
 * msgbox SlateportCity_OceanicMuseum_1F_Text_RememberMeTakeThis, MSGBOX_DEFAULT
 * giveitem ITEM_TM_THIEF
 * goto_if_eq VAR_RESULT, 0, SlateportCity_OceanicMuseum_1F_EventScript_NoRoomForThief
 * setflag FLAG_RECEIVED_TM_THIEF
 * msgbox SlateportCity_OceanicMuseum_1F_Text_HopeINeverSeeYouAgain, MSGBOX_DEFAULT
 * closemessage
 * goto_if_eq VAR_FACING, DIR_NORTH, SlateportCity_OceanicMuseum_1F_EventScript_FamiliarGruntExitNorth
 * goto_if_eq VAR_FACING, DIR_SOUTH, SlateportCity_OceanicMuseum_1F_EventScript_FamiliarGruntExitSouth
 * goto_if_eq VAR_FACING, DIR_WEST, SlateportCity_OceanicMuseum_1F_EventScript_FamiliarGruntExitWestEast
 * goto_if_eq VAR_FACING, DIR_EAST, SlateportCity_OceanicMuseum_1F_EventScript_FamiliarGruntExitWestEast
 * end
 * ```
 */
internal object SlateportCity_OceanicMuseum_1F_EventScript_FamiliarGrunt : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_OceanicMuseum_1F_EventScript_FamiliarGrunt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setvar VAR_0x8005, TVSHOW_RECENT_HAPPENINGS
 * special InterviewBefore
 * goto_if_eq VAR_RESULT, TRUE, SlateportCity_OceanicMuseum_1F_EventScript_AlreadyInterviewed
 * copyvar VAR_0x8009, VAR_0x8006
 * goto_if_set FLAG_OCEANIC_MUSEUM_MET_REPORTER, SlateportCity_OceanicMuseum_1F_EventScript_RequestInterviewShort
 * setflag FLAG_OCEANIC_MUSEUM_MET_REPORTER
 * msgbox SlateportCity_OceanicMuseum_1F_Text_InterviewRequest, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, SlateportCity_OceanicMuseum_1F_EventScript_AcceptInterview
 * goto_if_eq VAR_RESULT, NO, SlateportCity_OceanicMuseum_1F_EventScript_DeclineInterview
 * end
 * ```
 */
internal object SlateportCity_OceanicMuseum_1F_EventScript_Reporter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SlateportCity_OceanicMuseum_1F_EventScript_Reporter")
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_WhirlpoolExperiment : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.WhirlpoolExperiment)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_WaterfallExperiment : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.WaterfallExperiment)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_OceanSoilDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.OceanSoilDisplay)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_BeachSandDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.BeachSandDisplay)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_OceanicMinifact1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.OceanicMinifact1)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_OceanicMinifact2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.OceanicMinifact2)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_OceanicMinifact3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.OceanicMinifact3)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_FossilDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.FossilDisplay)
}

internal object SlateportCity_OceanicMuseum_1F_EventScript_DepthMeasuringMachine : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SlateportCity_OceanicMuseum_1F.DepthMeasuringMachine)
}

internal val SlateportCity_OceanicMuseum_1FScripts: Map<String, Script> =
    mapOf(
        "SlateportCity_OceanicMuseum_1F_EventScript_EntranceAttendant" to
            SlateportCity_OceanicMuseum_1F_EventScript_EntranceAttendant,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt3" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt3,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt4" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt4,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt2" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt2,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt1" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt1,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt5" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt5,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt6" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumGrunt6,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron1" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron1,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron2" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron2,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron3" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron3,
        "SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron4" to
            SlateportCity_OceanicMuseum_1F_EventScript_MuseumPatron4,
        "SlateportCity_OceanicMuseum_1F_EventScript_FamiliarGrunt" to
            SlateportCity_OceanicMuseum_1F_EventScript_FamiliarGrunt,
        "SlateportCity_OceanicMuseum_1F_EventScript_Reporter" to
            SlateportCity_OceanicMuseum_1F_EventScript_Reporter,
        "SlateportCity_OceanicMuseum_1F_EventScript_WhirlpoolExperiment" to
            SlateportCity_OceanicMuseum_1F_EventScript_WhirlpoolExperiment,
        "SlateportCity_OceanicMuseum_1F_EventScript_WaterfallExperiment" to
            SlateportCity_OceanicMuseum_1F_EventScript_WaterfallExperiment,
        "SlateportCity_OceanicMuseum_1F_EventScript_OceanSoilDisplay" to
            SlateportCity_OceanicMuseum_1F_EventScript_OceanSoilDisplay,
        "SlateportCity_OceanicMuseum_1F_EventScript_BeachSandDisplay" to
            SlateportCity_OceanicMuseum_1F_EventScript_BeachSandDisplay,
        "SlateportCity_OceanicMuseum_1F_EventScript_OceanicMinifact1" to
            SlateportCity_OceanicMuseum_1F_EventScript_OceanicMinifact1,
        "SlateportCity_OceanicMuseum_1F_EventScript_OceanicMinifact2" to
            SlateportCity_OceanicMuseum_1F_EventScript_OceanicMinifact2,
        "SlateportCity_OceanicMuseum_1F_EventScript_OceanicMinifact3" to
            SlateportCity_OceanicMuseum_1F_EventScript_OceanicMinifact3,
        "SlateportCity_OceanicMuseum_1F_EventScript_FossilDisplay" to
            SlateportCity_OceanicMuseum_1F_EventScript_FossilDisplay,
        "SlateportCity_OceanicMuseum_1F_EventScript_DepthMeasuringMachine" to
            SlateportCity_OceanicMuseum_1F_EventScript_DepthMeasuringMachine,
    )
