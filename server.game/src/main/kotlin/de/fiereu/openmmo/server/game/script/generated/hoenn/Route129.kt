package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHASE, Route129_Text_ChaseIntro, Route129_Text_ChaseDefeat
 * msgbox Route129_Text_ChasePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route129_EventScript_Chase : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route129_EventScript_Chase")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ALLISON, Route129_Text_AllisonIntro, Route129_Text_AllisonDefeat
 * msgbox Route129_Text_AllisonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route129_EventScript_Allison : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route129_EventScript_Allison")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TISHA, Route129_Text_TishaIntro, Route129_Text_TishaDefeat
 * msgbox Route129_Text_TishaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route129_EventScript_Tisha : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route129_EventScript_Tisha")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_REED, Route129_Text_ReedIntro, Route129_Text_ReedDefeat
 * msgbox Route129_Text_ReedPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route129_EventScript_Reed : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route129_EventScript_Reed")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CLARENCE, Route129_Text_ClarenceIntro, Route129_Text_ClarenceDefeat
 * msgbox Route129_Text_ClarencePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route129_EventScript_Clarence : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route129_EventScript_Clarence")
}

internal val Route129Scripts: Map<String, Script> =
    mapOf(
        "Route129_EventScript_Chase" to Route129_EventScript_Chase,
        "Route129_EventScript_Allison" to Route129_EventScript_Allison,
        "Route129_EventScript_Tisha" to Route129_EventScript_Tisha,
        "Route129_EventScript_Reed" to Route129_EventScript_Reed,
        "Route129_EventScript_Clarence" to Route129_EventScript_Clarence,
    )
