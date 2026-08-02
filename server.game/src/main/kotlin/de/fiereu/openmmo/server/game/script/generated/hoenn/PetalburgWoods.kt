package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PetalburgWoods
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_UP
import de.fiereu.openmmo.server.game.script.MovementStep.SET_INVISIBLE
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val LOCALID_AQUA_GRUNT = 2
private const val LOCALID_DEVON_RESEARCHER = 3
private const val POOCHYENA = 261

internal object PetalburgWoods_EventScript_DevonResearcherLeft : Script {
  override suspend fun run(ctx: ScriptContext) = devonResearcherEvent(ctx)
}

internal object PetalburgWoods_EventScript_DevonResearcherRight : Script {
  override suspend fun run(ctx: ScriptContext) = devonResearcherEvent(ctx)
}

private suspend fun devonResearcherEvent(ctx: ScriptContext) {
  if (ctx.getVar(HoennVars.VAR_PETALBURG_WOODS_STATE) != 0) return
  ctx.moveNpc(LOCALID_DEVON_RESEARCHER, FACE_UP)
  ctx.sayNpc(LOCALID_DEVON_RESEARCHER, PetalburgWoods.NotAOneToBeFound)
  ctx.moveNpc(LOCALID_DEVON_RESEARCHER, WALK_DOWN, WALK_DOWN)
  ctx.sayNpc(LOCALID_DEVON_RESEARCHER, PetalburgWoods.HaveYouSeenShroomish)
  ctx.moveNpc(LOCALID_AQUA_GRUNT, WALK_DOWN, WALK_DOWN)
  ctx.sayNpc(LOCALID_AQUA_GRUNT, PetalburgWoods.IWasGoingToAmbushYou)
  ctx.moveNpc(LOCALID_AQUA_GRUNT, WALK_DOWN, WALK_DOWN)
  ctx.sayNpc(LOCALID_AQUA_GRUNT, PetalburgWoods.HandOverThosePapers)
  ctx.sayNpc(LOCALID_DEVON_RESEARCHER, PetalburgWoods.YouHaveToHelpMe)
  ctx.moveNpc(LOCALID_AQUA_GRUNT, WALK_DOWN)
  ctx.sayNpc(LOCALID_AQUA_GRUNT, PetalburgWoods.NoOneCrossesTeamAqua)
  if (ctx.battle(POOCHYENA, 9, 33, 336, 28) != BattleResult.VICTORY) return
  ctx.sayNpc(LOCALID_AQUA_GRUNT, PetalburgWoods.YoureKiddingMe)
  ctx.sayNpc(LOCALID_AQUA_GRUNT, PetalburgWoods.YouveGotSomeNerve)
  ctx.moveNpc(
      LOCALID_AQUA_GRUNT,
      WALK_UP,
      WALK_UP,
      WALK_UP,
      WALK_UP,
      WALK_UP,
      SET_INVISIBLE,
  )
  ctx.setFlag(HoennFlags.FLAG_HIDE_PETALBURG_WOODS_AQUA_GRUNT)
  ctx.sayNpc(LOCALID_DEVON_RESEARCHER, PetalburgWoods.ThatWasAwfullyClose)
  ctx.giveItem(Items.GREAT_BALL)
  ctx.sayNpc(LOCALID_DEVON_RESEARCHER, PetalburgWoods.TeamAquaAfterSomethingInRustboro)
  ctx.sayNpc(LOCALID_DEVON_RESEARCHER, PetalburgWoods.ICantBeWastingTime)
  ctx.moveNpc(
      LOCALID_DEVON_RESEARCHER,
      WALK_UP,
      WALK_UP,
      WALK_UP,
      WALK_UP,
      WALK_UP,
      SET_INVISIBLE,
  )
  ctx.setFlag(HoennFlags.FLAG_HIDE_PETALBURG_WOODS_DEVON_EMPLOYEE)
  ctx.setVar(HoennVars.VAR_PETALBURG_WOODS_STATE, 1)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_unset FLAG_BADGE01_GET, EventScript_CheckTreeCantCut
 * checkpartymove MOVE_CUT
 * goto_if_eq VAR_RESULT, PARTY_SIZE, EventScript_CheckTreeCantCut
 * setfieldeffectargument 0, VAR_RESULT
 * bufferpartymonnick STR_VAR_1, VAR_RESULT
 * buffermovename STR_VAR_2, MOVE_CUT
 * msgbox Text_WantToCut, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_CancelCut
 * msgbox Text_MonUsedFieldMove, MSGBOX_DEFAULT
 * closemessage
 * dofieldeffect FLDEFF_USE_CUT_ON_TREE
 * waitstate
 * goto EventScript_CutTreeDown
 * end
 * ```
 */
internal object EventScript_CutTree : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port EventScript_CutTree")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_GREAT_BALL
 * end
 * ```
 */
internal object PetalburgWoods_EventScript_ItemGreatBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgWoods_EventScript_ItemGreatBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_X_ATTACK
 * end
 * ```
 */
internal object PetalburgWoods_EventScript_ItemXAttack : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgWoods_EventScript_ItemXAttack")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ETHER
 * end
 * ```
 */
internal object PetalburgWoods_EventScript_ItemEther : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgWoods_EventScript_ItemEther")
}

