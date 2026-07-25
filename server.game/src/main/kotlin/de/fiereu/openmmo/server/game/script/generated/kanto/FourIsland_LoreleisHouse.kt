package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FourIsland_LoreleisHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TALKED_TO_LORELEI_AFTER_WAREHOUSE, FourIsland_LoreleisHouse_EventScript_Lorelei3
 * goto_if_set FLAG_DEFEATED_ROCKETS_IN_WAREHOUSE, FourIsland_LoreleisHouse_EventScript_Lorelei2
 * msgbox FourIsland_LoreleisHouse_Text_IfAnythingWereToHappenToIsland
 * release
 * end
 * ```
 */
internal object FourIsland_LoreleisHouse_EventScript_Lorelei : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FourIsland_LoreleisHouse_EventScript_Lorelei")
}

internal object FourIsland_LoreleisHouse_EventScript_Doll : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(FourIsland_LoreleisHouse.StuffedMonDollsGalore)
}

internal val FourIsland_LoreleisHouseScripts: Map<String, Script> =
    mapOf(
        "FourIsland_LoreleisHouse_EventScript_Lorelei" to
            FourIsland_LoreleisHouse_EventScript_Lorelei,
        "FourIsland_LoreleisHouse_EventScript_Doll" to FourIsland_LoreleisHouse_EventScript_Doll,
    )
