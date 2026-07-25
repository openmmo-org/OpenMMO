package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_CuttersHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_HM_CUT, RustboroCity_CuttersHouse_EventScript_ExplainCut
 * msgbox RustboroCity_CuttersHouse_Text_YouCanPutThisHMToGoodUse, MSGBOX_DEFAULT
 * giveitem ITEM_HM_CUT
 * setflag FLAG_RECEIVED_HM_CUT
 * msgbox RustboroCity_CuttersHouse_Text_ExplainCut, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_CuttersHouse_EventScript_Cutter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_CuttersHouse_EventScript_Cutter")
}

internal object RustboroCity_CuttersHouse_EventScript_Lass : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(RustboroCity_CuttersHouse.DadHelpedClearLandOfTrees)
}

internal val RustboroCity_CuttersHouseScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_CuttersHouse_EventScript_Cutter" to
            RustboroCity_CuttersHouse_EventScript_Cutter,
        "RustboroCity_CuttersHouse_EventScript_Lass" to RustboroCity_CuttersHouse_EventScript_Lass,
    )
