package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.RustboroCity_Flat2_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_SKITTY, CRY_MODE_NORMAL
 * msgbox RustboroCity_Flat2_1F_Text_Skitty, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object RustboroCity_Flat2_1F_EventScript_Skitty : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_Flat2_1F_EventScript_Skitty")
}

internal object RustboroCity_Flat2_1F_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(RustboroCity_Flat2_1F.DevonWorkersLiveHere)
}

internal val RustboroCity_Flat2_1FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_Flat2_1F_EventScript_Skitty" to RustboroCity_Flat2_1F_EventScript_Skitty,
        "RustboroCity_Flat2_1F_EventScript_OldWoman" to RustboroCity_Flat2_1F_EventScript_OldWoman,
    )
