package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.SaffronCity_Dojo
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLACK_BELT_HITOSHI, SaffronCity_Dojo_Text_HitoshiIntro, SaffronCity_Dojo_Text_HitoshiDefeat
 * msgbox SaffronCity_Dojo_Text_HitoshiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Dojo_EventScript_Hitoshi : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Dojo_EventScript_Hitoshi")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLACK_BELT_HIDEKI, SaffronCity_Dojo_Text_HidekiIntro, SaffronCity_Dojo_Text_HidekiDefeat
 * msgbox SaffronCity_Dojo_Text_HidekiPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Dojo_EventScript_Hideki : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Dojo_EventScript_Hideki")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLACK_BELT_AARON, SaffronCity_Dojo_Text_AaronIntro, SaffronCity_Dojo_Text_AaronDefeat
 * msgbox SaffronCity_Dojo_Text_AaronPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Dojo_EventScript_Aaron : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Dojo_EventScript_Aaron")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLACK_BELT_MIKE, SaffronCity_Dojo_Text_MikeIntro, SaffronCity_Dojo_Text_MikeDefeat
 * msgbox SaffronCity_Dojo_Text_MikePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Dojo_EventScript_Mike : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port SaffronCity_Dojo_EventScript_Mike")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_BLACK_BELT_KOICHI, SaffronCity_Dojo_Text_MasterKoichiIntro, SaffronCity_Dojo_Text_MasterKoichiDefeat, SaffronCity_Dojo_EventScript_DefeatedMasterKoichi
 * goto_if_set FLAG_GOT_HITMON_FROM_DOJO, SaffronCity_Dojo_EventScript_MasterKoichiAlreadyGotHitmon
 * msgbox SaffronCity_Dojo_Text_ChoosePrizedFightingMon, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object SaffronCity_Dojo_EventScript_MasterKoichi : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_Dojo_EventScript_MasterKoichi")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_HITMON_FROM_DOJO, SaffronCity_Dojo_EventScript_AlreadyGotHitmon
 * showmonpic SPECIES_HITMONLEE, 10, 3
 * setvar VAR_TEMP_1, SPECIES_HITMONLEE
 * applymovement LOCALID_KARATE_MASTER, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * textcolor NPC_TEXT_COLOR_MALE
 * msgbox SaffronCity_Dojo_Text_YouWantHitmonlee, MSGBOX_YESNO
 * call EventScript_RestorePrevTextColor
 * goto_if_eq VAR_RESULT, YES, SaffronCity_Dojo_EventScript_GiveHitmon
 * hidemonpic
 * release
 * end
 * ```
 */
internal object SaffronCity_Dojo_EventScript_HitmonleeBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_Dojo_EventScript_HitmonleeBall")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_HITMON_FROM_DOJO, SaffronCity_Dojo_EventScript_AlreadyGotHitmon
 * showmonpic SPECIES_HITMONCHAN, 10, 3
 * setvar VAR_TEMP_1, SPECIES_HITMONCHAN
 * applymovement LOCALID_KARATE_MASTER, Common_Movement_WalkInPlaceFasterUp
 * waitmovement 0
 * textcolor NPC_TEXT_COLOR_MALE
 * msgbox SaffronCity_Dojo_Text_YouWantHitmonchan, MSGBOX_YESNO
 * call EventScript_RestorePrevTextColor
 * goto_if_eq VAR_RESULT, YES, SaffronCity_Dojo_EventScript_GiveHitmon
 * hidemonpic
 * release
 * end
 * ```
 */
internal object SaffronCity_Dojo_EventScript_HitmonchanBall : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port SaffronCity_Dojo_EventScript_HitmonchanBall")
}

internal object SaffronCity_Dojo_EventScript_Statue : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity_Dojo.FightingDojo)
}

internal object SaffronCity_Dojo_EventScript_LeftScroll : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity_Dojo.EnemiesOnEverySide)
}

internal object SaffronCity_Dojo_EventScript_RightScroll : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(SaffronCity_Dojo.GoesAroundComesAround)
}

internal val SaffronCity_DojoScripts: Map<String, Script> =
    mapOf(
        "SaffronCity_Dojo_EventScript_Hitoshi" to SaffronCity_Dojo_EventScript_Hitoshi,
        "SaffronCity_Dojo_EventScript_Hideki" to SaffronCity_Dojo_EventScript_Hideki,
        "SaffronCity_Dojo_EventScript_Aaron" to SaffronCity_Dojo_EventScript_Aaron,
        "SaffronCity_Dojo_EventScript_Mike" to SaffronCity_Dojo_EventScript_Mike,
        "SaffronCity_Dojo_EventScript_MasterKoichi" to SaffronCity_Dojo_EventScript_MasterKoichi,
        "SaffronCity_Dojo_EventScript_HitmonleeBall" to SaffronCity_Dojo_EventScript_HitmonleeBall,
        "SaffronCity_Dojo_EventScript_HitmonchanBall" to
            SaffronCity_Dojo_EventScript_HitmonchanBall,
        "SaffronCity_Dojo_EventScript_Statue" to SaffronCity_Dojo_EventScript_Statue,
        "SaffronCity_Dojo_EventScript_LeftScroll" to SaffronCity_Dojo_EventScript_LeftScroll,
        "SaffronCity_Dojo_EventScript_RightScroll" to SaffronCity_Dojo_EventScript_RightScroll,
    )
