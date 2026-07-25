package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_FRANKLIN, Route133_Text_FranklinIntro, Route133_Text_FranklinDefeat
 * msgbox Route133_Text_FranklinPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route133_EventScript_Franklin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_Franklin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LINDA, Route133_Text_LindaIntro, Route133_Text_LindaDefeat
 * msgbox Route133_Text_LindaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route133_EventScript_Linda : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_Linda")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_DEBRA, Route133_Text_DebraIntro, Route133_Text_DebraDefeat
 * msgbox Route133_Text_DebraPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route133_EventScript_Debra : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_Debra")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_BIG_PEARL
 * end
 * ```
 */
internal object Route133_EventScript_ItemBigPearl : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_ItemBigPearl")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_STAR_PIECE
 * end
 * ```
 */
internal object Route133_EventScript_ItemStarPiece : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_ItemStarPiece")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BECK, Route133_Text_BeckIntro, Route133_Text_BeckDefeat
 * msgbox Route133_Text_BeckPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route133_EventScript_Beck : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_Beck")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_WARREN, Route133_Text_WarrenIntro, Route133_Text_WarrenDefeat
 * msgbox Route133_Text_WarrenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route133_EventScript_Warren : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_Warren")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MOLLIE, Route133_Text_MollieIntro, Route133_Text_MollieDefeat
 * msgbox Route133_Text_MolliePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route133_EventScript_Mollie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_Mollie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CONOR, Route133_Text_ConorIntro, Route133_Text_ConorDefeat
 * msgbox Route133_Text_ConorPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route133_EventScript_Conor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_Conor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_REVIVE
 * end
 * ```
 */
internal object Route133_EventScript_ItemMaxRevive : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route133_EventScript_ItemMaxRevive")
}

internal val Route133Scripts: Map<String, Script> =
    mapOf(
        "Route133_EventScript_Franklin" to Route133_EventScript_Franklin,
        "Route133_EventScript_Linda" to Route133_EventScript_Linda,
        "Route133_EventScript_Debra" to Route133_EventScript_Debra,
        "Route133_EventScript_ItemBigPearl" to Route133_EventScript_ItemBigPearl,
        "Route133_EventScript_ItemStarPiece" to Route133_EventScript_ItemStarPiece,
        "Route133_EventScript_Beck" to Route133_EventScript_Beck,
        "Route133_EventScript_Warren" to Route133_EventScript_Warren,
        "Route133_EventScript_Mollie" to Route133_EventScript_Mollie,
        "Route133_EventScript_Conor" to Route133_EventScript_Conor,
        "Route133_EventScript_ItemMaxRevive" to Route133_EventScript_ItemMaxRevive,
    )
