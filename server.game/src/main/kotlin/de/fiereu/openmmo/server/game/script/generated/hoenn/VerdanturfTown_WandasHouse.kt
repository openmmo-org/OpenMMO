package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.VerdanturfTown_WandasHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_WALLY_SPEECH, VerdanturfTown_WandasHouse_EventScript_WallyShortSpeech
 * msgbox VerdanturfTown_WandasHouse_Text_StrongerSpeech, MSGBOX_DEFAULT
 * setflag FLAG_WALLY_SPEECH
 * release
 * end
 * ```
 */
internal object VerdanturfTown_WandasHouse_EventScript_Wally : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_WandasHouse_EventScript_Wally")
}

internal object VerdanturfTown_WandasHouse_EventScript_WandasBoyfriend : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VerdanturfTown_WandasHouse.CanSeeGirlfriendEveryDay)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_WALLY_VICTORY_ROAD, VerdanturfTown_WandasHouse_EventScript_WallysUncleEverGrande
 * goto_if_set FLAG_DEFEATED_LAVARIDGE_GYM, VerdanturfTown_WandasHouse_EventScript_WallysUncleSlippedOff
 * msgbox VerdanturfTown_WandasHouse_Text_WallysNextDoor, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object VerdanturfTown_WandasHouse_EventScript_WallysUncle : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_WandasHouse_EventScript_WallysUncle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_WALLY_VICTORY_ROAD, VerdanturfTown_WandasHouse_EventScript_WallysAuntEverGrande
 * goto_if_set FLAG_DEFEATED_LAVARIDGE_GYM, VerdanturfTown_WandasHouse_EventScript_WallysAuntAnythingHappened
 * goto_if_set FLAG_RUSTURF_TUNNEL_OPENED, VerdanturfTown_WandasHouse_EventScript_WallysAuntTunnelOpen
 * msgbox VerdanturfTown_WandasHouse_Text_DaughtersBoyfriendDriven, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object VerdanturfTown_WandasHouse_EventScript_WallysAunt : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_WandasHouse_EventScript_WallysAunt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_LAVARIDGE_GYM, VerdanturfTown_WandasHouse_EventScript_WandaDontWorry
 * goto_if_set FLAG_DEFEATED_WALLY_MAUVILLE, VerdanturfTown_WandasHouse_EventScript_MeetWanda
 * msgbox VerdanturfTown_WandasHouse_Text_DontWorryAboutWally, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object VerdanturfTown_WandasHouse_EventScript_Wanda : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_WandasHouse_EventScript_Wanda")
}

internal val VerdanturfTown_WandasHouseScripts: Map<String, Script> =
    mapOf(
        "VerdanturfTown_WandasHouse_EventScript_Wally" to
            VerdanturfTown_WandasHouse_EventScript_Wally,
        "VerdanturfTown_WandasHouse_EventScript_WandasBoyfriend" to
            VerdanturfTown_WandasHouse_EventScript_WandasBoyfriend,
        "VerdanturfTown_WandasHouse_EventScript_WallysUncle" to
            VerdanturfTown_WandasHouse_EventScript_WallysUncle,
        "VerdanturfTown_WandasHouse_EventScript_WallysAunt" to
            VerdanturfTown_WandasHouse_EventScript_WallysAunt,
        "VerdanturfTown_WandasHouse_EventScript_Wanda" to
            VerdanturfTown_WandasHouse_EventScript_Wanda,
    )
