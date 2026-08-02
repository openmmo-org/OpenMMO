package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.hoenn.PlayersHouse_1F
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars

private const val LOCALID_HOUSE_MOM = 0

internal object LittlerootTown_BrendansHouse_1F_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) = positionMom(ctx, female = false)
}

internal object LittlerootTown_MaysHouse_1F_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) = positionMom(ctx, female = true)
}

private fun positionMom(ctx: ScriptContext, female: Boolean) {
  val position =
      when (ctx.getVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE)) {
        3 -> if (female) 1 to 8 else 9 to 8
        5 -> if (female) 2 to 4 else 8 to 4
        6 -> if (female) 6 to 5 else 4 to 5
        else -> return
      }
  ctx.showNpcAt(LOCALID_HOUSE_MOM, position.first, position.second)
}

internal object LittlerootTown_BrendansHouse_1F_EventScript_EnterHouseMovingIn : Script {
  override suspend fun run(ctx: ScriptContext) = enterHouseMovingIn(ctx, female = false)
}

internal object LittlerootTown_MaysHouse_1F_EventScript_EnterHouseMovingIn : Script {
  override suspend fun run(ctx: ScriptContext) = enterHouseMovingIn(ctx, female = true)
}

private suspend fun enterHouseMovingIn(ctx: ScriptContext, female: Boolean) {
  positionMom(ctx, female)
  ctx.sayNpc(LOCALID_HOUSE_MOM, PlayersHouse_1F.IsntItNiceInHere)
  ctx.moveNpc(LOCALID_HOUSE_MOM, if (female) FACE_RIGHT else FACE_LEFT)
  ctx.moveSelf(if (female) FACE_LEFT else FACE_RIGHT)
  ctx.sayNpc(LOCALID_HOUSE_MOM, PlayersHouse_1F.MoversPokemonGoSetClock)
  ctx.setVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE, 4)
  ctx.moveSelf(WALK_UP)
}

internal object LittlerootTown_BrendansHouse_1F_EventScript_GoUpstairsToSetClock : Script {
  override suspend fun run(ctx: ScriptContext) = goUpstairsToSetClock(ctx, female = false)
}

internal object LittlerootTown_MaysHouse_1F_EventScript_GoUpstairsToSetClock : Script {
  override suspend fun run(ctx: ScriptContext) = goUpstairsToSetClock(ctx, female = true)
}

private suspend fun goUpstairsToSetClock(ctx: ScriptContext, female: Boolean) {
  positionMom(ctx, female)
  ctx.sayNpc(LOCALID_HOUSE_MOM, PlayersHouse_1F.GoSetTheClock)
  ctx.moveSelf(WALK_UP)
  ctx.warp(
      regionId = 1,
      bankId = 51,
      mapId = if (female) 3 else 1,
      x = if (female) 1 else 7,
      y = 1,
      facing = Direction.DOWN,
  )
}

internal object LittlerootTown_BrendansHouse_1F_EventScript_PetalburgGymReport : Script {
  override suspend fun run(ctx: ScriptContext) = watchPetalburgReport(ctx, female = false)
}

internal object LittlerootTown_MaysHouse_1F_EventScript_PetalburgGymReport : Script {
  override suspend fun run(ctx: ScriptContext) = watchPetalburgReport(ctx, female = true)
}

