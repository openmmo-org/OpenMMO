package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.OneIsland_KindleRoad_EmberSpa
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object OneIsland_KindleRoad_EmberSpa_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_KindleRoad_EmberSpa.WaterWarmsMeToCore)
}

internal object OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_KindleRoad_EmberSpa.EnjoyBowlOfChowder)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BRUNO, 3
 * msgbox OneIsland_KindleRoad_EmberSpa_Text_BrunoVisitsSpaOnOccasion
 * release
 * end
 * ```
 */
internal object OneIsland_KindleRoad_EmberSpa_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port OneIsland_KindleRoad_EmberSpa_EventScript_BlackBelt")
}

internal object OneIsland_KindleRoad_EmberSpa_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_KindleRoad_EmberSpa.SeeHowSmoothMySkinIs)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_HM06, OneIsland_KindleRoad_EmberSpa_EventScript_AlreadyGotHM06
 * msgbox OneIsland_KindleRoad_EmberSpa_Text_UsedThisToMakeEmberSpa
 * giveitem ITEM_HM06
 * setflag FLAG_GOT_HM06
 * msgbox OneIsland_KindleRoad_EmberSpa_Text_ExplainHM06
 * release
 * end
 * ```
 */
internal object OneIsland_KindleRoad_EmberSpa_EventScript_RockSmashMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port OneIsland_KindleRoad_EmberSpa_EventScript_RockSmashMan")
}

internal object OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(OneIsland_KindleRoad_EmberSpa.HotSpringIsTherapeutic)
}

internal val OneIsland_KindleRoad_EmberSpaScripts: Map<String, Script> =
    mapOf(
        "OneIsland_KindleRoad_EmberSpa_EventScript_OldMan" to
            OneIsland_KindleRoad_EmberSpa_EventScript_OldMan,
        "OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan1" to
            OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan1,
        "OneIsland_KindleRoad_EmberSpa_EventScript_BlackBelt" to
            OneIsland_KindleRoad_EmberSpa_EventScript_BlackBelt,
        "OneIsland_KindleRoad_EmberSpa_EventScript_OldWoman" to
            OneIsland_KindleRoad_EmberSpa_EventScript_OldWoman,
        "OneIsland_KindleRoad_EmberSpa_EventScript_RockSmashMan" to
            OneIsland_KindleRoad_EmberSpa_EventScript_RockSmashMan,
        "OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan2" to
            OneIsland_KindleRoad_EmberSpa_EventScript_BaldingMan2,
    )
