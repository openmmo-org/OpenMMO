package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TEMP_1, RusturfTunnel_EventScript_AlreadySpokenTo
 * setflag FLAG_TEMP_1
 * msgbox RusturfTunnel_Text_WhyCantTheyKeepDigging, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_WandasBoyfriend : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RusturfTunnel_EventScript_WandasBoyfriend")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_POKE_BALL
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_ItemPokeBall : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RusturfTunnel_EventScript_ItemPokeBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ETHER
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RusturfTunnel_EventScript_ItemMaxEther")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * playbgm MUS_ENCOUNTER_AQUA, FALSE
 * msgbox RusturfTunnel_Text_GruntIntro, MSGBOX_DEFAULT
 * trainerbattle_no_intro TRAINER_GRUNT_RUSTURF_TUNNEL, RusturfTunnel_Text_GruntDefeat
 * msgbox RusturfTunnel_Text_GruntTakePackage, MSGBOX_DEFAULT
 * giveitem ITEM_DEVON_GOODS
 * closemessage
 * applymovement LOCALID_PLAYER, RusturfTunnel_Movement_PushPlayerAsideForGrunt
 * applymovement LOCALID_RUSTURF_TUNNEL_GRUNT, RusturfTunnel_Movement_GruntEscape
 * waitmovement 0
 * removeobject LOCALID_RUSTURF_TUNNEL_GRUNT
 * delay 50
 * addobject LOCALID_RUSTURF_TUNNEL_BRINEY
 * applymovement LOCALID_RUSTURF_TUNNEL_BRINEY, RusturfTunnel_Movement_BrineyApproachPeeko1
 * waitmovement 0
 * applymovement LOCALID_PLAYER, RusturfTunnel_Movement_PlayerMoveAsideForBriney
 * applymovement LOCALID_RUSTURF_TUNNEL_BRINEY, RusturfTunnel_Movement_BrineyApproachPeeko2
 * waitmovement 0
 * msgbox RusturfTunnel_Text_PeekoGladToSeeYouSafe, MSGBOX_DEFAULT
 * applymovement LOCALID_RUSTURF_TUNNEL_BRINEY, Common_Movement_FacePlayer
 * waitmovement 0
 * message RusturfTunnel_Text_ThankYouLetsGoHomePeeko
 * waitmessage
 * waitse
 * playmoncry SPECIES_WINGULL, CRY_MODE_NORMAL
 * waitbuttonpress
 * waitmoncry
 * closemessage
 * applymovement LOCALID_PLAYER, RusturfTunnel_Movement_PlayerWatchBrineyExit
 * applymovement LOCALID_RUSTURF_TUNNEL_BRINEY, RusturfTunnel_Movement_BrineyExit
 * applymovement LOCALID_RUSTURF_TUNNEL_PEEKO, RusturfTunnel_Movement_PeekoExit
 * waitmovement 0
 * removeobject LOCALID_RUSTURF_TUNNEL_BRINEY
 * removeobject LOCALID_RUSTURF_TUNNEL_PEEKO
 * clearflag FLAG_DEVON_GOODS_STOLEN
 * setflag FLAG_RECOVERED_DEVON_GOODS
 * setvar VAR_RUSTBORO_CITY_STATE, 4
 * setvar VAR_BRINEY_HOUSE_STATE, 1
 * setflag FLAG_HIDE_ROUTE_116_MR_BRINEY
 * release
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_Grunt : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RusturfTunnel_EventScript_Grunt")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * waitse
 * playmoncry SPECIES_WINGULL, CRY_MODE_NORMAL
 * msgbox RusturfTunnel_Text_Peeko, MSGBOX_DEFAULT
 * waitmoncry
 * release
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_Peeko : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RusturfTunnel_EventScript_Peeko")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_MIKE_2, RusturfTunnel_Text_MikeIntro, RusturfTunnel_Text_MikeDefeat
 * msgbox RusturfTunnel_Text_MikePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_Mike : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RusturfTunnel_EventScript_Mike")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * msgbox RusturfTunnel_Text_BoyfriendOnOtherSideOfRock, MSGBOX_DEFAULT
 * closemessage
 * applymovement VAR_LAST_TALKED, Common_Movement_FaceOriginalDirection
 * waitmovement 0
 * release
 * end
 * ```
 */
internal object RusturfTunnel_EventScript_Wanda : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RusturfTunnel_EventScript_Wanda")
}

internal val RusturfTunnelScripts: Map<String, Script> =
    mapOf(
        "RusturfTunnel_EventScript_WandasBoyfriend" to RusturfTunnel_EventScript_WandasBoyfriend,
        "RusturfTunnel_EventScript_ItemPokeBall" to RusturfTunnel_EventScript_ItemPokeBall,
        "RusturfTunnel_EventScript_ItemMaxEther" to RusturfTunnel_EventScript_ItemMaxEther,
        "RusturfTunnel_EventScript_Grunt" to RusturfTunnel_EventScript_Grunt,
        "RusturfTunnel_EventScript_Peeko" to RusturfTunnel_EventScript_Peeko,
        "RusturfTunnel_EventScript_Mike" to RusturfTunnel_EventScript_Mike,
        "RusturfTunnel_EventScript_Wanda" to RusturfTunnel_EventScript_Wanda,
    )
