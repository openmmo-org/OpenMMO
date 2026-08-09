package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.PalletTown_ProfessorOaksLab
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.MovementStep
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_UP
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val LOCALID_PROF_OAK = 3
private const val LOCALID_BULBASAUR_BALL = 4
private const val LOCALID_SQUIRTLE_BALL = 5
private const val LOCALID_CHARMANDER_BALL = 6
private const val LOCALID_RIVAL = 7
private const val LOCALID_POKEDEX_1 = 8
private const val LOCALID_POKEDEX_2 = 9

private const val BULBASAUR = 1
private const val CHARMANDER = 4
private const val SQUIRTLE = 7

private const val MOVE_SCRATCH = 10
private const val MOVE_TACKLE = 33
private const val MOVE_TAIL_WHIP = 39
private const val MOVE_GROWL = 45

private const val STARTER_LEVEL = 5

// Bulbasaur's ball on Oak's table. The other two sit one tile further right each, in starter order.
private const val FIRST_BALL_X = 8

// The three tiles the player can be standing on when the rival stops them on the way out.
private const val EXIT_X_LEFT = 5
private const val EXIT_X_MID = 6
private const val EXIT_X_RIGHT = 7

/**
 * One of the three balls on Oak's table. [starterNumber] is what VAR_STARTER_MON ends up holding,
 * and the rival always takes the starter that beats the player's.
 */
private enum class Starter(
    val starterNumber: Int,
    val localId: Int,
    val species: Int,
    val confirmLine: PalletTown_ProfessorOaksLab,
) {
  BULBASAUR_BALL(
      0,
      LOCALID_BULBASAUR_BALL,
      BULBASAUR,
      PalletTown_ProfessorOaksLab.OakChoosingBulbasaur,
  ),
  SQUIRTLE_BALL(
      1,
      LOCALID_SQUIRTLE_BALL,
      SQUIRTLE,
      PalletTown_ProfessorOaksLab.OakChoosingSquirtle,
  ),
  CHARMANDER_BALL(
      2,
      LOCALID_CHARMANDER_BALL,
      CHARMANDER,
      PalletTown_ProfessorOaksLab.OakChoosingCharmander,
  );

  /** The ball the rival walks over to once the player has taken theirs. */
  val rival: Starter
    get() =
        when (this) {
          BULBASAUR_BALL -> CHARMANDER_BALL
          SQUIRTLE_BALL -> BULBASAUR_BALL
          CHARMANDER_BALL -> SQUIRTLE_BALL
        }

  /** The path the rival walks from his spot to this ball. */
  val rivalWalk: List<MovementStep>
    get() =
        when (this) {
          BULBASAUR_BALL -> listOf(WALK_DOWN, WALK_RIGHT, WALK_RIGHT, WALK_RIGHT, FACE_UP)
          SQUIRTLE_BALL ->
              listOf(WALK_DOWN, WALK_RIGHT, WALK_RIGHT, WALK_RIGHT, WALK_RIGHT, FACE_UP)
          CHARMANDER_BALL ->
              listOf(
                  WALK_DOWN,
                  WALK_DOWN,
                  WALK_RIGHT,
                  WALK_RIGHT,
                  WALK_RIGHT,
                  WALK_RIGHT,
                  WALK_RIGHT,
                  WALK_UP,
              )
        }

  /** The moves the species knows at level 5, for the rival battle. */
  val moves: IntArray
    get() =
        when (species) {
          BULBASAUR -> intArrayOf(MOVE_TACKLE, MOVE_GROWL)
          CHARMANDER -> intArrayOf(MOVE_SCRATCH, MOVE_GROWL)
          else -> intArrayOf(MOVE_TACKLE, MOVE_TAIL_WHIP)
        }

  companion object {
    fun byStarterNumber(number: Int): Starter? = entries.firstOrNull { it.starterNumber == number }
  }
}

internal object PalletTown_ProfessorOaksLab_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(KantoFlags.FLAG_VISITED_OAKS_LAB)
    // Oak follows the player in and starts the scene just inside the door.
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB) == 1) {
      ctx.repositionNpc(LOCALID_PROF_OAK, 6, 11)
    }
  }
}

