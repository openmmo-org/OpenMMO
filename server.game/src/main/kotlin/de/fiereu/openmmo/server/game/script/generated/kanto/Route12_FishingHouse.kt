package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_SUPER_ROD, Route12_FishingHouse_EventScript_CheckMagikarpRecord
 * msgbox Route12_FishingHouse_Text_DoYouLikeToFish, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, Route12_FishingHouse_EventScript_GiveSuperRod
 * msgbox Route12_FishingHouse_Text_OhThatsDisappointing
 * release
 * end
 * ```
 */
internal object Route12_FishingHouse_EventScript_FishingGuruBrother : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route12_FishingHouse_EventScript_FishingGuruBrother")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_GOT_RECORD_SETTING_MAGIKARP, Route12_FishingHouse_EventScript_MagikarpRecordSignRecordSet
 * msgbox Route12_FishingHouse_Text_BlankChartOfSomeSort
 * releaseall
 * end
 * ```
 */
internal object Route12_FishingHouse_EventScript_MagikarpRecordSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route12_FishingHouse_EventScript_MagikarpRecordSign")
}

internal val Route12_FishingHouseScripts: Map<String, Script> =
    mapOf(
        "Route12_FishingHouse_EventScript_FishingGuruBrother" to
            Route12_FishingHouse_EventScript_FishingGuruBrother,
        "Route12_FishingHouse_EventScript_MagikarpRecordSign" to
            Route12_FishingHouse_EventScript_MagikarpRecordSign,
    )
