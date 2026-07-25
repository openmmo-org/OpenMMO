package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.FallarborTown_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * message gText_HowMayIServeYou
 * waitmessage
 * pokemart FallarborTown_Mart_Pokemart
 * msgbox gText_PleaseComeAgain, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object FallarborTown_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FallarborTown_Mart_EventScript_Clerk")
}

internal object FallarborTown_Mart_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FallarborTown_Mart.DecidingSkittyEvolve)
}

internal object FallarborTown_Mart_EventScript_PokefanM : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(FallarborTown_Mart.SellNuggetIFound)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_SKITTY, CRY_MODE_NORMAL
 * msgbox FallarborTown_Mart_Text_Skitty, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object FallarborTown_Mart_EventScript_Skitty : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port FallarborTown_Mart_EventScript_Skitty")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MOVE_TUTOR_TAUGHT_METRONOME, MoveTutor_EventScript_MetronomeTaught
 * msgbox MoveTutor_Text_MetronomeTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_MetronomeDeclined
 * call MoveTutor_EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, MoveTutor_EventScript_MetronomeDeclined
 * msgbox MoveTutor_Text_MetronomeWhichMon, MSGBOX_DEFAULT
 * setvar VAR_0x8005, TUTOR_MOVE_METRONOME
 * call MoveTutor_EventScript_OpenPartyMenu
 * goto_if_eq VAR_RESULT, 0, MoveTutor_EventScript_MetronomeDeclined
 * setflag FLAG_MOVE_TUTOR_TAUGHT_METRONOME
 * goto MoveTutor_EventScript_MetronomeTaught
 * end
 * ```
 */
internal object FallarborTown_Mart_EventScript_MetronomeTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port FallarborTown_Mart_EventScript_MetronomeTutor")
}

internal val FallarborTown_MartScripts: Map<String, Script> =
    mapOf(
        "FallarborTown_Mart_EventScript_Clerk" to FallarborTown_Mart_EventScript_Clerk,
        "FallarborTown_Mart_EventScript_Woman" to FallarborTown_Mart_EventScript_Woman,
        "FallarborTown_Mart_EventScript_PokefanM" to FallarborTown_Mart_EventScript_PokefanM,
        "FallarborTown_Mart_EventScript_Skitty" to FallarborTown_Mart_EventScript_Skitty,
        "FallarborTown_Mart_EventScript_MetronomeTutor" to
            FallarborTown_Mart_EventScript_MetronomeTutor,
    )
