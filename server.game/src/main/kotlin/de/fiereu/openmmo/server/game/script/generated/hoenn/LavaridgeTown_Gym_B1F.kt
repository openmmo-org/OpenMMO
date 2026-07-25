package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle TRAINER_BATTLE_CONTINUE_SCRIPT, TRAINER_JACE, LOCALID_JACE, LavaridgeTown_Gym_B1F_Text_JaceIntro, LavaridgeTown_Gym_B1F_Text_JaceDefeat, LavaridgeTown_Gym_EventScript_CheckTrainerScript
 * msgbox LavaridgeTown_Gym_B1F_Text_JacePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object LavaridgeTown_Gym_B1F_EventScript_Jace : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LavaridgeTown_Gym_B1F_EventScript_Jace")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle TRAINER_BATTLE_CONTINUE_SCRIPT, TRAINER_KEEGAN, LOCALID_KEEGAN, LavaridgeTown_Gym_B1F_Text_KeeganIntro, LavaridgeTown_Gym_B1F_Text_KeeganDefeat, LavaridgeTown_Gym_EventScript_CheckTrainerScript
 * msgbox LavaridgeTown_Gym_B1F_Text_KeeganPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object LavaridgeTown_Gym_B1F_EventScript_Keegan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LavaridgeTown_Gym_B1F_EventScript_Keegan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle TRAINER_BATTLE_CONTINUE_SCRIPT, TRAINER_JEFF, LOCALID_JEFF, LavaridgeTown_Gym_B1F_Text_JeffIntro, LavaridgeTown_Gym_B1F_Text_JeffDefeat, LavaridgeTown_Gym_EventScript_CheckTrainerScript
 * msgbox LavaridgeTown_Gym_B1F_Text_JeffPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object LavaridgeTown_Gym_B1F_EventScript_Jeff : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LavaridgeTown_Gym_B1F_EventScript_Jeff")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle TRAINER_BATTLE_CONTINUE_SCRIPT, TRAINER_ELI, LOCALID_ELI, LavaridgeTown_Gym_B1F_Text_EliIntro, LavaridgeTown_Gym_B1F_Text_EliDefeat, LavaridgeTown_Gym_EventScript_CheckTrainerScript
 * msgbox LavaridgeTown_Gym_B1F_Text_EliPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object LavaridgeTown_Gym_B1F_EventScript_Eli : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port LavaridgeTown_Gym_B1F_EventScript_Eli")
}

internal val LavaridgeTown_Gym_B1FScripts: Map<String, Script> =
    mapOf(
        "LavaridgeTown_Gym_B1F_EventScript_Jace" to LavaridgeTown_Gym_B1F_EventScript_Jace,
        "LavaridgeTown_Gym_B1F_EventScript_Keegan" to LavaridgeTown_Gym_B1F_EventScript_Keegan,
        "LavaridgeTown_Gym_B1F_EventScript_Jeff" to LavaridgeTown_Gym_B1F_EventScript_Jeff,
        "LavaridgeTown_Gym_B1F_EventScript_Eli" to LavaridgeTown_Gym_B1F_EventScript_Eli,
    )
