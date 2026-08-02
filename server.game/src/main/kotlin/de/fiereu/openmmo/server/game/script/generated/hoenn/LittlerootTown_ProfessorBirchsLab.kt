package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LittlerootTown_ProfessorBirchsLab
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val LOCALID_BIRCH = 1
private const val LOCALID_RIVAL = 2
private const val TREECKO = 252
private val HOENN_STARTERS = listOf(TREECKO, 255, 258)

internal object LittlerootTown_ProfessorBirchsLab_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) = Unit
}

internal object LittlerootTown_ProfessorBirchsLab_EventScript_GiveStarterEvent : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(HoennVars.VAR_BIRCH_LAB_STATE) != 2) return
    val starter = HOENN_STARTERS.getOrElse(ctx.getVar(HoennVars.VAR_STARTER_MON)) { TREECKO }
    ctx.sayNpcWithSpeciesName(
        LOCALID_BIRCH,
        LittlerootTown_ProfessorBirchsLab.LikeYouToHavePokemon,
        starter,
    )

    // The captured client skips the nickname screen.
    var agreed =
        ctx.askYesNoNpc(
            LOCALID_BIRCH,
            LittlerootTown_ProfessorBirchsLab.MightBeGoodIdeaToGoSeeRival,
        )
    while (!agreed) {
      agreed =
          ctx.askYesNoNpc(
              LOCALID_BIRCH,
              LittlerootTown_ProfessorBirchsLab.DontBeThatWay,
          )
    }
    ctx.sayNpc(LOCALID_BIRCH, LittlerootTown_ProfessorBirchsLab.GetRivalToTeachYou)
    ctx.clearFlag(HoennFlags.FLAG_HIDE_ROUTE_101_BOY)
    ctx.setVar(HoennVars.VAR_BIRCH_LAB_STATE, 3)
  }
}

internal object LittlerootTown_ProfessorBirchsLab_EventScript_GivePokedexEvent : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(HoennVars.VAR_BIRCH_LAB_STATE) != 4) return
    repeat(7) { ctx.moveSelf(WALK_UP) }
    ctx.sayNpc(LOCALID_BIRCH, LittlerootTown_ProfessorBirchsLab.HeardYouBeatRivalTakePokedex)
    ctx.sayNpc(LOCALID_BIRCH, LittlerootTown_ProfessorBirchsLab.ReceivedPokedex)
    ctx.setFlag(HoennFlags.FLAG_SYS_POKEDEX_GET)
    ctx.setFlag(HoennFlags.FLAG_RECEIVED_POKEDEX_FROM_BIRCH)
    ctx.sayNpc(LOCALID_BIRCH, LittlerootTown_ProfessorBirchsLab.ExplainPokedex)
    ctx.moveNpc(LOCALID_RIVAL, WALK_DOWN, FACE_LEFT)
    ctx.moveSelf(FACE_RIGHT)
    if (ctx.isFemale) {
      ctx.sayNpc(
          LOCALID_RIVAL,
          LittlerootTown_ProfessorBirchsLab.BrendanGotPokedexTooTakeThese,
      )
    } else {
      ctx.sayNpc(LOCALID_RIVAL, LittlerootTown_ProfessorBirchsLab.MayGotPokedexTooTakeThese)
    }
    ctx.giveItem(Items.POKE_BALL, 5)
    if (ctx.isFemale) {
      ctx.sayNpc(LOCALID_RIVAL, LittlerootTown_ProfessorBirchsLab.CatchCoolPokemonWithPokeBalls)
    } else {
      ctx.sayNpc(LOCALID_RIVAL, LittlerootTown_ProfessorBirchsLab.CatchCutePokemonWithPokeBalls)
    }
    ctx.setVar(HoennVars.VAR_BIRCH_LAB_STATE, 5)
    ctx.setFlag(HoennFlags.FLAG_ADVENTURE_STARTED)
    ctx.setVar(HoennVars.VAR_OLDALE_TOWN_STATE, 1)
    ctx.setVar(HoennVars.VAR_LITTLEROOT_RIVAL_STATE, 4)
    ctx.setVar(HoennVars.VAR_LITTLEROOT_TOWN_STATE, 3)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_BIRCH_LAB_STATE, 3, LittlerootTown_ProfessorBirchsLab_EventScript_AideReceivedStarter
 * goto_if_set FLAG_BIRCH_AIDE_MET, LittlerootTown_ProfessorBirchsLab_EventScript_AideAlreadyMet
 * msgbox LittlerootTown_ProfessorBirchsLab_Text_BirchAwayOnFieldwork, MSGBOX_DEFAULT
 * setflag FLAG_BIRCH_AIDE_MET
 * release
 * end
 * ```
 */
internal object LittlerootTown_ProfessorBirchsLab_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(HoennVars.VAR_BIRCH_LAB_STATE) >= 3) {
      ctx.say(LittlerootTown_ProfessorBirchsLab.BirchEnjoysRivalsHelpToo)
    } else if (ctx.isFlagSet(HoennFlags.FLAG_BIRCH_AIDE_MET)) {
      ctx.say(LittlerootTown_ProfessorBirchsLab.BirchIsntOneForDeskWork)
    } else {
      ctx.say(LittlerootTown_ProfessorBirchsLab.BirchAwayOnFieldwork)
      ctx.setFlag(HoennFlags.FLAG_BIRCH_AIDE_MET)
    }
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_DEX_UPGRADE_JOHTO_STARTER_STATE, 5, LittlerootTown_ProfessorBirchsLab_EventScript_CanHaveAnyOneOfRarePokemon
 * goto_if_eq VAR_DEX_UPGRADE_JOHTO_STARTER_STATE, 2, LittlerootTown_ProfessorBirchsLab_EventScript_GrassyPatchWaiting
 * goto_if_unset FLAG_HAS_MATCH_CALL, LittlerootTown_ProfessorBirchsLab_EventScript_TryRatePokedexOrRegister
 * goto_if_unset FLAG_ENABLE_PROF_BIRCH_MATCH_CALL, EventScript_RegisterProfBirch
 * goto LittlerootTown_ProfessorBirchsLab_EventScript_TryRatePokedexOrRegister
 * end
 * ```
 */
