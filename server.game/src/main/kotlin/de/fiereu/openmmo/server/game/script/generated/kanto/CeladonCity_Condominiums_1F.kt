package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_Condominiums_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_MEOWTH, CRY_MODE_NORMAL
 * msgbox CeladonCity_Condominiums_1F_Text_Meowth
 * waitmoncry
 * release
 * end
 * ```
 */
internal object CeladonCity_Condominiums_1F_EventScript_Meowth : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_Condominiums_1F_EventScript_Meowth")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_CLEFAIRY, CRY_MODE_NORMAL
 * msgbox CeladonCity_Condominiums_1F_Text_Clefairy
 * waitmoncry
 * release
 * end
 * ```
 */
internal object CeladonCity_Condominiums_1F_EventScript_Clefairy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_Condominiums_1F_EventScript_Clefairy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_NIDORAN_F, CRY_MODE_NORMAL
 * msgbox CeladonCity_Condominiums_1F_Text_Nidoran
 * waitmoncry
 * release
 * end
 * ```
 */
internal object CeladonCity_Condominiums_1F_EventScript_Nidoran : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_Condominiums_1F_EventScript_Nidoran")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TALKED_TO_TEA_LADY_AFTER_HOF, CeladonCity_Condominiums_1F_EventScript_TeaWomanAfterTea
 * goto_if_set FLAG_SYS_GAME_CLEAR, CeladonCity_Condominiums_1F_EventScript_TeaWomanMentionDaisy
 * goto_if_set FLAG_GOT_TEA, CeladonCity_Condominiums_1F_EventScript_TeaWomanAfterTea
 * msgbox CeladonCity_Condominiums_1F_Text_TryThisDrinkInstead
 * setflag FLAG_GOT_TEA
 * giveitem ITEM_TEA
 * goto_if_eq VAR_RESULT, FALSE, EventScript_BagIsFull
 * msgbox CeladonCity_Condominiums_1F_Text_NothingBeatsThirstLikeTea
 * release
 * end
 * ```
 */
internal object CeladonCity_Condominiums_1F_EventScript_TeaWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_Condominiums_1F_EventScript_TeaWoman")
}

internal object CeladonCity_Condominiums_1F_EventScript_SuiteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeladonCity_Condominiums_1F.ManagersSuite)
}

internal val CeladonCity_Condominiums_1FScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_Condominiums_1F_EventScript_Meowth" to
            CeladonCity_Condominiums_1F_EventScript_Meowth,
        "CeladonCity_Condominiums_1F_EventScript_Clefairy" to
            CeladonCity_Condominiums_1F_EventScript_Clefairy,
        "CeladonCity_Condominiums_1F_EventScript_Nidoran" to
            CeladonCity_Condominiums_1F_EventScript_Nidoran,
        "CeladonCity_Condominiums_1F_EventScript_TeaWoman" to
            CeladonCity_Condominiums_1F_EventScript_TeaWoman,
        "CeladonCity_Condominiums_1F_EventScript_SuiteSign" to
            CeladonCity_Condominiums_1F_EventScript_SuiteSign,
    )
