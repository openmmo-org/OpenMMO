package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route10_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

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
internal object Route10_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route10_PokemonCenter_1F_EventScript_Nurse")
}

internal object Route10_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route10_PokemonCenter_1F.EveryTypeStrongerThanOthers)
}

internal object Route10_PokemonCenter_1F_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route10_PokemonCenter_1F.NuggetUselessSoldFor5000)
}

internal object Route10_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(Route10_PokemonCenter_1F.HeardGhostsHauntLavender)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call Route10_PokemonCenter_1F_EventScript_GetAideRequestInfo
 * goto_if_set FLAG_GOT_EVERSTONE_FROM_OAKS_AIDE, Route10_PokemonCenter_1F_EventScript_AlreadyGotEverstone
 * msgbox Route10_PokemonCenter_1F_Text_GiveEverstoneIfCaught20Mons, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, Aide_EventScript_DeclineCheckMons
 * setvar VAR_0x8004, 0
 * specialvar VAR_RESULT, GetPokedexCount
 * buffernumberstring STR_VAR_3, VAR_0x8006
 * call Route10_PokemonCenter_1F_EventScript_GetAideRequestInfo
 * goto_if_lt VAR_0x8006, REQUIRED_OWNED_MONS, Aide_EventScript_HaventCaughtEnough
 * msgbox Route10_PokemonCenter_1F_Text_GreatHereYouGo
 * checkitemspace ITEM_EVERSTONE
 * goto_if_eq VAR_RESULT, FALSE, Aide_EventScript_NoRoomForItem
 * giveitem_msg Route10_PokemonCenter_1F_Text_ReceivedEverstoneFromAide, ITEM_EVERSTONE
 * setflag FLAG_GOT_EVERSTONE_FROM_OAKS_AIDE
 * msgbox Route10_PokemonCenter_1F_Text_ExplainEverstone
 * release
 * end
 * ```
 */
internal object Route10_PokemonCenter_1F_EventScript_Aide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Route10_PokemonCenter_1F_EventScript_Aide")
}

internal val Route10_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "Route10_PokemonCenter_1F_EventScript_Nurse" to Route10_PokemonCenter_1F_EventScript_Nurse,
        "Route10_PokemonCenter_1F_EventScript_Gentleman" to
            Route10_PokemonCenter_1F_EventScript_Gentleman,
        "Route10_PokemonCenter_1F_EventScript_FatMan" to
            Route10_PokemonCenter_1F_EventScript_FatMan,
        "Route10_PokemonCenter_1F_EventScript_Youngster" to
            Route10_PokemonCenter_1F_EventScript_Youngster,
        "Route10_PokemonCenter_1F_EventScript_Aide" to Route10_PokemonCenter_1F_EventScript_Aide,
    )
