package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.VermilionCity_PokemonCenter_1F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

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
internal object VermilionCity_PokemonCenter_1F_EventScript_Nurse : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonCenter_1F_EventScript_Nurse")
}

internal object VermilionCity_PokemonCenter_1F_EventScript_Man : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VermilionCity_PokemonCenter_1F.PoisonedMonFaintedWhileWalking)
}

internal object VermilionCity_PokemonCenter_1F_EventScript_Hiker : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VermilionCity_PokemonCenter_1F.TrainerMonsStrongerThanWild)
}

internal object VermilionCity_PokemonCenter_1F_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(VermilionCity_PokemonCenter_1F.AllMonWeakToSpecificTypes)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * script body not found
 * ```
 */
internal object VermilionCity_PokemonCenter_1F_EventScript_VSSeekerWoman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonCenter_1F_EventScript_VSSeekerWoman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * famechecker FAMECHECKER_LTSURGE, 5
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * msgbox PokemonJournal_Text_SpecialFeatureLtSurge
 * releaseall
 * end
 * ```
 */
internal object VermilionCity_PokemonCenter_1F_EventScript_PokemonJournalLtSurge : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port VermilionCity_PokemonCenter_1F_EventScript_PokemonJournalLtSurge")
}

internal val VermilionCity_PokemonCenter_1FScripts: Map<String, Script> =
    mapOf(
        "VermilionCity_PokemonCenter_1F_EventScript_Nurse" to
            VermilionCity_PokemonCenter_1F_EventScript_Nurse,
        "VermilionCity_PokemonCenter_1F_EventScript_Man" to
            VermilionCity_PokemonCenter_1F_EventScript_Man,
        "VermilionCity_PokemonCenter_1F_EventScript_Hiker" to
            VermilionCity_PokemonCenter_1F_EventScript_Hiker,
        "VermilionCity_PokemonCenter_1F_EventScript_Youngster" to
            VermilionCity_PokemonCenter_1F_EventScript_Youngster,
        "VermilionCity_PokemonCenter_1F_EventScript_VSSeekerWoman" to
            VermilionCity_PokemonCenter_1F_EventScript_VSSeekerWoman,
        "VermilionCity_PokemonCenter_1F_EventScript_PokemonJournalLtSurge" to
            VermilionCity_PokemonCenter_1F_EventScript_PokemonJournalLtSurge,
    )
