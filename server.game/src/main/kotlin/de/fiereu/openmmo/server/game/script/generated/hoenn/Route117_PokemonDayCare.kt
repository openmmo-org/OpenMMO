package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * specialvar VAR_RESULT, GetDaycareState
 * goto_if_eq VAR_RESULT, DAYCARE_EGG_WAITING, Route117_PokemonDayCare_EventScript_EggWaiting
 * goto_if_eq VAR_RESULT, DAYCARE_ONE_MON, Route117_PokemonDayCare_EventScript_OneMonInDaycare
 * goto_if_eq VAR_RESULT, DAYCARE_TWO_MONS, Route117_PokemonDayCare_EventScript_TwoMonsInDaycare
 * msgbox Route117_PokemonDayCare_Text_WouldYouLikeUsToRaiseAMon, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route117_PokemonDayCare_EventScript_GiveMonToRaise
 * msgbox Route117_PokemonDayCare_Text_FineThenComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route117_PokemonDayCare_EventScript_DaycareWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route117_PokemonDayCare_EventScript_DaycareWoman")
}

internal val Route117_PokemonDayCareScripts: Map<String, Script> =
    mapOf(
        "Route117_PokemonDayCare_EventScript_DaycareWoman" to
            Route117_PokemonDayCare_EventScript_DaycareWoman,
    )
