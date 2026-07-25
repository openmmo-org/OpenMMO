package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VermilionCity_PokemonFanClub
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_BIKE_VOUCHER, VermilionCity_PokemonFanClub_EventScript_AlreadyHeardStory
 * msgbox VermilionCity_PokemonFanClub_Text_DidYouComeToHearAboutMyMons, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, VermilionCity_PokemonFanClub_EventScript_ChairmanStory
 * msgbox VermilionCity_PokemonFanClub_Text_ComeBackToHearStory
 * release
 * end
 * ```
 */
internal object VermilionCity_PokemonFanClub_EventScript_Chairman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonFanClub_EventScript_Chairman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, VermilionCity_PokemonFanClub_EventScript_WorkerFGameClear
 * msgbox VermilionCity_PokemonFanClub_Text_ChairmanVeryVocalAboutPokemon
 * release
 * end
 * ```
 */
internal object VermilionCity_PokemonFanClub_EventScript_WorkerF : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonFanClub_EventScript_WorkerF")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_PIKACHU, CRY_MODE_NORMAL
 * msgbox VermilionCity_PokemonFanClub_Text_Pikachu
 * waitmoncry
 * release
 * end
 * ```
 */
internal object VermilionCity_PokemonFanClub_EventScript_Pikachu : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonFanClub_EventScript_Pikachu")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_SEEL, CRY_MODE_NORMAL
 * msgbox VermilionCity_PokemonFanClub_Text_Seel
 * waitmoncry
 * release
 * end
 * ```
 */
internal object VermilionCity_PokemonFanClub_EventScript_Seel : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonFanClub_EventScript_Seel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set SPOKE_TO_FAT_MAN_LAST, VermilionCity_PokemonFanClub_EventScript_WomanSpokeToFatMan
 * msgbox VermilionCity_PokemonFanClub_Text_AdoreMySeel
 * closemessage
 * applymovement LOCALID_POKEMON_FAN_CLUB_WOMAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * setflag SPOKE_TO_WOMAN_LAST
 * release
 * end
 * ```
 */
internal object VermilionCity_PokemonFanClub_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonFanClub_EventScript_Woman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set SPOKE_TO_WOMAN_LAST, VermilionCity_PokemonFanClub_EventScript_FatManSpokeToWoman
 * msgbox VermilionCity_PokemonFanClub_Text_AdmirePikachusTail
 * closemessage
 * applymovement LOCALID_POKEMON_FAN_CLUB_FAT_MAN, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * setflag SPOKE_TO_FAT_MAN_LAST
 * release
 * end
 * ```
 */
internal object VermilionCity_PokemonFanClub_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonFanClub_EventScript_FatMan")
}

internal object VermilionCity_PokemonFanClub_EventScript_RulesSign1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(VermilionCity_PokemonFanClub.ListenPolitelyToOtherTrainers)
}

internal object VermilionCity_PokemonFanClub_EventScript_RulesSign2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(VermilionCity_PokemonFanClub.SomeoneBragsBragBack)
}

internal val VermilionCity_PokemonFanClubScripts: Map<String, Script> =
    mapOf(
        "VermilionCity_PokemonFanClub_EventScript_Chairman" to
            VermilionCity_PokemonFanClub_EventScript_Chairman,
        "VermilionCity_PokemonFanClub_EventScript_WorkerF" to
            VermilionCity_PokemonFanClub_EventScript_WorkerF,
        "VermilionCity_PokemonFanClub_EventScript_Pikachu" to
            VermilionCity_PokemonFanClub_EventScript_Pikachu,
        "VermilionCity_PokemonFanClub_EventScript_Seel" to
            VermilionCity_PokemonFanClub_EventScript_Seel,
        "VermilionCity_PokemonFanClub_EventScript_Woman" to
            VermilionCity_PokemonFanClub_EventScript_Woman,
        "VermilionCity_PokemonFanClub_EventScript_FatMan" to
            VermilionCity_PokemonFanClub_EventScript_FatMan,
        "VermilionCity_PokemonFanClub_EventScript_RulesSign1" to
            VermilionCity_PokemonFanClub_EventScript_RulesSign1,
        "VermilionCity_PokemonFanClub_EventScript_RulesSign2" to
            VermilionCity_PokemonFanClub_EventScript_RulesSign2,
    )
