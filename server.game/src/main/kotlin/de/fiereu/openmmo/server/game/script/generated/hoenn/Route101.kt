package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route101
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object Route101_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route101.TakeTiredPokemonToPokeCenter)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setflag FLAG_SYS_POKEMON_GET
 * setflag FLAG_RESCUED_BIRCH
 * fadescreen FADE_TO_BLACK
 * removeobject LOCALID_ROUTE101_ZIGZAGOON
 * setobjectxy LOCALID_PLAYER, 6, 13
 * applymovement LOCALID_PLAYER, Common_Movement_WalkInPlaceFasterLeft
 * waitmovement 0
 * special ChooseStarter
 * applymovement LOCALID_ROUTE101_BIRCH, Route101_Movement_BirchApproachPlayer
 * waitmovement 0
 * msgbox Route101_Text_YouSavedMe, MSGBOX_DEFAULT
 * special HealPlayerParty
 * setflag FLAG_HIDE_ROUTE_101_BIRCH_ZIGZAGOON_BATTLE
 * clearflag FLAG_HIDE_LITTLEROOT_TOWN_BIRCHS_LAB_BIRCH
 * setflag FLAG_HIDE_ROUTE_101_BIRCH_STARTERS_BAG
 * setvar VAR_BIRCH_LAB_STATE, 2
 * setvar VAR_ROUTE101_STATE, 3
 * clearflag FLAG_HIDE_MAP_NAME_POPUP
 * checkplayergender
 * call_if_eq VAR_RESULT, MALE, Route101_EventScript_HideMayInBedroom
 * call_if_eq VAR_RESULT, FEMALE, Route101_EventScript_HideBrendanInBedroom
 * warp MAP_LITTLEROOT_TOWN_PROFESSOR_BIRCHS_LAB, 6, 5
 * waitstate
 * release
 * end
 * ```
 */
internal object Route101_EventScript_BirchsBag : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route101_EventScript_BirchsBag")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_unset FLAG_HAS_MATCH_CALL, ProfBirch_EventScript_AskRatePokedex
 * goto_if_unset FLAG_ENABLE_PROF_BIRCH_MATCH_CALL, EventScript_RegisterProfBirch
 * ```
 */
internal object ProfBirch_EventScript_RatePokedexOrRegister : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ProfBirch_EventScript_RatePokedexOrRegister")
}

internal object Route101_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route101.WildPokemonInTallGrass)
}

internal object Route101_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route101.RouteSign)
}

internal val Route101Scripts: Map<String, Script> =
    mapOf(
        "Route101_EventScript_Youngster" to Route101_EventScript_Youngster,
        "Route101_EventScript_BirchsBag" to Route101_EventScript_BirchsBag,
        "ProfBirch_EventScript_RatePokedexOrRegister" to
            ProfBirch_EventScript_RatePokedexOrRegister,
        "Route101_EventScript_Boy" to Route101_EventScript_Boy,
        "Route101_EventScript_RouteSign" to Route101_EventScript_RouteSign,
    )
