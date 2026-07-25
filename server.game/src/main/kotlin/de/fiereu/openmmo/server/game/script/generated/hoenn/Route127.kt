package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAMDEN, Route127_Text_CamdenIntro, Route127_Text_CamdenDefeat
 * msgbox Route127_Text_CamdenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route127_EventScript_Camden : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_Camden")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DONNY, Route127_Text_DonnyIntro, Route127_Text_DonnyDefeat
 * msgbox Route127_Text_DonnyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route127_EventScript_Donny : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_Donny")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ZINC
 * end
 * ```
 */
internal object Route127_EventScript_ItemZinc : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_ItemZinc")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_CARBOS
 * end
 * ```
 */
internal object Route127_EventScript_ItemCarbos : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_ItemCarbos")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JONAH, Route127_Text_JonahIntro, Route127_Text_JonahDefeat
 * msgbox Route127_Text_JonahPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route127_EventScript_Jonah : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_Jonah")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ROGER, Route127_Text_RogerIntro, Route127_Text_RogerDefeat
 * msgbox Route127_Text_RogerPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route127_EventScript_Roger : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_Roger")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HENRY, Route127_Text_HenryIntro, Route127_Text_HenryDefeat
 * msgbox Route127_Text_HenryPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route127_EventScript_Henry : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_Henry")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_AIDAN, Route127_Text_AidanIntro, Route127_Text_AidanDefeat
 * msgbox Route127_Text_AidanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route127_EventScript_Aidan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_Aidan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KOJI_1, Route127_Text_KojiIntro, Route127_Text_KojiDefeat, Route127_EventScript_RegisterKoji
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route127_EventScript_RematchKoji
 * msgbox Route127_Text_KojiPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route127_EventScript_Koji : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_Koji")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ATHENA, Route127_Text_AthenaIntro, Route127_Text_AthenaDefeat
 * msgbox Route127_Text_AthenaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route127_EventScript_Athena : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_Athena")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object Route127_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route127_EventScript_ItemRareCandy")
}

internal val Route127Scripts: Map<String, Script> =
    mapOf(
        "Route127_EventScript_Camden" to Route127_EventScript_Camden,
        "Route127_EventScript_Donny" to Route127_EventScript_Donny,
        "Route127_EventScript_ItemZinc" to Route127_EventScript_ItemZinc,
        "Route127_EventScript_ItemCarbos" to Route127_EventScript_ItemCarbos,
        "Route127_EventScript_Jonah" to Route127_EventScript_Jonah,
        "Route127_EventScript_Roger" to Route127_EventScript_Roger,
        "Route127_EventScript_Henry" to Route127_EventScript_Henry,
        "Route127_EventScript_Aidan" to Route127_EventScript_Aidan,
        "Route127_EventScript_Koji" to Route127_EventScript_Koji,
        "Route127_EventScript_Athena" to Route127_EventScript_Athena,
        "Route127_EventScript_ItemRareCandy" to Route127_EventScript_ItemRareCandy,
    )