internal object LittlerootTown_ProfessorBirchsLab_EventScript_Birch : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(HoennVars.VAR_BIRCH_LAB_STATE) >= 5) {
      ctx.say(LittlerootTown_ProfessorBirchsLab.CountlessPokemonAwait)
    } else {
      ctx.say(LittlerootTown_ProfessorBirchsLab.BirchRivalGoneHome)
    }
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_DEX_UPGRADE_JOHTO_STARTER_STATE, 5, LittlerootTown_ProfessorBirchsLab_EventScript_RivalFuturePlans
 * goto_if_ge VAR_DEX_UPGRADE_JOHTO_STARTER_STATE, 6, LittlerootTown_ProfessorBirchsLab_EventScript_RivalHaveYouGoneToBattleFrontier
 * goto_if_ge VAR_DEX_UPGRADE_JOHTO_STARTER_STATE, 2, LittlerootTown_ProfessorBirchsLab_EventScript_RivalTakeBreakFromFieldwork
 * checkplayergender
 * call_if_eq VAR_RESULT, MALE, LittlerootTown_ProfessorBirchsLab_EventScript_MayWhereShouldIGoNext
 * call_if_eq VAR_RESULT, FEMALE, LittlerootTown_ProfessorBirchsLab_EventScript_BrendanWhereShouldIGoNext
 * release
 * end
 * ```
 */
internal object LittlerootTown_ProfessorBirchsLab_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_ProfessorBirchsLab_EventScript_Rival")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * release
 * goto_if_ge VAR_DEX_UPGRADE_JOHTO_STARTER_STATE, 6, LittlerootTown_ProfessorBirchsLab_EventScript_AlreadyChoseJohtoStarter
 * applymovement LOCALID_BIRCHS_LAB_BIRCH, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * showmonpic SPECIES_CYNDAQUIL, 10, 3
 * msgbox LittlerootTown_ProfessorBirchsLab_Text_YoullTakeCyndaquil, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, LittlerootTown_ProfessorBirchsLab_EventScript_TakeYourTime
 * goto LittlerootTown_ProfessorBirchsLab_EventScript_GiveCyndaquil
 * end
 * ```
 */
