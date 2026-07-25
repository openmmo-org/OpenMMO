package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SAMUEL, VictoryRoad_B1F_Text_SamuelIntro, VictoryRoad_B1F_Text_SamuelDefeat
 * msgbox VictoryRoad_B1F_Text_SamuelPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B1F_EventScript_Samuel : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B1F_EventScript_Samuel")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHANNON, VictoryRoad_B1F_Text_ShannonIntro, VictoryRoad_B1F_Text_ShannonDefeat
 * msgbox VictoryRoad_B1F_Text_ShannonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B1F_EventScript_Shannon : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B1F_EventScript_Shannon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MICHELLE, VictoryRoad_B1F_Text_MichelleIntro, VictoryRoad_B1F_Text_MichelleDefeat
 * msgbox VictoryRoad_B1F_Text_MichellePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B1F_EventScript_Michelle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B1F_EventScript_Michelle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_PSYCHIC
 * end
 * ```
 */
internal object VictoryRoad_B1F_EventScript_ItemTMPsychic : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_B1F_EventScript_ItemTMPsychic")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_RESTORE
 * end
 * ```
 */
internal object VictoryRoad_B1F_EventScript_ItemFullRestore : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VictoryRoad_B1F_EventScript_ItemFullRestore")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MITCHELL, VictoryRoad_B1F_Text_MitchellIntro, VictoryRoad_B1F_Text_MitchellDefeat
 * msgbox VictoryRoad_B1F_Text_MitchellPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B1F_EventScript_Mitchell : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B1F_EventScript_Mitchell")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HALLE, VictoryRoad_B1F_Text_HalleIntro, VictoryRoad_B1F_Text_HalleDefeat
 * msgbox VictoryRoad_B1F_Text_HallePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object VictoryRoad_B1F_EventScript_Halle : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port VictoryRoad_B1F_EventScript_Halle")
}

internal val VictoryRoad_B1FScripts: Map<String, Script> =
    mapOf(
        "VictoryRoad_B1F_EventScript_Samuel" to VictoryRoad_B1F_EventScript_Samuel,
        "VictoryRoad_B1F_EventScript_Shannon" to VictoryRoad_B1F_EventScript_Shannon,
        "VictoryRoad_B1F_EventScript_Michelle" to VictoryRoad_B1F_EventScript_Michelle,
        "VictoryRoad_B1F_EventScript_ItemTMPsychic" to VictoryRoad_B1F_EventScript_ItemTMPsychic,
        "VictoryRoad_B1F_EventScript_ItemFullRestore" to
            VictoryRoad_B1F_EventScript_ItemFullRestore,
        "VictoryRoad_B1F_EventScript_Mitchell" to VictoryRoad_B1F_EventScript_Mitchell,
        "VictoryRoad_B1F_EventScript_Halle" to VictoryRoad_B1F_EventScript_Halle,
    )
