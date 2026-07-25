package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_House1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object LilycoveCity_House1_EventScript_ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_House1.PokemonPartnersNotTools)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_KECLEON, CRY_MODE_NORMAL
 * msgbox LilycoveCity_House1_Text_Kecleon, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object LilycoveCity_House1_EventScript_Kecleon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_House1_EventScript_Kecleon")
}

internal val LilycoveCity_House1Scripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_House1_EventScript_ExpertM" to LilycoveCity_House1_EventScript_ExpertM,
        "LilycoveCity_House1_EventScript_Kecleon" to LilycoveCity_House1_EventScript_Kecleon,
    )
