package de.fiereu.openmmo.server.game.script

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.dialog.DialogLine
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.session.PlayerState

/** What a [Script] uses to show dialog to the player it interacted with. */
class ScriptContext
internal constructor(
    private val session: SessionContext,
    private val state: PlayerState,
    /** The npc the player talked to, or -1 for a sign. */
    val entityId: Long,
    private val dialog: DialogService,
) {
  /** Show [line] as a sign and wait for the player to close it. */
  suspend fun sign(line: DialogLine) = dialog.showAndWait(session, state, line.textId, SIGN, -1)

  /** Show [line] from the interacted entity and wait for the player to go on. */
  suspend fun say(line: DialogLine) = dialog.showAndWait(session, state, line.textId, NPC, entityId)

  private companion object {
    // Sign boxes have no speaker, npc boxes point at the entity.
    const val SIGN = 3
    const val NPC = 4
  }
}
