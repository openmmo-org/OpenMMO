package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_RICHARD, Route131_Text_RichardIntro, Route131_Text_RichardDefeat
 * msgbox Route131_Text_RichardPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route131_EventScript_Richard : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route131_EventScript_Richard")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HERMAN, Route131_Text_HermanIntro, Route131_Text_HermanDefeat
 * msgbox Route131_Text_HermanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route131_EventScript_Herman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route131_EventScript_Herman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SUSIE, Route131_Text_SusieIntro, Route131_Text_SusieDefeat
 * msgbox Route131_Text_SusiePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route131_EventScript_Susie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route131_EventScript_Susie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KARA, Route131_Text_KaraIntro, Route131_Text_KaraDefeat
 * msgbox Route131_Text_KaraPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route131_EventScript_Kara : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route131_EventScript_Kara")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_RELI_AND_IAN, Route131_Text_ReliIntro, Route131_Text_ReliDefeat, Route131_Text_ReliNotEnoughMons
 * msgbox Route131_Text_ReliPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route131_EventScript_Reli : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route131_EventScript_Reli")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_RELI_AND_IAN, Route131_Text_IanIntro, Route131_Text_IanDefeat, Route131_Text_IanNotEnoughMons
 * msgbox Route131_Text_IanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route131_EventScript_Ian : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route131_EventScript_Ian")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KEVIN, Route131_Text_KevinIntro, Route131_Text_KevinDefeat
 * msgbox Route131_Text_KevinPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route131_EventScript_Kevin : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route131_EventScript_Kevin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TALIA, Route131_Text_TaliaIntro, Route131_Text_TaliaDefeat
 * msgbox Route131_Text_TaliaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route131_EventScript_Talia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route131_EventScript_Talia")
}

internal val Route131Scripts: Map<String, Script> =
    mapOf(
        "Route131_EventScript_Richard" to Route131_EventScript_Richard,
        "Route131_EventScript_Herman" to Route131_EventScript_Herman,
        "Route131_EventScript_Susie" to Route131_EventScript_Susie,
        "Route131_EventScript_Kara" to Route131_EventScript_Kara,
        "Route131_EventScript_Reli" to Route131_EventScript_Reli,
        "Route131_EventScript_Ian" to Route131_EventScript_Ian,
        "Route131_EventScript_Kevin" to Route131_EventScript_Kevin,
        "Route131_EventScript_Talia" to Route131_EventScript_Talia,
    )
