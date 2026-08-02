package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_Gym
import de.fiereu.openmmo.server.game.battle.BattleResult
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val GEODUDE = 74
private const val NOSEPASS = 299
private const val TM39_ROCK_TOMB_ITEM = 5327

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ROXANNE_1, RustboroCity_Gym_Text_RoxanneIntro, RustboroCity_Gym_Text_RoxanneDefeat, RustboroCity_Gym_EventScript_RoxanneDefeated, NO_MUSIC
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, RustboroCity_Gym_EventScript_RoxanneRematch
 * goto_if_unset FLAG_RECEIVED_TM_ROCK_TOMB, RustboroCity_Gym_EventScript_GiveRockTomb
 * msgbox RustboroCity_Gym_Text_RoxannePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_Roxanne : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_DEFEATED_RUSTBORO_GYM)) {
      if (!ctx.isFlagSet(HoennFlags.FLAG_RECEIVED_TM_ROCK_TOMB)) giveRockTomb(ctx)
      else ctx.say(RustboroCity_Gym.RoxannePostBattle)
      return
    }
    ctx.say(RustboroCity_Gym.RoxanneIntro)

    // Trainer switching uses consecutive battle rounds.
    if (ctx.battle(GEODUDE, 12, 33, 111, 88, 317) != BattleResult.VICTORY) return
    if (ctx.battle(GEODUDE, 12, 33, 111, 88, 317) != BattleResult.VICTORY) return
    if (ctx.battle(NOSEPASS, 15, 335, 106, 33, 317) != BattleResult.VICTORY) return

    ctx.say(RustboroCity_Gym.RoxanneDefeat)
    ctx.say(RustboroCity_Gym.ReceivedStoneBadge)
    ctx.say(RustboroCity_Gym.StoneBadgeInfoTakeThis)
    ctx.setFlag(HoennFlags.FLAG_DEFEATED_RUSTBORO_GYM)
    ctx.setFlag(HoennFlags.FLAG_BADGE01_GET)
    ctx.setVar(HoennVars.VAR_RUSTBORO_CITY_STATE, 1)
    ctx.setVar(
        HoennVars.VAR_PETALBURG_GYM_STATE,
        ctx.getVar(HoennVars.VAR_PETALBURG_GYM_STATE) + 1,
    )
    giveRockTomb(ctx)
  }
}

private suspend fun giveRockTomb(ctx: ScriptContext) {
  if (ctx.giveItem(TM39_ROCK_TOMB_ITEM)) {
    ctx.setFlag(HoennFlags.FLAG_RECEIVED_TM_ROCK_TOMB)
    ctx.say(RustboroCity_Gym.ExplainRockTomb)
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JOSH, RustboroCity_Gym_Text_JoshIntro, RustboroCity_Gym_Text_JoshDefeat
 * msgbox RustboroCity_Gym_Text_JoshPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_Josh : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_Gym_EventScript_Josh")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TOMMY, RustboroCity_Gym_Text_TommyIntro, RustboroCity_Gym_Text_TommyDefeat
 * msgbox RustboroCity_Gym_Text_TommyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_Tommy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_Gym_EventScript_Tommy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_RUSTBORO_GYM, RustboroCity_Gym_EventScript_GymGuidePostVictory
 * msgbox RustboroCity_Gym_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(HoennFlags.FLAG_DEFEATED_RUSTBORO_GYM)) {
      ctx.say(RustboroCity_Gym.GymGuidePostVictory)
    } else {
      ctx.say(RustboroCity_Gym.GymGuideAdvice)
    }
  }
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MARC, RustboroCity_Gym_Text_MarcIntro, RustboroCity_Gym_Text_MarcDefeat
 * msgbox RustboroCity_Gym_Text_MarcPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_Marc : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RustboroCity_Gym_EventScript_Marc")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE01_GET, RustboroCity_Gym_EventScript_GymStatueCertified
 * goto RustboroCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = showGymStatue(ctx)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE01_GET, RustboroCity_Gym_EventScript_GymStatueCertified
 * goto RustboroCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object RustboroCity_Gym_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) = showGymStatue(ctx)
}

private suspend fun showGymStatue(ctx: ScriptContext) {
  if (ctx.isFlagSet(HoennFlags.FLAG_BADGE01_GET)) ctx.sign(RustboroCity_Gym.GymStatueCertified)
  else ctx.sign(RustboroCity_Gym.GymStatue)
}

internal val RustboroCity_GymScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_Gym_EventScript_Roxanne" to RustboroCity_Gym_EventScript_Roxanne,
        "RustboroCity_Gym_EventScript_Josh" to RustboroCity_Gym_EventScript_Josh,
        "RustboroCity_Gym_EventScript_Tommy" to RustboroCity_Gym_EventScript_Tommy,
        "RustboroCity_Gym_EventScript_GymGuide" to RustboroCity_Gym_EventScript_GymGuide,
        "RustboroCity_Gym_EventScript_Marc" to RustboroCity_Gym_EventScript_Marc,
        "RustboroCity_Gym_EventScript_LeftGymStatue" to RustboroCity_Gym_EventScript_LeftGymStatue,
        "RustboroCity_Gym_EventScript_RightGymStatue" to
            RustboroCity_Gym_EventScript_RightGymStatue,
    )
