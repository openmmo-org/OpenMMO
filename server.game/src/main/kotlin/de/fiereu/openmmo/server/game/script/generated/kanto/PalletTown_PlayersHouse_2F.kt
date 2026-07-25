package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.PalletTown_PlayersHouse_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

internal object PalletTown_PlayersHouse_2F_EventScript_NES : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PalletTown_PlayersHouse_2F.PlayedWithNES)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lockall
 * setvar VAR_0x8004, HELPCONTEXT_BEDROOM_PC
 * special Script_SetHelpContext
 * setvar VAR_0x8004, 1
 * special AnimatePcTurnOn
 * playse SE_PC_ON
 * msgbox Text_PlayerBootedUpPC
 * special BedroomPC
 * waitstate
 * special SetHelpContextForMap
 * releaseall
 * end
 * ```
 */
internal object PalletTown_PlayersHouse_2F_EventScript_PC : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port PalletTown_PlayersHouse_2F_EventScript_PC")
}

internal object PalletTown_PlayersHouse_2F_EventScript_Sign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(PalletTown_PlayersHouse_2F.PressLRForHelp)
}

internal val PalletTown_PlayersHouse_2FScripts: Map<String, Script> =
    mapOf(
        "PalletTown_PlayersHouse_2F_EventScript_NES" to PalletTown_PlayersHouse_2F_EventScript_NES,
        "PalletTown_PlayersHouse_2F_EventScript_PC" to PalletTown_PlayersHouse_2F_EventScript_PC,
        "PalletTown_PlayersHouse_2F_EventScript_Sign" to
            PalletTown_PlayersHouse_2F_EventScript_Sign,
    )
