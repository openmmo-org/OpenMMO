package de.fiereu.openmmo.server.game.script.generated.kanto

import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptContext

/**
 * Not ported yet. Decomp body:
 * ```
 * lock
 * faceplayer
 * setflag FLAG_GOT_RUBY
 * removeobject LOCALID_RUBY
 * giveitem_msg MtEmber_RubyPath_B3F_Text_FoundARuby, ITEM_RUBY, 1, MUS_OBTAIN_KEY_ITEM
 * release
 * end
 * ```
 */
internal object MtEmber_RubyPath_B5F_EventScript_Ruby : Script {
  override suspend fun run(ctx: ScriptContext) = TODO("port MtEmber_RubyPath_B5F_EventScript_Ruby")
}

/**
 * Not ported yet. Decomp body:
 * ```
 * lockall
 * setvar VAR_0x8005, 130
 * braillemessage_wait Braille_Text_Everything
 * braillemessage_wait Braille_Text_HasMeaning1
 * braillemessage_wait Braille_Text_Existence
 * braillemessage_wait Braille_Text_HasMeaning2
 * braillemessage_wait Braille_Text_BeingAlive
 * braillemessage_wait Braille_Text_HasMeaning3
 * braillemessage_wait Braille_Text_HaveDreams
 * braillemessage Braille_Text_UsePower
 * waitbuttonpress
 * releaseall
 * end
 * ```
 */
internal object MtEmber_RubyPath_B5F_EventScript_BrailleMessage : Script {
  override suspend fun run(ctx: ScriptContext) =
      TODO("port MtEmber_RubyPath_B5F_EventScript_BrailleMessage")
}

internal val MtEmber_RubyPath_B5FScripts: Map<String, Script> =
    mapOf(
        "MtEmber_RubyPath_B5F_EventScript_Ruby" to MtEmber_RubyPath_B5F_EventScript_Ruby,
        "MtEmber_RubyPath_B5F_EventScript_BrailleMessage" to
            MtEmber_RubyPath_B5F_EventScript_BrailleMessage,
    )
