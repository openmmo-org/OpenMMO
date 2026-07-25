package de.fiereu.openmmo.server.game.script.generated.hoenn

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_TM_IRON_TAIL
 * end
 * ```
 */
internal object MeteorFalls_1F_1R_EventScript_ItemTMIronTail : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MeteorFalls_1F_1R_EventScript_ItemTMIronTail")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_MOON_STONE
 * end
 * ```
 */
internal object MeteorFalls_1F_1R_EventScript_ItemMoonStone : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MeteorFalls_1F_1R_EventScript_ItemMoonStone")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_FULL_HEAL
 * end
 * ```
 */
internal object MeteorFalls_1F_1R_EventScript_ItemFullHeal : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MeteorFalls_1F_1R_EventScript_ItemFullHeal")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * finditem ITEM_PP_UP
 * end
 * ```
 */
internal object MeteorFalls_1F_1R_EventScript_ItemPPUP : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MeteorFalls_1F_1R_EventScript_ItemPPUP")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_MET_PROF_COZMO, MeteorFalls_1F_1R_EventScript_MetCozmo
 * setflag FLAG_MET_PROF_COZMO
 * msgbox MeteorFalls_1F_1R_Text_MeetProfCozmo, MSGBOX_DEFAULT
 * release
 * end
 * ```
 */
internal object MeteorFalls_1F_1R_EventScript_ProfCozmo : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MeteorFalls_1F_1R_EventScript_ProfCozmo")
}

internal val MeteorFalls_1F_1RScripts: Map<String, Script> =
    mapOf(
        "MeteorFalls_1F_1R_EventScript_ItemTMIronTail" to
            MeteorFalls_1F_1R_EventScript_ItemTMIronTail,
        "MeteorFalls_1F_1R_EventScript_ItemMoonStone" to
            MeteorFalls_1F_1R_EventScript_ItemMoonStone,
        "MeteorFalls_1F_1R_EventScript_ItemFullHeal" to MeteorFalls_1F_1R_EventScript_ItemFullHeal,
        "MeteorFalls_1F_1R_EventScript_ItemPPUP" to MeteorFalls_1F_1R_EventScript_ItemPPUP,
        "MeteorFalls_1F_1R_EventScript_ProfCozmo" to MeteorFalls_1F_1R_EventScript_ProfCozmo,
    )
