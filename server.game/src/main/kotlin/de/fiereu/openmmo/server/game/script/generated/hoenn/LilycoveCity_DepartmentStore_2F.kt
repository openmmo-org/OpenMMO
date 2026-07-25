package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_DepartmentStore_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object LilycoveCity_DepartmentStore_2F_EventScript_Cook : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_DepartmentStore_2F.LearnToUseItemsProperly)
}

internal object LilycoveCity_DepartmentStore_2F_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_DepartmentStore_2F.GoodGiftForHusband)
}

internal object LilycoveCity_DepartmentStore_2F_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_DepartmentStore_2F.StockUpOnItems)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart LilycoveCity_DepartmentStore_2F_Pokemart2
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStore_2F_EventScript_ClerkRight : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStore_2F_EventScript_ClerkRight")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart LilycoveCity_DepartmentStore_2F_Pokemart1
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStore_2F_EventScript_ClerkLeft : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStore_2F_EventScript_ClerkLeft")
}

internal val LilycoveCity_DepartmentStore_2FScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_DepartmentStore_2F_EventScript_Cook" to
            LilycoveCity_DepartmentStore_2F_EventScript_Cook,
        "LilycoveCity_DepartmentStore_2F_EventScript_PokefanF" to
            LilycoveCity_DepartmentStore_2F_EventScript_PokefanF,
        "LilycoveCity_DepartmentStore_2F_EventScript_Sailor" to
            LilycoveCity_DepartmentStore_2F_EventScript_Sailor,
        "LilycoveCity_DepartmentStore_2F_EventScript_ClerkRight" to
            LilycoveCity_DepartmentStore_2F_EventScript_ClerkRight,
        "LilycoveCity_DepartmentStore_2F_EventScript_ClerkLeft" to
            LilycoveCity_DepartmentStore_2F_EventScript_ClerkLeft,
    )
