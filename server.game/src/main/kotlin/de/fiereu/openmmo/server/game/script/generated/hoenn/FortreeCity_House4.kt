package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FortreeCity_House4
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object FortreeCity_House4_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FortreeCity_House4.BringsWorldCloserTogether)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_MENTAL_HERB, FortreeCity_House4_EventScript_ReceivedMentalHerb
 * goto_if_set FLAG_WINGULL_DELIVERED_MAIL, FortreeCity_House4_EventScript_WingullReturned
 * goto_if_set FLAG_WINGULL_SENT_ON_ERRAND, FortreeCity_House4_EventScript_WingullOnErrand
 * msgbox FortreeCity_House4_Text_GoBirdPokemon, MSGBOX_DEFAULT
 * closemessage
 * setflag FLAG_WINGULL_SENT_ON_ERRAND
 * clearflag FLAG_HIDE_MOSSDEEP_CITY_HOUSE_2_WINGULL
 * applymovement LOCALID_FORTREE_HOUSE_WINGULL, FortreeCity_House4_Movement_WingullExit
 * waitmovement 0
 * removeobject LOCALID_FORTREE_HOUSE_WINGULL
 * releaseall
 * end
 * ```
 */
internal object FortreeCity_House4_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_House4_EventScript_Boy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_WINGULL, CRY_MODE_NORMAL
 * msgbox FortreeCity_House4_Text_Wingull, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object FortreeCity_House4_EventScript_Wingull : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FortreeCity_House4_EventScript_Wingull")
}

internal val FortreeCity_House4Scripts: Map<String, Script> =
    mapOf(
        "FortreeCity_House4_EventScript_Woman" to FortreeCity_House4_EventScript_Woman,
        "FortreeCity_House4_EventScript_Boy" to FortreeCity_House4_EventScript_Boy,
        "FortreeCity_House4_EventScript_Wingull" to FortreeCity_House4_EventScript_Wingull,
    )
