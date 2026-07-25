package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LittlerootTown_ProfessorBirchsLab
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

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
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_ProfessorBirchsLab_EventScript_Aide")
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
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LittlerootTown_ProfessorBirchsLab_EventScript_Birch")
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
