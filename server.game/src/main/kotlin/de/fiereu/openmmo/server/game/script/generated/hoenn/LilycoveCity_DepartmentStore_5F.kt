package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_DepartmentStore_5F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object LilycoveCity_DepartmentStore_5F_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_DepartmentStore_5F.GettingDollInsteadOfPokemon)
}

internal object LilycoveCity_DepartmentStore_5F_EventScript_PokefanF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_DepartmentStore_5F.PlaceFullOfCuteDolls)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemartdecoration2 LilycoveCity_DepartmentStore_5F_Pokemart_Dolls
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStore_5F_EventScript_ClerkFarLeft : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStore_5F_EventScript_ClerkFarLeft")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemartdecoration2 LilycoveCity_DepartmentStore_5F_Pokemart_Cushions
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStore_5F_EventScript_ClerkMidLeft : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStore_5F_EventScript_ClerkMidLeft")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemartdecoration2 LilycoveCity_DepartmentStore_5F_Pokemart_Posters
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStore_5F_EventScript_ClerkMidRight : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStore_5F_EventScript_ClerkMidRight")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemartdecoration2 LilycoveCity_DepartmentStore_5F_Pokemart_Mats
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStore_5F_EventScript_ClerkFarRight : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStore_5F_EventScript_ClerkFarRight")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * applymovement LOCALID_DEPARTMENT_STORE_STAIRS_WOMAN, Common_Movement_FacePlayer
 * waitmovement 0
 * goto_if_eq VAR_SOOTOPOLIS_CITY_STATE, 0, LilycoveCity_DepartmentStore_5F_EventScript_WomanNormal
 * goto_if_ge VAR_SOOTOPOLIS_CITY_STATE, 4, LilycoveCity_DepartmentStore_5F_EventScript_WomanNormal
 * goto LilycoveCity_DepartmentStore_5F_EventScript_WomanLegendaryWeather
 * end
 * ```
 */
internal object LilycoveCity_DepartmentStore_5F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_DepartmentStore_5F_EventScript_Woman")
}

internal val LilycoveCity_DepartmentStore_5FScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_DepartmentStore_5F_EventScript_LittleGirl" to
            LilycoveCity_DepartmentStore_5F_EventScript_LittleGirl,
        "LilycoveCity_DepartmentStore_5F_EventScript_PokefanF" to
            LilycoveCity_DepartmentStore_5F_EventScript_PokefanF,
        "LilycoveCity_DepartmentStore_5F_EventScript_ClerkFarLeft" to
            LilycoveCity_DepartmentStore_5F_EventScript_ClerkFarLeft,
        "LilycoveCity_DepartmentStore_5F_EventScript_ClerkMidLeft" to
            LilycoveCity_DepartmentStore_5F_EventScript_ClerkMidLeft,
        "LilycoveCity_DepartmentStore_5F_EventScript_ClerkMidRight" to
            LilycoveCity_DepartmentStore_5F_EventScript_ClerkMidRight,
        "LilycoveCity_DepartmentStore_5F_EventScript_ClerkFarRight" to
            LilycoveCity_DepartmentStore_5F_EventScript_ClerkFarRight,
        "LilycoveCity_DepartmentStore_5F_EventScript_Woman" to
            LilycoveCity_DepartmentStore_5F_EventScript_Woman,
    )
