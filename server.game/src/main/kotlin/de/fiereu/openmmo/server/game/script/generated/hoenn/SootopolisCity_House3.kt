package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SootopolisCity_House3
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox SootopolisCity_House3_Text_JuanHasManyFansDoYou, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, YES, SootopolisCity_House3_EventScript_HaveFans
 * msgbox SootopolisCity_House3_Text_LonesomeTryWorkingHarder, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SootopolisCity_House3_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SootopolisCity_House3_EventScript_Woman")
}

internal object SootopolisCity_House3_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SootopolisCity_House3.TrainerFanClubWasWild)
}

internal val SootopolisCity_House3Scripts: Map<String, Script> =
    mapOf(
        "SootopolisCity_House3_EventScript_Woman" to SootopolisCity_House3_EventScript_Woman,
        "SootopolisCity_House3_EventScript_Girl" to SootopolisCity_House3_EventScript_Girl,
    )
