package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonMansion_B1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM22
 * end
 * ```
 */
internal object PokemonMansion_B1F_EventScript_ItemTM22 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonMansion_B1F_EventScript_ItemTM22")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BURGLAR_LEWIS, PokemonMansion_B1F_Text_LewisIntro, PokemonMansion_B1F_Text_LewisDefeat
 * msgbox PokemonMansion_B1F_Text_LewisPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonMansion_B1F_EventScript_Lewis : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_B1F_EventScript_Lewis")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_IVAN, PokemonMansion_B1F_Text_IvanIntro, PokemonMansion_B1F_Text_IvanDefeat
 * msgbox PokemonMansion_B1F_Text_IvanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonMansion_B1F_EventScript_Ivan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_B1F_EventScript_Ivan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM14
 * end
 * ```
 */
internal object PokemonMansion_B1F_EventScript_ItemTM14 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonMansion_B1F_EventScript_ItemTM14")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_RESTORE
 * end
 * ```
 */
internal object PokemonMansion_B1F_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonMansion_B1F_EventScript_ItemFullRestore")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_SECRET_KEY
 * end
 * ```
 */
internal object PokemonMansion_B1F_EventScript_ItemSecretKey : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonMansion_B1F_EventScript_ItemSecretKey")
}

internal object PokemonMansion_B1F_EventScript_DiarySep1st : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PokemonMansion_B1F.MewtwoIsFarTooPowerful)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 3
 * call PokemonMansion_EventScript_SecretSwitch
 * playse SE_UNLOCK
 * special DrawWholeMapView
 * waitse
 * releaseall
 * end
 * ```
 */
internal object PokemonMansion_B1F_EventScript_Statue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_B1F_EventScript_Statue")
}

internal val PokemonMansion_B1FScripts: Map<String, Script> =
    mapOf(
        "PokemonMansion_B1F_EventScript_ItemTM22" to PokemonMansion_B1F_EventScript_ItemTM22,
        "PokemonMansion_B1F_EventScript_Lewis" to PokemonMansion_B1F_EventScript_Lewis,
        "PokemonMansion_B1F_EventScript_Ivan" to PokemonMansion_B1F_EventScript_Ivan,
        "PokemonMansion_B1F_EventScript_ItemTM14" to PokemonMansion_B1F_EventScript_ItemTM14,
        "PokemonMansion_B1F_EventScript_ItemFullRestore" to
            PokemonMansion_B1F_EventScript_ItemFullRestore,
        "PokemonMansion_B1F_EventScript_ItemSecretKey" to
            PokemonMansion_B1F_EventScript_ItemSecretKey,
        "PokemonMansion_B1F_EventScript_DiarySep1st" to PokemonMansion_B1F_EventScript_DiarySep1st,
        "PokemonMansion_B1F_EventScript_Statue" to PokemonMansion_B1F_EventScript_Statue,
    )
