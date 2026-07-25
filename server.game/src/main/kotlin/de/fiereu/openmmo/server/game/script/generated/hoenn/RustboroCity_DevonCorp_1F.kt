package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_DevonCorp_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RETURNED_DEVON_GOODS, RustboroCity_DevonCorp_1F_EventScript_GoodsRecovered
 * goto_if_set FLAG_DEVON_GOODS_STOLEN, RustboroCity_DevonCorp_1F_EventScript_RobberWasntBright
 * msgbox RustboroCity_DevonCorp_1F_Text_ThoseShoesAreOurProduct, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_1F_EventScript_Employee : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_1F_EventScript_Employee")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RETURNED_DEVON_GOODS, RustboroCity_DevonCorp_1F_EventScript_AlwaysWelcome
 * goto_if_set FLAG_RECOVERED_DEVON_GOODS, RustboroCity_DevonCorp_1F_EventScript_GotRobbed
 * goto_if_set FLAG_DEVON_GOODS_STOLEN, RustboroCity_DevonCorp_1F_EventScript_GotRobbed
 * msgbox RustboroCity_DevonCorp_1F_Text_OnlyAuthorizedPeopleEnter, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_1F_EventScript_StairGuard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_1F_EventScript_StairGuard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RETURNED_DEVON_GOODS, RustboroCity_DevonCorp_1F_EventScript_WelcomeToDevonCorp
 * goto_if_set FLAG_RECOVERED_DEVON_GOODS, RustboroCity_DevonCorp_1F_EventScript_StaffGotRobbed
 * goto_if_set FLAG_DEVON_GOODS_STOLEN, RustboroCity_DevonCorp_1F_EventScript_StaffGotRobbed
 * msgbox RustboroCity_DevonCorp_1F_Text_WelcomeToDevonCorp, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_1F_EventScript_Greeter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_1F_EventScript_Greeter")
}

internal object RustboroCity_DevonCorp_1F_EventScript_ProductsDisplay : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RustboroCity_DevonCorp_1F.ProductDisplay)
}

internal object RustboroCity_DevonCorp_1F_EventScript_RocksMetalDisplay : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(RustboroCity_DevonCorp_1F.RocksMetalDisplay)
}

internal val RustboroCity_DevonCorp_1FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_DevonCorp_1F_EventScript_Employee" to
            RustboroCity_DevonCorp_1F_EventScript_Employee,
        "RustboroCity_DevonCorp_1F_EventScript_StairGuard" to
            RustboroCity_DevonCorp_1F_EventScript_StairGuard,
        "RustboroCity_DevonCorp_1F_EventScript_Greeter" to
            RustboroCity_DevonCorp_1F_EventScript_Greeter,
        "RustboroCity_DevonCorp_1F_EventScript_ProductsDisplay" to
            RustboroCity_DevonCorp_1F_EventScript_ProductsDisplay,
        "RustboroCity_DevonCorp_1F_EventScript_RocksMetalDisplay" to
            RustboroCity_DevonCorp_1F_EventScript_RocksMetalDisplay,
    )
