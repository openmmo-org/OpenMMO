package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.OneIsland_TreasureBeach
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SWIMMER_FEMALE_AMARA, OneIsland_TreasureBeach_Text_AmaraIntro, OneIsland_TreasureBeach_Text_AmaraDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, OneIsland_TreasureBeach_EventScript_AmaraRematch
 * msgbox OneIsland_TreasureBeach_Text_AmaraPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object OneIsland_TreasureBeach_EventScript_Amara : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port OneIsland_TreasureBeach_EventScript_Amara")
}

internal object OneIsland_TreasureBeach_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_TreasureBeach.GoodThingsWashUpOnBeach)
}

internal val OneIsland_TreasureBeachScripts: Map<String, Script> =
    mapOf(
        "OneIsland_TreasureBeach_EventScript_Amara" to OneIsland_TreasureBeach_EventScript_Amara,
        "OneIsland_TreasureBeach_EventScript_Boy" to OneIsland_TreasureBeach_EventScript_Boy,
    )
