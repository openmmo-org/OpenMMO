package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * specialvar VAR_RESULT, GetDaycareState
 * goto_if_eq VAR_RESULT, DAYCARE_EGG_WAITING, FourIsland_PokemonDayCare_EggWaiting
 * goto_if_eq VAR_RESULT, DAYCARE_ONE_MON, FourIsland_PokemonDayCare_OneMonInDaycare
 * goto_if_eq VAR_RESULT, DAYCARE_TWO_MONS, FourIsland_PokemonDayCare_TwoMonsInDaycare
 * msgbox DayCare_Text_WouldYouLikeUsToRaiseMon, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, FourIsland_PokemonDayCare_GiveMonToRaise
 * msgbox DayCare_Text_FineThenComeAgain
 * release
 * end
 * ```
 */
internal object FourIsland_PokemonDayCare_EventScript_DaycareWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FourIsland_PokemonDayCare_EventScript_DaycareWoman")
}

internal val FourIsland_PokemonDayCareScripts: Map<String, Script> =
    mapOf(
        "FourIsland_PokemonDayCare_EventScript_DaycareWoman" to
            FourIsland_PokemonDayCare_EventScript_DaycareWoman,
    )
