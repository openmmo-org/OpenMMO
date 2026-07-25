package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart BattleFrontier_Mart_Pokemart
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object BattleFrontier_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port BattleFrontier_Mart_EventScript_Clerk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * applymovement LOCALID_FRONTIER_MART_OLD_WOMAN, Common_Movement_FaceDown
 * waitmovement 0
 * msgbox BattleFrontier_Mart_Text_ProteinMakeNiceGift, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object BattleFrontier_Mart_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_Mart_EventScript_OldWoman")
}

internal object BattleFrontier_Mart_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BattleFrontier_Mart.ChaperonGrandson)
}

internal object BattleFrontier_Mart_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Mart.FacilitiesDontAllowItems)
}

internal val BattleFrontier_MartScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_Mart_EventScript_Clerk" to BattleFrontier_Mart_EventScript_Clerk,
        "BattleFrontier_Mart_EventScript_OldWoman" to BattleFrontier_Mart_EventScript_OldWoman,
        "BattleFrontier_Mart_EventScript_OldMan" to BattleFrontier_Mart_EventScript_OldMan,
        "BattleFrontier_Mart_EventScript_Boy" to BattleFrontier_Mart_EventScript_Boy,
    )
