package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SevenIsland_SevaultCanyon
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_CRUSH_GIRL_CYNDY, SevenIsland_SevaultCanyon_Text_CyndyIntro, SevenIsland_SevaultCanyon_Text_CyndyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_SevaultCanyon_EventScript_CyndyRematch
 * msgbox SevenIsland_SevaultCanyon_Text_CyndyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_Cyndy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_Cyndy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_TAMER_EVAN, SevenIsland_SevaultCanyon_Text_EvanIntro, SevenIsland_SevaultCanyon_Text_EvanDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_SevaultCanyon_EventScript_EvanRematch
 * msgbox SevenIsland_SevaultCanyon_Text_EvanPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_Evan : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_Evan")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PKMN_RANGER_JACKSON, SevenIsland_SevaultCanyon_Text_JacksonIntro, SevenIsland_SevaultCanyon_Text_JacksonDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_SevaultCanyon_EventScript_JacksonRematch
 * msgbox SevenIsland_SevaultCanyon_Text_JacksonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_Jackson : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_Jackson")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PKMN_RANGER_KATELYN, SevenIsland_SevaultCanyon_Text_KatelynIntro, SevenIsland_SevaultCanyon_Text_KatelynDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_SevaultCanyon_EventScript_KatelynRematch
 * msgbox SevenIsland_SevaultCanyon_Text_KatelynPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_Katelyn : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_Katelyn")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_LEROY, SevenIsland_SevaultCanyon_Text_LeroyIntro, SevenIsland_SevaultCanyon_Text_LeroyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_SevaultCanyon_EventScript_LeroyRematch
 * msgbox SevenIsland_SevaultCanyon_Text_LeroyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_Leroy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_Leroy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_COOLTRAINER_MICHELLE, SevenIsland_SevaultCanyon_Text_MichelleIntro, SevenIsland_SevaultCanyon_Text_MichelleDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_SevaultCanyon_EventScript_MichelleRematch
 * msgbox SevenIsland_SevaultCanyon_Text_MichellePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_Michelle : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_Michelle")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_COOL_COUPLE_LEX_NYA, SevenIsland_SevaultCanyon_Text_LexIntro, SevenIsland_SevaultCanyon_Text_LexDefeat, SevenIsland_SevaultCanyon_Text_LexNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_SevaultCanyon_EventScript_LexRematch
 * msgbox SevenIsland_SevaultCanyon_Text_LexPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_Lex : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_Lex")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_double TRAINER_COOL_COUPLE_LEX_NYA, SevenIsland_SevaultCanyon_Text_NyaIntro, SevenIsland_SevaultCanyon_Text_NyaDefeat, SevenIsland_SevaultCanyon_Text_NyaNotEnoughMons
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, SevenIsland_SevaultCanyon_EventScript_NyaRematch
 * msgbox SevenIsland_SevaultCanyon_Text_NyaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_Nya : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_Nya")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_KINGS_ROCK
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_ItemKingsRock : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_ItemKingsRock")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ELIXIR
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_ItemMaxElixir : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_ItemMaxElixir")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_NUGGET
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_ItemNugget : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_ItemNugget")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * famechecker FAMECHECKER_BRUNO, 5
 * msgbox SevenIsland_SevaultCanyon_Text_BrunoTrainedWithBrawly
 * release
 * end
 * ```
 */
internal object SevenIsland_SevaultCanyon_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SevenIsland_SevaultCanyon_EventScript_BlackBelt")
}

internal object SevenIsland_SevaultCanyon_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SevenIsland_SevaultCanyon.RouteSign)
}

internal val SevenIsland_SevaultCanyonScripts: Map<String, Script> =
    mapOf(
        "SevenIsland_SevaultCanyon_EventScript_Cyndy" to
            SevenIsland_SevaultCanyon_EventScript_Cyndy,
        "SevenIsland_SevaultCanyon_EventScript_Evan" to SevenIsland_SevaultCanyon_EventScript_Evan,
        "SevenIsland_SevaultCanyon_EventScript_Jackson" to
            SevenIsland_SevaultCanyon_EventScript_Jackson,
        "SevenIsland_SevaultCanyon_EventScript_Katelyn" to
            SevenIsland_SevaultCanyon_EventScript_Katelyn,
        "SevenIsland_SevaultCanyon_EventScript_Leroy" to
            SevenIsland_SevaultCanyon_EventScript_Leroy,
        "SevenIsland_SevaultCanyon_EventScript_Michelle" to
            SevenIsland_SevaultCanyon_EventScript_Michelle,
        "SevenIsland_SevaultCanyon_EventScript_Lex" to SevenIsland_SevaultCanyon_EventScript_Lex,
        "SevenIsland_SevaultCanyon_EventScript_Nya" to SevenIsland_SevaultCanyon_EventScript_Nya,
        "SevenIsland_SevaultCanyon_EventScript_ItemKingsRock" to
            SevenIsland_SevaultCanyon_EventScript_ItemKingsRock,
        "SevenIsland_SevaultCanyon_EventScript_ItemMaxElixir" to
            SevenIsland_SevaultCanyon_EventScript_ItemMaxElixir,
        "SevenIsland_SevaultCanyon_EventScript_ItemNugget" to
            SevenIsland_SevaultCanyon_EventScript_ItemNugget,
        "SevenIsland_SevaultCanyon_EventScript_BlackBelt" to
            SevenIsland_SevaultCanyon_EventScript_BlackBelt,
        "SevenIsland_SevaultCanyon_EventScript_RouteSign" to
            SevenIsland_SevaultCanyon_EventScript_RouteSign,
    )
