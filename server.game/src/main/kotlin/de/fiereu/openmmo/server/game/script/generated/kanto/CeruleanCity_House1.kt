package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox CeruleanCity_House1_Text_BadgesHaveAmazingSecrets
 * message CeruleanCity_House1_Text_DescribeWhichBadge
 * waitmessage
 * setvar VAR_0x8004, LISTMENU_BADGES
 * special ListMenu
 * waitstate
 * switch VAR_RESULT
 * case 0, CeruleanCity_House1_EventScript_DescribeBoulderBadge
 * case 1, CeruleanCity_House1_EventScript_DescribeCascadeBadge
 * case 2, CeruleanCity_House1_EventScript_DescribeThunderBadge
 * case 3, CeruleanCity_House1_EventScript_DescribeRainbowBadge
 * case 4, CeruleanCity_House1_EventScript_DescribeSoulBadge
 * case 5, CeruleanCity_House1_EventScript_DescribeMarshBadge
 * case 6, CeruleanCity_House1_EventScript_DescribeVolcanoBadge
 * case 7, CeruleanCity_House1_EventScript_DescribeEarthBadge
 * case 8, CeruleanCity_House1_EventScript_StopDescribingBadges
 * case 127, CeruleanCity_House1_EventScript_StopDescribingBadges
 * end
 * ```
 */
internal object CeruleanCity_House1_EventScript_BadgeGuy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCity_House1_EventScript_BadgeGuy")
}

internal val CeruleanCity_House1Scripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_House1_EventScript_BadgeGuy" to CeruleanCity_House1_EventScript_BadgeGuy,
    )
