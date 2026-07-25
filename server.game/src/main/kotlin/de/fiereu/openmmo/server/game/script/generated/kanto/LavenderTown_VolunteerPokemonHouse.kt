package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.LavenderTown_VolunteerPokemonHouse
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_POKE_FLUTE, LavenderTown_VolunteerPokemonHouse_EventScript_AlreadyHavePokeFlute
 * msgbox LavenderTown_VolunteerPokemonHouse_Text_IdLikeYouToHaveThis
 * checkitemspace ITEM_POKE_FLUTE
 * goto_if_eq VAR_RESULT, FALSE, LavenderTown_VolunteerPokemonHouse_EventScript_NoRoomForPokeFlute
 * setflag FLAG_GOT_POKE_FLUTE
 * giveitem_msg LavenderTown_VolunteerPokemonHouse_Text_ReceivedPokeFluteFromMrFuji, ITEM_POKE_FLUTE, 1, MUS_OBTAIN_KEY_ITEM
 * msgbox LavenderTown_VolunteerPokemonHouse_Text_ExplainPokeFlute
 * release
 * end
 * ```
 */
internal object LavenderTown_VolunteerPokemonHouse_EventScript_MrFuji : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavenderTown_VolunteerPokemonHouse_EventScript_MrFuji")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_NIDORINO, CRY_MODE_NORMAL
 * msgbox LavenderTown_VolunteerPokemonHouse_Text_Nidorino
 * waitmoncry
 * release
 * end
 * ```
 */
internal object LavenderTown_VolunteerPokemonHouse_EventScript_Nidorino : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavenderTown_VolunteerPokemonHouse_EventScript_Nidorino")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_PSYDUCK, CRY_MODE_NORMAL
 * msgbox LavenderTown_VolunteerPokemonHouse_Text_Psyduck
 * waitmoncry
 * release
 * end
 * ```
 */
internal object LavenderTown_VolunteerPokemonHouse_EventScript_Psyduck : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavenderTown_VolunteerPokemonHouse_EventScript_Psyduck")
}

internal object LavenderTown_VolunteerPokemonHouse_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LavenderTown_VolunteerPokemonHouse.MonsNiceToHug)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RESCUED_MR_FUJI, LavenderTown_VolunteerPokemonHouse_EventScript_YoungsterFujiBack
 * msgbox LavenderTown_VolunteerPokemonHouse_Text_WhereDidMrFujiGo
 * release
 * end
 * ```
 */
internal object LavenderTown_VolunteerPokemonHouse_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavenderTown_VolunteerPokemonHouse_EventScript_Youngster")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_MRFUJI, 0
 * msgbox LavenderTown_VolunteerPokemonHouse_Text_MrFujiLooksAfterOrphanedMons
 * release
 * end
 * ```
 */
internal object LavenderTown_VolunteerPokemonHouse_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavenderTown_VolunteerPokemonHouse_EventScript_LittleGirl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_MRFUJI, 2
 * msgbox LavenderTown_VolunteerPokemonHouse_Text_GrandPrizeDrawingClipped
 * releaseall
 * end
 * ```
 */
internal object LavenderTown_VolunteerPokemonHouse_EventScript_PokemonFanMagazine : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavenderTown_VolunteerPokemonHouse_EventScript_PokemonFanMagazine")
}

internal object LavenderTown_VolunteerPokemonHouse_EventScript_Bookshelf : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(LavenderTown_VolunteerPokemonHouse.PokemonMagazinesLineShelf)
}

internal val LavenderTown_VolunteerPokemonHouseScripts: Map<String, Script> =
    mapOf(
        "LavenderTown_VolunteerPokemonHouse_EventScript_MrFuji" to
            LavenderTown_VolunteerPokemonHouse_EventScript_MrFuji,
        "LavenderTown_VolunteerPokemonHouse_EventScript_Nidorino" to
            LavenderTown_VolunteerPokemonHouse_EventScript_Nidorino,
        "LavenderTown_VolunteerPokemonHouse_EventScript_Psyduck" to
            LavenderTown_VolunteerPokemonHouse_EventScript_Psyduck,
        "LavenderTown_VolunteerPokemonHouse_EventScript_LittleBoy" to
            LavenderTown_VolunteerPokemonHouse_EventScript_LittleBoy,
        "LavenderTown_VolunteerPokemonHouse_EventScript_Youngster" to
            LavenderTown_VolunteerPokemonHouse_EventScript_Youngster,
        "LavenderTown_VolunteerPokemonHouse_EventScript_LittleGirl" to
            LavenderTown_VolunteerPokemonHouse_EventScript_LittleGirl,
        "LavenderTown_VolunteerPokemonHouse_EventScript_PokemonFanMagazine" to
            LavenderTown_VolunteerPokemonHouse_EventScript_PokemonFanMagazine,
        "LavenderTown_VolunteerPokemonHouse_EventScript_Bookshelf" to
            LavenderTown_VolunteerPokemonHouse_EventScript_Bookshelf,
    )
