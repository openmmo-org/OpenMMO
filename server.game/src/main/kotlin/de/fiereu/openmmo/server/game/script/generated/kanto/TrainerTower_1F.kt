package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * call TrainerTower_EventScript_SpeakToDoublesTrainer1
 * end
 * ```
 */
internal object TrainerTower_EventScript_DoublesTrainer1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TrainerTower_EventScript_DoublesTrainer1")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * call TrainerTower_EventScript_SpeakToSinglesTrainer
 * end
 * ```
 */
internal object TrainerTower_EventScript_SinglesTrainer : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TrainerTower_EventScript_SinglesTrainer")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * call TrainerTower_EventScript_SpeakToKnockoutTrainer
 * end
 * ```
 */
internal object TrainerTower_EventScript_KnockoutTrainer : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TrainerTower_EventScript_KnockoutTrainer")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * call TrainerTower_EventScript_SpeakToDoublesTrainer2
 * end
 * ```
 */
internal object TrainerTower_EventScript_DoublesTrainer2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port TrainerTower_EventScript_DoublesTrainer2")
}

internal val TrainerTower_1FScripts: Map<String, Script> =
    mapOf(
        "TrainerTower_EventScript_DoublesTrainer1" to TrainerTower_EventScript_DoublesTrainer1,
        "TrainerTower_EventScript_SinglesTrainer" to TrainerTower_EventScript_SinglesTrainer,
        "TrainerTower_EventScript_KnockoutTrainer" to TrainerTower_EventScript_KnockoutTrainer,
        "TrainerTower_EventScript_DoublesTrainer2" to TrainerTower_EventScript_DoublesTrainer2,
    )
