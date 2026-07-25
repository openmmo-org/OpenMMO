package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.BattleFrontier_Lounge5
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox BattleFrontier_Lounge5_Text_NatureGirlGreeting, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, BattleFrontier_Lounge5_EventScript_NatureGirlNoneShown
 * special ChoosePartyMon
 * lock
 * faceplayer
 * goto_if_eq VAR_0x8004, PARTY_NOTHING_CHOSEN, BattleFrontier_Lounge5_EventScript_NatureGirlNoneShown
 * specialvar VAR_RESULT, ScriptGetPartyMonSpecies
 * goto_if_eq VAR_RESULT, SPECIES_EGG, BattleFrontier_Lounge5_EventScript_NatureGirlEgg
 * special ShowNatureGirlMessage
 * waitmessage
 * waitbuttonpress
 * release
 * end
 * ```
 */
internal object BattleFrontier_Lounge5_EventScript_NatureGirl : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port BattleFrontier_Lounge5_EventScript_NatureGirl")
}

internal object BattleFrontier_Lounge5_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge5.LadyClaimsSheUnderstandsPokemon)
}

internal object BattleFrontier_Lounge5_EventScript_BlackBelt : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge5.GirlSayingSomethingProfound)
}

internal object BattleFrontier_Lounge5_EventScript_LittleBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(BattleFrontier_Lounge5.GirlPlaysAtRedHouseALot)
}

internal val BattleFrontier_Lounge5Scripts: Map<String, Script> =
    mapOf(
        "BattleFrontier_Lounge5_EventScript_NatureGirl" to
            BattleFrontier_Lounge5_EventScript_NatureGirl,
        "BattleFrontier_Lounge5_EventScript_Gentleman" to
            BattleFrontier_Lounge5_EventScript_Gentleman,
        "BattleFrontier_Lounge5_EventScript_BlackBelt" to
            BattleFrontier_Lounge5_EventScript_BlackBelt,
        "BattleFrontier_Lounge5_EventScript_LittleBoy" to
            BattleFrontier_Lounge5_EventScript_LittleBoy,
    )
