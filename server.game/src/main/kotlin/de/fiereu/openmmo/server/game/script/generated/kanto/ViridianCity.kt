package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ViridianCity
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_UP
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val LOCALID_TUTORIAL_MAN = 3
private const val LOCALID_POTION_BALL = 8

internal object ViridianCity_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(KantoFlags.FLAG_WORLD_MAP_VIRIDIAN_CITY)
    // He naps across the road north until Oak's parcel is delivered, then stands beside it.
    when (ctx.getVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN)) {
      0 -> ctx.repositionNpc(LOCALID_TUTORIAL_MAN, 21, 11)
      1 -> ctx.repositionNpc(LOCALID_TUTORIAL_MAN, 21, 8)
    }
    // TODO Unlock the Viridian gym once seven badges are in
    //  The decomp checks FLAG_BADGE02_GET through FLAG_BADGE07_GET here. Badges are not tracked
    //  yet, so the gym door stays locked.
  }
}

internal object ViridianCity_EventScript_RoadBlocked : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.sign(ViridianCity.ThisIsPrivateProperty)
    ctx.moveSelf(WALK_DOWN)
  }
}

internal object ViridianCity_EventScript_GymDoorLocked : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.moveSelf(FACE_UP)
    ctx.sign(ViridianCity.GymDoorsAreLocked)
    // The decomp hops the player back down the ledge, and a plain step down is the closest we have.
    ctx.moveSelf(WALK_DOWN)
  }
}

internal object ViridianCity_EventScript_TutorialTriggerLeft : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.moveNpc(LOCALID_TUTORIAL_MAN, FACE_LEFT)
    ctx.moveSelf(FACE_RIGHT)
    tutorialBattle(ctx)
  }
}

internal object ViridianCity_EventScript_TutorialTriggerRight : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.moveNpc(LOCALID_TUTORIAL_MAN, FACE_RIGHT)
    ctx.moveSelf(FACE_LEFT)
    tutorialBattle(ctx)
  }
}

/** The old man's catching demonstration, which ends with him handing over the Teachy TV. */
private suspend fun tutorialBattle(ctx: ScriptContext) {
  ctx.sayNpc(LOCALID_TUTORIAL_MAN, ViridianCity.ShowYouHowToCatchMons)
  // TODO Play the catching tutorial battle
  //  The decomp runs StartOldManTutorialBattle, a scripted battle the old man fights for the
  //  player. Scripted battles always use the player's own party today, so the demonstration is
  //  skipped and only its reward is given.
  ctx.sayNpc(LOCALID_TUTORIAL_MAN, ViridianCity.ThatWasEducationalTakeThis)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN, 2)
  ctx.giveItem(Items.TEACHY_TV)
  ctx.sayNpc(LOCALID_TUTORIAL_MAN, ViridianCity.WatchThatToLearnBasics)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto EventScript_DreamEaterTutor
 * end
 * ```
 */
internal object ViridianCity_EventScript_DreamEaterTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ViridianCity_EventScript_DreamEaterTutor")
}

internal object ViridianCity_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_GYM_DOOR) == 1) {
      return ctx.say(ViridianCity.ViridiansGymLeaderReturned)
    }
    ctx.say(ViridianCity.GymClosedWonderWhoLeaderIs)
  }
}

internal object ViridianCity_EventScript_TutorialOldMan : Script {
  override suspend fun run(ctx: ScriptContext) {
    when {
      ctx.isFlagSet(KantoFlags.FLAG_BADGE01_GET) -> {
        if (ctx.askYesNo(ViridianCity.HowsTeachyTVHelping)) {
          ctx.say(ViridianCity.MyGrandsonOnTheShow)
        } else {
          ctx.say(ViridianCity.TooBusyForTeachyTV)
        }
      }
      ctx.getVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN) >= 2 ->
          ctx.say(ViridianCity.WeakenMonsFirstToCatch)
      ctx.getVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN) == 1 -> tutorialBattle(ctx)
      else -> ctx.say(ViridianCity.ThisIsPrivateProperty)
    }
  }
}

internal object ViridianCity_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_OLD_MAN) == 0) {
      return ctx.say(ViridianCity.GrandpaHasntHadCoffeeYet)
    }
    ctx.say(ViridianCity.GoShoppingInPewterOccasionally)
  }
}

internal object ViridianCity_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.askYesNo(ViridianCity.WantToKnowAboutCaterpillarMons)) {
      ctx.say(ViridianCity.ExplainCaterpieWeedle)
    } else {
      ctx.say(ViridianCity.OhOkayThen)
    }
  }
}

internal object ViridianCity_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianCity.CanCarryMonsAnywhere)
}

internal object ViridianCity_EventScript_ItemPotion : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (!ctx.giveItem(Items.POTION)) return
    ctx.removeNpc(LOCALID_POTION_BALL)
    ctx.setFlag(KantoFlags.FLAG_HIDE_VIRIDIAN_CITY_POTION)
  }
}

internal object ViridianCity_EventScript_TrainerTips1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.CatchMonsForEasierBattles)
}

internal object ViridianCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.GymSign)
}

internal object ViridianCity_EventScript_TrainerTips2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.MovesLimitedByPP)
}

internal object ViridianCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.CitySign)
}

internal object ViridianCity_EventScript_GymDoor : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity.GymDoorsAreLocked)
}

internal val ViridianCityScripts: Map<String, Script> =
    mapOf(
        "ViridianCity_OnTransition" to ViridianCity_OnTransition,
        "ViridianCity_EventScript_RoadBlocked" to ViridianCity_EventScript_RoadBlocked,
        "ViridianCity_EventScript_GymDoorLocked" to ViridianCity_EventScript_GymDoorLocked,
        "ViridianCity_EventScript_TutorialTriggerLeft" to
            ViridianCity_EventScript_TutorialTriggerLeft,
        "ViridianCity_EventScript_TutorialTriggerRight" to
            ViridianCity_EventScript_TutorialTriggerRight,
        "ViridianCity_EventScript_DreamEaterTutor" to ViridianCity_EventScript_DreamEaterTutor,
        "ViridianCity_EventScript_OldMan" to ViridianCity_EventScript_OldMan,
        "ViridianCity_EventScript_TutorialOldMan" to ViridianCity_EventScript_TutorialOldMan,
        "ViridianCity_EventScript_Woman" to ViridianCity_EventScript_Woman,
        "ViridianCity_EventScript_Youngster" to ViridianCity_EventScript_Youngster,
        "ViridianCity_EventScript_Boy" to ViridianCity_EventScript_Boy,
        "ViridianCity_EventScript_ItemPotion" to ViridianCity_EventScript_ItemPotion,
        "ViridianCity_EventScript_TrainerTips1" to ViridianCity_EventScript_TrainerTips1,
        "ViridianCity_EventScript_GymSign" to ViridianCity_EventScript_GymSign,
        "ViridianCity_EventScript_TrainerTips2" to ViridianCity_EventScript_TrainerTips2,
        "ViridianCity_EventScript_CitySign" to ViridianCity_EventScript_CitySign,
        "ViridianCity_EventScript_GymDoor" to ViridianCity_EventScript_GymDoor,
    )
