package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, MossdeepCity_House4_EventScript_CanBattleAtSecretBases
 * msgbox MossdeepCity_House4_Text_BrotherLikesToFindBases, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_House4_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_House4_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * special CheckPlayerHasSecretBase
 * goto_if_eq VAR_RESULT, FALSE, MossdeepCity_House4_EventScript_NoSecretBase
 * special GetSecretBaseNearbyMapName
 * msgbox MossdeepCity_House4_Text_YouMadeSecretBaseNearX, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MossdeepCity_House4_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_House4_EventScript_NinjaBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_SKITTY, CRY_MODE_NORMAL
 * msgbox MossdeepCity_House4_Text_Skitty, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object MossdeepCity_House4_EventScript_Skitty : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MossdeepCity_House4_EventScript_Skitty")
}

internal val MossdeepCity_House4Scripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_House4_EventScript_Woman" to MossdeepCity_House4_EventScript_Woman,
        "MossdeepCity_House4_EventScript_NinjaBoy" to MossdeepCity_House4_EventScript_NinjaBoy,
        "MossdeepCity_House4_EventScript_Skitty" to MossdeepCity_House4_EventScript_Skitty,
    )
