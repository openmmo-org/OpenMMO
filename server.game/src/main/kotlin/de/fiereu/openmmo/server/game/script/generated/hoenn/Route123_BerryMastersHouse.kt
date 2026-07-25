package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * goto_if_set FLAG_DAILY_BERRY_MASTER_RECEIVED_BERRY, Route123_BerryMastersHouse_EventScript_ReceivedBerryToday
 * msgbox Route123_BerryMastersHouse_Text_YoureDeservingOfBerry, MSGBOX_DEFAULT
 * random NUM_BERRY_MASTER_BERRIES
 * addvar VAR_RESULT, NUM_BERRY_MASTER_BERRIES_SKIPPED
 * addvar VAR_RESULT, FIRST_BERRY_INDEX
 * giveitem VAR_RESULT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_DAILY_BERRY_MASTER_RECEIVED_BERRY
 * msgbox Route123_BerryMastersHouse_Text_WhyBeStingyTakeAnother, MSGBOX_DEFAULT
 * random NUM_BERRY_MASTER_BERRIES
 * addvar VAR_RESULT, NUM_BERRY_MASTER_BERRIES_SKIPPED
 * addvar VAR_RESULT, FIRST_BERRY_INDEX
 * giveitem VAR_RESULT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * msgbox Route123_BerryMastersHouse_Text_VisitPrettyPetalFlowerShop, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route123_BerryMastersHouse_EventScript_BerryMaster : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route123_BerryMastersHouse_EventScript_BerryMaster")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * goto_if_set FLAG_DAILY_BERRY_MASTERS_WIFE, Route123_BerryMastersHouse_EventScript_ReceivedWifeBerryToday
 * msgbox Route123_BerryMastersHouse_Text_HeardAGoodSayingLately, MSGBOX_DEFAULT
 * setvar VAR_0x8004, EASY_CHAT_TYPE_GOOD_SAYING
 * call Common_ShowEasyChatScreen
 * lock
 * faceplayer
 * goto_if_eq VAR_RESULT, TRUE, Route123_BerryMastersHouse_EventScript_GavePhrase
 * goto_if_eq VAR_RESULT, FALSE, Route123_BerryMastersHouse_EventScript_CancelPhrase
 * end
 * ```
 */
internal object Route123_BerryMastersHouse_EventScript_BerryMastersWife : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route123_BerryMastersHouse_EventScript_BerryMastersWife")
}

internal val Route123_BerryMastersHouseScripts: Map<String, Script> =
    mapOf(
        "Route123_BerryMastersHouse_EventScript_BerryMaster" to
            Route123_BerryMastersHouse_EventScript_BerryMaster,
        "Route123_BerryMastersHouse_EventScript_BerryMastersWife" to
            Route123_BerryMastersHouse_EventScript_BerryMastersWife,
    )
