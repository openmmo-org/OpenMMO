package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PalletTown_RivalsHouse
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_RIGHT
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val LOCALID_DAISY = 0
private const val LOCALID_TOWN_MAP = 1

internal object PalletTown_RivalsHouse_OnTransition : Script {
  override suspend fun run(ctx: ScriptContext) {
    // Daisy sits at the table until the town map is handed over.
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_RIVALS_HOUSE) < 2) {
      ctx.repositionNpc(LOCALID_DAISY, 5, 4)
    }
  }
}

internal object PalletTown_RivalsHouse_EventScript_Daisy : Script {
  override suspend fun run(ctx: ScriptContext) {
    when (ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_RIVALS_HOUSE)) {
      2 -> return ctx.say(PalletTown_RivalsHouse.ExplainTownMap)
      1 -> return giveTownMap(ctx)
    }
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB) >= 1) {
      return ctx.say(PalletTown_RivalsHouse.HeardYouBattledRival)
    }
    ctx.say(PalletTown_RivalsHouse.HiBrothersAtLab)
  }
}

/** Oak's errand is what makes Daisy hand over her brother's town map. */
private suspend fun giveTownMap(ctx: ScriptContext) {
  ctx.say(PalletTown_RivalsHouse.ErrandForGrandpaThisWillHelp)
  ctx.moveNpc(LOCALID_DAISY, FACE_RIGHT)
  ctx.removeNpc(LOCALID_TOWN_MAP)
  ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_RIVALS_HOUSE, 2)
  if (!ctx.giveItem(Items.TOWN_MAP)) {
    return ctx.say(PalletTown_RivalsHouse.DontHaveSpaceForThis)
  }
  ctx.sign(PalletTown_RivalsHouse.ReceivedTownMapFromDaisy)
}

internal object PalletTown_RivalsHouse_EventScript_TownMap : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PalletTown_RivalsHouse.ItsBigMapOfKanto)
}

internal object PalletTown_RivalsHouse_EventScript_Bookshelf : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(PalletTown_RivalsHouse.ShelvesCrammedFullOfBooks)
}

internal object PalletTown_RivalsHouse_EventScript_Picture : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(PalletTown_RivalsHouse.LovelyAndSweetClefairy)
}

internal val PalletTown_RivalsHouseScripts: Map<String, Script> =
    mapOf(
        "PalletTown_RivalsHouse_OnTransition" to PalletTown_RivalsHouse_OnTransition,
        "PalletTown_RivalsHouse_EventScript_Daisy" to PalletTown_RivalsHouse_EventScript_Daisy,
        "PalletTown_RivalsHouse_EventScript_TownMap" to PalletTown_RivalsHouse_EventScript_TownMap,
        "PalletTown_RivalsHouse_EventScript_Bookshelf" to
            PalletTown_RivalsHouse_EventScript_Bookshelf,
        "PalletTown_RivalsHouse_EventScript_Picture" to PalletTown_RivalsHouse_EventScript_Picture,
    )
