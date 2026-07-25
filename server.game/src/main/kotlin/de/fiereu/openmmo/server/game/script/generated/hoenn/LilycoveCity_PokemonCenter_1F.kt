package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * setvar VAR_0x800B, LOCALID_LILYCOVE_NURSE
 * call Common_EventScript_PkmnCenterNurse
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonCenter_1F_EventScript_Nurse")
}

internal object LilycoveCity_PokemonCenter_1F_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_PokemonCenter_1F.HowManyKindsOfPokemon)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_BADGE07_GET, LilycoveCity_PokemonCenter_1F_EventScript_ManiacBadTeamGone
 * msgbox LilycoveCity_PokemonCenter_1F_Text_HeardAboutRottenScoundrels, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object LilycoveCity_PokemonCenter_1F_EventScript_Maniac : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonCenter_1F_EventScript_Maniac")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * special Script_GetLilycoveLadyId
 * switch VAR_RESULT
 * case LILYCOVE_LADY_QUIZ, LilycoveCity_PokemonCenter_1F_EventScript_QuizLady
 * case LILYCOVE_LADY_FAVOR, LilycoveCity_PokemonCenter_1F_EventScript_FavorLady
 * case LILYCOVE_LADY_CONTEST, LilycoveCity_PokemonCenter_1F_EventScript_ContestLady
 * end
 * ```
 */
internal object LilycoveCity_PokemonCenter_1F_EventScript_LilycoveLady : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonCenter_1F_EventScript_LilycoveLady")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * specialvar VAR_RESULT, GetContestLadyCategory
 * special Script_BufferContestLadyCategoryAndMonName
 * special GetContestLadyMonSpecies
 * goto_if_eq VAR_RESULT, CONTEST_CATEGORY_COOL, LilycoveCity_PokemonCenter_1F_EventScript_Zigzagoon
 * goto_if_eq VAR_RESULT, CONTEST_CATEGORY_BEAUTY, LilycoveCity_PokemonCenter_1F_EventScript_Skitty
 * goto_if_eq VAR_RESULT, CONTEST_CATEGORY_CUTE, LilycoveCity_PokemonCenter_1F_EventScript_Poochyena
 * goto_if_eq VAR_RESULT, CONTEST_CATEGORY_SMART, LilycoveCity_PokemonCenter_1F_EventScript_Kecleon
 * goto_if_eq VAR_RESULT, CONTEST_CATEGORY_TOUGH, LilycoveCity_PokemonCenter_1F_EventScript_Pikachu
 * end
 * ```
 */
internal object LilycoveCity_PokemonCenter_1F_EventScript_ContestLadyMon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_PokemonCenter_1F_EventScript_ContestLadyMon")
}

internal val LilycoveCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_PokemonCenter_1F_EventScript_Nurse" to
            LilycoveCity_PokemonCenter_1F_EventScript_Nurse,
        "LilycoveCity_PokemonCenter_1F_EventScript_Boy" to
            LilycoveCity_PokemonCenter_1F_EventScript_Boy,
        "LilycoveCity_PokemonCenter_1F_EventScript_Maniac" to
            LilycoveCity_PokemonCenter_1F_EventScript_Maniac,
        "LilycoveCity_PokemonCenter_1F_EventScript_LilycoveLady" to
            LilycoveCity_PokemonCenter_1F_EventScript_LilycoveLady,
        "LilycoveCity_PokemonCenter_1F_EventScript_ContestLadyMon" to
            LilycoveCity_PokemonCenter_1F_EventScript_ContestLadyMon,
    )
