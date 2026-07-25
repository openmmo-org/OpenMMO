package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.OldaleTown
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object OldaleTown_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(OldaleTown.SavingMyProgress)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_POTION_OLDALE, OldaleTown_EventScript_ExplainPotion
 * goto_if_set FLAG_TEMP_1, OldaleTown_EventScript_ExplainPotion
 * setflag FLAG_TEMP_1
 * playbgm MUS_FOLLOW_ME, FALSE
 * msgbox OldaleTown_Text_IWorkAtPokemonMart, MSGBOX_DEFAULT
 * closemessage
 * switch VAR_FACING
 * case DIR_SOUTH, OldaleTown_EventScript_GoToMartSouth
 * case DIR_NORTH, OldaleTown_EventScript_GoToMartNorth
 * case DIR_EAST, OldaleTown_EventScript_GoToMartEast
 * end
 * ```
 */
internal object OldaleTown_EventScript_MartEmployee : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port OldaleTown_EventScript_MartEmployee")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_ADVENTURE_STARTED, OldaleTown_EventScript_NotBlockingPath
 * msgbox OldaleTown_Text_DiscoveredFootprints, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_FOOTPRINTS_MAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object OldaleTown_EventScript_FootprintsMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port OldaleTown_EventScript_FootprintsMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_OLDALE_RIVAL, Common_Movement_FacePlayer
 * waitmovement 0
 * setvar VAR_0x8009, 0
 * goto OldaleTown_EventScript_ShowRivalMessage
 * end
 * ```
 */
internal object OldaleTown_EventScript_Rival : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port OldaleTown_EventScript_Rival")
}

internal object OldaleTown_EventScript_TownSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(OldaleTown.TownSign)
}

internal val OldaleTownScripts: Map<String, Script> =
    mapOf(
        "OldaleTown_EventScript_Girl" to OldaleTown_EventScript_Girl,
        "OldaleTown_EventScript_MartEmployee" to OldaleTown_EventScript_MartEmployee,
        "OldaleTown_EventScript_FootprintsMan" to OldaleTown_EventScript_FootprintsMan,
        "OldaleTown_EventScript_Rival" to OldaleTown_EventScript_Rival,
        "OldaleTown_EventScript_TownSign" to OldaleTown_EventScript_TownSign,
    )
