package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_GameCorner_PrizeRoom
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_GameCorner_PrizeRoom_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_GameCorner_PrizeRoom.FancyThatPorygon)
}

internal object CeladonCity_GameCorner_PrizeRoom_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_GameCorner_PrizeRoom.RakedItInToday)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_GOT_COIN_CASE, CeladonCity_GameCorner_PrizeRoom_EventScript_NeedCoinCase
 * goto_if_questlog EventScript_ReleaseEnd
 * showcoinsbox 0, 0
 * msgbox CeladonCity_GameCorner_PrizeRoom_Text_WeExchangeCoinsForPrizes
 * goto CeladonCity_GameCorner_PrizeRoom_EventScript_ChoosePrizeMon
 * end
 * ```
 */
internal object CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkMons : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkMons")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_GOT_COIN_CASE, CeladonCity_GameCorner_PrizeRoom_EventScript_NeedCoinCase
 * goto_if_questlog EventScript_ReleaseEnd
 * showcoinsbox 0, 0
 * msgbox CeladonCity_GameCorner_PrizeRoom_Text_WeExchangeCoinsForPrizes
 * goto CeladonCity_GameCorner_PrizeRoom_EventScript_ChoosePrizeTM
 * end
 * ```
 */
internal object CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkTMs : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkTMs")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_GOT_COIN_CASE, CeladonCity_GameCorner_PrizeRoom_EventScript_NeedCoinCase
 * goto_if_questlog EventScript_ReleaseEnd
 * showcoinsbox 0, 0
 * msgbox CeladonCity_GameCorner_PrizeRoom_Text_WeExchangeCoinsForPrizes
 * goto CeladonCity_GameCorner_PrizeRoom_EventScript_ChoosePrizeItem
 * end
 * ```
 */
internal object CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkItems : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkItems")
}

internal val CeladonCity_GameCorner_PrizeRoomScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_GameCorner_PrizeRoom_EventScript_BaldingMan" to
            CeladonCity_GameCorner_PrizeRoom_EventScript_BaldingMan,
        "CeladonCity_GameCorner_PrizeRoom_EventScript_OldMan" to
            CeladonCity_GameCorner_PrizeRoom_EventScript_OldMan,
        "CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkMons" to
            CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkMons,
        "CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkTMs" to
            CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkTMs,
        "CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkItems" to
            CeladonCity_GameCorner_PrizeRoom_EventScript_PrizeClerkItems,
    )
