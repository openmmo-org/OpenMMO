package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PokemonMansion_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BURGLAR_SIMON, PokemonMansion_1F_Text_SimonIntro, PokemonMansion_1F_Text_SimonDefeat
 * msgbox PokemonMansion_1F_Text_SimonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonMansion_3F_EventScript_Simon : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_3F_EventScript_Simon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SCIENTIST_BRAYDON, PokemonMansion_1F_Text_BraydonIntro, PokemonMansion_1F_Text_BraydonDefeat
 * msgbox PokemonMansion_1F_Text_BraydonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object PokemonMansion_3F_EventScript_Braydon : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_3F_EventScript_Braydon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_POTION
 * end
 * ```
 */
internal object PokemonMansion_3F_EventScript_ItemMaxPotion : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PokemonMansion_3F_EventScript_ItemMaxPotion")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_IRON
 * end
 * ```
 */
internal object PokemonMansion_3F_EventScript_ItemIron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_3F_EventScript_ItemIron")
}

internal object PokemonMansion_3F_EventScript_DiaryFeb6th : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PokemonMansion_1F.MewGaveBirthToMewtwo)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8004, 2
 * call PokemonMansion_EventScript_SecretSwitch
 * playse SE_UNLOCK
 * special DrawWholeMapView
 * waitse
 * releaseall
 * end
 * ```
 */
internal object PokemonMansion_3F_EventScript_Statue : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port PokemonMansion_3F_EventScript_Statue")
}

internal val PokemonMansion_3FScripts: Map<String, Script> =
    mapOf(
        "PokemonMansion_3F_EventScript_Simon" to PokemonMansion_3F_EventScript_Simon,
        "PokemonMansion_3F_EventScript_Braydon" to PokemonMansion_3F_EventScript_Braydon,
        "PokemonMansion_3F_EventScript_ItemMaxPotion" to
            PokemonMansion_3F_EventScript_ItemMaxPotion,
        "PokemonMansion_3F_EventScript_ItemIron" to PokemonMansion_3F_EventScript_ItemIron,
        "PokemonMansion_3F_EventScript_DiaryFeb6th" to PokemonMansion_3F_EventScript_DiaryFeb6th,
        "PokemonMansion_3F_EventScript_Statue" to PokemonMansion_3F_EventScript_Statue,
    )
