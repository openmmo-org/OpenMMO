package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.SSTidalCorridor
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_SS_TIDAL_STATE, SS_TIDAL_LAND_LILYCOVE, SSTidalCorridor_EventScript_ExitLilycove
 * goto_if_eq VAR_SS_TIDAL_STATE, SS_TIDAL_LAND_SLATEPORT, SSTidalCorridor_EventScript_ExitSlateport
 * msgbox SSTidalCorridor_Text_CanRestInCabin2, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SSTidalCorridor_EventScript_ExitSailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalCorridor_EventScript_ExitSailor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_DEFEATED_SS_TIDAL_TRAINERS, SSTidalCorridor_EventScript_EnjoyYourCruise
 * call SSTidalCorridor_EventScript_CheckIfTrainersDefeated
 * msgbox SSTidalCorridor_Text_VisitOtherCabins, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object SSTidalCorridor_EventScript_Sailor : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalCorridor_EventScript_Sailor")
}

internal object SSTidalCorridor_EventScript_Briney : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(SSTidalCorridor.BrineyWelcomeAboard)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_WINGULL, CRY_MODE_NORMAL
 * msgbox SSTidalCorridor_Text_Peeko, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object SSTidalCorridor_EventScript_Peeko : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalCorridor_EventScript_Peeko")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_eq VAR_SS_TIDAL_STATE, SS_TIDAL_DEPART_SLATEPORT, SSTidalCorridor_EventScript_LookThroughPorthole
 * goto_if_eq VAR_SS_TIDAL_STATE, SS_TIDAL_HALFWAY_SLATEPORT, SSTidalCorridor_EventScript_LookThroughPorthole
 * msgbox SSTidalCorridor_Text_HorizonSpreadsBeyondPorthole, MSGBOX_DEFAULT
 * releaseall
 * end
 * ```
 */
internal object SSTidalCorridor_EventScript_Porthole : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SSTidalCorridor_EventScript_Porthole")
}

internal object SSTidalCorridor_EventScript_Cabin1Sign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SSTidalCorridor.Cabin1)
}

internal object SSTidalCorridor_EventScript_Cabin2Sign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SSTidalCorridor.Cabin2)
}

internal object SSTidalCorridor_EventScript_Cabin3Sign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SSTidalCorridor.Cabin3)
}

internal object SSTidalCorridor_EventScript_Cabin4Sign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SSTidalCorridor.Cabin4)
}

internal val SSTidalCorridorScripts: Map<String, Script> =
    mapOf(
        "SSTidalCorridor_EventScript_ExitSailor" to SSTidalCorridor_EventScript_ExitSailor,
        "SSTidalCorridor_EventScript_Sailor" to SSTidalCorridor_EventScript_Sailor,
        "SSTidalCorridor_EventScript_Briney" to SSTidalCorridor_EventScript_Briney,
        "SSTidalCorridor_EventScript_Peeko" to SSTidalCorridor_EventScript_Peeko,
        "SSTidalCorridor_EventScript_Porthole" to SSTidalCorridor_EventScript_Porthole,
        "SSTidalCorridor_EventScript_Cabin1Sign" to SSTidalCorridor_EventScript_Cabin1Sign,
        "SSTidalCorridor_EventScript_Cabin2Sign" to SSTidalCorridor_EventScript_Cabin2Sign,
        "SSTidalCorridor_EventScript_Cabin3Sign" to SSTidalCorridor_EventScript_Cabin3Sign,
        "SSTidalCorridor_EventScript_Cabin4Sign" to SSTidalCorridor_EventScript_Cabin4Sign,
    )
