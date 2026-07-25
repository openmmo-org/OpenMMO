package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route114_FossilManiacsHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_DIG, Route114_FossilManiacsHouse_EventScript_ReceivedDig
 * msgbox Route114_FossilManiacsHouse_Text_HaveThisToDigLikeMyBrother, MSGBOX_DEFAULT
 * giveitem ITEM_TM_DIG
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_DIG
 * release
 * end
 * ```
 */
internal object Route114_FossilManiacsHouse_EventScript_FossilManiacsBrother : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route114_FossilManiacsHouse_EventScript_FossilManiacsBrother")
}

internal object Route114_FossilManiacsHouse_EventScript_RockDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(Route114_FossilManiacsHouse.RocksFillDisplayCase)
}

internal object Route114_FossilManiacsHouse_EventScript_Bookshelf : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(Route114_FossilManiacsHouse.CrammedWithBooks)
}

internal val Route114_FossilManiacsHouseScripts: Map<String, Script> =
    mapOf(
        "Route114_FossilManiacsHouse_EventScript_FossilManiacsBrother" to
            Route114_FossilManiacsHouse_EventScript_FossilManiacsBrother,
        "Route114_FossilManiacsHouse_EventScript_RockDisplay" to
            Route114_FossilManiacsHouse_EventScript_RockDisplay,
        "Route114_FossilManiacsHouse_EventScript_Bookshelf" to
            Route114_FossilManiacsHouse_EventScript_Bookshelf,
    )
