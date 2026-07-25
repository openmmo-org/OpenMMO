package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_VITO, VictoryRoad_B2F_Text_VitoIntro, VictoryRoad_B2F_Text_VitoDefeat
 * msgbox VictoryRoad_B2F_Text_VitoPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B2F_EventScript_Vito : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B2F_EventScript_Vito")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_OWEN, VictoryRoad_B2F_Text_OwenIntro, VictoryRoad_B2F_Text_OwenDefeat
 * msgbox VictoryRoad_B2F_Text_OwenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B2F_EventScript_Owen : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B2F_EventScript_Owen")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CAROLINE, VictoryRoad_B2F_Text_CarolineIntro, VictoryRoad_B2F_Text_CarolineDefeat
 * msgbox VictoryRoad_B2F_Text_CarolinePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B2F_EventScript_Caroline : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B2F_EventScript_Caroline")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_JULIE, VictoryRoad_B2F_Text_JulieIntro, VictoryRoad_B2F_Text_JulieDefeat
 * msgbox VictoryRoad_B2F_Text_JuliePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B2F_EventScript_Julie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B2F_EventScript_Julie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_HEAL
 * end
 * ```
 */
internal object VictoryRoad_B2F_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_B2F_EventScript_ItemFullHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DIANNE, VictoryRoad_B2F_Text_DianneIntro, VictoryRoad_B2F_Text_DianneDefeat
 * msgbox VictoryRoad_B2F_Text_DiannePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B2F_EventScript_Dianne : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B2F_EventScript_Dianne")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FELIX, VictoryRoad_B2F_Text_FelixIntro, VictoryRoad_B2F_Text_FelixDefeat
 * msgbox VictoryRoad_B2F_Text_FelixPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B2F_EventScript_Felix : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B2F_EventScript_Felix")
}

internal val VictoryRoad_B2FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_B2F_EventScript_Vito" to VictoryRoad_B2F_EventScript_Vito,
        "VictoryRoad_B2F_EventScript_Owen" to VictoryRoad_B2F_EventScript_Owen,
        "VictoryRoad_B2F_EventScript_Caroline" to VictoryRoad_B2F_EventScript_Caroline,
        "VictoryRoad_B2F_EventScript_Julie" to VictoryRoad_B2F_EventScript_Julie,
        "VictoryRoad_B2F_EventScript_ItemFullHeal" to VictoryRoad_B2F_EventScript_ItemFullHeal,
        "VictoryRoad_B2F_EventScript_Dianne" to VictoryRoad_B2F_EventScript_Dianne,
        "VictoryRoad_B2F_EventScript_Felix" to VictoryRoad_B2F_EventScript_Felix,
    )
