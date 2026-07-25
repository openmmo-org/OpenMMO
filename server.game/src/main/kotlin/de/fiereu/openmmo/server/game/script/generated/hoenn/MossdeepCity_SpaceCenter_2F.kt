package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.dialog.generated.hoenn.MossdeepCity_SpaceCenter_2F
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, MossdeepCity_SpaceCenter_2F_EventScript_RichBoyNormal
 * goto_if_eq VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_2F_EventScript_RichBoyMagma
 * goto_if_lt VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_2F_EventScript_RichBoyNormal
 * goto MossdeepCity_SpaceCenter_2F_EventScript_RichBoyMagma
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_2F_EventScript_RichBoy : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_2F_EventScript_RichBoy")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, MossdeepCity_SpaceCenter_2F_EventScript_GentlemanNormal
 * goto_if_eq VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_2F_EventScript_GentlemanMagma
 * goto_if_lt VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_2F_EventScript_GentlemanNormal
 * goto MossdeepCity_SpaceCenter_2F_EventScript_GentlemanMagma
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_2F_EventScript_Gentleman : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_2F_EventScript_Gentleman")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_SYS_GAME_CLEAR, MossdeepCity_SpaceCenter_2F_EventScript_ScientistNormal
 * goto_if_eq VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_2F_EventScript_ScientistMagma
 * goto_if_lt VAR_MOSSDEEP_CITY_STATE, 2, MossdeepCity_SpaceCenter_2F_EventScript_ScientistNormal
 * goto MossdeepCity_SpaceCenter_2F_EventScript_ScientistMagma
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_2F_EventScript_Scientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_2F_EventScript_Scientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * goto_if_set FLAG_INTERACTED_WITH_STEVEN_SPACE_CENTER, MossdeepCity_SpaceCenter_2F_EventScript_ReadyForBattlePrompt
 * setflag FLAG_INTERACTED_WITH_STEVEN_SPACE_CENTER
 * msgbox MossdeepCity_SpaceCenter_2F_Text_StevenWhyStealRocketFuel, MSGBOX_DEFAULT
 * msgbox MossdeepCity_SpaceCenter_2F_Text_MaxieUseFuelToEruptVolcano, MSGBOX_DEFAULT
 * closemessage
 * delay 30
 * playse SE_NOT_EFFECTIVE
 * switch VAR_FACING
 * case DIR_SOUTH, MossdeepCity_SpaceCenter_2F_EventScript_StevenFightMovementSouth
 * applymovement LOCALID_SPACE_CENTER_2F_STEVEN, MossdeepCity_SpaceCenter_2F_Movement_StevenFight
 * waitmovement 0
 * releaseall
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_2F_EventScript_Steven : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_2F_EventScript_Steven")
}

internal object MossdeepCity_SpaceCenter_2F_EventScript_Grunt6 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MossdeepCity_SpaceCenter_2F.Grunt6PostBattle)
}

internal object MossdeepCity_SpaceCenter_2F_EventScript_Grunt5 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MossdeepCity_SpaceCenter_2F.Grunt5PostBattle)
}

internal object MossdeepCity_SpaceCenter_2F_EventScript_Grunt7 : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MossdeepCity_SpaceCenter_2F.Grunt7PostBattle)
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox MossdeepCity_SpaceCenter_2F_Text_WellTakeCareOfYou, MSGBOX_DEFAULT
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object MossdeepCity_SpaceCenter_2F_EventScript_Tabitha : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MossdeepCity_SpaceCenter_2F_EventScript_Tabitha")
}

internal object MossdeepCity_SpaceCenter_2F_EventScript_Maxie : Script {
  override suspend fun run(ctx: ScriptContext) =
      ctx.say(MossdeepCity_SpaceCenter_2F.MaxieDontInterfere)
}

internal val MossdeepCity_SpaceCenter_2FScripts: Map<String, Script> =
    mapOf(
        "MossdeepCity_SpaceCenter_2F_EventScript_RichBoy" to
            MossdeepCity_SpaceCenter_2F_EventScript_RichBoy,
        "MossdeepCity_SpaceCenter_2F_EventScript_Gentleman" to
            MossdeepCity_SpaceCenter_2F_EventScript_Gentleman,
        "MossdeepCity_SpaceCenter_2F_EventScript_Scientist" to
            MossdeepCity_SpaceCenter_2F_EventScript_Scientist,
        "MossdeepCity_SpaceCenter_2F_EventScript_Steven" to
            MossdeepCity_SpaceCenter_2F_EventScript_Steven,
        "MossdeepCity_SpaceCenter_2F_EventScript_Grunt6" to
            MossdeepCity_SpaceCenter_2F_EventScript_Grunt6,
        "MossdeepCity_SpaceCenter_2F_EventScript_Grunt5" to
            MossdeepCity_SpaceCenter_2F_EventScript_Grunt5,
        "MossdeepCity_SpaceCenter_2F_EventScript_Grunt7" to
            MossdeepCity_SpaceCenter_2F_EventScript_Grunt7,
        "MossdeepCity_SpaceCenter_2F_EventScript_Tabitha" to
            MossdeepCity_SpaceCenter_2F_EventScript_Tabitha,
        "MossdeepCity_SpaceCenter_2F_EventScript_Maxie" to
            MossdeepCity_SpaceCenter_2F_EventScript_Maxie,
    )
