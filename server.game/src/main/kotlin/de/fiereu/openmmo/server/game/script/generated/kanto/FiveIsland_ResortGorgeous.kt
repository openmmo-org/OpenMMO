package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.FiveIsland_ResortGorgeous
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PAINTER_DAISY, FiveIsland_ResortGorgeous_Text_DaisyIntro, FiveIsland_ResortGorgeous_Text_DaisyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FiveIsland_ResortGorgeous_EventScript_DaisyRematch
 * msgbox FiveIsland_ResortGorgeous_Text_DaisyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_ResortGorgeous_EventScript_Daisy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_ResortGorgeous_EventScript_Daisy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PAINTER_CELINA, FiveIsland_ResortGorgeous_Text_CelinaIntro, FiveIsland_ResortGorgeous_Text_CelinaDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FiveIsland_ResortGorgeous_EventScript_CelinaRematch
 * msgbox FiveIsland_ResortGorgeous_Text_CelinaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_ResortGorgeous_EventScript_Celina : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_ResortGorgeous_EventScript_Celina")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PAINTER_RAYNA, FiveIsland_ResortGorgeous_Text_RaynaIntro, FiveIsland_ResortGorgeous_Text_RaynaDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FiveIsland_ResortGorgeous_EventScript_RaynaRematch
 * msgbox FiveIsland_ResortGorgeous_Text_RaynaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_ResortGorgeous_EventScript_Rayna : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_ResortGorgeous_EventScript_Rayna")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LADY_JACKI, FiveIsland_ResortGorgeous_Text_JackiIntro, FiveIsland_ResortGorgeous_Text_JackiDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FiveIsland_ResortGorgeous_EventScript_JackiRematch
 * msgbox FiveIsland_ResortGorgeous_Text_JackiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_ResortGorgeous_EventScript_Jacki : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_ResortGorgeous_EventScript_Jacki")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_LADY_GILLIAN, FiveIsland_ResortGorgeous_Text_GillianIntro, FiveIsland_ResortGorgeous_Text_GillianDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FiveIsland_ResortGorgeous_EventScript_GillianRematch
 * msgbox FiveIsland_ResortGorgeous_Text_GillianPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_ResortGorgeous_EventScript_Gillian : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_ResortGorgeous_EventScript_Gillian")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_YOUNGSTER_DESTIN, FiveIsland_ResortGorgeous_Text_DestinIntro, FiveIsland_ResortGorgeous_Text_DestinDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FiveIsland_ResortGorgeous_EventScript_DestinRematch
 * msgbox FiveIsland_ResortGorgeous_Text_DestinPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_ResortGorgeous_EventScript_Destin : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_ResortGorgeous_EventScript_Destin")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_SWIMMER_MALE_TOBY, FiveIsland_ResortGorgeous_Text_TobyIntro, FiveIsland_ResortGorgeous_Text_TobyDefeat
 * specialvar VAR_RESULT, ShouldTryRematchBattle
 * goto_if_eq VAR_RESULT, TRUE, FiveIsland_ResortGorgeous_EventScript_TobyRematch
 * msgbox FiveIsland_ResortGorgeous_Text_TobyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object FiveIsland_ResortGorgeous_EventScript_Toby : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FiveIsland_ResortGorgeous_EventScript_Toby")
}

internal object FiveIsland_ResortGorgeous_EventScript_SelphysHouseSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(FiveIsland_ResortGorgeous.SelphysHouse)
}

internal val FiveIsland_ResortGorgeousScripts: Map<String, Script> =
    mapOf(
        "FiveIsland_ResortGorgeous_EventScript_Daisy" to
            FiveIsland_ResortGorgeous_EventScript_Daisy,
        "FiveIsland_ResortGorgeous_EventScript_Celina" to
            FiveIsland_ResortGorgeous_EventScript_Celina,
        "FiveIsland_ResortGorgeous_EventScript_Rayna" to
            FiveIsland_ResortGorgeous_EventScript_Rayna,
        "FiveIsland_ResortGorgeous_EventScript_Jacki" to
            FiveIsland_ResortGorgeous_EventScript_Jacki,
        "FiveIsland_ResortGorgeous_EventScript_Gillian" to
            FiveIsland_ResortGorgeous_EventScript_Gillian,
        "FiveIsland_ResortGorgeous_EventScript_Destin" to
            FiveIsland_ResortGorgeous_EventScript_Destin,
        "FiveIsland_ResortGorgeous_EventScript_Toby" to FiveIsland_ResortGorgeous_EventScript_Toby,
        "FiveIsland_ResortGorgeous_EventScript_SelphysHouseSign" to
            FiveIsland_ResortGorgeous_EventScript_SelphysHouseSign,
    )
