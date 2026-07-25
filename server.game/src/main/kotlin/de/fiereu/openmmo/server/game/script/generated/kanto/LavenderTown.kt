package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.LavenderTown
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox LavenderTown_Text_DoYouBelieveInGhosts, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, LavenderTown_EventScript_LittleGirlBelieve
 * msgbox LavenderTown_Text_JustImaginingWhiteHand
 * release
 * end
 * ```
 */
internal object LavenderTown_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LavenderTown_EventScript_LittleGirl")
}

internal object LavenderTown_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LavenderTown.TownKnownAsMonGraveSite)
}

internal object LavenderTown_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LavenderTown.GhostsAppearedInTower)
}

internal object LavenderTown_EventScript_SilphScopeNotice : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LavenderTown.SilphScopeNotice)
}

internal object LavenderTown_EventScript_TownSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LavenderTown.TownSign)
}

internal object LavenderTown_EventScript_PokemonTowerSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LavenderTown.PokemonTowerSign)
}

internal object LavenderTown_EventScript_VolunteerHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(LavenderTown.VolunteerPokemonHouse)
}

internal val LavenderTownScripts: Map<String, Script> =
    mapOf(
        "LavenderTown_EventScript_LittleGirl" to LavenderTown_EventScript_LittleGirl,
        "LavenderTown_EventScript_WorkerM" to LavenderTown_EventScript_WorkerM,
        "LavenderTown_EventScript_Boy" to LavenderTown_EventScript_Boy,
        "LavenderTown_EventScript_SilphScopeNotice" to LavenderTown_EventScript_SilphScopeNotice,
        "LavenderTown_EventScript_TownSign" to LavenderTown_EventScript_TownSign,
        "LavenderTown_EventScript_PokemonTowerSign" to LavenderTown_EventScript_PokemonTowerSign,
        "LavenderTown_EventScript_VolunteerHouseSign" to
            LavenderTown_EventScript_VolunteerHouseSign,
    )
