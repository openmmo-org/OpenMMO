package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SilphCo_5F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_29, SilphCo_5F_Text_Grunt2Intro, SilphCo_5F_Text_Grunt2Defeat
 * msgbox SilphCo_5F_Text_Grunt2PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_Grunt2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_Grunt2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JUGGLER_DALTON, SilphCo_5F_Text_DaltonIntro, SilphCo_5F_Text_DaltonDefeat
 * msgbox SilphCo_5F_Text_DaltonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_Dalton : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_Dalton")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_ge VAR_MAP_SCENE_SILPH_CO_11F, 1, SilphCo_5F_EventScript_WorkerMRocketsGone
 * msgbox SilphCo_5F_Text_RocketsInUproarAboutIntruder
 * release
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_WorkerM : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_WorkerM")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_BEAU, SilphCo_5F_Text_BeauIntro, SilphCo_5F_Text_BeauDefeat
 * msgbox SilphCo_5F_Text_BeauPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_Beau : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_Beau")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TEAM_ROCKET_GRUNT_28, SilphCo_5F_Text_Grunt1Intro, SilphCo_5F_Text_Grunt1Defeat
 * msgbox SilphCo_5F_Text_Grunt1PostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_Grunt1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_Grunt1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PROTEIN
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_ItemProtein : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_ItemProtein")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM01
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_ItemTM01 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_ItemTM01")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_CARD_KEY
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_ItemCardKey : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_ItemCardKey")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_GIOVANNI, 1
 * msgbox SilphCo_5F_Text_RocketBossLookingForStrongMons
 * release
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_Scientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 7
 * setvar VAR_0x8004, FLAG_SILPH_5F_DOOR_1
 * goto_if_set FLAG_SILPH_5F_DOOR_1, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_Door1 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_Door1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 8
 * setvar VAR_0x8004, FLAG_SILPH_5F_DOOR_2
 * goto_if_set FLAG_SILPH_5F_DOOR_2, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_Door2 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_Door2")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_TEMP_1, 9
 * setvar VAR_0x8004, FLAG_SILPH_5F_DOOR_3
 * goto_if_set FLAG_SILPH_5F_DOOR_3, EventScript_DoorUnlocked
 * goto EventScript_TryUnlockDoor
 * end
 * ```
 */
internal object SilphCo_5F_EventScript_Door3 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SilphCo_5F_EventScript_Door3")
}

internal object SilphCo_5F_EventScript_PokemonReport3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_5F.SomeMonsEvolveWhenTraded)
}

internal object SilphCo_5F_EventScript_PokemonReport2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_5F.Over350TechniquesConfirmed)
}

internal object SilphCo_5F_EventScript_PokemonReport1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_5F.PorygonFirstVRMon)
}

internal object SilphCo_5F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SilphCo_5F.FloorSign)
}

internal val SilphCo_5FScripts: Map<String, Script> =
    mapOf(
        "SilphCo_5F_EventScript_Grunt2" to SilphCo_5F_EventScript_Grunt2,
        "SilphCo_5F_EventScript_Dalton" to SilphCo_5F_EventScript_Dalton,
        "SilphCo_5F_EventScript_WorkerM" to SilphCo_5F_EventScript_WorkerM,
        "SilphCo_5F_EventScript_Beau" to SilphCo_5F_EventScript_Beau,
        "SilphCo_5F_EventScript_Grunt1" to SilphCo_5F_EventScript_Grunt1,
        "SilphCo_5F_EventScript_ItemProtein" to SilphCo_5F_EventScript_ItemProtein,
        "SilphCo_5F_EventScript_ItemTM01" to SilphCo_5F_EventScript_ItemTM01,
        "SilphCo_5F_EventScript_ItemCardKey" to SilphCo_5F_EventScript_ItemCardKey,
        "SilphCo_5F_EventScript_Scientist" to SilphCo_5F_EventScript_Scientist,
        "SilphCo_5F_EventScript_Door1" to SilphCo_5F_EventScript_Door1,
        "SilphCo_5F_EventScript_Door2" to SilphCo_5F_EventScript_Door2,
        "SilphCo_5F_EventScript_Door3" to SilphCo_5F_EventScript_Door3,
        "SilphCo_5F_EventScript_PokemonReport3" to SilphCo_5F_EventScript_PokemonReport3,
        "SilphCo_5F_EventScript_PokemonReport2" to SilphCo_5F_EventScript_PokemonReport2,
        "SilphCo_5F_EventScript_PokemonReport1" to SilphCo_5F_EventScript_PokemonReport1,
        "SilphCo_5F_EventScript_FloorSign" to SilphCo_5F_EventScript_FloorSign,
    )