internal object PetalburgWoods_EventScript_Boy1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgWoods.StayOutOfTallGrass)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LYLE, PetalburgWoods_Text_GoBugPokemonTeam, PetalburgWoods_Text_ICouldntWin
 * msgbox PetalburgWoods_Text_ImOutOfPokeBalls, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PetalburgWoods_EventScript_Lyle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgWoods_EventScript_Lyle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JAMES_1, PetalburgWoods_Text_InstantlyPopularWithBugPokemon, PetalburgWoods_Text_CantBePopularIfILose, PetalburgWoods_EventScript_TryRegisterJames
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, PetalburgWoods_EventScript_JamesRematch
 * setvar VAR_0x8004, TRAINER_JAMES_1
 * specialvar VAR_RESULT, IsTrainerRegistered
 * goto_if_eq VAR_RESULT, FALSE, PetalburgWoods_EventScript_TryRegisterJames2
 * msgbox PetalburgWoods_Text_PeopleRespectYou, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object PetalburgWoods_EventScript_James : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgWoods_EventScript_James")
}

internal object PetalburgWoods_EventScript_Boy2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgWoods.HiddenItemsExplanation)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PARALYZE_HEAL
 * end
 * ```
 */
internal object PetalburgWoods_EventScript_ItemParalyzeHeal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgWoods_EventScript_ItemParalyzeHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_MIRACLE_SEED, PetalburgWoods_EventScript_ExplainMiracleSeed
 * msgbox PetalburgWoods_Text_TryUsingThisItem, MSGBOX_DEFAULT
 * giveitem ITEM_MIRACLE_SEED
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_MIRACLE_SEED
 * release
 * end
 * ```
 */
internal object PetalburgWoods_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgWoods_EventScript_Girl")
}

internal object PetalburgWoods_EventScript_Sign1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PetalburgWoods.TrainerTipsExperience)
}

internal object PetalburgWoods_EventScript_Sign2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PetalburgWoods.TrainerTipsPP)
}

internal val PetalburgWoodsScripts: Map<String, Script> =
    mapOf(
        "PetalburgWoods_EventScript_DevonResearcherLeft" to
            PetalburgWoods_EventScript_DevonResearcherLeft,
        "PetalburgWoods_EventScript_DevonResearcherRight" to
            PetalburgWoods_EventScript_DevonResearcherRight,
        "EventScript_CutTree" to EventScript_CutTree,
        "PetalburgWoods_EventScript_ItemGreatBall" to PetalburgWoods_EventScript_ItemGreatBall,
        "PetalburgWoods_EventScript_ItemXAttack" to PetalburgWoods_EventScript_ItemXAttack,
        "PetalburgWoods_EventScript_ItemEther" to PetalburgWoods_EventScript_ItemEther,
        "PetalburgWoods_EventScript_Boy1" to PetalburgWoods_EventScript_Boy1,
        "PetalburgWoods_EventScript_Lyle" to PetalburgWoods_EventScript_Lyle,
        "PetalburgWoods_EventScript_James" to PetalburgWoods_EventScript_James,
        "PetalburgWoods_EventScript_Boy2" to PetalburgWoods_EventScript_Boy2,
        "PetalburgWoods_EventScript_ItemParalyzeHeal" to
            PetalburgWoods_EventScript_ItemParalyzeHeal,
        "PetalburgWoods_EventScript_Girl" to PetalburgWoods_EventScript_Girl,
        "PetalburgWoods_EventScript_Sign1" to PetalburgWoods_EventScript_Sign1,
        "PetalburgWoods_EventScript_Sign2" to PetalburgWoods_EventScript_Sign2,
    )
