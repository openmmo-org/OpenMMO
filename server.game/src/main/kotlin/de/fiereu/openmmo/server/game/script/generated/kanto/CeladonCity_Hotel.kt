package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_Hotel
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_Hotel_EventScript_Receptionist : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Hotel.ThisHotelIsForPeople)
}

internal object CeladonCity_Hotel_EventScript_Beauty : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_Hotel.OnVacationWithBrotherAndBoyfriend)
}

internal object CeladonCity_Hotel_EventScript_BeautyBoyfriend : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Hotel.WhyDidSheBringBrother)
}

internal object CeladonCity_Hotel_EventScript_BeautyBrother : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Hotel.SisBroughtMeOnVacation)
}

internal val CeladonCity_HotelScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_Hotel_EventScript_Receptionist" to CeladonCity_Hotel_EventScript_Receptionist,
        "CeladonCity_Hotel_EventScript_Beauty" to CeladonCity_Hotel_EventScript_Beauty,
        "CeladonCity_Hotel_EventScript_BeautyBoyfriend" to
            CeladonCity_Hotel_EventScript_BeautyBoyfriend,
        "CeladonCity_Hotel_EventScript_BeautyBrother" to
            CeladonCity_Hotel_EventScript_BeautyBrother,
    )
