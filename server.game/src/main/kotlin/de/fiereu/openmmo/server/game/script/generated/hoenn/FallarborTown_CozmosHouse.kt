package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_RETURN, FallarborTown_CozmosHouse_EventScript_GaveMeteorite
 * checkitem ITEM_METEORITE
 * goto_if_eq VAR_RESULT, TRUE, FallarborTown_CozmosHouse_EventScript_PlayerHasMeteorite
 * msgbox FallarborTown_CozmosHouse_Text_MeteoriteWillNeverBeMineNow, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FallarborTown_CozmosHouse_EventScript_ProfCozmo : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FallarborTown_CozmosHouse_EventScript_ProfCozmo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_RETURN, FallarborTown_CozmosHouse_EventScript_CozmoIsHappy
 * goto_if_set FLAG_DEFEATED_EVIL_TEAM_MT_CHIMNEY, FallarborTown_CozmosHouse_EventScript_CozmoIsSad
 * msgbox FallarborTown_CozmosHouse_Text_CozmoWentToMeteorFalls, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FallarborTown_CozmosHouse_EventScript_CozmosWife : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FallarborTown_CozmosHouse_EventScript_CozmosWife")
}

internal val FallarborTown_CozmosHouseScripts: Map<String, Script> =
    mapOf(
        "FallarborTown_CozmosHouse_EventScript_ProfCozmo" to
            FallarborTown_CozmosHouse_EventScript_ProfCozmo,
        "FallarborTown_CozmosHouse_EventScript_CozmosWife" to
            FallarborTown_CozmosHouse_EventScript_CozmosWife,
    )
