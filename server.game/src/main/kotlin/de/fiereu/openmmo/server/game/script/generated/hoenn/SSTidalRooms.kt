package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COLTON, SSTidalRooms_Text_ColtonIntro, SSTidalRooms_Text_ColtonDefeat
 * msgbox SSTidalRooms_Text_ColtonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_Colton : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_Colton")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MICAH, SSTidalRooms_Text_MicahIntro, SSTidalRooms_Text_MicahDefeat
 * msgbox SSTidalRooms_Text_MicahPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_Micah : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_Micah")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_THOMAS, SSTidalRooms_Text_ThomasIntro, SSTidalRooms_Text_ThomasDefeat
 * msgbox SSTidalRooms_Text_ThomasPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_Thomas : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_Thomas")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LEA_AND_JED, SSTidalRooms_Text_JedIntro, SSTidalRooms_Text_JedDefeat, SSTidalRooms_Text_JedNotEnoughMons
 * msgbox SSTidalRooms_Text_JedPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_Jed : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_Jed")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_LEA_AND_JED, SSTidalRooms_Text_LeaIntro, SSTidalRooms_Text_LeaDefeat, SSTidalRooms_Text_LeaNotEnoughMons
 * msgbox SSTidalRooms_Text_LeaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_Lea : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_Lea")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GARRET, SSTidalRooms_Text_GarretIntro, SSTidalRooms_Text_GarretDefeat
 * msgbox SSTidalRooms_Text_GarretPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_Garret : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_Garret")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_NAOMI, SSTidalRooms_Text_NaomiIntro, SSTidalRooms_Text_NaomiDefeat
 * msgbox SSTidalRooms_Text_NaomiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_Naomi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_Naomi")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_SNATCH, SSTidalRooms_EventScript_ExplainSnatch
 * msgbox SSTidalRooms_Text_NotSuspiciousTakeThis, MSGBOX_DEFAULT
 * giveitem ITEM_TM_SNATCH
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_SNATCH
 * msgbox SSTidalRooms_Text_ExplainSnatch, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_SnatchGiver : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_SnatchGiver")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox SSTidalRooms_Text_TakeRestOnBed, MSGBOX_DEFAULT
 * closemessage
 * call Common_EventScript_OutOfCenterPartyHeal
 * call SSTidalRooms_EventScript_ProgessCruiseAfterBed
 * releaseall
 * end
 * ```
 */
internal object SSTidalRooms_EventScript_Bed : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalRooms_EventScript_Bed")
}

internal val SSTidalRoomsScripts: Map<String, Script> =
    mapOf(
        "SSTidalRooms_EventScript_Colton" to SSTidalRooms_EventScript_Colton,
        "SSTidalRooms_EventScript_Micah" to SSTidalRooms_EventScript_Micah,
        "SSTidalRooms_EventScript_Thomas" to SSTidalRooms_EventScript_Thomas,
        "SSTidalRooms_EventScript_Jed" to SSTidalRooms_EventScript_Jed,
        "SSTidalRooms_EventScript_Lea" to SSTidalRooms_EventScript_Lea,
        "SSTidalRooms_EventScript_Garret" to SSTidalRooms_EventScript_Garret,
        "SSTidalRooms_EventScript_Naomi" to SSTidalRooms_EventScript_Naomi,
        "SSTidalRooms_EventScript_SnatchGiver" to SSTidalRooms_EventScript_SnatchGiver,
        "SSTidalRooms_EventScript_Bed" to SSTidalRooms_EventScript_Bed,
    )
