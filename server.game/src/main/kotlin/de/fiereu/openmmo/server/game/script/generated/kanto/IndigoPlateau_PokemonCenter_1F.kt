package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.IndigoPlateau_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart IndigoPlateau_PokemonCenter_1F_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object IndigoPlateau_PokemonCenter_1F_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port IndigoPlateau_PokemonCenter_1F_EventScript_Clerk")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call EventScript_PkmnCenterNurse
 * release
 * end
 * ```
 */
internal object IndigoPlateau_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port IndigoPlateau_PokemonCenter_1F_EventScript_Nurse")
}

internal object IndigoPlateau_PokemonCenter_1F_EventScript_GymGuy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(IndigoPlateau_PokemonCenter_1F.GymGuyAdvice)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * specialvar VAR_RESULT, IsNationalPokedexEnabled
 * goto_if_eq VAR_RESULT, TRUE, IndigoPlateau_PokemonCenter_1F_EventScript_CheckSeviiIslandComplete
 * msgbox IndigoPlateau_PokemonCenter_1F_Text_FaceEliteFourGoodLuck
 * release
 * end
 * ```
 */
internal object IndigoPlateau_PokemonCenter_1F_EventScript_DoorGuard : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port IndigoPlateau_PokemonCenter_1F_EventScript_DoorGuard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_AGATHA, 1
 * famechecker FAMECHECKER_AGATHA, 2
 * msgbox IndigoPlateau_PokemonCenter_1F_Text_AgathaWhuppedUs
 * release
 * end
 * ```
 */
internal object IndigoPlateau_PokemonCenter_1F_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port IndigoPlateau_PokemonCenter_1F_EventScript_BlackBelt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_LANCE, 4
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureLance
 * release
 * end
 * ```
 */
internal object IndigoPlateau_PokemonCenter_1F_EventScript_PokemonJournal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port IndigoPlateau_PokemonCenter_1F_EventScript_PokemonJournal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_LANCE, 5
 * msgbox IndigoPlateau_PokemonCenter_1F_Text_LancesCousinGymLeaderFarAway
 * release
 * end
 * ```
 */
internal object IndigoPlateau_PokemonCenter_1F_EventScript_CooltrainerM : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port IndigoPlateau_PokemonCenter_1F_EventScript_CooltrainerM")
}

internal val IndigoPlateau_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "IndigoPlateau_PokemonCenter_1F_EventScript_Clerk" to
            IndigoPlateau_PokemonCenter_1F_EventScript_Clerk,
        "IndigoPlateau_PokemonCenter_1F_EventScript_Nurse" to
            IndigoPlateau_PokemonCenter_1F_EventScript_Nurse,
        "IndigoPlateau_PokemonCenter_1F_EventScript_GymGuy" to
            IndigoPlateau_PokemonCenter_1F_EventScript_GymGuy,
        "IndigoPlateau_PokemonCenter_1F_EventScript_DoorGuard" to
            IndigoPlateau_PokemonCenter_1F_EventScript_DoorGuard,
        "IndigoPlateau_PokemonCenter_1F_EventScript_BlackBelt" to
            IndigoPlateau_PokemonCenter_1F_EventScript_BlackBelt,
        "IndigoPlateau_PokemonCenter_1F_EventScript_PokemonJournal" to
            IndigoPlateau_PokemonCenter_1F_EventScript_PokemonJournal,
        "IndigoPlateau_PokemonCenter_1F_EventScript_CooltrainerM" to
            IndigoPlateau_PokemonCenter_1F_EventScript_CooltrainerM,
    )
