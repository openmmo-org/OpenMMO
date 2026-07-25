package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * switch VAR_PETALBURG_GYM_STATE
 * case 2, PetalburgCity_Gym_EventScript_NormanNoBadges
 * case 3, PetalburgCity_Gym_EventScript_NormanOneBadge
 * case 4, PetalburgCity_Gym_EventScript_NormanTwoBadges
 * case 5, PetalburgCity_Gym_EventScript_NormanThreeBadges
 * case 6, PetalburgCity_Gym_EventScript_NormanBattle
 * case 7, PetalburgCity_Gym_EventScript_NormanPostBattle
 * case 8, PetalburgCity_Gym_EventScript_NormanRematch
 * msgbox PetalburgCity_Gym_Text_DadYoureHereWithYourPokemon, MSGBOX_DEFAULT
 * closemessage
 * switch VAR_FACING
 * case DIR_SOUTH, PetalburgCity_Gym_EventScript_BeginWallyTutorialSouth
 * case DIR_NORTH, PetalburgCity_Gym_EventScript_BeginWallyTutorialNorth
 * case DIR_WEST, PetalburgCity_Gym_EventScript_BeginWallyTutorialWest
 * case DIR_EAST, PetalburgCity_Gym_EventScript_BeginWallyTutorialEast
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_Norman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Gym_EventScript_Norman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MARY, PetalburgCity_Gym_Text_MaryIntro, PetalburgCity_Gym_Text_MaryDefeat, PetalburgCity_Gym_EventScript_SlideOpenAccuracyRoomDoors
 * goto_if_set FLAG_DEFEATED_PETALBURG_GYM, PetalburgCity_Gym_EventScript_MaryPostBadge
 * msgbox PetalburgCity_Gym_Text_MaryPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_Mary : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Gym_EventScript_Mary")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RANDALL, PetalburgCity_Gym_Text_RandallIntro, PetalburgCity_Gym_Text_RandallDefeat, PetalburgCity_Gym_EventScript_SlideOpenSpeedRoomDoors
 * goto_if_set FLAG_DEFEATED_PETALBURG_GYM, PetalburgCity_Gym_EventScript_RandallPostBadge
 * msgbox PetalburgCity_Gym_Text_RandallPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_Randall : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Gym_EventScript_Randall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PARKER, PetalburgCity_Gym_Text_ParkerIntro, PetalburgCity_Gym_Text_ParkerDefeat, PetalburgCity_Gym_EventScript_SlideOpenConfusionRoomDoors
 * goto_if_set FLAG_DEFEATED_PETALBURG_GYM, PetalburgCity_Gym_EventScript_ParkerPostBadge
 * msgbox PetalburgCity_Gym_Text_ParkerPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_Parker : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Gym_EventScript_Parker")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ALEXIA, PetalburgCity_Gym_Text_AlexiaIntro, PetalburgCity_Gym_Text_AlexiaDefeat, PetalburgCity_Gym_EventScript_SlideOpenDefenseRoomDoors
 * goto_if_set FLAG_DEFEATED_PETALBURG_GYM, PetalburgCity_Gym_EventScript_AlexiaPostBadge
 * msgbox PetalburgCity_Gym_Text_AlexiaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_Alexia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Gym_EventScript_Alexia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GEORGE, PetalburgCity_Gym_Text_GeorgeIntro, PetalburgCity_Gym_Text_GeorgeDefeat, PetalburgCity_Gym_EventScript_SlideOpenRecoveryRoomDoors
 * goto_if_set FLAG_DEFEATED_PETALBURG_GYM, PetalburgCity_Gym_EventScript_GeorgePostBadge
 * msgbox PetalburgCity_Gym_Text_GeorgePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_George : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Gym_EventScript_George")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JODY, PetalburgCity_Gym_Text_JodyIntro, PetalburgCity_Gym_Text_JodyDefeat, PetalburgCity_Gym_EventScript_SlideOpenStrengthRoomDoors
 * goto_if_set FLAG_DEFEATED_PETALBURG_GYM, PetalburgCity_Gym_EventScript_JodyPostBadge
 * msgbox PetalburgCity_Gym_Text_JodyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_Jody : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Gym_EventScript_Jody")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BERKE, PetalburgCity_Gym_Text_BerkeIntro, PetalburgCity_Gym_Text_BerkeDefeat, PetalburgCity_Gym_EventScript_SlideOpenOHKORoomDoors
 * goto_if_set FLAG_DEFEATED_PETALBURG_GYM, PetalburgCity_Gym_EventScript_BerkePostBadge
 * msgbox PetalburgCity_Gym_Text_BerkePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_Berke : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Gym_EventScript_Berke")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_PETALBURG_GYM, PetalburgCity_Gym_EventScript_GymGuidePostVictory
 * msgbox PetalburgCity_Gym_Text_GymGuideAdvice, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_GymGuide : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_Gym_EventScript_GymGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_lt VAR_PETALBURG_GYM_STATE, 6, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 7
 * setvar VAR_0x8009, 85
 * msgbox PetalburgCity_Gym_Text_EnterSpeedRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_SpeedRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_SpeedRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_lt VAR_PETALBURG_GYM_STATE, 6, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 1
 * setvar VAR_0x8009, 98
 * msgbox PetalburgCity_Gym_Text_EnterAccuracyRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_AccuracyRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_AccuracyRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_RANDALL, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 7
 * setvar VAR_0x8009, 46
 * msgbox PetalburgCity_Gym_Text_EnterConfusionRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_ConfusionRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_ConfusionRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_RANDALL, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 1
 * setvar VAR_0x8009, 59
 * msgbox PetalburgCity_Gym_Text_EnterDefenseRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_LeftDefenseRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_LeftDefenseRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_MARY, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 7
 * setvar VAR_0x8009, 59
 * msgbox PetalburgCity_Gym_Text_EnterDefenseRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_RightDefenseRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_RightDefenseRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_MARY, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 1
 * setvar VAR_0x8009, 72
 * msgbox PetalburgCity_Gym_Text_EnterRecoveryRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_RecoveryRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_RecoveryRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_PARKER, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 1
 * setvar VAR_0x8009, 20
 * msgbox PetalburgCity_Gym_Text_EnterStrengthRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_LeftStrengthRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_LeftStrengthRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_ALEXIA, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 7
 * setvar VAR_0x8009, 20
 * msgbox PetalburgCity_Gym_Text_EnterStrengthRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_RightStrengthRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_RightStrengthRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_ALEXIA, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 1
 * setvar VAR_0x8009, 33
 * msgbox PetalburgCity_Gym_Text_EnterOHKORoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_LeftOHKORoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_LeftOHKORoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_GEORGE, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 7
 * setvar VAR_0x8009, 33
 * msgbox PetalburgCity_Gym_Text_EnterOHKORoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_RightOHKORoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_RightOHKORoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_JODY, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 1
 * setvar VAR_0x8009, 7
 * msgbox PetalburgCity_Gym_Text_EnterGymLeadersRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_LeftGymLeadersRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_LeftGymLeadersRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_not_defeated TRAINER_BERKE, PetalburgCity_Gym_EventScript_DoorLocked
 * setvar VAR_0x8008, 7
 * setvar VAR_0x8009, 7
 * msgbox PetalburgCity_Gym_Text_EnterGymLeadersRoom, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, PetalburgCity_Gym_EventScript_EnterRoom
 * goto_if_eq VAR_RESULT, NO, PetalburgCity_Gym_EventScript_DontEnterRoom
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_RightGymLeadersRoomDoor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_RightGymLeadersRoomDoor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE05_GET, PetalburgCity_Gym_EventScript_GymStatueCertified
 * goto PetalburgCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_LeftGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_LeftGymStatue")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BADGE05_GET, PetalburgCity_Gym_EventScript_GymStatueCertified
 * goto PetalburgCity_Gym_EventScript_GymStatue
 * end
 * ```
 */
internal object PetalburgCity_Gym_EventScript_RightGymStatue : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_Gym_EventScript_RightGymStatue")
}

internal val PetalburgCity_GymScripts: Map<String, Script> =
    mapOf(
        "PetalburgCity_Gym_EventScript_Norman" to PetalburgCity_Gym_EventScript_Norman,
        "PetalburgCity_Gym_EventScript_Mary" to PetalburgCity_Gym_EventScript_Mary,
        "PetalburgCity_Gym_EventScript_Randall" to PetalburgCity_Gym_EventScript_Randall,
        "PetalburgCity_Gym_EventScript_Parker" to PetalburgCity_Gym_EventScript_Parker,
        "PetalburgCity_Gym_EventScript_Alexia" to PetalburgCity_Gym_EventScript_Alexia,
        "PetalburgCity_Gym_EventScript_George" to PetalburgCity_Gym_EventScript_George,
        "PetalburgCity_Gym_EventScript_Jody" to PetalburgCity_Gym_EventScript_Jody,
        "PetalburgCity_Gym_EventScript_Berke" to PetalburgCity_Gym_EventScript_Berke,
        "PetalburgCity_Gym_EventScript_GymGuide" to PetalburgCity_Gym_EventScript_GymGuide,
        "PetalburgCity_Gym_EventScript_SpeedRoomDoor" to
            PetalburgCity_Gym_EventScript_SpeedRoomDoor,
        "PetalburgCity_Gym_EventScript_AccuracyRoomDoor" to
            PetalburgCity_Gym_EventScript_AccuracyRoomDoor,
        "PetalburgCity_Gym_EventScript_ConfusionRoomDoor" to
            PetalburgCity_Gym_EventScript_ConfusionRoomDoor,
        "PetalburgCity_Gym_EventScript_LeftDefenseRoomDoor" to
            PetalburgCity_Gym_EventScript_LeftDefenseRoomDoor,
        "PetalburgCity_Gym_EventScript_RightDefenseRoomDoor" to
            PetalburgCity_Gym_EventScript_RightDefenseRoomDoor,
        "PetalburgCity_Gym_EventScript_RecoveryRoomDoor" to
            PetalburgCity_Gym_EventScript_RecoveryRoomDoor,
        "PetalburgCity_Gym_EventScript_LeftStrengthRoomDoor" to
            PetalburgCity_Gym_EventScript_LeftStrengthRoomDoor,
        "PetalburgCity_Gym_EventScript_RightStrengthRoomDoor" to
            PetalburgCity_Gym_EventScript_RightStrengthRoomDoor,
        "PetalburgCity_Gym_EventScript_LeftOHKORoomDoor" to
            PetalburgCity_Gym_EventScript_LeftOHKORoomDoor,
        "PetalburgCity_Gym_EventScript_RightOHKORoomDoor" to
            PetalburgCity_Gym_EventScript_RightOHKORoomDoor,
        "PetalburgCity_Gym_EventScript_LeftGymLeadersRoomDoor" to
            PetalburgCity_Gym_EventScript_LeftGymLeadersRoomDoor,
        "PetalburgCity_Gym_EventScript_RightGymLeadersRoomDoor" to
            PetalburgCity_Gym_EventScript_RightGymLeadersRoomDoor,
        "PetalburgCity_Gym_EventScript_LeftGymStatue" to
            PetalburgCity_Gym_EventScript_LeftGymStatue,
        "PetalburgCity_Gym_EventScript_RightGymStatue" to
            PetalburgCity_Gym_EventScript_RightGymStatue,
    )
