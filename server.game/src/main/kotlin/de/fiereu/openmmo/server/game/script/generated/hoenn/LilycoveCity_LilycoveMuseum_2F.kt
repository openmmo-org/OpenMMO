package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.LilycoveCity_LilycoveMuseum_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_RECEIVED_GLASS_ORNAMENT, LilycoveCity_LilycoveMuseum_2F_EventScript_ReceivedGlassOrnament
 * specialvar VAR_0x8004, CountPlayerMuseumPaintings
 * switch VAR_0x8004
 * case 1, LilycoveCity_LilycoveMuseum_2F_EventScript_AddedPainting
 * case 2, LilycoveCity_LilycoveMuseum_2F_EventScript_AddedPainting
 * case 3, LilycoveCity_LilycoveMuseum_2F_EventScript_AddedPainting
 * case 4, LilycoveCity_LilycoveMuseum_2F_EventScript_AddedPainting
 * case 5, LilycoveCity_LilycoveMuseum_2F_EventScript_ThankPlayer
 * msgbox LilycoveCity_LilycoveMuseum_2F_Text_WishToFillExhibit, MSGBOX_NPC
 * end
 * ```
 */
internal object LilycoveCity_LilycoveMuseum_2F_EventScript_Curator : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_LilycoveMuseum_2F_EventScript_Curator")
}

internal object LilycoveCity_LilycoveMuseum_2F_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_LilycoveMuseum_2F.NewPaintingsSurprisedMe)
}

internal object LilycoveCity_LilycoveMuseum_2F_EventScript_ExpertM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_LilycoveMuseum_2F.NewPaintingsRatherAmusing)
}

internal object LilycoveCity_LilycoveMuseum_2F_EventScript_RichBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(LilycoveCity_LilycoveMuseum_2F.ThesePaintingsOfYourPokemon)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_CUTE_PAINTING_MADE, LilycoveCity_LilycoveMuseum_2F_EventScript_ShowCutePainting
 * msgbox LilycoveCity_LilycoveMuseum_2F_Text_ItsPinkPictureFrame, MSGBOX_SIGN
 * end
 * ```
 */
internal object LilycoveCity_LilycoveMuseum_2F_EventScript_CutePainting : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_LilycoveMuseum_2F_EventScript_CutePainting")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_TOUGH_PAINTING_MADE, LilycoveCity_LilycoveMuseum_2F_EventScript_ShowToughPainting
 * msgbox LilycoveCity_LilycoveMuseum_2F_Text_ItsYellowPictureFrame, MSGBOX_SIGN
 * end
 * ```
 */
internal object LilycoveCity_LilycoveMuseum_2F_EventScript_ToughPainting : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_LilycoveMuseum_2F_EventScript_ToughPainting")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_COOL_PAINTING_MADE, LilycoveCity_LilycoveMuseum_2F_EventScript_ShowCoolPainting
 * msgbox LilycoveCity_LilycoveMuseum_2F_Text_ItsRedPictureFrame, MSGBOX_SIGN
 * end
 * ```
 */
internal object LilycoveCity_LilycoveMuseum_2F_EventScript_CoolPainting : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_LilycoveMuseum_2F_EventScript_CoolPainting")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_BEAUTY_PAINTING_MADE, LilycoveCity_LilycoveMuseum_2F_EventScript_ShowBeautyPainting
 * msgbox LilycoveCity_LilycoveMuseum_2F_Text_ItsBluePictureFrame, MSGBOX_SIGN
 * end
 * ```
 */
internal object LilycoveCity_LilycoveMuseum_2F_EventScript_BeautyPainting : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_LilycoveMuseum_2F_EventScript_BeautyPainting")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_SMART_PAINTING_MADE, LilycoveCity_LilycoveMuseum_2F_EventScript_ShowSmartPainting
 * msgbox LilycoveCity_LilycoveMuseum_2F_Text_ItsGreenPictureFrame, MSGBOX_SIGN
 * end
 * ```
 */
internal object LilycoveCity_LilycoveMuseum_2F_EventScript_SmartPainting : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port LilycoveCity_LilycoveMuseum_2F_EventScript_SmartPainting")
}

internal val LilycoveCity_LilycoveMuseum_2FScripts: Map<String, Script> =
    mapOf(
        "LilycoveCity_LilycoveMuseum_2F_EventScript_Curator" to
            LilycoveCity_LilycoveMuseum_2F_EventScript_Curator,
        "LilycoveCity_LilycoveMuseum_2F_EventScript_Girl" to
            LilycoveCity_LilycoveMuseum_2F_EventScript_Girl,
        "LilycoveCity_LilycoveMuseum_2F_EventScript_ExpertM" to
            LilycoveCity_LilycoveMuseum_2F_EventScript_ExpertM,
        "LilycoveCity_LilycoveMuseum_2F_EventScript_RichBoy" to
            LilycoveCity_LilycoveMuseum_2F_EventScript_RichBoy,
        "LilycoveCity_LilycoveMuseum_2F_EventScript_CutePainting" to
            LilycoveCity_LilycoveMuseum_2F_EventScript_CutePainting,
        "LilycoveCity_LilycoveMuseum_2F_EventScript_ToughPainting" to
            LilycoveCity_LilycoveMuseum_2F_EventScript_ToughPainting,
        "LilycoveCity_LilycoveMuseum_2F_EventScript_CoolPainting" to
            LilycoveCity_LilycoveMuseum_2F_EventScript_CoolPainting,
        "LilycoveCity_LilycoveMuseum_2F_EventScript_BeautyPainting" to
            LilycoveCity_LilycoveMuseum_2F_EventScript_BeautyPainting,
        "LilycoveCity_LilycoveMuseum_2F_EventScript_SmartPainting" to
            LilycoveCity_LilycoveMuseum_2F_EventScript_SmartPainting,
    )
