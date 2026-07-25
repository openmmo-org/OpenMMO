package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MtPyre_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MARK, MtPyre_2F_Text_MarkIntro, MtPyre_2F_Text_MarkDefeat
 * msgbox MtPyre_2F_Text_MarkPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_2F_EventScript_Mark : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_2F_EventScript_Mark")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ULTRA_BALL
 * end
 * ```
 */
internal object MtPyre_2F_EventScript_ItemUltraBall : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_2F_EventScript_ItemUltraBall")
}

internal object MtPyre_2F_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MtPyre_2F.MemoriesOfSkitty)
}

internal object MtPyre_2F_EventScript_PokefanM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(MtPyre_2F.TumbledFromFloorAbove)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_DEZ_AND_LUKE, MtPyre_2F_Text_DezIntro, MtPyre_2F_Text_DezDefeat, MtPyre_2F_Text_DezNotEnoughMons
 * msgbox MtPyre_2F_Text_DezPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_2F_EventScript_Dez : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_2F_EventScript_Dez")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_DEZ_AND_LUKE, MtPyre_2F_Text_LukeIntro, MtPyre_2F_Text_LukeDefeat, MtPyre_2F_Text_LukeNotEnoughMons
 * msgbox MtPyre_2F_Text_LukePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_2F_EventScript_Luke : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_2F_EventScript_Luke")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ZANDER, MtPyre_2F_Text_ZanderIntro, MtPyre_2F_Text_ZanderDefeat
 * msgbox MtPyre_2F_Text_ZanderPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_2F_EventScript_Zander : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_2F_EventScript_Zander")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LEAH, MtPyre_2F_Text_LeahIntro, MtPyre_2F_Text_LeahDefeat
 * msgbox MtPyre_2F_Text_LeahPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtPyre_2F_EventScript_Leah : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtPyre_2F_EventScript_Leah")
}

internal val MtPyre_2FScripts: Map<String, Script> =
    mapOf(
        "MtPyre_2F_EventScript_Mark" to MtPyre_2F_EventScript_Mark,
        "MtPyre_2F_EventScript_ItemUltraBall" to MtPyre_2F_EventScript_ItemUltraBall,
        "MtPyre_2F_EventScript_Woman" to MtPyre_2F_EventScript_Woman,
        "MtPyre_2F_EventScript_PokefanM" to MtPyre_2F_EventScript_PokefanM,
        "MtPyre_2F_EventScript_Dez" to MtPyre_2F_EventScript_Dez,
        "MtPyre_2F_EventScript_Luke" to MtPyre_2F_EventScript_Luke,
        "MtPyre_2F_EventScript_Zander" to MtPyre_2F_EventScript_Zander,
        "MtPyre_2F_EventScript_Leah" to MtPyre_2F_EventScript_Leah,
    )
