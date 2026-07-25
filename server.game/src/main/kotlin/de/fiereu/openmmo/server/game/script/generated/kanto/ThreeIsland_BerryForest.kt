package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ThreeIsland_BerryForest
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox ThreeIsland_BerryForest_Text_HelpScaryPokemon
 * playse SE_PIN
 * applymovement LOCALID_BERRY_FOREST_LOSTELLE, Common_Movement_ExclamationMark
 * waitmovement 0
 * applymovement LOCALID_BERRY_FOREST_LOSTELLE, ThreeIsland_BerryForest_Movement_LostelleLookAround
 * waitmovement 0
 * msgbox ThreeIsland_BerryForest_Text_HereItComesAgain
 * goto_if_questlog EventScript_ReleaseEnd
 * waitse
 * playmoncry SPECIES_HYPNO, CRY_MODE_ENCOUNTER
 * waitmoncry
 * setwildbattle SPECIES_HYPNO, 30
 * dowildbattle
 * special QuestLog_CutRecording
 * applymovement LOCALID_BERRY_FOREST_LOSTELLE, Common_Movement_FacePlayer
 * waitmovement 0
 * msgbox ThreeIsland_BerryForest_Text_ThankYouHaveThis
 * giveitem ITEM_IAPAPA_BERRY
 * call_if_eq VAR_RESULT, FALSE, ThreeIsland_BerryForest_EventScript_NoRoomForBerry
 * special BufferBigGuyOrBigGirlString
 * msgbox ThreeIsland_BerryForest_Text_LetsGoHome
 * closemessage
 * setflag FLAG_RESCUED_LOSTELLE
 * setflag FLAG_HIDE_LOSTELLE_IN_BERRY_FOREST
 * setvar VAR_MAP_SCENE_TWO_ISLAND_JOYFUL_GAME_CORNER, 2
 * clearflag FLAG_HIDE_TWO_ISLAND_GAME_CORNER_LOSTELLE
 * warp MAP_TWO_ISLAND_JOYFUL_GAME_CORNER, 6, 6
 * waitstate
 * release
 * end
 * ```
 */
internal object ThreeIsland_BerryForest_EventScript_Lostelle : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ThreeIsland_BerryForest_EventScript_Lostelle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ETHER
 * end
 * ```
 */
internal object ThreeIsland_BerryForest_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ThreeIsland_BerryForest_EventScript_ItemMaxEther")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_HEAL
 * end
 * ```
 */
internal object ThreeIsland_BerryForest_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ThreeIsland_BerryForest_EventScript_ItemFullHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ELIXIR
 * end
 * ```
 */
internal object ThreeIsland_BerryForest_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port ThreeIsland_BerryForest_EventScript_ItemMaxElixir")
}

internal object ThreeIsland_BerryForest_EventScript_BewareSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(ThreeIsland_BerryForest.BewareWildBerryLovingMons)
}

internal object ThreeIsland_BerryForest_EventScript_WelcomeSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(ThreeIsland_BerryForest.WelcomeToBerryForest)
}

internal val ThreeIsland_BerryForestScripts: Map<String, Script> =
    mapOf(
        "ThreeIsland_BerryForest_EventScript_Lostelle" to
            ThreeIsland_BerryForest_EventScript_Lostelle,
        "ThreeIsland_BerryForest_EventScript_ItemMaxEther" to
            ThreeIsland_BerryForest_EventScript_ItemMaxEther,
        "ThreeIsland_BerryForest_EventScript_ItemFullHeal" to
            ThreeIsland_BerryForest_EventScript_ItemFullHeal,
        "ThreeIsland_BerryForest_EventScript_ItemMaxElixir" to
            ThreeIsland_BerryForest_EventScript_ItemMaxElixir,
        "ThreeIsland_BerryForest_EventScript_BewareSign" to
            ThreeIsland_BerryForest_EventScript_BewareSign,
        "ThreeIsland_BerryForest_EventScript_WelcomeSign" to
            ThreeIsland_BerryForest_EventScript_WelcomeSign,
    )