internal object PalletTown_ProfessorOaksLab_ChooseStarterScene : Script {
  override suspend fun run(ctx: ScriptContext) {
    // Oak leads, then goes away and comes back behind his desk while the player is still far
    // enough down the room not to see it. Moving him there in view would read as a teleport.
    ctx.moveNpc(LOCALID_PROF_OAK, *List(6) { WALK_UP }.toTypedArray())
    ctx.removeNpc(LOCALID_PROF_OAK)
    ctx.showNpcAt(LOCALID_PROF_OAK, 6, 3)
    ctx.moveNpc(LOCALID_PROF_OAK, FACE_DOWN)
    ctx.moveSelf(*List(8) { WALK_UP }.toTypedArray())
    ctx.moveNpc(LOCALID_RIVAL, FACE_UP)
    ctx.clearFlag(KantoFlags.FLAG_DONT_TRANSITION_MUSIC)

    ctx.sayNpc(LOCALID_RIVAL, PalletTown_ProfessorOaksLab.RivalFedUpWithWaiting)
    ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakThreeMonsChooseOne)
    ctx.moveNpc(LOCALID_RIVAL, FACE_UP)
    ctx.sayNpc(LOCALID_RIVAL, PalletTown_ProfessorOaksLab.RivalNoFairWhatAboutMe)
    ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakBePatientRival)
    ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 2)
  }
}

internal object PalletTown_ProfessorOaksLab_EventScript_LeaveStarterSceneTrigger : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.moveNpc(LOCALID_PROF_OAK, FACE_DOWN)
    ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakHeyDontGoAwayYet)
    ctx.moveSelf(WALK_UP)
  }
}

internal object PalletTown_ProfessorOaksLab_EventScript_BulbasaurBall : Script {
  override suspend fun run(ctx: ScriptContext) = starterBall(ctx, Starter.BULBASAUR_BALL)
}

internal object PalletTown_ProfessorOaksLab_EventScript_SquirtleBall : Script {
  override suspend fun run(ctx: ScriptContext) = starterBall(ctx, Starter.SQUIRTLE_BALL)
}

internal object PalletTown_ProfessorOaksLab_EventScript_CharmanderBall : Script {
  override suspend fun run(ctx: ScriptContext) = starterBall(ctx, Starter.CHARMANDER_BALL)
}

private suspend fun starterBall(ctx: ScriptContext, starter: Starter) {
  val scene = ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB)
  if (scene >= 3) return ctx.sign(PalletTown_ProfessorOaksLab.OaksLastMon)
  if (scene != 2) return ctx.sign(PalletTown_ProfessorOaksLab.ThoseArePokeBalls)

  ctx.moveNpc(LOCALID_PROF_OAK, FACE_RIGHT)
  // TODO Show the starter's picture while Oak asks
  //  The decomp opens a showmonpic window over the yes/no box. There is no verb for it yet.
  if (!ctx.askYesNoNpc(LOCALID_PROF_OAK, starter.confirmLine)) return

  ctx.removeNpc(starter.localId)
  ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakThisMonIsEnergetic)
  ctx.setFlag(KantoFlags.FLAG_SYS_POKEMON_GET)
  ctx.setFlag(KantoFlags.FLAG_PALLET_LADY_NOT_BLOCKING_SIGN)
  ctx.givePokemon(starter.species, STARTER_LEVEL, *starter.moves)
  ctx.setVar(KantoVars.VAR_STARTER_MON, starter.starterNumber)
  ctx.sign(PalletTown_ProfessorOaksLab.ReceivedMonFromOak)

  val rivalStarter = starter.rival
  ctx.moveNpc(LOCALID_RIVAL, *rivalStarter.rivalWalk.toTypedArray())
  ctx.sayNpc(LOCALID_RIVAL, PalletTown_ProfessorOaksLab.RivalIllTakeThisOneThen)
  ctx.removeNpc(rivalStarter.localId)
  ctx.sign(PalletTown_ProfessorOaksLab.RivalReceivedMonFromOak)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 3)
  if (ctx.isFlagSet(KantoFlags.FLAG_OPENED_START_MENU)) {
    ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_SIGN_LADY, 1)
  }
}

