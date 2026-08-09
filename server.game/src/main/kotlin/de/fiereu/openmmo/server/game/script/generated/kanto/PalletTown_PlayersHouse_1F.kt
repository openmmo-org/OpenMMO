package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.dialog.generated.kanto.PalletTown_PlayersHouse_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoFlags

internal object PalletTown_PlayersHouse_1F_EventScript_Mom : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.isFlagSet(KantoFlags.FLAG_BEAT_RIVAL_IN_OAKS_LAB)) {
      ctx.say(PalletTown_PlayersHouse_1F.YouShouldTakeQuickRest)
      ctx.healParty()
      ctx.say(PalletTown_PlayersHouse_1F.LookingGreatTakeCare)
      return
    }
    if (ctx.isFemale) {
      ctx.say(PalletTown_PlayersHouse_1F.AllGirlsLeaveOakLookingForYou)
    } else {
      ctx.say(PalletTown_PlayersHouse_1F.AllBoysLeaveOakLookingForYou)
    }
  }
}

internal object PalletTown_PlayersHouse_1F_EventScript_TV : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.facingDirection != Direction.UP) {
      return ctx.sign(PalletTown_PlayersHouse_1F.OopsWrongSide)
    }
    if (ctx.isFemale) {
      ctx.sign(PalletTown_PlayersHouse_1F.MovieOnTVGirlOnBrickRoad)
    } else {
      ctx.sign(PalletTown_PlayersHouse_1F.MovieOnTVFourBoysOnRailroad)
    }
  }
}

internal val PalletTown_PlayersHouse_1FScripts: Map<String, Script> =
    mapOf(
        "PalletTown_PlayersHouse_1F_EventScript_Mom" to PalletTown_PlayersHouse_1F_EventScript_Mom,
        "PalletTown_PlayersHouse_1F_EventScript_TV" to PalletTown_PlayersHouse_1F_EventScript_TV,
    )
