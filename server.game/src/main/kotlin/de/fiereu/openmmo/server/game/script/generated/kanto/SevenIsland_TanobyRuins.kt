package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RUIN_MANIAC_BRANDON, SevenIsland_TanobyRuins_Text_BrandonIntro, SevenIsland_TanobyRuins_Text_BrandonDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_TanobyRuins_EventScript_BrandonRematch
 * msgbox SevenIsland_TanobyRuins_Text_BrandonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_TanobyRuins_EventScript_Brandon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_TanobyRuins_EventScript_Brandon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RUIN_MANIAC_BENJAMIN, SevenIsland_TanobyRuins_Text_BenjaminIntro, SevenIsland_TanobyRuins_Text_BenjaminDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_TanobyRuins_EventScript_BenjaminRematch
 * msgbox SevenIsland_TanobyRuins_Text_BenjaminPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_TanobyRuins_EventScript_Benjamin : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_TanobyRuins_EventScript_Benjamin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PAINTER_EDNA, SevenIsland_TanobyRuins_Text_EdnaIntro, SevenIsland_TanobyRuins_Text_EdnaDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_TanobyRuins_EventScript_EdnaRematch
 * msgbox SevenIsland_TanobyRuins_Text_EdnaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_TanobyRuins_EventScript_Edna : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_TanobyRuins_EventScript_Edna")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GENTLEMAN_CLIFFORD, SevenIsland_TanobyRuins_Text_CliffordIntro, SevenIsland_TanobyRuins_Text_CliffordDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_TanobyRuins_EventScript_CliffordRematch
 * msgbox SevenIsland_TanobyRuins_Text_CliffordPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_TanobyRuins_EventScript_Clifford : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_TanobyRuins_EventScript_Clifford")
}

internal val SevenIsland_TanobyRuinsScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_TanobyRuins_EventScript_Brandon" to
            SevenIsland_TanobyRuins_EventScript_Brandon,
        "SevenIsland_TanobyRuins_EventScript_Benjamin" to
            SevenIsland_TanobyRuins_EventScript_Benjamin,
        "SevenIsland_TanobyRuins_EventScript_Edna" to SevenIsland_TanobyRuins_EventScript_Edna,
        "SevenIsland_TanobyRuins_EventScript_Clifford" to
            SevenIsland_TanobyRuins_EventScript_Clifford,
    )
