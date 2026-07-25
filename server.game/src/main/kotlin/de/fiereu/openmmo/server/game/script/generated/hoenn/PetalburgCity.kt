package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.PetalburgCity
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PetalburgCity_EventScript_WallysMom : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity.WhereIsWally)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox PetalburgCity_Text_WaterReflection, MSGBOX_DEFAULT
 * closemessage
 * applymovement LOCALID_PETALBURG_BOY, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object PetalburgCity_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_EventScript_Boy")
}

internal object PetalburgCity_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity.FullPartyExplanation)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REVIVE
 * end
 * ```
 */
internal object PetalburgCity_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PetalburgCity_EventScript_ItemMaxRevive")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ETHER
 * end
 * ```
 */
internal object PetalburgCity_EventScript_ItemEther : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PetalburgCity_EventScript_ItemEther")
}

internal object PetalburgCity_EventScript_GymBoy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(PetalburgCity.AreYouRookieTrainer)
}

internal object PetalburgCity_EventScript_GymSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PetalburgCity.GymSign)
}

internal object PetalburgCity_EventScript_CitySign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PetalburgCity.CitySign)
}

internal object PetalburgCity_EventScript_WallyHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PetalburgCity.WallyHouseSign)
}

internal val PetalburgCityScripts: Map<String, Script> =
    mapOf(
        "PetalburgCity_EventScript_WallysMom" to PetalburgCity_EventScript_WallysMom,
        "PetalburgCity_EventScript_Boy" to PetalburgCity_EventScript_Boy,
        "PetalburgCity_EventScript_Gentleman" to PetalburgCity_EventScript_Gentleman,
        "PetalburgCity_EventScript_ItemMaxRevive" to PetalburgCity_EventScript_ItemMaxRevive,
        "PetalburgCity_EventScript_ItemEther" to PetalburgCity_EventScript_ItemEther,
        "PetalburgCity_EventScript_GymBoy" to PetalburgCity_EventScript_GymBoy,
        "PetalburgCity_EventScript_GymSign" to PetalburgCity_EventScript_GymSign,
        "PetalburgCity_EventScript_CitySign" to PetalburgCity_EventScript_CitySign,
        "PetalburgCity_EventScript_WallyHouseSign" to PetalburgCity_EventScript_WallyHouseSign,
    )
