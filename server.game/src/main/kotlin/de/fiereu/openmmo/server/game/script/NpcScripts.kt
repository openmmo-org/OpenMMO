package de.fiereu.openmmo.server.game.script

import de.fiereu.openmmo.dialog.generated.hoenn.LittlerootTown

/**
 * How the client frames a dialog line, following the decomp msgbox type. The value maps straight to
 * the DialogAction packet action type.
 */
enum class MsgboxType(val actionType: Int) {
  /** A sign or notice board. Shown with no on screen speaker. */
  SIGN(3),
  /** An npc or default message box tied to the entity the player talked to. */
  NPC(4),
}

/** A single dialog line an interaction shows: the generated [textId] and its [msgbox] framing. */
data class DialogScript(val textId: Int, val msgbox: MsgboxType)

/**
 * Hand written map from a decomp script label to the dialog line it shows. The overworld scripts
 * are ported by hand for now, so only the labels listed here open a dialog. Look up an
 * [de.fiereu.openmmo.maps.NpcDef] or [de.fiereu.openmmo.maps.BgEventDef] script label to find the
 * line it should trigger. Branching scripts pick their first line here, the real branch logic will
 * be added per script over time.
 */
object NpcScripts {
  private val byLabel: Map<String, DialogScript> =
      mapOf(
          "LittlerootTown_EventScript_TownSign" to
              DialogScript(LittlerootTown.TownSign.textId, MsgboxType.SIGN),
          "LittlerootTown_EventScript_BirchsLabSign" to
              DialogScript(LittlerootTown.ProfBirchsLab.textId, MsgboxType.SIGN),
          "LittlerootTown_EventScript_Twin" to
              DialogScript(LittlerootTown.IfYouGoInGrassPokemonWillJumpOut.textId, MsgboxType.NPC),
      )

  /** The dialog line wired for [scriptLabel], or null when the script has no dialog yet. */
  fun forScript(scriptLabel: String): DialogScript? = byLabel[scriptLabel]
}
