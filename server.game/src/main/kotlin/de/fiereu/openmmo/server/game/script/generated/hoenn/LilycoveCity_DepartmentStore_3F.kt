package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_DepartmentStore_3F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object LilycoveCity_DepartmentStore_3F_EventScript_TriathleteM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_DepartmentStore_3F.ItemsBestForTougheningPokemon)
}

internal object LilycoveCity_DepartmentStore_3F_EventScript_PokefanM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_DepartmentStore_3F.WantMoreEndurance)
}

internal object LilycoveCity_DepartmentStore_3F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_DepartmentStore_3F.GaveCarbosToSpeedUpMon)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart LilycoveCity_DepartmentStore_3F_Pokemart_Vitamins
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStore_3F_EventScript_ClerkLeft : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStore_3F_EventScript_ClerkLeft")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart LilycoveCity_DepartmentStore_3F_Pokemart_StatBoosters
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStore_3F_EventScript_ClerkRight : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStore_3F_EventScript_ClerkRight")
}

internal val LilycoveCity_DepartmentStore_3FScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_DepartmentStore_3F_EventScript_TriathleteM" to
            LilycoveCity_DepartmentStore_3F_EventScript_TriathleteM,
        "LilycoveCity_DepartmentStore_3F_EventScript_PokefanM" to
            LilycoveCity_DepartmentStore_3F_EventScript_PokefanM,
        "LilycoveCity_DepartmentStore_3F_EventScript_Woman" to
            LilycoveCity_DepartmentStore_3F_EventScript_Woman,
        "LilycoveCity_DepartmentStore_3F_EventScript_ClerkLeft" to
            LilycoveCity_DepartmentStore_3F_EventScript_ClerkLeft,
        "LilycoveCity_DepartmentStore_3F_EventScript_ClerkRight" to
            LilycoveCity_DepartmentStore_3F_EventScript_ClerkRight,
    )
