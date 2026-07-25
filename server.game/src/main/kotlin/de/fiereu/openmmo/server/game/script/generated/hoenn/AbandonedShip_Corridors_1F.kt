package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AbandonedShip_Corridors_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object AbandonedShip_Corridors_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(AbandonedShip_Corridors_1F.IsntItFunHere)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHARLIE, AbandonedShip_Corridors_1F_Text_CharlieIntro, AbandonedShip_Corridors_1F_Text_CharlieDefeat
 * msgbox AbandonedShip_Corridors_1F_Text_CharliePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object AbandonedShip_Corridors_1F_EventScript_Charlie : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Corridors_1F_EventScript_Charlie")
}

internal val AbandonedShip_Corridors_1FScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_Corridors_1F_EventScript_Youngster" to
            AbandonedShip_Corridors_1F_EventScript_Youngster,
        "AbandonedShip_Corridors_1F_EventScript_Charlie" to
            AbandonedShip_Corridors_1F_EventScript_Charlie,
    )
