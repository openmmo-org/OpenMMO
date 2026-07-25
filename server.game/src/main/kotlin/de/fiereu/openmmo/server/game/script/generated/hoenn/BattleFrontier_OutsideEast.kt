package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_OutsideEast
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object BattleFrontier_OutsideEast_EventScript_NinjaBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.BattleTowerFeelsSpecial)
}

internal object BattleFrontier_OutsideEast_EventScript_Man1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.ConquerLeagueAndFrontier)
}

internal object BattleFrontier_OutsideEast_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BattleFrontier_OutsideEast.PyramidTooHarsh)
}

internal object BattleFrontier_OutsideEast_EventScript_HexManiac : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.ThriveInDarkness)
}

internal object BattleFrontier_OutsideEast_EventScript_BlackBelt1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.PutTogetherUltimateTeam)
}

internal object BattleFrontier_OutsideEast_EventScript_Cook : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.BelieveInYouBuddy)
}

internal object BattleFrontier_OutsideEast_EventScript_ExpertF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.OnceBeatGymLeader)
}

internal object BattleFrontier_OutsideEast_EventScript_TriathleteM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.DidScottBringYouHere)
}

internal object BattleFrontier_OutsideEast_EventScript_RichBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.PeopleCallMeBusybody)
}

internal object BattleFrontier_OutsideEast_EventScript_TriathleteF : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.FastOnBikeAndBattles)
}

internal object BattleFrontier_OutsideEast_EventScript_Twin : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.BetterThanDaddyAtPokemon)
}

internal object BattleFrontier_OutsideEast_EventScript_Man2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.GoRackUpSomeWinsForDaddy)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_ZIGZAGOON, CRY_MODE_NORMAL
 * msgbox BattleFrontier_OutsideEast_Text_ZigzagoonLooksVacant, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object BattleFrontier_OutsideEast_EventScript_Zigzagoon : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_OutsideEast_EventScript_Zigzagoon")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playse SE_SUDOWOODO_SHAKE
 * applymovement LOCALID_FRONTIER_SUDOWOODO, BattleFrontier_OutsideEast_Movement_SudowoodoShake
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object BattleFrontier_OutsideEast_EventScript_Sudowoodo : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_OutsideEast_EventScript_Sudowoodo")
}

internal object BattleFrontier_OutsideEast_EventScript_Maniac1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.HeardPrettyGirlAtBattleArena)
}

internal object BattleFrontier_OutsideEast_EventScript_Girl : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.SometimesImportantOldManInThere)
}

internal object BattleFrontier_OutsideEast_EventScript_PsychicM : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.LegendOfBattlePyramid)
}

internal object BattleFrontier_OutsideEast_EventScript_Man3 : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(BattleFrontier_OutsideEast.GotWipedOut)
}

internal object BattleFrontier_OutsideEast_EventScript_Woman1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.ToughTrainerInBattleTower)
}

internal object BattleFrontier_OutsideEast_EventScript_Woman2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.EnoughBattlePointsForDoll)
}

internal object BattleFrontier_OutsideEast_EventScript_BlackBelt2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.LikeToHaveNameRecordedHere)
}

internal object BattleFrontier_OutsideEast_EventScript_Maniac2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.CanDoAnythingWithYou)
}

internal object BattleFrontier_OutsideEast_EventScript_Woman3 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.PowerOfOurLoveWillOvercome)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_SUDOWOODO, BattleFrontier_OutsideEast_EventScript_OldWomanSudowoodoGone
 * msgbox BattleFrontier_OutsideEast_Text_OddTreeHereSeemsToWiggle, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object BattleFrontier_OutsideEast_EventScript_OldWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_OutsideEast_EventScript_OldWoman")
}

internal object BattleFrontier_OutsideEast_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.FrontierNotExclusivelyForToughTrainers)
}

internal object BattleFrontier_OutsideEast_EventScript_Camper : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_OutsideEast.StickyMonWithLongTail)
}

internal object BattleFrontier_OutsideEast_EventScript_BattleTowerSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(BattleFrontier_OutsideEast.BattleTowerSign)
}

internal object BattleFrontier_OutsideEast_EventScript_BattlePalaceSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(BattleFrontier_OutsideEast.BattlePalaceSign)
}

internal object BattleFrontier_OutsideEast_EventScript_BattleArenaSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(BattleFrontier_OutsideEast.BattleArenaSign)
}

internal object BattleFrontier_OutsideEast_EventScript_BattlePyramidSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(BattleFrontier_OutsideEast.BattlePyramidSign)
}

internal object BattleFrontier_OutsideEast_EventScript_ExchangeCornerSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(BattleFrontier_OutsideEast.ExchangeCornerSign)
}