private suspend fun watchPetalburgReport(ctx: ScriptContext, female: Boolean) {
  positionMom(ctx, female)
  ctx.sayNpc(LOCALID_HOUSE_MOM, PlayersHouse_1F.OhComeQuickly)
  ctx.sayNpc(LOCALID_HOUSE_MOM, PlayersHouse_1F.MaybeDadWillBeOn)
  ctx.sign(PlayersHouse_1F.ReportFromPetalburgGym)
  ctx.sayNpc(LOCALID_HOUSE_MOM, PlayersHouse_1F.ItsOverWeMissedHim)
  ctx.sayNpc(LOCALID_HOUSE_MOM, PlayersHouse_1F.GoIntroduceYourselfNextDoor)
  ctx.setFlag(HoennFlags.FLAG_SYS_TV_HOME)
  ctx.setVar(HoennVars.VAR_LITTLEROOT_INTRO_STATE, 7)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_LITTLEROOT_HOUSES_STATE_MAY, 4, PlayersHouse_1F_EventScript_DontPushYourselfTooHard
 * goto_if_eq VAR_LITTLEROOT_HOUSES_STATE_BRENDAN, 4, PlayersHouse_1F_EventScript_DontPushYourselfTooHard
 * goto_if_set FLAG_HAS_MATCH_CALL, PlayersHouse_1F_EventScript_TryRegisterMom
 * goto_if_set FLAG_RESCUED_BIRCH, PlayersHouse_1F_EventScript_MomHealsParty
 * goto_if_eq VAR_TEMP_1, 1, PlayersHouse_1F_EventScript_SeeYouHoney
 * goto_if_eq VAR_LITTLEROOT_INTRO_STATE, 7, PlayersHouse_1F_EventScript_DidYouMeetProfBirch
 * msgbox PlayersHouse_1F_Text_IsntItNiceInHere, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object PlayersHouse_1F_EventScript_Mom : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PlayersHouse_1F_EventScript_Mom")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_VIGOROTH, CRY_MODE_NORMAL
 * msgbox PlayersHouse_1F_Text_Vigoroth2, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object PlayersHouse_1F_EventScript_Vigoroth2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PlayersHouse_1F_EventScript_Vigoroth2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_VIGOROTH, CRY_MODE_NORMAL
 * msgbox PlayersHouse_1F_Text_Vigoroth1, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object PlayersHouse_1F_EventScript_Vigoroth1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PlayersHouse_1F_EventScript_Vigoroth1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_RIVAL_ROUTE103, RivalsHouse_1F_EventScript_GoHomeEverySoOften
 * goto_if_set FLAG_SYS_POKEMON_GET, RivalsHouse_1F_EventScript_RivalIsOnRoute103
 * goto_if_eq VAR_LITTLEROOT_RIVAL_STATE, 3, RivalsHouse_1F_EventScript_RivalTooBusy
 * special GetRivalSonDaughterString
 * msgbox RivalsHouse_1F_Text_LikeChildLikeFather, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RivalsHouse_1F_EventScript_RivalMom : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RivalsHouse_1F_EventScript_RivalMom")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * special GetPlayerBigGuyGirlString
 * msgbox RivalsHouse_1F_Text_DoYouHavePokemon, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RivalsHouse_1F_EventScript_RivalSibling : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RivalsHouse_1F_EventScript_RivalSibling")
}

internal val LittlerootTown_BrendansHouse_1FScripts: Map<String, Script> =
    mapOf(
        "LittlerootTown_BrendansHouse_1F_OnTransition" to
            LittlerootTown_BrendansHouse_1F_OnTransition,
        "LittlerootTown_MaysHouse_1F_OnTransition" to LittlerootTown_MaysHouse_1F_OnTransition,
        "LittlerootTown_BrendansHouse_1F_EventScript_EnterHouseMovingIn" to
            LittlerootTown_BrendansHouse_1F_EventScript_EnterHouseMovingIn,
        "LittlerootTown_MaysHouse_1F_EventScript_EnterHouseMovingIn" to
            LittlerootTown_MaysHouse_1F_EventScript_EnterHouseMovingIn,
        "LittlerootTown_BrendansHouse_1F_EventScript_GoUpstairsToSetClock" to
            LittlerootTown_BrendansHouse_1F_EventScript_GoUpstairsToSetClock,
        "LittlerootTown_MaysHouse_1F_EventScript_GoUpstairsToSetClock" to
            LittlerootTown_MaysHouse_1F_EventScript_GoUpstairsToSetClock,
        "LittlerootTown_BrendansHouse_1F_EventScript_PetalburgGymReport" to
            LittlerootTown_BrendansHouse_1F_EventScript_PetalburgGymReport,
        "LittlerootTown_MaysHouse_1F_EventScript_PetalburgGymReport" to
            LittlerootTown_MaysHouse_1F_EventScript_PetalburgGymReport,
        "PlayersHouse_1F_EventScript_Mom" to PlayersHouse_1F_EventScript_Mom,
        "PlayersHouse_1F_EventScript_Vigoroth2" to PlayersHouse_1F_EventScript_Vigoroth2,
        "PlayersHouse_1F_EventScript_Vigoroth1" to PlayersHouse_1F_EventScript_Vigoroth1,
        "RivalsHouse_1F_EventScript_RivalMom" to RivalsHouse_1F_EventScript_RivalMom,
        "RivalsHouse_1F_EventScript_RivalSibling" to RivalsHouse_1F_EventScript_RivalSibling,
    )
