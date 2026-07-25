package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_FOSSIL_RESURRECTION_STATE, 1, RustboroCity_DevonCorp_2F_EventScript_SetFossilReady
 * msgbox RustboroCity_DevonCorp_2F_Text_DeviceForTalkingToPokemon, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_2F_EventScript_TalkToPokemonScientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_2F_EventScript_TalkToPokemonScientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_FOSSIL_RESURRECTION_STATE, 1, RustboroCity_DevonCorp_2F_EventScript_SetFossilReady
 * goto_if_set FLAG_MET_DEVON_EMPLOYEE, RustboroCity_DevonCorp_2F_EventScript_DevelopedBalls
 * msgbox RustboroCity_DevonCorp_2F_Text_DevelopingNewBalls, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_2F_EventScript_BallScientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_2F_EventScript_BallScientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_FOSSIL_RESURRECTION_STATE, 1, RustboroCity_DevonCorp_2F_EventScript_SetFossilReady
 * goto_if_set FLAG_RECEIVED_POKENAV, RustboroCity_DevonCorp_2F_EventScript_HasPokenav
 * msgbox RustboroCity_DevonCorp_2F_Text_IMadePokenav, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_2F_EventScript_PokenavScientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_2F_EventScript_PokenavScientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_FOSSIL_RESURRECTION_STATE, 1, RustboroCity_DevonCorp_2F_EventScript_SetFossilReady
 * msgbox RustboroCity_DevonCorp_2F_Text_DeviceToVisualizePokemonDreams, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_2F_EventScript_PokemonDreamsScientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_2F_EventScript_PokemonDreamsScientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_eq VAR_FOSSIL_RESURRECTION_STATE, 2, RustboroCity_DevonCorp_2F_EventScript_FossilMonReady
 * goto_if_eq VAR_FOSSIL_RESURRECTION_STATE, 1, RustboroCity_DevonCorp_2F_EventScript_StillRegenerating
 * msgbox RustboroCity_DevonCorp_2F_Text_DevelopDeviceToResurrectFossils, MSGBOX_DEFAULT
 * checkitem ITEM_ROOT_FOSSIL
 * goto_if_eq VAR_RESULT, TRUE, RustboroCity_DevonCorp_2F_EventScript_NoticeRootFossil
 * checkitem ITEM_CLAW_FOSSIL
 * goto_if_eq VAR_RESULT, TRUE, RustboroCity_DevonCorp_2F_EventScript_NoticeClawFossil
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_2F_EventScript_FossilScientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_2F_EventScript_FossilScientist")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * call_if_eq VAR_FOSSIL_RESURRECTION_STATE, 1, RustboroCity_DevonCorp_2F_EventScript_SetFossilReady
 * goto_if_ge VAR_RUSTBORO_CITY_STATE, 6, RustboroCity_DevonCorp_2F_EventScript_WorkOnNext
 * msgbox RustboroCity_DevonCorp_2F_Text_DevelopNewPokenavFeature, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object RustboroCity_DevonCorp_2F_EventScript_MatchCallScientist : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RustboroCity_DevonCorp_2F_EventScript_MatchCallScientist")
}

internal val RustboroCity_DevonCorp_2FScripts: Map<String, Script> =
    mapOf(
        "RustboroCity_DevonCorp_2F_EventScript_TalkToPokemonScientist" to
            RustboroCity_DevonCorp_2F_EventScript_TalkToPokemonScientist,
        "RustboroCity_DevonCorp_2F_EventScript_BallScientist" to
            RustboroCity_DevonCorp_2F_EventScript_BallScientist,
        "RustboroCity_DevonCorp_2F_EventScript_PokenavScientist" to
            RustboroCity_DevonCorp_2F_EventScript_PokenavScientist,
        "RustboroCity_DevonCorp_2F_EventScript_PokemonDreamsScientist" to
            RustboroCity_DevonCorp_2F_EventScript_PokemonDreamsScientist,
        "RustboroCity_DevonCorp_2F_EventScript_FossilScientist" to
            RustboroCity_DevonCorp_2F_EventScript_FossilScientist,
        "RustboroCity_DevonCorp_2F_EventScript_MatchCallScientist" to
            RustboroCity_DevonCorp_2F_EventScript_MatchCallScientist,
    )
