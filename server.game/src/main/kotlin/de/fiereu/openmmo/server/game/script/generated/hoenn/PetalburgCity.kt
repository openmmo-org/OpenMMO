package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PetalburgCity
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_UP
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val LOCALID_GYM_BOY = 7

internal object PetalburgCity_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.setFlag(HoennFlags.FLAG_VISITED_PETALBURG_CITY)
    if (ctx.getVar(HoennVars.VAR_PETALBURG_CITY_STATE) == 0) {
      ctx.showNpcAt(LOCALID_GYM_BOY, 5, 11)
    }
  }
}

internal object PetalburgCity_EventScript_WallysMom : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity.WhereIsWally)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox PetalburgCity_Text_WaterReflection, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_PETALBURG_BOY, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object PetalburgCity_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_EventScript_Boy")
}

internal object PetalburgCity_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity.FullPartyExplanation)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REVIVE
 * end
 * ```
 */
internal object PetalburgCity_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_EventScript_ItemMaxRevive")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ETHER
 * end
 * ```
 */
internal object PetalburgCity_EventScript_ItemEther : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_EventScript_ItemEther")
}

internal object PetalburgCity_EventScript_GymBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity.AreYouRookieTrainer)
}

internal object PetalburgCity_EventScript_ShowGymToPlayer0 : Script {
  override suspend fun run(ctx: ScriptContext) = showGymToPlayer(ctx, 0)
}

internal object PetalburgCity_EventScript_ShowGymToPlayer1 : Script {
  override suspend fun run(ctx: ScriptContext) = showGymToPlayer(ctx, 1)
}

internal object PetalburgCity_EventScript_ShowGymToPlayer2 : Script {
  override suspend fun run(ctx: ScriptContext) = showGymToPlayer(ctx, 2)
}

internal object PetalburgCity_EventScript_ShowGymToPlayer3 : Script {
  override suspend fun run(ctx: ScriptContext) = showGymToPlayer(ctx, 3)
}

private suspend fun showGymToPlayer(ctx: ScriptContext, entranceRow: Int) {
  val approach =
      when (entranceRow) {
        0 -> listOf(WALK_RIGHT, WALK_RIGHT, WALK_RIGHT, FACE_UP)
        1 -> listOf(WALK_RIGHT, WALK_RIGHT)
        2 -> listOf(WALK_RIGHT, WALK_RIGHT, WALK_RIGHT, FACE_DOWN)
        else -> listOf(WALK_DOWN, WALK_RIGHT, WALK_RIGHT, WALK_RIGHT, FACE_DOWN)
      }
  ctx.moveNpc(LOCALID_GYM_BOY, *approach.toTypedArray())
  ctx.sayNpc(LOCALID_GYM_BOY, PetalburgCity.AreYouRookieTrainer)

  val boyToGym =
      when (entranceRow) {
        0 -> List(7) { WALK_RIGHT } + listOf(WALK_UP, WALK_RIGHT, FACE_UP)
        1 ->
            listOf(WALK_DOWN) +
                List(8) { WALK_RIGHT } +
                listOf(WALK_UP, WALK_UP, WALK_RIGHT, FACE_UP)
        2 -> List(7) { WALK_RIGHT } + listOf(WALK_UP, WALK_RIGHT, FACE_UP)
        else -> List(7) { WALK_RIGHT } + listOf(WALK_UP, WALK_UP, WALK_RIGHT, FACE_UP)
      }
  val playerToGym =
      when (entranceRow) {
        0 -> listOf(WALK_DOWN) + List(7) { WALK_RIGHT } + WALK_UP
        1 -> listOf(WALK_DOWN) + List(7) { WALK_RIGHT } + listOf(WALK_UP, WALK_UP)
        2 -> listOf(WALK_UP) + List(7) { WALK_RIGHT } + WALK_UP
        else -> listOf(WALK_UP) + List(7) { WALK_RIGHT } + listOf(WALK_UP, WALK_UP)
      }
  // Run Wally and Norman's paths before dialog.
  ctx.moveNpc(LOCALID_GYM_BOY, *boyToGym.toTypedArray())
  ctx.moveSelf(*playerToGym.toTypedArray())
  ctx.sayNpc(LOCALID_GYM_BOY, PetalburgCity.ThisIsPetalburgGym)
  ctx.sayNpc(LOCALID_GYM_BOY, PetalburgCity.ThisIsGymSign)
  ctx.moveNpc(LOCALID_GYM_BOY, WALK_DOWN, *Array(11) { WALK_LEFT })
}

internal object PetalburgCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PetalburgCity.GymSign)
}

internal object PetalburgCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PetalburgCity.CitySign)
}

internal object PetalburgCity_EventScript_WallyHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PetalburgCity.WallyHouseSign)
}

internal val PetalburgCityScripts: Map<String, Script> =
    mapOf(
        "PetalburgCity_OnTransition" to PetalburgCity_OnTransition,
        "PetalburgCity_EventScript_WallysMom" to PetalburgCity_EventScript_WallysMom,
        "PetalburgCity_EventScript_Boy" to PetalburgCity_EventScript_Boy,
        "PetalburgCity_EventScript_Gentleman" to PetalburgCity_EventScript_Gentleman,
        "PetalburgCity_EventScript_ItemMaxRevive" to PetalburgCity_EventScript_ItemMaxRevive,
        "PetalburgCity_EventScript_ItemEther" to PetalburgCity_EventScript_ItemEther,
        "PetalburgCity_EventScript_GymBoy" to PetalburgCity_EventScript_GymBoy,
        "PetalburgCity_EventScript_ShowGymToPlayer0" to PetalburgCity_EventScript_ShowGymToPlayer0,
        "PetalburgCity_EventScript_ShowGymToPlayer1" to PetalburgCity_EventScript_ShowGymToPlayer1,
        "PetalburgCity_EventScript_ShowGymToPlayer2" to PetalburgCity_EventScript_ShowGymToPlayer2,
        "PetalburgCity_EventScript_ShowGymToPlayer3" to PetalburgCity_EventScript_ShowGymToPlayer3,
        "PetalburgCity_EventScript_GymSign" to PetalburgCity_EventScript_GymSign,
        "PetalburgCity_EventScript_CitySign" to PetalburgCity_EventScript_CitySign,
        "PetalburgCity_EventScript_WallyHouseSign" to PetalburgCity_EventScript_WallyHouseSign,
    )
