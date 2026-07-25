package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SaffronCity_CopycatsHouse_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_DODUO, CRY_MODE_NORMAL
 * msgbox SaffronCity_CopycatsHouse_2F_Text_Doduo
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SaffronCity_CopycatsHouse_2F_EventScript_Doduo : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_CopycatsHouse_2F_EventScript_Doduo")
}

internal object SaffronCity_CopycatsHouse_2F_EventScript_Doll : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(SaffronCity_CopycatsHouse_2F.RareMonOnlyDoll)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * special QuestLog_CutRecording
 * lock
 * faceplayer
 * checkitem ITEM_POKE_DOLL
 * goto_if_eq VAR_RESULT, TRUE, EventScript_MimicTutor
 * checkplayergender
 * call_if_eq VAR_RESULT, MALE, SaffronCity_CopycatsHouse_2F_EventScript_MimicPlayerMale
 * call_if_eq VAR_RESULT, FEMALE, SaffronCity_CopycatsHouse_2F_EventScript_MimicPlayerFemale
 * release
 * end
 * ```
 */
internal object SaffronCity_CopycatsHouse_2F_EventScript_Copycat : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_CopycatsHouse_2F_EventScript_Copycat")
}

internal object SaffronCity_CopycatsHouse_2F_EventScript_Computer : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity_CopycatsHouse_2F.MySecrets)
}

internal object SaffronCity_CopycatsHouse_2F_EventScript_Game : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(SaffronCity_CopycatsHouse_2F.MarioWearingABucket)
}

internal val SaffronCity_CopycatsHouse_2FScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_CopycatsHouse_2F_EventScript_Doduo" to
            SaffronCity_CopycatsHouse_2F_EventScript_Doduo,
        "SaffronCity_CopycatsHouse_2F_EventScript_Doll" to
            SaffronCity_CopycatsHouse_2F_EventScript_Doll,
        "SaffronCity_CopycatsHouse_2F_EventScript_Copycat" to
            SaffronCity_CopycatsHouse_2F_EventScript_Copycat,
        "SaffronCity_CopycatsHouse_2F_EventScript_Computer" to
            SaffronCity_CopycatsHouse_2F_EventScript_Computer,
        "SaffronCity_CopycatsHouse_2F_EventScript_Game" to
            SaffronCity_CopycatsHouse_2F_EventScript_Game,
    )
