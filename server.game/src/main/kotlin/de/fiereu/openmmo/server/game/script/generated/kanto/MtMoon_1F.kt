package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.MtMoon_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LASS_IRIS, MtMoon_1F_Text_IrisIntro, MtMoon_1F_Text_IrisDefeat
 * msgbox MtMoon_1F_Text_IrisPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_Iris : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_Iris")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_ROBBY, MtMoon_1F_Text_RobbyIntro, MtMoon_1F_Text_RobbyDefeat
 * msgbox MtMoon_1F_Text_RobbyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_Robby : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_Robby")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SUPER_NERD_JOVAN, MtMoon_1F_Text_JovanIntro, MtMoon_1F_Text_JovanDefeat
 * msgbox MtMoon_1F_Text_JovanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_Jovan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_Jovan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LASS_MIRIAM, MtMoon_1F_Text_MiriamIntro, MtMoon_1F_Text_MiriamDefeat
 * msgbox MtMoon_1F_Text_MiriamPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_Miriam : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_Miriam")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BUG_CATCHER_KENT, MtMoon_1F_Text_KentIntro, MtMoon_1F_Text_KentDefeat
 * msgbox MtMoon_1F_Text_KentPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_Kent : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_Kent")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_YOUNGSTER_JOSH, MtMoon_1F_Text_JoshIntro, MtMoon_1F_Text_JoshDefeat
 * msgbox MtMoon_1F_Text_JoshPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_Josh : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_Josh")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_MARCOS, MtMoon_1F_Text_MarcosIntro, MtMoon_1F_Text_MarcosDefeat
 * msgbox MtMoon_1F_Text_MarcosPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_Marcos : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_Marcos")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PARALYZE_HEAL
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_ItemParalyzeHeal : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_ItemParalyzeHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM09
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_ItemTM09 : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_ItemTM09")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_POTION
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_ItemPotion : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_ItemPotion")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ESCAPE_ROPE
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_ItemEscapeRope : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_ItemEscapeRope")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MOON_STONE
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_ItemMoonStone : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_ItemMoonStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BROCK, 4
 * msgbox MtMoon_1F_Text_BrockHelpsExcavateFossils
 * release
 * end
 * ```
 */
internal object MtMoon_1F_EventScript_BaldingMan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtMoon_1F_EventScript_BaldingMan")
}

internal object MtMoon_1F_EventScript_ZubatSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(MtMoon_1F.ZubatIsABloodsucker)
}

internal val MtMoon_1FScripts: Map<String, Script> =
    mapOf(
        "MtMoon_1F_EventScript_Iris" to MtMoon_1F_EventScript_Iris,
        "MtMoon_1F_EventScript_Robby" to MtMoon_1F_EventScript_Robby,
        "MtMoon_1F_EventScript_Jovan" to MtMoon_1F_EventScript_Jovan,
        "MtMoon_1F_EventScript_Miriam" to MtMoon_1F_EventScript_Miriam,
        "MtMoon_1F_EventScript_Kent" to MtMoon_1F_EventScript_Kent,
        "MtMoon_1F_EventScript_Josh" to MtMoon_1F_EventScript_Josh,
        "MtMoon_1F_EventScript_Marcos" to MtMoon_1F_EventScript_Marcos,
        "MtMoon_1F_EventScript_ItemParalyzeHeal" to MtMoon_1F_EventScript_ItemParalyzeHeal,
        "MtMoon_1F_EventScript_ItemTM09" to MtMoon_1F_EventScript_ItemTM09,
        "MtMoon_1F_EventScript_ItemPotion" to MtMoon_1F_EventScript_ItemPotion,
        "MtMoon_1F_EventScript_ItemRareCandy" to MtMoon_1F_EventScript_ItemRareCandy,
        "MtMoon_1F_EventScript_ItemEscapeRope" to MtMoon_1F_EventScript_ItemEscapeRope,
        "MtMoon_1F_EventScript_ItemMoonStone" to MtMoon_1F_EventScript_ItemMoonStone,
        "MtMoon_1F_EventScript_BaldingMan" to MtMoon_1F_EventScript_BaldingMan,
        "MtMoon_1F_EventScript_ZubatSign" to MtMoon_1F_EventScript_ZubatSign,
    )
