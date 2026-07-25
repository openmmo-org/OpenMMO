package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.dialog.generated.kanto.CeruleanCity_Mart
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * goto_if_questlog EventScript_ReleaseEnd
 * lock
 * faceplayer
 * message Text_MayIHelpYou
 * waitmessage
 * pokemart CeruleanCity_Mart_Items
 * msgbox Text_PleaseComeAgain
 * release
 * end
 * ```
 */
internal object CeruleanCity_Mart_EventScript_Clerk : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port CeruleanCity_Mart_EventScript_Clerk")
}

internal object CeruleanCity_Mart_EventScript_Woman : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity_Mart.DoYouKnowAboutRareCandy)
}

internal object CeruleanCity_Mart_EventScript_Youngster : Script {
  override suspend fun run(ctx: ScriptContext) = ctx.say(CeruleanCity_Mart.RepelWorksOnWeakMons)
}

internal val CeruleanCity_MartScripts: Map<String, Script> =
    mapOf(
        "CeruleanCity_Mart_EventScript_Clerk" to CeruleanCity_Mart_EventScript_Clerk,
        "CeruleanCity_Mart_EventScript_Woman" to CeruleanCity_Mart_EventScript_Woman,
        "CeruleanCity_Mart_EventScript_Youngster" to CeruleanCity_Mart_EventScript_Youngster,
    )