internal object LittlerootTown_ProfessorBirchsLab_EventScript_Cyndaquil : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_ProfessorBirchsLab_EventScript_Cyndaquil")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * release
 * goto_if_ge VAR_DEX_UPGRADE_JOHTO_STARTER_STATE, 6, LittlerootTown_ProfessorBirchsLab_EventScript_AlreadyChoseJohtoStarter
 * applymovement LOCALID_BIRCHS_LAB_BIRCH, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * showmonpic SPECIES_TOTODILE, 10, 3
 * msgbox LittlerootTown_ProfessorBirchsLab_Text_YoullTakeTotodile, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, LittlerootTown_ProfessorBirchsLab_EventScript_TakeYourTime
 * goto LittlerootTown_ProfessorBirchsLab_EventScript_GiveTotodile
 * end
 * ```
 */
internal object LittlerootTown_ProfessorBirchsLab_EventScript_Totodile : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_ProfessorBirchsLab_EventScript_Totodile")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * release
 * goto_if_ge VAR_DEX_UPGRADE_JOHTO_STARTER_STATE, 6, LittlerootTown_ProfessorBirchsLab_EventScript_AlreadyChoseJohtoStarter
 * applymovement LOCALID_BIRCHS_LAB_BIRCH, Common_Movement_WalkInPlaceFasterRight
 * waitmovement 0
 * showmonpic SPECIES_CHIKORITA, 10, 3
 * msgbox LittlerootTown_ProfessorBirchsLab_Text_YoullTakeChikorita, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, LittlerootTown_ProfessorBirchsLab_EventScript_TakeYourTime
 * goto LittlerootTown_ProfessorBirchsLab_EventScript_GiveChikorita
 * end
 * ```
 */
internal object LittlerootTown_ProfessorBirchsLab_EventScript_Chikorita : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_ProfessorBirchsLab_EventScript_Chikorita")
}

internal object LittlerootTown_ProfessorBirchsLab_EventScript_Machine : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(LittlerootTown_ProfessorBirchsLab.SeriousLookingMachine)
}

internal object LittlerootTown_ProfessorBirchsLab_EventScript_Book : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(LittlerootTown_ProfessorBirchsLab.BookTooHardToRead)
}

internal object LittlerootTown_ProfessorBirchsLab_EventScript_Bookshelf : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(LittlerootTown_ProfessorBirchsLab.CrammedWithBooksOnPokemon)
}

internal object LittlerootTown_ProfessorBirchsLab_EventScript_PC : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(LittlerootTown_ProfessorBirchsLab.PCUsedForResearch)
}

internal val LittlerootTown_ProfessorBirchsLabScripts: Map<String, Script> =
    mapOf(
        "LittlerootTown_ProfessorBirchsLab_OnTransition" to
            LittlerootTown_ProfessorBirchsLab_OnTransition,
        "LittlerootTown_ProfessorBirchsLab_EventScript_GiveStarterEvent" to
            LittlerootTown_ProfessorBirchsLab_EventScript_GiveStarterEvent,
        "LittlerootTown_ProfessorBirchsLab_EventScript_GivePokedexEvent" to
            LittlerootTown_ProfessorBirchsLab_EventScript_GivePokedexEvent,
        "LittlerootTown_ProfessorBirchsLab_EventScript_Aide" to
            LittlerootTown_ProfessorBirchsLab_EventScript_Aide,
        "LittlerootTown_ProfessorBirchsLab_EventScript_Birch" to
            LittlerootTown_ProfessorBirchsLab_EventScript_Birch,
        "LittlerootTown_ProfessorBirchsLab_EventScript_Rival" to
            LittlerootTown_ProfessorBirchsLab_EventScript_Rival,
        "LittlerootTown_ProfessorBirchsLab_EventScript_Cyndaquil" to
            LittlerootTown_ProfessorBirchsLab_EventScript_Cyndaquil,
        "LittlerootTown_ProfessorBirchsLab_EventScript_Totodile" to
            LittlerootTown_ProfessorBirchsLab_EventScript_Totodile,
        "LittlerootTown_ProfessorBirchsLab_EventScript_Chikorita" to
            LittlerootTown_ProfessorBirchsLab_EventScript_Chikorita,
        "LittlerootTown_ProfessorBirchsLab_EventScript_Machine" to
            LittlerootTown_ProfessorBirchsLab_EventScript_Machine,
        "LittlerootTown_ProfessorBirchsLab_EventScript_Book" to
            LittlerootTown_ProfessorBirchsLab_EventScript_Book,
        "LittlerootTown_ProfessorBirchsLab_EventScript_Bookshelf" to
            LittlerootTown_ProfessorBirchsLab_EventScript_Bookshelf,
        "LittlerootTown_ProfessorBirchsLab_EventScript_PC" to
            LittlerootTown_ProfessorBirchsLab_EventScript_PC,
    )
