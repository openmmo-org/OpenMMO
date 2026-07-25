package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SaffronCity_CopycatsHouse_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object SaffronCity_CopycatsHouse_1F_EventScript_CopycatsDad : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SaffronCity_CopycatsHouse_1F.DaughterLikesToMimicPeople)
}

internal object SaffronCity_CopycatsHouse_1F_EventScript_CopycatsMom : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SaffronCity_CopycatsHouse_1F.DaughterIsSelfCentered)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_CHANSEY, CRY_MODE_NORMAL
 * msgbox SaffronCity_CopycatsHouse_1F_Text_Chansey
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SaffronCity_CopycatsHouse_1F_EventScript_Chansey : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_CopycatsHouse_1F_EventScript_Chansey")
}

internal val SaffronCity_CopycatsHouse_1FScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_CopycatsHouse_1F_EventScript_CopycatsDad" to
            SaffronCity_CopycatsHouse_1F_EventScript_CopycatsDad,
        "SaffronCity_CopycatsHouse_1F_EventScript_CopycatsMom" to
            SaffronCity_CopycatsHouse_1F_EventScript_CopycatsMom,
        "SaffronCity_CopycatsHouse_1F_EventScript_Chansey" to
            SaffronCity_CopycatsHouse_1F_EventScript_Chansey,
    )
