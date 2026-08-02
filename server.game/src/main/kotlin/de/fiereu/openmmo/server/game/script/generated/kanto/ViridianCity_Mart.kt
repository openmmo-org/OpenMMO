package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ViridianCity_Mart
import de.fiereu.openmmo.items.generated.Items
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_DOWN
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_LEFT
import de.fiereu.openmmo.server.game.script.MovementStep.FACE_RIGHT
import de.fiereu.openmmo.server.game.script.MovementStep.WALK_UP
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext
import de.fiereu.openmmo.story.generated.kanto.KantoVars

private const val LOCALID_MART_CLERK = 0

/** The clerk calls the player over the moment they walk in, and hands them Oak's parcel. */
internal object ViridianCity_Mart_EventScript_ParcelScene : Script {
  override suspend fun run(ctx: ScriptContext) {
    ctx.moveNpc(LOCALID_MART_CLERK, FACE_DOWN)
    ctx.sayNpc(LOCALID_MART_CLERK, ViridianCity_Mart.YouCameFromPallet)
    ctx.moveSelfAndNpcs(
        listOf(WALK_UP, WALK_UP, WALK_UP, WALK_UP, FACE_LEFT),
        LOCALID_MART_CLERK to listOf(FACE_RIGHT),
    )
    ctx.sayNpc(LOCALID_MART_CLERK, ViridianCity_Mart.TakeThisToProfOak)
    ctx.setVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_MART, 1)
    ctx.giveItem(Items.OAKS_PARCEL)
    ctx.sign(ViridianCity_Mart.ReceivedOaksParcelFromClerk)
    ctx.setVar(KantoVars.VAR_MAP_SCENE_PALLET_TOWN_PROFESSOR_OAKS_LAB, 5)
  }
}

internal object ViridianCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) {
    if (ctx.getVar(KantoVars.VAR_MAP_SCENE_VIRIDIAN_CITY_MART) == 1) {
      return ctx.say(ViridianCity_Mart.SayHiToOakForMe)
    }
    // TODO Open the mart shop
    //  The decomp runs pokemart with the shop's item list. Buying and selling needs a shop verb.
  }
}

internal object ViridianCity_Mart_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianCity_Mart.GotToBuySomePotions)
}

internal object ViridianCity_Mart_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(ViridianCity_Mart.ShopDoesGoodBusinessInAntidotes)
}

internal val ViridianCity_MartScripts: Map<String, Script> =
    mapOf(
        "ViridianCity_Mart_EventScript_ParcelScene" to ViridianCity_Mart_EventScript_ParcelScene,
        "ViridianCity_Mart_EventScript_Clerk" to ViridianCity_Mart_EventScript_Clerk,
        "ViridianCity_Mart_EventScript_Youngster" to ViridianCity_Mart_EventScript_Youngster,
        "ViridianCity_Mart_EventScript_Woman" to ViridianCity_Mart_EventScript_Woman,
    )
