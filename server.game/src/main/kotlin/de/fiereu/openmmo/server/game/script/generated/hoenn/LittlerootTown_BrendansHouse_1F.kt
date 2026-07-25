package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_LITTLEROOT_HOUSES_STATE_MAY, 4, PlayersHouse_1F_EventScript_DontPushYourselfTooHard
 * goto_if_eq VAR_LITTLEROOT_HOUSES_STATE_BRENDAN, 4, PlayersHouse_1F_EventScript_DontPushYourselfTooHard
 * goto_if_set FLAG_HAS_MATCH_CALL, PlayersHouse_1F_EventScript_TryRegisterMom
 * goto_if_set FLAG_RESCUED_BIRCH, PlayersHouse_1F_EventScript_MomHealsParty
 * goto_if_eq VAR_TEMP_1, 1, PlayersHouse_1F_EventScript_SeeYouHoney
 * goto_if_eq VAR_LITTLEROOT_INTRO_STATE, 7, PlayersHouse_1F_EventScript_DidYouMeetProfBirch
 * msgbox PlayersHouse_1F_Text_IsntItNiceInHere, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object PlayersHouse_1F_EventScript_Mom : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PlayersHouse_1F_EventScript_Mom")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_VIGOROTH, CRY_MODE_NORMAL
 * msgbox PlayersHouse_1F_Text_Vigoroth2, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object PlayersHouse_1F_EventScript_Vigoroth2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PlayersHouse_1F_EventScript_Vigoroth2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_VIGOROTH, CRY_MODE_NORMAL
 * msgbox PlayersHouse_1F_Text_Vigoroth1, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object PlayersHouse_1F_EventScript_Vigoroth1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PlayersHouse_1F_EventScript_Vigoroth1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_RIVAL_ROUTE103, RivalsHouse_1F_EventScript_GoHomeEverySoOften
 * goto_if_set FLAG_SYS_POKEMON_GET, RivalsHouse_1F_EventScript_RivalIsOnRoute103
 * goto_if_eq VAR_LITTLEROOT_RIVAL_STATE, 3, RivalsHouse_1F_EventScript_RivalTooBusy
 * special GetRivalSonDaughterString
 * msgbox RivalsHouse_1F_Text_LikeChildLikeFather, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RivalsHouse_1F_EventScript_RivalMom : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RivalsHouse_1F_EventScript_RivalMom")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * special GetPlayerBigGuyGirlString
 * msgbox RivalsHouse_1F_Text_DoYouHavePokemon, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RivalsHouse_1F_EventScript_RivalSibling : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RivalsHouse_1F_EventScript_RivalSibling")
}

internal val LittlerootTown_BrendansHouse_1FScripts: Map<String, Script> =
    mapOf(
        "PlayersHouse_1F_EventScript_Mom" to PlayersHouse_1F_EventScript_Mom,
        "PlayersHouse_1F_EventScript_Vigoroth2" to PlayersHouse_1F_EventScript_Vigoroth2,
        "PlayersHouse_1F_EventScript_Vigoroth1" to PlayersHouse_1F_EventScript_Vigoroth1,
        "RivalsHouse_1F_EventScript_RivalMom" to RivalsHouse_1F_EventScript_RivalMom,
        "RivalsHouse_1F_EventScript_RivalSibling" to RivalsHouse_1F_EventScript_RivalSibling,
    )