internal object BattleFrontier_OutsideEast_EventScript_RankingHallSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.sign(BattleFrontier_OutsideEast.RankingHallSign)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * script body not found
 * ```
 */
internal object Common_EventScript_ShowPokemonCenterSign : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port Common_EventScript_ShowPokemonCenterSign")
}

internal val BattleFrontier_OutsideEastScripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_OutsideEast_EventScript_NinjaBoy" to
            BattleFrontier_OutsideEast_EventScript_NinjaBoy,
        "BattleFrontier_OutsideEast_EventScript_Man1" to
            BattleFrontier_OutsideEast_EventScript_Man1,
        "BattleFrontier_OutsideEast_EventScript_Hiker" to
            BattleFrontier_OutsideEast_EventScript_Hiker,
        "BattleFrontier_OutsideEast_EventScript_HexManiac" to
            BattleFrontier_OutsideEast_EventScript_HexManiac,
        "BattleFrontier_OutsideEast_EventScript_BlackBelt1" to
            BattleFrontier_OutsideEast_EventScript_BlackBelt1,
        "BattleFrontier_OutsideEast_EventScript_Cook" to
            BattleFrontier_OutsideEast_EventScript_Cook,
        "BattleFrontier_OutsideEast_EventScript_ExpertF" to
            BattleFrontier_OutsideEast_EventScript_ExpertF,
        "BattleFrontier_OutsideEast_EventScript_TriathleteM" to
            BattleFrontier_OutsideEast_EventScript_TriathleteM,
        "BattleFrontier_OutsideEast_EventScript_RichBoy" to
            BattleFrontier_OutsideEast_EventScript_RichBoy,
        "BattleFrontier_OutsideEast_EventScript_TriathleteF" to
            BattleFrontier_OutsideEast_EventScript_TriathleteF,
        "BattleFrontier_OutsideEast_EventScript_Twin" to
            BattleFrontier_OutsideEast_EventScript_Twin,
        "BattleFrontier_OutsideEast_EventScript_Man2" to
            BattleFrontier_OutsideEast_EventScript_Man2,
        "BattleFrontier_OutsideEast_EventScript_Zigzagoon" to
            BattleFrontier_OutsideEast_EventScript_Zigzagoon,
        "BattleFrontier_OutsideEast_EventScript_Sudowoodo" to
            BattleFrontier_OutsideEast_EventScript_Sudowoodo,
        "BattleFrontier_OutsideEast_EventScript_Maniac1" to
            BattleFrontier_OutsideEast_EventScript_Maniac1,
        "BattleFrontier_OutsideEast_EventScript_Girl" to
            BattleFrontier_OutsideEast_EventScript_Girl,
        "BattleFrontier_OutsideEast_EventScript_PsychicM" to
            BattleFrontier_OutsideEast_EventScript_PsychicM,
        "BattleFrontier_OutsideEast_EventScript_Man3" to
            BattleFrontier_OutsideEast_EventScript_Man3,
        "BattleFrontier_OutsideEast_EventScript_Woman1" to
            BattleFrontier_OutsideEast_EventScript_Woman1,
        "BattleFrontier_OutsideEast_EventScript_Woman2" to
            BattleFrontier_OutsideEast_EventScript_Woman2,
        "BattleFrontier_OutsideEast_EventScript_BlackBelt2" to
            BattleFrontier_OutsideEast_EventScript_BlackBelt2,
        "BattleFrontier_OutsideEast_EventScript_Maniac2" to
            BattleFrontier_OutsideEast_EventScript_Maniac2,
        "BattleFrontier_OutsideEast_EventScript_Woman3" to
            BattleFrontier_OutsideEast_EventScript_Woman3,
        "BattleFrontier_OutsideEast_EventScript_OldWoman" to
            BattleFrontier_OutsideEast_EventScript_OldWoman,
        "BattleFrontier_OutsideEast_EventScript_Gentleman" to
            BattleFrontier_OutsideEast_EventScript_Gentleman,
        "BattleFrontier_OutsideEast_EventScript_Camper" to
            BattleFrontier_OutsideEast_EventScript_Camper,
        "BattleFrontier_OutsideEast_EventScript_BattleTowerSign" to
            BattleFrontier_OutsideEast_EventScript_BattleTowerSign,
        "BattleFrontier_OutsideEast_EventScript_BattlePalaceSign" to
            BattleFrontier_OutsideEast_EventScript_BattlePalaceSign,
        "BattleFrontier_OutsideEast_EventScript_BattleArenaSign" to
            BattleFrontier_OutsideEast_EventScript_BattleArenaSign,
        "BattleFrontier_OutsideEast_EventScript_BattlePyramidSign" to
            BattleFrontier_OutsideEast_EventScript_BattlePyramidSign,
        "BattleFrontier_OutsideEast_EventScript_ExchangeCornerSign" to
            BattleFrontier_OutsideEast_EventScript_ExchangeCornerSign,
        "BattleFrontier_OutsideEast_EventScript_RankingHallSign" to
            BattleFrontier_OutsideEast_EventScript_RankingHallSign,
        "Common_EventScript_ShowPokemonCenterSign" to Common_EventScript_ShowPokemonCenterSign,
    )
