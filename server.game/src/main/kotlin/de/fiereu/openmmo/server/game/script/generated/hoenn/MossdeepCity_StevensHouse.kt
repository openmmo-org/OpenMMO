package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MossdeepCity_StevensHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object MossdeepCity_StevensHouse_EventScript_Steven : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MossdeepCity_StevensHouse.UnderwateCavernBetweenMossdeepSootopolis)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * msgbox MossdeepCity_StevensHouse_Text_TakeBallContainingBeldum, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MossdeepCity_StevensHouse_EventScript_LeaveBeldum
 * goto MossdeepCity_StevensHouse_EventScript_GiveBeldum
 * end
 * ```
 */
internal object MossdeepCity_StevensHouse_EventScript_BeldumPokeball : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_StevensHouse_EventScript_BeldumPokeball")
}

internal object MossdeepCity_StevensHouse_EventScript_Letter : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MossdeepCity_StevensHouse.LetterFromSteven)
}

internal object MossdeepCity_StevensHouse_EventScript_RockDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(MossdeepCity_StevensHouse.CollectionOfRareRocks)
}

internal val MossdeepCity_StevensHouseScripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_StevensHouse_EventScript_Steven" to
            MossdeepCity_StevensHouse_EventScript_Steven,
        "MossdeepCity_StevensHouse_EventScript_BeldumPokeball" to
            MossdeepCity_StevensHouse_EventScript_BeldumPokeball,
        "MossdeepCity_StevensHouse_EventScript_Letter" to
            MossdeepCity_StevensHouse_EventScript_Letter,
        "MossdeepCity_StevensHouse_EventScript_RockDisplay" to
            MossdeepCity_StevensHouse_EventScript_RockDisplay,
    )
