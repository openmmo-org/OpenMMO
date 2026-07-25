package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JEROME, Route108_Text_JeromeIntro, Route108_Text_JeromeDefeated
 * msgbox Route108_Text_JeromePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route108_EventScript_Jerome : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route108_EventScript_Jerome")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TARA, Route108_Text_TaraIntro, Route108_Text_TaraDefeated
 * msgbox Route108_Text_TaraPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route108_EventScript_Tara : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route108_EventScript_Tara")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MATTHEW, Route108_Text_MatthewIntro, Route108_Text_MatthewDefeated
 * msgbox Route108_Text_MatthewPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route108_EventScript_Matthew : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route108_EventScript_Matthew")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MISSY, Route108_Text_MissyIntro, Route108_Text_MissyDefeated
 * msgbox Route108_Text_MissyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route108_EventScript_Missy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route108_EventScript_Missy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAROLINA, Route108_Text_CarolinaIntro, Route108_Text_CarolinaDefeated
 * msgbox Route108_Text_CarolinaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route108_EventScript_Carolina : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route108_EventScript_Carolina")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CORY_1, Route108_Text_CoryIntro, Route108_Text_CoryDefeated, Route108_EventScript_CoryRegisterMatchCallAfterBattle
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route108_EventScript_CoryRematch
 * msgbox Route108_Text_CoryPostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route108_EventScript_Cory : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route108_EventScript_Cory")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_STAR_PIECE
 * end
 * ```
 */
internal object Route108_EventScript_ItemStarPiece : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route108_EventScript_ItemStarPiece")
}

internal val Route108Scripts: Map<String, Script> =
    mapOf(
        "Route108_EventScript_Jerome" to Route108_EventScript_Jerome,
        "Route108_EventScript_Tara" to Route108_EventScript_Tara,
        "Route108_EventScript_Matthew" to Route108_EventScript_Matthew,
        "Route108_EventScript_Missy" to Route108_EventScript_Missy,
        "Route108_EventScript_Carolina" to Route108_EventScript_Carolina,
        "Route108_EventScript_Cory" to Route108_EventScript_Cory,
        "Route108_EventScript_ItemStarPiece" to Route108_EventScript_ItemStarPiece,
    )
