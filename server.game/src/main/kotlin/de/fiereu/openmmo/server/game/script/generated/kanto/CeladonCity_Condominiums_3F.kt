package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_Condominiums_3F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object CeladonCity_Condominiums_3F_EventScript_Programmer : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_Condominiums_3F.ImTheProgrammer)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * specialvar VAR_RESULT, HasAllKantoMons
 * goto_if_eq VAR_RESULT, TRUE, CeladonCity_Condominiums_3F_EventScript_CompletedPokedex
 * msgbox CeladonCity_Condominiums_3F_Text_ImGameDesignerShowMeFinishedPokedex
 * release
 * end
 * ```
 */
internal object CeladonCity_Condominiums_3F_EventScript_Designer : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_Condominiums_3F_EventScript_Designer")
}

internal object CeladonCity_Condominiums_3F_EventScript_GraphicArtist : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_Condominiums_3F.ImTheGraphicArtist)
}

internal object CeladonCity_Condominiums_3F_EventScript_Writer : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeladonCity_Condominiums_3F.IWroteTheStory)
}

internal object CeladonCity_Condominiums_3F_EventScript_DevelopmentRoomSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_Condominiums_3F.GameFreakDevelopmentRoom)
}

internal object CeladonCity_Condominiums_3F_EventScript_Computer1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_Condominiums_3F.ItsTheGameProgram)
}

internal object CeladonCity_Condominiums_3F_EventScript_Computer2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_Condominiums_3F.SomeonesPlayingGame)
}

internal object CeladonCity_Condominiums_3F_EventScript_Computer3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_Condominiums_3F.ItsScriptBetterNotLookAtEnding)
}

internal val CeladonCity_Condominiums_3FScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_Condominiums_3F_EventScript_Programmer" to
            CeladonCity_Condominiums_3F_EventScript_Programmer,
        "CeladonCity_Condominiums_3F_EventScript_Designer" to
            CeladonCity_Condominiums_3F_EventScript_Designer,
        "CeladonCity_Condominiums_3F_EventScript_GraphicArtist" to
            CeladonCity_Condominiums_3F_EventScript_GraphicArtist,
        "CeladonCity_Condominiums_3F_EventScript_Writer" to
            CeladonCity_Condominiums_3F_EventScript_Writer,
        "CeladonCity_Condominiums_3F_EventScript_DevelopmentRoomSign" to
            CeladonCity_Condominiums_3F_EventScript_DevelopmentRoomSign,
        "CeladonCity_Condominiums_3F_EventScript_Computer1" to
            CeladonCity_Condominiums_3F_EventScript_Computer1,
        "CeladonCity_Condominiums_3F_EventScript_Computer2" to
            CeladonCity_Condominiums_3F_EventScript_Computer2,
        "CeladonCity_Condominiums_3F_EventScript_Computer3" to
            CeladonCity_Condominiums_3F_EventScript_Computer3,
    )
