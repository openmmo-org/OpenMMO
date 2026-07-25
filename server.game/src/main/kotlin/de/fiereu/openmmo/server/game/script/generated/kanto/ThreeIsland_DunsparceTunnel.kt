package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_NUGGET_FROM_DUNSPARCE_TUNNEL, ThreeIsland_DunsparceTunnel_EventScript_ProspectorAlreadyGaveNugget
 * specialvar VAR_RESULT, IsNationalPokedexEnabled
 * goto_if_eq VAR_RESULT, TRUE, ThreeIsland_DunsparceTunnel_EventScript_ProspectorStruckGold
 * msgbox ThreeIsland_DunsparceTunnel_Text_ProspectingForGold
 * closemessage
 * applymovement LOCALID_PROSPECTOR, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object ThreeIsland_DunsparceTunnel_EventScript_Prospector : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ThreeIsland_DunsparceTunnel_EventScript_Prospector")
}

internal val ThreeIsland_DunsparceTunnelScripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_DunsparceTunnel_EventScript_Prospector" to
            ThreeIsland_DunsparceTunnel_EventScript_Prospector,
    )
