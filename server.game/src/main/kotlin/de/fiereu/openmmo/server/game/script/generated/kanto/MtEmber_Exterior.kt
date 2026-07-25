package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_EXPLOSION, EventScript_ExplosionTaught
 * msgbox Text_ExplosionTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_ExplosionDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_ExplosionDeclined
 * msgbox Text_ExplosionWhichMon
 * setvar VAR_0x8005, MOVETUTOR_EXPLOSION
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_ExplosionDeclined
 * setflag FLAG_TUTOR_EXPLOSION
 * goto EventScript_ExplosionTaught
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_ExplosionTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MtEmber_Exterior_EventScript_ExplosionTutor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * goto_if_defeated TRAINER_TEAM_ROCKET_GRUNT_43, MtEmber_Exterior_EventScript_Grunt1Defeated
 * goto_if_eq VAR_MAP_SCENE_ONE_ISLAND_POKEMON_CENTER_1F, 4, MtEmber_Exterior_EventScript_BattleGrunt1
 * msgbox MtEmber_Exterior_Text_WellTryDiggingHere
 * release
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtEmber_Exterior_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_defeated TRAINER_TEAM_ROCKET_GRUNT_44, MtEmber_Exterior_EventScript_DefeatedGrunt2
 * goto_if_eq VAR_MAP_SCENE_ONE_ISLAND_POKEMON_CENTER_1F, 4, MtEmber_Exterior_EventScript_BattleGrunt2
 * msgbox MtEmber_Exterior_Text_YoureInTheWayGetLost
 * closemessage
 * applymovement LOCALID_MT_EMBER_GRUNT2, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtEmber_Exterior_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CRUSH_GIRL_JOCELYN, MtEmber_Exterior_Text_JocelynIntro, MtEmber_Exterior_Text_JocelynDefeat
 * msgbox MtEmber_Exterior_Text_JocelynPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_Jocelyn : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtEmber_Exterior_EventScript_Jocelyn")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PKMN_RANGER_LOGAN, MtEmber_Exterior_Text_LoganIntro, MtEmber_Exterior_Text_LoganDefeat
 * msgbox MtEmber_Exterior_Text_LoganPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_Logan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtEmber_Exterior_EventScript_Logan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PKMN_RANGER_BETH, MtEmber_Exterior_Text_BethIntro, MtEmber_Exterior_Text_BethDefeat
 * msgbox MtEmber_Exterior_Text_BethPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_Beth : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtEmber_Exterior_EventScript_Beth")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ULTRA_BALL
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MtEmber_Exterior_EventScript_ItemUltraBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FIRE_STONE
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_ItemFireStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MtEmber_Exterior_EventScript_ItemFireStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_DIRE_HIT
 * end
 * ```
 */
internal object MtEmber_Exterior_EventScript_ItemDireHit : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MtEmber_Exterior_EventScript_ItemDireHit")
}

internal val MtEmber_ExteriorScripts: Map<String, Script> =
    mapOf(
        "MtEmber_Exterior_EventScript_ExplosionTutor" to
            MtEmber_Exterior_EventScript_ExplosionTutor,
        "MtEmber_Exterior_EventScript_Grunt1" to MtEmber_Exterior_EventScript_Grunt1,
        "MtEmber_Exterior_EventScript_Grunt2" to MtEmber_Exterior_EventScript_Grunt2,
        "MtEmber_Exterior_EventScript_Jocelyn" to MtEmber_Exterior_EventScript_Jocelyn,
        "MtEmber_Exterior_EventScript_Logan" to MtEmber_Exterior_EventScript_Logan,
        "MtEmber_Exterior_EventScript_Beth" to MtEmber_Exterior_EventScript_Beth,
        "MtEmber_Exterior_EventScript_ItemUltraBall" to MtEmber_Exterior_EventScript_ItemUltraBall,
        "MtEmber_Exterior_EventScript_ItemFireStone" to MtEmber_Exterior_EventScript_ItemFireStone,
        "MtEmber_Exterior_EventScript_ItemDireHit" to MtEmber_Exterior_EventScript_ItemDireHit,
    )
