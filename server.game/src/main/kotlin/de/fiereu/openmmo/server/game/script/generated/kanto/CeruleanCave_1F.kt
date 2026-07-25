package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object CeruleanCave_1F_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCave_1F_EventScript_ItemNugget")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_RESTORE
 * end
 * ```
 */
internal object CeruleanCave_1F_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCave_1F_EventScript_ItemFullRestore")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ELIXIR
 * end
 * ```
 */
internal object CeruleanCave_1F_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeruleanCave_1F_EventScript_ItemMaxElixir")
}

internal val CeruleanCave_1FScripts: Map<String, Script> =
    mapOf(
        "CeruleanCave_1F_EventScript_ItemNugget" to CeruleanCave_1F_EventScript_ItemNugget,
        "CeruleanCave_1F_EventScript_ItemFullRestore" to
            CeruleanCave_1F_EventScript_ItemFullRestore,
        "CeruleanCave_1F_EventScript_ItemMaxElixir" to CeruleanCave_1F_EventScript_ItemMaxElixir,
    )
