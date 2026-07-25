package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_6_SODA_POP, Route109_SeashoreHouse_EventScript_AlreadyReceivedSodaPop
 * goto_if_set FLAG_DEFEATED_SEASHORE_HOUSE, Route109_SeashoreHouse_EventScript_DefeatedTrainers
 * goto_if_set FLAG_TEMP_2, Route109_SeashoreHouse_EventScript_AlreadyGaveIntroduction
 * msgbox Route109_SeashoreHouse_Text_SeashoreHouseIntro, MSGBOX_DEFAULT
 * setflag FLAG_TEMP_2
 * release
 * end
 * ```
 */
internal object Route109_SeashoreHouse_EventScript_Owner : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route109_SeashoreHouse_EventScript_Owner")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DWAYNE, Route109_SeashoreHouse_Text_DwayneIntro, Route109_SeashoreHouse_Text_DwayneDefeated, Route109_SeashoreHouse_EventScript_CheckTrainersCompletion
 * msgbox Route109_SeashoreHouse_Text_DwaynePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route109_SeashoreHouse_EventScript_Dwayne : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route109_SeashoreHouse_EventScript_Dwayne")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SIMON, Route109_SeashoreHouse_Text_SimonIntro, Route109_SeashoreHouse_Text_SimonDefeated, Route109_SeashoreHouse_EventScript_CheckTrainersCompletion
 * msgbox Route109_SeashoreHouse_Text_SimonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route109_SeashoreHouse_EventScript_Simon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route109_SeashoreHouse_EventScript_Simon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JOHANNA, Route109_SeashoreHouse_Text_JohannaIntro, Route109_SeashoreHouse_Text_JohannaDefeated, Route109_SeashoreHouse_EventScript_CheckTrainersCompletion
 * msgbox Route109_SeashoreHouse_Text_JohannaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route109_SeashoreHouse_EventScript_Johanna : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route109_SeashoreHouse_EventScript_Johanna")
}

internal val Route109_SeashoreHouseScripts: Map<String, Script> =
    mapOf(
        "Route109_SeashoreHouse_EventScript_Owner" to Route109_SeashoreHouse_EventScript_Owner,
        "Route109_SeashoreHouse_EventScript_Dwayne" to Route109_SeashoreHouse_EventScript_Dwayne,
        "Route109_SeashoreHouse_EventScript_Simon" to Route109_SeashoreHouse_EventScript_Simon,
        "Route109_SeashoreHouse_EventScript_Johanna" to Route109_SeashoreHouse_EventScript_Johanna,
    )