internal object PalletTown_ProfessorOaksLab_EventScript_RivalBattleTriggerLeft : Script {
  override suspend fun run(ctx: ScriptContext) = rivalBattle(ctx, playerX = EXIT_X_LEFT)
}

internal object PalletTown_ProfessorOaksLab_EventScript_RivalBattleTriggerMid : Script {
  override suspend fun run(ctx: ScriptContext) = rivalBattle(ctx, playerX = EXIT_X_MID)
}

internal object PalletTown_ProfessorOaksLab_EventScript_RivalBattleTriggerRight : Script {
  override suspend fun run(ctx: ScriptContext) = rivalBattle(ctx, playerX = EXIT_X_RIGHT)
}

/**
 * The rival stops the player on the way out for the tutorial battle. He is still standing at his
 * own ball, so he walks left until he is above the player's exit tile [playerX], then two down to
 * block the way.
 */
private suspend fun rivalBattle(ctx: ScriptContext, playerX: Int) {
  val playerStarter = Starter.byStarterNumber(ctx.getVar(KantoVars.VAR_STARTER_MON)) ?: return
  val rivalStarter = playerStarter.rival

  ctx.moveNpc(LOCALID_RIVAL, FACE_DOWN)
  ctx.moveSelf(FACE_UP)
  ctx.sayNpc(LOCALID_RIVAL, PalletTown_ProfessorOaksLab.RivalLetsCheckOutMons)
  ctx.moveNpc(LOCALID_PROF_OAK, FACE_DOWN)

  val stepsLeft = FIRST_BALL_X + rivalStarter.starterNumber - playerX
  val approach = List(stepsLeft) { WALK_LEFT } + listOf(WALK_DOWN, WALK_DOWN)
  ctx.moveNpc(LOCALID_RIVAL, *approach.toTypedArray())

  // The decomp ends this scene the same way whether the player won or lost, so a loss must not
  // leave the rival standing on the exit tile with the trigger still armed.
  val result = ctx.battle(rivalStarter.species, STARTER_LEVEL, *rivalStarter.moves)
  if (result == BattleResult.DISCONNECTED || result == BattleResult.FAILED) return

  ctx.healParty()
  ctx.sayNpc(LOCALID_RIVAL, PalletTown_ProfessorOaksLab.RivalGoToughenMyMon)
  // The rival leaves around whichever side the player blocked, so each exit tile has its own path.
  val exit =
      when (playerX) {
        EXIT_X_LEFT -> listOf(WALK_RIGHT, WALK_DOWN, WALK_DOWN, WALK_DOWN, WALK_DOWN, WALK_DOWN)
        EXIT_X_MID ->
            listOf(WALK_RIGHT, WALK_DOWN, WALK_DOWN, WALK_DOWN, WALK_LEFT, WALK_DOWN, WALK_DOWN)
        else -> listOf(WALK_LEFT, WALK_DOWN, WALK_DOWN, WALK_DOWN, WALK_DOWN, WALK_DOWN)
      }
  ctx.moveNpc(LOCALID_RIVAL, *exit.toTypedArray())
  ctx.removeNpc(LOCALID_RIVAL)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 4)
  ctx.setFlag(KantoFlags.FLAG_BEAT_RIVAL_IN_OAKS_LAB)
}

internal object PalletTown_ProfessorOaksLab_EventScript_ProfOak : Script {
  override suspend fun run(ctx: ScriptContext) {
    val scene = ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB)
    when {
      scene >= 8 -> ctx.say(PalletTown_ProfessorOaksLab.OakMonsAroundWorldWait)
      scene == 6 -> ctx.say(PalletTown_ProfessorOaksLab.OakComeSeeMeSometime)
      ctx.getVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_MART) >= 1 -> receiveDexScene(ctx)
      scene == 4 -> ctx.say(PalletTown_ProfessorOaksLab.OakBattleMonForItToGrow)
      scene == 3 -> ctx.say(PalletTown_ProfessorOaksLab.OakCanReachNextTownWithMon)
      else -> ctx.say(PalletTown_ProfessorOaksLab.OakWhichOneWillYouChoose)
    }
  }
}

