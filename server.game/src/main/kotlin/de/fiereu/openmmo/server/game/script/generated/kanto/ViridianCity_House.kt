package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.ViridianCity_House
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object ViridianCity_House_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianCity_House.NicknamingIsFun)
}

internal object ViridianCity_House_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(ViridianCity_House.MyDaddyLovesMonsToo)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_SPEAROW, CRY_MODE_NORMAL
 * msgbox ViridianCity_House_Text_Speary
 * waitmoncry
 * release
 * end
 * ```
 */
internal object ViridianCity_House_EventScript_Speary : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port ViridianCity_House_EventScript_Speary")
}

internal object ViridianCity_House_EventScript_NicknameSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(ViridianCity_House.SpearowNameSpeary)
}

internal val ViridianCity_HouseScripts: Map<String, Script> =
    mapOf(
        "ViridianCity_House_EventScript_BaldingMan" to ViridianCity_House_EventScript_BaldingMan,
        "ViridianCity_House_EventScript_LittleGirl" to ViridianCity_House_EventScript_LittleGirl,
        "ViridianCity_House_EventScript_Speary" to ViridianCity_House_EventScript_Speary,
        "ViridianCity_House_EventScript_NicknameSign" to
            ViridianCity_House_EventScript_NicknameSign,
    )
