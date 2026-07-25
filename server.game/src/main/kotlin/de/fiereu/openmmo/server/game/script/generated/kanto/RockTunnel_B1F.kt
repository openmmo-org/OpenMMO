package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_POKEMANIAC_COOPER, RockTunnel_B1F_Text_CooperIntro, RockTunnel_B1F_Text_CooperDefeat
 * msgbox RockTunnel_B1F_Text_CooperPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_Cooper : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_B1F_EventScript_Cooper")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_DUDLEY, RockTunnel_B1F_Text_DudleyIntro, RockTunnel_B1F_Text_DudleyDefeat
 * msgbox RockTunnel_B1F_Text_DudleyPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_Dudley : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_B1F_EventScript_Dudley")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_SOFIA, RockTunnel_B1F_Text_SofiaIntro, RockTunnel_B1F_Text_SofiaDefeat
 * msgbox RockTunnel_B1F_Text_SofiaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_Sofia : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_B1F_EventScript_Sofia")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_ALLEN, RockTunnel_B1F_Text_AllenIntro, RockTunnel_B1F_Text_AllenDefeat
 * msgbox RockTunnel_B1F_Text_AllenPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_Allen : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_B1F_EventScript_Allen")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_HIKER_ERIC, RockTunnel_B1F_Text_EricIntro, RockTunnel_B1F_Text_EricDefeat
 * msgbox RockTunnel_B1F_Text_EricPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_Eric : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_B1F_EventScript_Eric")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_POKEMANIAC_STEVE, RockTunnel_B1F_Text_SteveIntro, RockTunnel_B1F_Text_SteveDefeat
 * msgbox RockTunnel_B1F_Text_StevePostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_Steve : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_B1F_EventScript_Steve")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_PICNICKER_MARTHA, RockTunnel_B1F_Text_MarthaIntro, RockTunnel_B1F_Text_MarthaDefeat
 * msgbox RockTunnel_B1F_Text_MarthaPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_Martha : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_B1F_EventScript_Martha")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * trainerbattle_single TRAINER_POKEMANIAC_WINSTON, RockTunnel_B1F_Text_WinstonIntro, RockTunnel_B1F_Text_WinstonDefeat
 * msgbox RockTunnel_B1F_Text_WinstonPostBattle, MSGBOX_AUTOCLOSE
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_Winston : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_B1F_EventScript_Winston")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_TUTOR_ROCK_SLIDE, EventScript_RockSlideTaught
 * msgbox Text_RockSlideTeach, MSGBOX_YESNO
 * goto_if_eq VAR_RESULT, NO, EventScript_RockSlideDeclined
 * call EventScript_CanOnlyBeLearnedOnce
 * goto_if_eq VAR_RESULT, NO, EventScript_RockSlideDeclined
 * msgbox Text_RockSlideWhichMon
 * setvar VAR_0x8005, MOVETUTOR_ROCK_SLIDE
 * call EventScript_ChooseMoveTutorMon
 * goto_if_eq VAR_RESULT, FALSE, EventScript_RockSlideDeclined
 * setflag FLAG_TUTOR_ROCK_SLIDE
 * goto EventScript_RockSlideTaught
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_RockSlideTutor : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RockTunnel_B1F_EventScript_RockSlideTutor")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_REVIVE
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_ItemRevive : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port RockTunnel_B1F_EventScript_ItemRevive")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MAX_ETHER
 * end
 * ```
 */
internal object RockTunnel_B1F_EventScript_ItemMaxEther : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port RockTunnel_B1F_EventScript_ItemMaxEther")
}

internal val RockTunnel_B1FScripts: Map<String, Script> =
    mapOf(
        "RockTunnel_B1F_EventScript_Cooper" to RockTunnel_B1F_EventScript_Cooper,
        "RockTunnel_B1F_EventScript_Dudley" to RockTunnel_B1F_EventScript_Dudley,
        "RockTunnel_B1F_EventScript_Sofia" to RockTunnel_B1F_EventScript_Sofia,
        "RockTunnel_B1F_EventScript_Allen" to RockTunnel_B1F_EventScript_Allen,
        "RockTunnel_B1F_EventScript_Eric" to RockTunnel_B1F_EventScript_Eric,
        "RockTunnel_B1F_EventScript_Steve" to RockTunnel_B1F_EventScript_Steve,
        "RockTunnel_B1F_EventScript_Martha" to RockTunnel_B1F_EventScript_Martha,
        "RockTunnel_B1F_EventScript_Winston" to RockTunnel_B1F_EventScript_Winston,
        "RockTunnel_B1F_EventScript_RockSlideTutor" to RockTunnel_B1F_EventScript_RockSlideTutor,
        "RockTunnel_B1F_EventScript_ItemRevive" to RockTunnel_B1F_EventScript_ItemRevive,
        "RockTunnel_B1F_EventScript_ItemMaxEther" to RockTunnel_B1F_EventScript_ItemMaxEther,
    )
