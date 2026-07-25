package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FuchsiaCity_WardensHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_HM04, FuchsiaCity_WardensHouse_EventScript_ExplainStrength
 * goto_if_set FLAG_HIDE_SAFARI_ZONE_WEST_GOLD_TEETH, FuchsiaCity_WardensHouse_EventScript_GiveGoldTeeth
 * msgbox FuchsiaCity_WardensHouse_Text_HifFuffHefifoo, MSGBOX_YESNO
 * call_if_eq VAR_RESULT, YES, FuchsiaCity_WardensHouse_EventScript_WardenYes
 * call_if_eq VAR_RESULT, NO, FuchsiaCity_WardensHouse_EventScript_WardenNo
 * release
 * end
 * ```
 */
internal object FuchsiaCity_WardensHouse_EventScript_Warden : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FuchsiaCity_WardensHouse_EventScript_Warden")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object FuchsiaCity_WardensHouse_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FuchsiaCity_WardensHouse_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_KOGA, 4
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureKoga
 * release
 * end
 * ```
 */
internal object FuchsiaCity_WardensHouse_EventScript_PokemonJournalKoga : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FuchsiaCity_WardensHouse_EventScript_PokemonJournalKoga")
}

internal object FuchsiaCity_WardensHouse_EventScript_DisplaySign2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(FuchsiaCity_WardensHouse.OldMonMerchandiseOnDisplay)
}

internal object FuchsiaCity_WardensHouse_EventScript_DisplaySign1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(FuchsiaCity_WardensHouse.MonPhotosFossilsOnDisplay)
}

internal val FuchsiaCity_WardensHouseScripts: Map<String, Script> =
    mapOf(
        "FuchsiaCity_WardensHouse_EventScript_Warden" to
            FuchsiaCity_WardensHouse_EventScript_Warden,
        "FuchsiaCity_WardensHouse_EventScript_ItemRareCandy" to
            FuchsiaCity_WardensHouse_EventScript_ItemRareCandy,
        "FuchsiaCity_WardensHouse_EventScript_PokemonJournalKoga" to
            FuchsiaCity_WardensHouse_EventScript_PokemonJournalKoga,
        "FuchsiaCity_WardensHouse_EventScript_DisplaySign2" to
            FuchsiaCity_WardensHouse_EventScript_DisplaySign2,
        "FuchsiaCity_WardensHouse_EventScript_DisplaySign1" to
            FuchsiaCity_WardensHouse_EventScript_DisplaySign1,
    )
