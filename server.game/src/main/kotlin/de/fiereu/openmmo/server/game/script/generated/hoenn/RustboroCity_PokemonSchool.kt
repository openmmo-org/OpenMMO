package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_PokemonSchool
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object RustboroCity_PokemonSchool_EventScript_GameboyKid1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity_PokemonSchool.TradingRightNow)
}

internal object RustboroCity_PokemonSchool_EventScript_GameboyKid2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_PokemonSchool.AlwaysWantedSeedot)
}

internal object RustboroCity_PokemonSchool_EventScript_RichBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_PokemonSchool.PokemontCantUseManMadeItems)
}

internal object RustboroCity_PokemonSchool_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_PokemonSchool.ConfusedPokemonAttacksItself)
}

internal object RustboroCity_PokemonSchool_EventScript_SchoolKidM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_PokemonSchool.PokemonHealItselfWithBerry)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_QUICK_CLAW, RustboroCity_PokemonSchool_EventScript_GaveQuickClaw
 * call_if_eq VAR_FACING, DIR_EAST, RustboroCity_PokemonSchool_EventScript_TeacherCheckOnStudentsEast
 * call_if_eq VAR_FACING, DIR_WEST, RustboroCity_PokemonSchool_EventScript_TeacherCheckOnStudentsWest
 * msgbox RustboroCity_PokemonSchool_Text_StudentsWhoDontStudyGetQuickClaw, MSGBOX_DEFAULT
 * giveitem ITEM_QUICK_CLAW
 * goto_if_eq VAR_RESULT, 0, Common_EventScript_ShowBagIsFull
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_WalkInPlaceFasterDown
 * waitmovement 0
 * setflag FLAG_RECEIVED_QUICK_CLAW
 * release
 * end
 * ```
 */
internal object RustboroCity_PokemonSchool_EventScript_Teacher : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_PokemonSchool_EventScript_Teacher")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MET_SCOTT_AFTER_OBTAINING_STONE_BADGE, RustboroCity_PokemonSchool_EventScript_ScottWatchStudents
 * goto_if_set FLAG_MET_SCOTT_RUSTBORO, RustboroCity_PokemonSchool_EventScript_ScottSpokeAlready
 * goto_if_set FLAG_BADGE01_GET, RustboroCity_PokemonSchool_EventScript_ScottGreetHasBadge
 * msgbox RustboroCity_PokemonSchool_Text_ScottMetAlreadyCut, MSGBOX_DEFAULT
 * addvar VAR_SCOTT_STATE, 1
 * setflag FLAG_MET_SCOTT_RUSTBORO
 * release
 * end
 * ```
 */
internal object RustboroCity_PokemonSchool_EventScript_Scott : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_PokemonSchool_EventScript_Scott")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox RustboroCity_PokemonSchool_Text_BlackboardListsStatusChanges, MSGBOX_DEFAULT
 * goto RustboroCity_PokemonSchool_EventScript_ChooseBlackboardTopic
 * end
 * ```
 */
internal object RustboroCity_PokemonSchool_EventScript_Blackboard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_PokemonSchool_EventScript_Blackboard")
}

internal object RustboroCity_PokemonSchool_EventScript_StudentNotebook : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RustboroCity_PokemonSchool.StudentsNotes)
}

internal val RustboroCity_PokemonSchoolScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_PokemonSchool_EventScript_GameboyKid1" to
            RustboroCity_PokemonSchool_EventScript_GameboyKid1,
        "RustboroCity_PokemonSchool_EventScript_GameboyKid2" to
            RustboroCity_PokemonSchool_EventScript_GameboyKid2,
        "RustboroCity_PokemonSchool_EventScript_RichBoy" to
            RustboroCity_PokemonSchool_EventScript_RichBoy,
        "RustboroCity_PokemonSchool_EventScript_Lass" to
            RustboroCity_PokemonSchool_EventScript_Lass,
        "RustboroCity_PokemonSchool_EventScript_SchoolKidM" to
            RustboroCity_PokemonSchool_EventScript_SchoolKidM,
        "RustboroCity_PokemonSchool_EventScript_Teacher" to
            RustboroCity_PokemonSchool_EventScript_Teacher,
        "RustboroCity_PokemonSchool_EventScript_Scott" to
            RustboroCity_PokemonSchool_EventScript_Scott,
        "RustboroCity_PokemonSchool_EventScript_Blackboard" to
            RustboroCity_PokemonSchool_EventScript_Blackboard,
        "RustboroCity_PokemonSchool_EventScript_StudentNotebook" to
            RustboroCity_PokemonSchool_EventScript_StudentNotebook,
    )
