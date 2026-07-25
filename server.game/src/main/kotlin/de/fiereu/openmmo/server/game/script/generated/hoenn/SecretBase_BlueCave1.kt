package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * special GetSecretBaseOwnerAndState
 * goto_if_eq VAR_0x8004, 0, SecretBase_EventScript_Trainer0
 * goto_if_eq VAR_0x8004, 1, SecretBase_EventScript_Trainer1
 * goto_if_eq VAR_0x8004, 2, SecretBase_EventScript_Trainer2
 * goto_if_eq VAR_0x8004, 3, SecretBase_EventScript_Trainer3
 * goto_if_eq VAR_0x8004, 4, SecretBase_EventScript_Trainer4
 * goto_if_eq VAR_0x8004, 5, SecretBase_EventScript_Trainer5
 * goto_if_eq VAR_0x8004, 6, SecretBase_EventScript_Trainer6
 * goto_if_eq VAR_0x8004, 7, SecretBase_EventScript_Trainer7
 * goto_if_eq VAR_0x8004, 8, SecretBase_EventScript_Trainer8
 * goto_if_eq VAR_0x8004, 9, SecretBase_EventScript_Trainer9
 * end
 * ```
 */
internal object SecretBase_EventScript_RecordMixTrainer : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SecretBase_EventScript_RecordMixTrainer")
}

internal val SecretBase_BlueCave1Scripts: Map<String, Script> =
    mapOf(
        "SecretBase_EventScript_RecordMixTrainer" to SecretBase_EventScript_RecordMixTrainer,
    )