/** Handing over Oak's parcel is what earns the Pokedex and the first five Poke Balls. */
private suspend fun receiveDexScene(ctx: ScriptContext) {
  ctx.say(PalletTown_ProfessorOaksLab.OakHaveSomethingForMe)
  ctx.sign(PalletTown_ProfessorOaksLab.DeliveredOaksParcel)
  ctx.takeItem(Items.OAKS_PARCEL)
  ctx.say(PalletTown_ProfessorOaksLab.OakCustomBallIOrdered)

  ctx.sign(PalletTown_ProfessorOaksLab.RivalGramps)
  // A player facing Oak stands in the middle column, so the rival comes up the one beside it.
  val facing = ctx.facingDirection
  ctx.showNpcAt(LOCALID_RIVAL, if (facing == Direction.UP) 5 else 6, 10)
  ctx.moveNpc(LOCALID_RIVAL, *List(6) { WALK_UP }.toTypedArray())
  if (facing != Direction.DOWN) ctx.moveSelf(FACE_DOWN)
  ctx.sayNpc(LOCALID_RIVAL, PalletTown_ProfessorOaksLab.RivalWhatDidYouCallMeFor)
  ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakHaveRequestForYouTwo)

  ctx.moveNpc(LOCALID_PROF_OAK, WALK_UP, WALK_LEFT, FACE_DOWN)
  ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakPokedexOnDesk)
  ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakTakeTheseWithYou)
  ctx.removeNpc(LOCALID_POKEDEX_1)
  ctx.removeNpc(LOCALID_POKEDEX_2)
  ctx.moveNpc(LOCALID_PROF_OAK, WALK_RIGHT, WALK_DOWN)

  ctx.sign(PalletTown_ProfessorOaksLab.ReceivedPokedexFromOak)
  ctx.setFlag(KantoFlags.FLAG_SYS_POKEDEX_GET)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_POKEMON_CENTER_TEALA, 1)
  ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakCatchMonsForDataTakeThese)
  ctx.giveItem(Items.POKE_BALL, 5)
  ctx.sign(PalletTown_ProfessorOaksLab.ReceivedFivePokeBalls)
  ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakExplainCatching)
  ctx.sayNpc(LOCALID_PROF_OAK, PalletTown_ProfessorOaksLab.OakCompleteMonGuideWasMyDream)
  ctx.sayNpc(LOCALID_RIVAL, PalletTown_ProfessorOaksLab.RivalLeaveItToMeGramps)
  ctx.sayNpc(LOCALID_RIVAL, PalletTown_ProfessorOaksLab.RivalTellSisNotToGiveYouMap)

  ctx.moveNpc(LOCALID_RIVAL, *List(6) { WALK_DOWN }.toTypedArray())
  ctx.removeNpc(LOCALID_RIVAL)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 6)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_MART, 2)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN, 1)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_RIVALS_HOUSE, 1)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_ROUTE22, 1)
}

internal object PalletTown_ProfessorOaksLab_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) {
    when (ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB)) {
      3 -> ctx.say(PalletTown_ProfessorOaksLab.RivalMyMonLooksTougher)
      2 -> ctx.say(PalletTown_ProfessorOaksLab.RivalGoChoosePlayer)
      else -> ctx.say(PalletTown_ProfessorOaksLab.RivalGrampsIsntAround)
    }
  }
}

internal object PalletTown_ProfessorOaksLab_EventScript_Aide1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PalletTown_ProfessorOaksLab.StudyAsOaksAide)
}

internal object PalletTown_ProfessorOaksLab_EventScript_Aide2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PalletTown_ProfessorOaksLab.StudyAsOaksAide)
}

