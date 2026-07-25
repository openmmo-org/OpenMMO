package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.Route1
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * goto_if_set FLAG_GOT_POTION_ON_ROUTE_1, Route1_EventScript_AlreadyGotPotion
 * msgbox Route1_Text_WorkAtPokeMartTakeSample
 * textcolor NPC_TEXT_COLOR_NEUTRAL
 * checkitemspace ITEM_POTION
 * goto_if_eq VAR_RESULT, FALSE, EventScript_BagIsFull
 * bufferitemname STR_VAR_2, ITEM_POTION
 * playfanfare MUS_LEVEL_UP
 * message Text_ObtainedTheX
 * waitmessage
 * waitfanfare
 * additem ITEM_POTION
 * msgbox Route1_Text_PutPotionAway
 * call EventScript_RestorePrevTextColor
 * setflag FLAG_GOT_POTION_ON_ROUTE_1
 * release
 * end
 * ```
 */
internal object Route1_EventScript_MartClerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port Route1_EventScript_MartClerk")
}

internal object Route1_EventScript_Boy : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(Route1.CanJumpFromLedges)
}

internal object Route1_EventScript_RouteSign : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.sign(Route1.RouteSign)
}

internal val Route1Scripts: Map<String, Script> =
    mapOf(
        "Route1_EventScript_MartClerk" to Route1_EventScript_MartClerk,
        "Route1_EventScript_Boy" to Route1_EventScript_Boy,
        "Route1_EventScript_RouteSign" to Route1_EventScript_RouteSign,
    )
