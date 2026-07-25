package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.Route114
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LENNY, Route114_Text_LennyIntro, Route114_Text_LennyDefeat
 * msgbox Route114_Text_LennyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Lenny : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Lenny")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LUCAS_1, Route114_Text_LucasIntro, Route114_Text_LucasDefeat
 * msgbox Route114_Text_LucasPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Lucas : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Lucas")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SHANE, Route114_Text_ShaneIntro, Route114_Text_ShaneDefeat
 * msgbox Route114_Text_ShanePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Shane : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Shane")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_NANCY, Route114_Text_NancyIntro, Route114_Text_NancyDefeat
 * msgbox Route114_Text_NancyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Nancy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Nancy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_STEVE_1, Route114_Text_SteveIntro, Route114_Text_SteveDefeat, Route114_EventScript_RegisterSteve
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route114_EventScript_RematchSteve
 * msgbox Route114_Text_StevePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route114_EventScript_Steve : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Steve")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_RARE_CANDY
 * end
 * ```
 */
internal object Route114_EventScript_ItemRareCandy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_ItemRareCandy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PROTEIN
 * end
 * ```
 */
internal object Route114_EventScript_ItemProtein : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_ItemProtein")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_RECEIVED_TM_ROAR, Route114_EventScript_ReceivedRoar
 * msgbox Route114_Text_AllMyMonDoesIsRoarTakeThis, MSGBOX_DEFAULT
 * giveitem ITEM_TM_ROAR
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_RECEIVED_TM_ROAR
 * msgbox Route114_Text_ExplainRoar, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route114_EventScript_RoarGentleman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_RoarGentleman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_POOCHYENA, CRY_MODE_ENCOUNTER
 * msgbox Route114_Text_Poochyena, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object Route114_EventScript_Poochyena : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Poochyena")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * dotimebasedevents
 * goto_if_set FLAG_DAILY_ROUTE_114_RECEIVED_BERRY, Route114_EventScript_ReceivedBerry
 * msgbox Route114_Text_LoveUsingBerryCrushShareBerry, MSGBOX_DEFAULT
 * random NUM_ROUTE_114_MAN_BERRIES
 * addvar VAR_RESULT, NUM_ROUTE_114_MAN_BERRIES_SKIPPED
 * addvar VAR_RESULT, FIRST_BERRY_INDEX
 * giveitem VAR_RESULT
 * goto_if_eq VAR_RESULT, FALSE, Common_EventScript_ShowBagIsFull
 * setflag FLAG_DAILY_ROUTE_114_RECEIVED_BERRY
 * msgbox Route114_Text_TryBerryCrushWithFriends, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route114_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Man")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_NOLAN, Route114_Text_NolanIntro, Route114_Text_NolanDefeat
 * msgbox Route114_Text_NolanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Nolan : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Nolan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CLAUDE, Route114_Text_ClaudeIntro, Route114_Text_ClaudeDefeat
 * msgbox Route114_Text_ClaudePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Claude : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Claude")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BERNIE_1, Route114_Text_BernieIntro, Route114_Text_BernieDefeat, Route114_EventScript_RegisterBernie
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, Route114_EventScript_RematchBernie
 * msgbox Route114_Text_BerniePostBattle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object Route114_EventScript_Bernie : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Bernie")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_TYRA_AND_IVY, Route114_Text_IvyIntro, Route114_Text_IvyDefeat, Route114_Text_IvyNotEnoughMons
 * msgbox Route114_Text_IvyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Ivy : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Ivy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_TYRA_AND_IVY, Route114_Text_TyraIntro, Route114_Text_TyraDefeat, Route114_Text_TyraNotEnoughMons
 * msgbox Route114_Text_TyraPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Tyra : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Tyra")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CHARLOTTE, Route114_Text_CharlotteIntro, Route114_Text_CharlotteDefeat
 * msgbox Route114_Text_CharlottePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Charlotte : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Charlotte")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_ANGELINA, Route114_Text_AngelinaIntro, Route114_Text_AngelinaDefeat
 * msgbox Route114_Text_AngelinaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Angelina : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Angelina")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_ENERGY_POWDER
 * end
 * ```
 */
internal object Route114_EventScript_ItemEnergyPowder : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_ItemEnergyPowder")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_KAI, Route114_Text_KaiIntro, Route114_Text_KaiDefeat
 * msgbox Route114_Text_KaiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object Route114_EventScript_Kai : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route114_EventScript_Kai")
}

internal object Route114_EventScript_MeteorFallsSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route114.MeteorFallsSign)
}

internal object Route114_EventScript_FossilManiacsHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route114.FossilManiacsHouseSign)
}

internal object Route114_EventScript_LanettesHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route114.LanettesHouse)
}

internal val Route114Scripts: Map<String, Script> =
    mapOf(
        "Route114_EventScript_Lenny" to Route114_EventScript_Lenny,
        "Route114_EventScript_Lucas" to Route114_EventScript_Lucas,
        "Route114_EventScript_Shane" to Route114_EventScript_Shane,
        "Route114_EventScript_Nancy" to Route114_EventScript_Nancy,
        "Route114_EventScript_Steve" to Route114_EventScript_Steve,
        "Route114_EventScript_ItemRareCandy" to Route114_EventScript_ItemRareCandy,
        "Route114_EventScript_ItemProtein" to Route114_EventScript_ItemProtein,
        "Route114_EventScript_RoarGentleman" to Route114_EventScript_RoarGentleman,
        "Route114_EventScript_Poochyena" to Route114_EventScript_Poochyena,
        "Route114_EventScript_Man" to Route114_EventScript_Man,
        "Route114_EventScript_Nolan" to Route114_EventScript_Nolan,
        "Route114_EventScript_Claude" to Route114_EventScript_Claude,
        "Route114_EventScript_Bernie" to Route114_EventScript_Bernie,
        "Route114_EventScript_Ivy" to Route114_EventScript_Ivy,
        "Route114_EventScript_Tyra" to Route114_EventScript_Tyra,
        "Route114_EventScript_Charlotte" to Route114_EventScript_Charlotte,
        "Route114_EventScript_Angelina" to Route114_EventScript_Angelina,
        "Route114_EventScript_ItemEnergyPowder" to Route114_EventScript_ItemEnergyPowder,
        "Route114_EventScript_Kai" to Route114_EventScript_Kai,
        "Route114_EventScript_MeteorFallsSign" to Route114_EventScript_MeteorFallsSign,
        "Route114_EventScript_FossilManiacsHouseSign" to
            Route114_EventScript_FossilManiacsHouseSign,
        "Route114_EventScript_LanettesHouseSign" to Route114_EventScript_LanettesHouseSign,
    )
