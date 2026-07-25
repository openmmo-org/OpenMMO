package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox VerdanturfTown_FriendshipRatersHouse_Text_SeeHowMuchPokemonLikesYou, MSGBOX_DEFAULT
 * specialvar VAR_RESULT, GetLeadMonFriendshipScore
 * switch VAR_RESULT
 * case FRIENDSHIP_NONE, VerdanturfTown_FriendshipRatersHouse_EventScript_DetestsYou
 * case FRIENDSHIP_1_TO_49, VerdanturfTown_FriendshipRatersHouse_EventScript_VeryWary
 * case FRIENDSHIP_50_TO_99, VerdanturfTown_FriendshipRatersHouse_EventScript_NotUsedToYou
 * case FRIENDSHIP_100_TO_149, VerdanturfTown_FriendshipRatersHouse_EventScript_GettingUsedToYou
 * case FRIENDSHIP_150_TO_199, VerdanturfTown_FriendshipRatersHouse_EventScript_LikesYouQuiteALot
 * case FRIENDSHIP_200_TO_254, VerdanturfTown_FriendshipRatersHouse_EventScript_VeryHappy
 * case FRIENDSHIP_MAX, VerdanturfTown_FriendshipRatersHouse_EventScript_AdoresYou
 * release
 * end
 * ```
 */
internal object VerdanturfTown_FriendshipRatersHouse_EventScript_FriendshipRater : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_FriendshipRatersHouse_EventScript_FriendshipRater")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_PIKACHU, CRY_MODE_NORMAL
 * msgbox VerdanturfTown_FriendshipRatersHouse_Text_Pikachu, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object VerdanturfTown_FriendshipRatersHouse_EventScript_Pikachu : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VerdanturfTown_FriendshipRatersHouse_EventScript_Pikachu")
}

internal val VerdanturfTown_FriendshipRatersHouseScripts: Map<String, Script> =
    mapOf(
        "VerdanturfTown_FriendshipRatersHouse_EventScript_FriendshipRater" to
            VerdanturfTown_FriendshipRatersHouse_EventScript_FriendshipRater,
        "VerdanturfTown_FriendshipRatersHouse_EventScript_Pikachu" to
            VerdanturfTown_FriendshipRatersHouse_EventScript_Pikachu,
    )