internal object PalletTown_ProfessorOaksLab_EventScript_Aide3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PalletTown_ProfessorOaksLab.OakIsAuthorityOnMons)
}

internal object PalletTown_ProfessorOaksLab_EventScript_Pokedex : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PalletTown_ProfessorOaksLab.BlankEncyclopedia)
}

internal object PalletTown_ProfessorOaksLab_EventScript_Computer : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PalletTown_ProfessorOaksLab.EmailMessage)
}

internal object PalletTown_ProfessorOaksLab_EventScript_LeftSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(PalletTown_ProfessorOaksLab.PressStartToOpenMenu)
}

internal object PalletTown_ProfessorOaksLab_EventScript_RightSign : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB) >= 6) {
      ctx.sign(PalletTown_ProfessorOaksLab.AllMonTypesHaveStrongAndWeakPoints)
    } else {
      ctx.sign(PalletTown_ProfessorOaksLab.SaveOptionInMenu)
    }
  }
}

internal val PalletTown_ProfessorOaksLabScripts: Map<String, Script> =
    mapOf(
        "PalletTown_ProfessorOaksLab_OnTransition" to PalletTown_ProfessorOaksLab_OnTransition,
        "PalletTown_ProfessorOaksLab_ChooseStarterScene" to
            PalletTown_ProfessorOaksLab_ChooseStarterScene,
        "PalletTown_ProfessorOaksLab_EventScript_LeaveStarterSceneTrigger" to
            PalletTown_ProfessorOaksLab_EventScript_LeaveStarterSceneTrigger,
        "PalletTown_ProfessorOaksLab_EventScript_RivalBattleTriggerLeft" to
            PalletTown_ProfessorOaksLab_EventScript_RivalBattleTriggerLeft,
        "PalletTown_ProfessorOaksLab_EventScript_RivalBattleTriggerMid" to
            PalletTown_ProfessorOaksLab_EventScript_RivalBattleTriggerMid,
        "PalletTown_ProfessorOaksLab_EventScript_RivalBattleTriggerRight" to
            PalletTown_ProfessorOaksLab_EventScript_RivalBattleTriggerRight,
        "PalletTown_ProfessorOaksLab_EventScript_Aide1" to
            PalletTown_ProfessorOaksLab_EventScript_Aide1,
        "PalletTown_ProfessorOaksLab_EventScript_Aide3" to
            PalletTown_ProfessorOaksLab_EventScript_Aide3,
        "PalletTown_ProfessorOaksLab_EventScript_Aide2" to
            PalletTown_ProfessorOaksLab_EventScript_Aide2,
        "PalletTown_ProfessorOaksLab_EventScript_ProfOak" to
            PalletTown_ProfessorOaksLab_EventScript_ProfOak,
        "PalletTown_ProfessorOaksLab_EventScript_BulbasaurBall" to
            PalletTown_ProfessorOaksLab_EventScript_BulbasaurBall,
        "PalletTown_ProfessorOaksLab_EventScript_SquirtleBall" to
            PalletTown_ProfessorOaksLab_EventScript_SquirtleBall,
        "PalletTown_ProfessorOaksLab_EventScript_CharmanderBall" to
            PalletTown_ProfessorOaksLab_EventScript_CharmanderBall,
        "PalletTown_ProfessorOaksLab_EventScript_Rival" to
            PalletTown_ProfessorOaksLab_EventScript_Rival,
        "PalletTown_ProfessorOaksLab_EventScript_Pokedex" to
            PalletTown_ProfessorOaksLab_EventScript_Pokedex,
        "PalletTown_ProfessorOaksLab_EventScript_Computer" to
            PalletTown_ProfessorOaksLab_EventScript_Computer,
        "PalletTown_ProfessorOaksLab_EventScript_LeftSign" to
            PalletTown_ProfessorOaksLab_EventScript_LeftSign,
        "PalletTown_ProfessorOaksLab_EventScript_RightSign" to
            PalletTown_ProfessorOaksLab_EventScript_RightSign,
    )
