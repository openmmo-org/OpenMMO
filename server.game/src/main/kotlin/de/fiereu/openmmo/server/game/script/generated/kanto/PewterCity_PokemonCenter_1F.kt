package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PewterCity_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * fadeoutbgm 0
 * playbgm MUS_JIGGLYPUFF, 0
 * message PewterCity_PokemonCenter_1F_Text_Jigglypuff
 * waitmessage
 * delay 455
 * playbgm MUS_POKE_CENTER, 1
 * release
 * end
 * ```
 */
internal object PewterCity_PokemonCenter_1F_EventScript_Jigglypuff : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PewterCity_PokemonCenter_1F_EventScript_Jigglypuff")
}

internal object PewterCity_PokemonCenter_1F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_PokemonCenter_1F.TeamRocketMtMoonImOnPhone)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call EventScript_PkmnCenterNurse
 * release
 * end
 * ```
 */
internal object PewterCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PewterCity_PokemonCenter_1F_EventScript_Nurse")
}

internal object PewterCity_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_PokemonCenter_1F.WhenJiggylypuffSingsMonsGetDrowsy)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq GAVE_PROFILE, TRUE, EventScript_AlreadyGaveProfile
 * goto_if_set FLAG_SYS_SET_TRAINER_CARD_PROFILE, EventScript_GivenProfileBefore
 * msgbox Text_IdLoveToHearYourProfile
 * goto EventScript_AskForProfile
 * end
 * ```
 */
internal object MysteryEventClub_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MysteryEventClub_EventScript_Woman")
}

internal object PewterCity_PokemonCenter_1F_EventScript_GBAKid1 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_PokemonCenter_1F.TradingMyClefairyForPikachu)
}

internal object PewterCity_PokemonCenter_1F_EventScript_GBAKid2 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(PewterCity_PokemonCenter_1F.TradingPikachuWithKid)
}

internal val PewterCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "PewterCity_PokemonCenter_1F_EventScript_Jigglypuff" to
            PewterCity_PokemonCenter_1F_EventScript_Jigglypuff,
        "PewterCity_PokemonCenter_1F_EventScript_Gentleman" to
            PewterCity_PokemonCenter_1F_EventScript_Gentleman,
        "PewterCity_PokemonCenter_1F_EventScript_Nurse" to
            PewterCity_PokemonCenter_1F_EventScript_Nurse,
        "PewterCity_PokemonCenter_1F_EventScript_Youngster" to
            PewterCity_PokemonCenter_1F_EventScript_Youngster,
        "MysteryEventClub_EventScript_Woman" to MysteryEventClub_EventScript_Woman,
        "PewterCity_PokemonCenter_1F_EventScript_GBAKid1" to
            PewterCity_PokemonCenter_1F_EventScript_GBAKid1,
        "PewterCity_PokemonCenter_1F_EventScript_GBAKid2" to
            PewterCity_PokemonCenter_1F_EventScript_GBAKid2,
    )
