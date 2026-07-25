package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_BEAT_RIVAL_IN_OAKS_LAB, PalletTown_PlayersHouse_1F_EventScript_MomHeal
 * checkplayergender
 * call_if_eq VAR_RESULT, MALE, PalletTown_PlayersHouse_1F_EventScript_MomOakLookingForYouMale
 * call_if_eq VAR_RESULT, FEMALE, PalletTown_PlayersHouse_1F_EventScript_MomOakLookingForYouFemale
 * closemessage
 * applymovement LOCALID_MOM, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object PalletTown_PlayersHouse_1F_EventScript_Mom : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_PlayersHouse_1F_EventScript_Mom")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_FACING, DIR_NORTH, PalletTown_PlayersHouse_1F_EventScript_TVScreen
 * msgbox PalletTown_PlayersHouse_1F_Text_OopsWrongSide
 * releaseall
 * end
 * ```
 */
internal object PalletTown_PlayersHouse_1F_EventScript_TV : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_PlayersHouse_1F_EventScript_TV")
}

internal val PalletTown_PlayersHouse_1FScripts: Map<String, Script> =
    mapOf(
        "PalletTown_PlayersHouse_1F_EventScript_Mom" to PalletTown_PlayersHouse_1F_EventScript_Mom,
        "PalletTown_PlayersHouse_1F_EventScript_TV" to PalletTown_PlayersHouse_1F_EventScript_TV,
    )
