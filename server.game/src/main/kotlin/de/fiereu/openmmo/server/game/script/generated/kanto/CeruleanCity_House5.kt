package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_POWDER_JAR, CeruleanCity_House5_EventScript_AskToExchangePowder
 * msgbox CeruleanCity_House1_Text_AnyInterestInBerries, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, CeruleanCity_House5_EventScript_NoInterestInBerries
 * goto_if_unset FLAG_SYS_GOT_BERRY_POUCH, CeruleanCity_House5_EventScript_NoBerries
 * msgbox CeruleanCity_House1_Text_HaveJustTheThing
 * setflag FLAG_GOT_POWDER_JAR
 * giveitem ITEM_POWDER_JAR
 * goto_if_eq VAR_RESULT, FALSE, EventScript_BagIsFull
 * msgbox CeruleanCity_House1_Text_GoCrushBerriesAtDirectCorner
 * release
 * end
 * ```
 */
internal object CeruleanCity_House5_EventScript_BerryPowderMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCity_House5_EventScript_BerryPowderMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_questlog EventScript_ReleaseEnd
 * special ShowBerryCrushRankings
 * waitstate
 * releaseall
 * end
 * ```
 */
internal object CeruleanCity_House5_EventScript_BerryCrushRankings : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCity_House5_EventScript_BerryCrushRankings")
}

internal val CeruleanCity_House5Scripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_House5_EventScript_BerryPowderMan" to
            CeruleanCity_House5_EventScript_BerryPowderMan,
        "CeruleanCity_House5_EventScript_BerryCrushRankings" to
            CeruleanCity_House5_EventScript_BerryCrushRankings,
    )
