package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_CoveLilyMotel_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_unset FLAG_TEMP_2, LilycoveCity_CoveLilyMotel_2F_EventScript_ShowMeCompletedDex
 * call_if_set FLAG_TEMP_2, LilycoveCity_CoveLilyMotel_2F_EventScript_ShowDiploma
 * specialvar VAR_RESULT, HasAllHoennMons
 * goto_if_eq VAR_RESULT, TRUE, LilycoveCity_CoveLilyMotel_2F_EventScript_AllHoennMonsFanfare
 * release
 * end
 * ```
 */
internal object LilycoveCity_CoveLilyMotel_2F_EventScript_GameDesigner : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_CoveLilyMotel_2F_EventScript_GameDesigner")
}

internal object LilycoveCity_CoveLilyMotel_2F_EventScript_GraphicArtist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_CoveLilyMotel_2F.ImTheGraphicArtist)
}

internal object LilycoveCity_CoveLilyMotel_2F_EventScript_FatMan : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(LilycoveCity_CoveLilyMotel_2F.GirlsAreCute)
}

internal object LilycoveCity_CoveLilyMotel_2F_EventScript_Programmer : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_CoveLilyMotel_2F.ImTheProgrammer)
}

internal object LilycoveCity_CoveLilyMotel_2F_EventScript_GameBoyKid : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_CoveLilyMotel_2F.NeverLeaveWithoutGameBoy)
}

internal object LilycoveCity_CoveLilyMotel_2F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_CoveLilyMotel_2F.SeaBreezeTicklesHeart)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MET_SCOTT_IN_LILYCOVE, LilycoveCity_CoveLilyMotel_2F_EventScript_MetScott
 * msgbox LilycoveCity_CoveLilyMotel_2F_Text_SnoozingPreferBattles, MSGBOX_DEFAULT
 * addvar VAR_SCOTT_STATE, 1
 * setflag FLAG_MET_SCOTT_IN_LILYCOVE
 * release
 * end
 * ```
 */
internal object LilycoveCity_CoveLilyMotel_2F_EventScript_Scott : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_CoveLilyMotel_2F_EventScript_Scott")
}

internal val LilycoveCity_CoveLilyMotel_2FScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_CoveLilyMotel_2F_EventScript_GameDesigner" to
            LilycoveCity_CoveLilyMotel_2F_EventScript_GameDesigner,
        "LilycoveCity_CoveLilyMotel_2F_EventScript_GraphicArtist" to
            LilycoveCity_CoveLilyMotel_2F_EventScript_GraphicArtist,
        "LilycoveCity_CoveLilyMotel_2F_EventScript_FatMan" to
            LilycoveCity_CoveLilyMotel_2F_EventScript_FatMan,
        "LilycoveCity_CoveLilyMotel_2F_EventScript_Programmer" to
            LilycoveCity_CoveLilyMotel_2F_EventScript_Programmer,
        "LilycoveCity_CoveLilyMotel_2F_EventScript_GameBoyKid" to
            LilycoveCity_CoveLilyMotel_2F_EventScript_GameBoyKid,
        "LilycoveCity_CoveLilyMotel_2F_EventScript_Woman" to
            LilycoveCity_CoveLilyMotel_2F_EventScript_Woman,
        "LilycoveCity_CoveLilyMotel_2F_EventScript_Scott" to
            LilycoveCity_CoveLilyMotel_2F_EventScript_Scott,
    )
