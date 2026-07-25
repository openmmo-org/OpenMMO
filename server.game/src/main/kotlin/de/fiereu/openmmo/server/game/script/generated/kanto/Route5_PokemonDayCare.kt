package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * showmoneybox 0, 0
 * specialvar VAR_RESULT, IsThereMonInRoute5Daycare
 * goto_if_eq VAR_RESULT, TRUE, Route5_PokemonDayCare_EventScript_CheckOnMon
 * msgbox Route5_PokemonDayCare_Text_WantMeToRaiseMon, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route5_PokemonDayCare_EventScript_TryGiveMon
 * msgbox Route5_PokemonDayCare_Text_ComeAgain
 * goto Route5_PokemonDayCare_EventScript_CloseMoneyBox
 * end
 * ```
 */
internal object Route5_PokemonDayCare_EventScript_DaycareMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route5_PokemonDayCare_EventScript_DaycareMan")
}

internal val Route5_PokemonDayCareScripts: Map<String, Script> =
    mapOf(
        "Route5_PokemonDayCare_EventScript_DaycareMan" to
            Route5_PokemonDayCare_EventScript_DaycareMan,
    )
