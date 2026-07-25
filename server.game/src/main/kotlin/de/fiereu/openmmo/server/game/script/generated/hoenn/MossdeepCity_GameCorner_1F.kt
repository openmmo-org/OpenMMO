package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RS_MysteryEventsHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto MossdeepCity_GameCorner_1F_EventScript_OldMan2
 * release
 * end
 * ```
 */
internal object MossdeepCity_GameCorner_1F_EventScript_OldMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_GameCorner_1F_EventScript_OldMan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto MossdeepCity_GameCorner_1F_EventScript_InfoMan2
 * release
 * end
 * ```
 */
internal object MossdeepCity_GameCorner_1F_EventScript_InfoMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_GameCorner_1F_EventScript_InfoMan")
}

internal object RS_MysteryEventsHouse_EventScript_Door : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(RS_MysteryEventsHouse.DoorIsLocked)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * special ShowDodrioBerryPickingRecords
 * releaseall
 * end
 * ```
 */
internal object MossdeepCity_GameCorner_1F_EventScript_DodrioBerryPickingRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_GameCorner_1F_EventScript_DodrioBerryPickingRecords")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * special ShowPokemonJumpRecords
 * releaseall
 * end
 * ```
 */
internal object MossdeepCity_GameCorner_1F_EventScript_PokemonJumpRecords : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_GameCorner_1F_EventScript_PokemonJumpRecords")
}

internal val MossdeepCity_GameCorner_1FScripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_GameCorner_1F_EventScript_OldMan" to
            MossdeepCity_GameCorner_1F_EventScript_OldMan,
        "MossdeepCity_GameCorner_1F_EventScript_InfoMan" to
            MossdeepCity_GameCorner_1F_EventScript_InfoMan,
        "RS_MysteryEventsHouse_EventScript_Door" to RS_MysteryEventsHouse_EventScript_Door,
        "MossdeepCity_GameCorner_1F_EventScript_DodrioBerryPickingRecords" to
            MossdeepCity_GameCorner_1F_EventScript_DodrioBerryPickingRecords,
        "MossdeepCity_GameCorner_1F_EventScript_PokemonJumpRecords" to
            MossdeepCity_GameCorner_1F_EventScript_PokemonJumpRecords,
    )
