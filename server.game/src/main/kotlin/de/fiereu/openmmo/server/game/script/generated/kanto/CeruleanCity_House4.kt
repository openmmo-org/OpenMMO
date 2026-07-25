package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * specialvar REWARD_TYPE, WonderNews_GetRewardInfo
 * copyvar REWARD_ITEM, VAR_RESULT
 * goto_if_eq REWARD_TYPE, NEWS_REWARD_NONE,       CeruleanCity_House4_EventScript_NoNews
 * goto_if_eq REWARD_TYPE, NEWS_REWARD_RECV_SMALL, CeruleanCity_House4_EventScript_Reward_RecvSmall
 * goto_if_eq REWARD_TYPE, NEWS_REWARD_RECV_BIG,   CeruleanCity_House4_EventScript_Reward_RecvBig
 * goto_if_eq REWARD_TYPE, NEWS_REWARD_WAITING,    CeruleanCity_House4_EventScript_Waiting
 * goto_if_eq REWARD_TYPE, NEWS_REWARD_SENT_SMALL, CeruleanCity_House4_EventScript_Reward_SentSmall
 * goto_if_eq REWARD_TYPE, NEWS_REWARD_SENT_BIG,   CeruleanCity_House4_EventScript_Reward_SentBig
 * goto_if_eq REWARD_TYPE, NEWS_REWARD_AT_MAX,     CeruleanCity_House4_EventScript_AtMax
 * end
 * ```
 */
internal object CeruleanCity_House4_EventScript_WonderNewsBerryMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCity_House4_EventScript_WonderNewsBerryMan")
}

internal val CeruleanCity_House4Scripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_House4_EventScript_WonderNewsBerryMan" to
            CeruleanCity_House4_EventScript_WonderNewsBerryMan,
    )
