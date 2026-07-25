package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeladonCity_DepartmentStore_3F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto EventScript_CounterTutor
 * end
 * ```
 */
internal object CeladonCity_DepartmentStore_3F_EventScript_CounterTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port CeladonCity_DepartmentStore_3F_EventScript_CounterTutor")
}

internal object CeladonCity_DepartmentStore_3F_EventScript_GBAKid1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_3F.OTStandsForOriginalTrainer)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_GBAKid3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_3F.HaunterEvolvedOnTrade)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_GBAKid2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_3F.BuddyTradingKangaskhanForHaunter)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_LittleGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(CeladonCity_DepartmentStore_3F.CanIdentifyTradeMonsByID)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_FloorSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeladonCity_DepartmentStore_3F.TVGameShop)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_TV1 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeladonCity_DepartmentStore_3F.AnRPG)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_TV2 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeladonCity_DepartmentStore_3F.SportsGame)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_TV3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(CeladonCity_DepartmentStore_3F.PuzzleGame)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_TV4 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_DepartmentStore_3F.FightingGame)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_SuperNES : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_DepartmentStore_3F.ItsSuperNES)
}

internal object CeladonCity_DepartmentStore_3F_EventScript_Poster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(CeladonCity_DepartmentStore_3F.RedGreenBothArePokemon)
}

internal val CeladonCity_DepartmentStore_3FScripts: Map<String, Script> =
    mapOf(
        "CeladonCity_DepartmentStore_3F_EventScript_CounterTutor" to
            CeladonCity_DepartmentStore_3F_EventScript_CounterTutor,
        "CeladonCity_DepartmentStore_3F_EventScript_GBAKid1" to
            CeladonCity_DepartmentStore_3F_EventScript_GBAKid1,
        "CeladonCity_DepartmentStore_3F_EventScript_GBAKid3" to
            CeladonCity_DepartmentStore_3F_EventScript_GBAKid3,
        "CeladonCity_DepartmentStore_3F_EventScript_GBAKid2" to
            CeladonCity_DepartmentStore_3F_EventScript_GBAKid2,
        "CeladonCity_DepartmentStore_3F_EventScript_LittleGirl" to
            CeladonCity_DepartmentStore_3F_EventScript_LittleGirl,
        "CeladonCity_DepartmentStore_3F_EventScript_FloorSign" to
            CeladonCity_DepartmentStore_3F_EventScript_FloorSign,
        "CeladonCity_DepartmentStore_3F_EventScript_TV1" to
            CeladonCity_DepartmentStore_3F_EventScript_TV1,
        "CeladonCity_DepartmentStore_3F_EventScript_TV2" to
            CeladonCity_DepartmentStore_3F_EventScript_TV2,
        "CeladonCity_DepartmentStore_3F_EventScript_TV3" to
            CeladonCity_DepartmentStore_3F_EventScript_TV3,
        "CeladonCity_DepartmentStore_3F_EventScript_TV4" to
            CeladonCity_DepartmentStore_3F_EventScript_TV4,
        "CeladonCity_DepartmentStore_3F_EventScript_SuperNES" to
            CeladonCity_DepartmentStore_3F_EventScript_SuperNES,
        "CeladonCity_DepartmentStore_3F_EventScript_Poster" to
            CeladonCity_DepartmentStore_3F_EventScript_Poster,
    )
