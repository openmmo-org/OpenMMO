package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_GILBERT, Route132_Text_GilbertIntro, Route132_Text_GilbertDefeat
 * msgbox Route132_Text_GilbertPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route132_EventScript_Gilbert : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_Gilbert")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DANA, Route132_Text_DanaIntro, Route132_Text_DanaDefeat
 * msgbox Route132_Text_DanaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route132_EventScript_Dana : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_Dana")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object Route132_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KIYO, Route132_Text_KiyoIntro, Route132_Text_KiyoDefeat
 * msgbox Route132_Text_KiyoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route132_EventScript_Kiyo : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_Kiyo")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RONALD, Route132_Text_RonaldIntro, Route132_Text_RonaldDefeat
 * msgbox Route132_Text_RonaldPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route132_EventScript_Ronald : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_Ronald")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PAXTON, Route132_Text_PaxtonIntro, Route132_Text_PaxtonDefeat
 * msgbox Route132_Text_PaxtonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route132_EventScript_Paxton : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_Paxton")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DARCY, Route132_Text_DarcyIntro, Route132_Text_DarcyDefeat
 * msgbox Route132_Text_DarcyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route132_EventScript_Darcy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_Darcy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MAKAYLA, Route132_Text_MakaylaIntro, Route132_Text_MakaylaDefeat
 * msgbox Route132_Text_MakaylaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route132_EventScript_Makayla : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_Makayla")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JONATHAN, Route132_Text_JonathanIntro, Route132_Text_JonathanDefeat
 * msgbox Route132_Text_JonathanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route132_EventScript_Jonathan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_Jonathan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PROTEIN
 * end
 * ```
 */
internal object Route132_EventScript_ItemProtein : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route132_EventScript_ItemProtein")
}

internal val Route132Scripts: Map<String, Script> =
    mapOf(
        "Route132_EventScript_Gilbert" to Route132_EventScript_Gilbert,
        "Route132_EventScript_Dana" to Route132_EventScript_Dana,
        "Route132_EventScript_ItemRareCandy" to Route132_EventScript_ItemRareCandy,
        "Route132_EventScript_Kiyo" to Route132_EventScript_Kiyo,
        "Route132_EventScript_Ronald" to Route132_EventScript_Ronald,
        "Route132_EventScript_Paxton" to Route132_EventScript_Paxton,
        "Route132_EventScript_Darcy" to Route132_EventScript_Darcy,
        "Route132_EventScript_Makayla" to Route132_EventScript_Makayla,
        "Route132_EventScript_Jonathan" to Route132_EventScript_Jonathan,
        "Route132_EventScript_ItemProtein" to Route132_EventScript_ItemProtein,
    )
