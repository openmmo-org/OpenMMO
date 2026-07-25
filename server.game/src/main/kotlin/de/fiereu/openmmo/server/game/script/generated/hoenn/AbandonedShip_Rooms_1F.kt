package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.AbandonedShip_Rooms_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object AbandonedShip_Rooms_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(AbandonedShip_Rooms_1F.TakingALookAround)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_HARBOR_MAIL
 * end
 * ```
 */
internal object AbandonedShip_Rooms_1F_EventScript_ItemHarborMail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Rooms_1F_EventScript_ItemHarborMail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_THALIA_1, AbandonedShip_Rooms_1F_Text_ThaliaIntro, AbandonedShip_Rooms_1F_Text_ThaliaDefeat, AbandonedShip_Rooms_1F_EventScript_RegisterThalia
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, AbandonedShip_Rooms_1F_EventScript_ThaliaRematch
 * msgbox AbandonedShip_Rooms_1F_Text_ThaliaPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object AbandonedShip_Rooms_1F_EventScript_Thalia : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Rooms_1F_EventScript_Thalia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DEMETRIUS, AbandonedShip_Rooms_1F_Text_DemetriusIntro, AbandonedShip_Rooms_1F_Text_DemetriusDefeat
 * msgbox AbandonedShip_Rooms_1F_Text_DemetriusPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object AbandonedShip_Rooms_1F_EventScript_Demetrius : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port AbandonedShip_Rooms_1F_EventScript_Demetrius")
}

internal val AbandonedShip_Rooms_1FScripts: Map<String, Script> =
    mapOf(
        "AbandonedShip_Rooms_1F_EventScript_Gentleman" to
            AbandonedShip_Rooms_1F_EventScript_Gentleman,
        "AbandonedShip_Rooms_1F_EventScript_ItemHarborMail" to
            AbandonedShip_Rooms_1F_EventScript_ItemHarborMail,
        "AbandonedShip_Rooms_1F_EventScript_Thalia" to AbandonedShip_Rooms_1F_EventScript_Thalia,
        "AbandonedShip_Rooms_1F_EventScript_Demetrius" to
            AbandonedShip_Rooms_1F_EventScript_Demetrius,
    )
