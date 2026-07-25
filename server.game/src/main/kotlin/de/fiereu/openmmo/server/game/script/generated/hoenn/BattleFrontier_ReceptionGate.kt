package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_ReceptionGate_Text_WelcomeToBattleFrontier, MSGBOX_DEFAULT
 * msgbox BattleFrontier_ReceptionGate_Text_EnjoyBattleFrontier, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object BattleFrontier_ReceptionGate_EventScript_Greeter : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_ReceptionGate_EventScript_Greeter")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_ReceptionGate_Text_YourGuideToFacilities, MSGBOX_DEFAULT
 * goto BattleFrontier_ReceptionGate_EventScript_ChooseFacilityToLearnAbout
 * end
 * ```
 */
internal object BattleFrontier_ReceptionGate_EventScript_FacilityGuide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_ReceptionGate_EventScript_FacilityGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_ReceptionGate_Text_YourGuideToRules, MSGBOX_DEFAULT
 * goto BattleFrontier_ReceptionGate_EventScript_ChooseRuleToLearnAbout
 * end
 * ```
 */
internal object BattleFrontier_ReceptionGate_EventScript_RulesGuide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_ReceptionGate_EventScript_RulesGuide")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_ReceptionGate_Text_YourGuideToFrontierPass, MSGBOX_DEFAULT
 * goto BattleFrontier_ReceptionGate_EventScript_ChooseFrontierPassInfoToLearnAbout
 * end
 * ```
 */
internal object BattleFrontier_ReceptionGate_EventScript_FrontierPassGuide : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_ReceptionGate_EventScript_FrontierPassGuide")
}

internal val BattleFrontier_ReceptionGateScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_ReceptionGate_EventScript_Greeter" to
            BattleFrontier_ReceptionGate_EventScript_Greeter,
        "BattleFrontier_ReceptionGate_EventScript_FacilityGuide" to
            BattleFrontier_ReceptionGate_EventScript_FacilityGuide,
        "BattleFrontier_ReceptionGate_EventScript_RulesGuide" to
            BattleFrontier_ReceptionGate_EventScript_RulesGuide,
        "BattleFrontier_ReceptionGate_EventScript_FrontierPassGuide" to
            BattleFrontier_ReceptionGate_EventScript_FrontierPassGuide,
    )
