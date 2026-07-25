package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerhill_settrainerflags
 * lock
 * faceplayer
 * trainerhill_getownerstate
 * switch VAR_RESULT
 * case 0, TrainerHill_Roof_EventScript_Arrived
 * case 1, TrainerHill_Roof_EventScript_GivePrize
 * case 2, TrainerHill_Roof_EventScript_AlreadyReceivedPrize
 * msgbox TrainerHill_Roof_Text_YouFinallyCameBravo, MSGBOX_DEFAULT
 * trainerhill_giveprize
 * switch VAR_RESULT
 * case 0, TrainerHill_Roof_EventScript_ReceivePrize
 * case 1, TrainerHill_Roof_EventScript_NoRoomForPrize
 * case 2, TrainerHill_Roof_EventScript_CheckFinalTime
 * msgbox TrainerHill_Roof_Text_HaveTheMostMarvelousGift, MSGBOX_DEFAULT
 * playfanfare MUS_LEVEL_UP
 * message gText_ObtainedTheItem
 * waitfanfare
 * waitmessage
 * goto TrainerHill_Roof_EventScript_CheckFinalTime
 * ```
 */
internal object TrainerHill_Roof_EventScript_Owner : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port TrainerHill_Roof_EventScript_Owner")
}

internal val TrainerHill_RoofScripts: Map<String, Script> =
    mapOf(
        "TrainerHill_Roof_EventScript_Owner" to TrainerHill_Roof_EventScript_Owner,
    )
