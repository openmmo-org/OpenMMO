package de.fiereu.openmmo.server.game.script

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.common.dialog.DialogLine
import de.fiereu.openmmo.server.game.services.DialogService
import de.fiereu.openmmo.server.game.services.ScriptMovementService
import de.fiereu.openmmo.server.game.services.StoryService
import de.fiereu.openmmo.server.game.session.PlayerState

/** What a [Script] uses to talk to the player it interacted with and read or write story state. */
class ScriptContext
internal constructor(
    private val session: SessionContext,
    private val state: PlayerState,
    /** The npc the player talked to, or -1 for a sign. */
    val entityId: Long,
    private val dialog: DialogService,
    private val story: StoryService,
    private val movement: ScriptMovementService,
) {
  private val characterId: Long?
    get() = state.characterId

  /** Show [line] as a sign and wait for the player to close it. */
  suspend fun sign(line: DialogLine) = dialog.showAndWait(session, state, line.textId, SIGN, -1)

  /** Show [line] from the interacted entity and wait for the player to go on. */
  suspend fun say(line: DialogLine) = dialog.showAndWait(session, state, line.textId, NPC, entityId)

  /** True if the story [flag] is set. Keys come from the content layer, for example HoennFlags. */
  fun isFlagSet(flag: String): Boolean = characterId?.let { story.isFlagSet(it, flag) } ?: false

  fun setFlag(flag: String) {
    characterId?.let { story.setFlag(it, flag) }
  }

  fun clearFlag(flag: String) {
    characterId?.let { story.clearFlag(it, flag) }
  }

  /** The story var [key], or 0 if it was never set. */
  fun getVar(key: String): Int = characterId?.let { story.getVar(it, key) } ?: 0

  fun setVar(key: String, value: Int) {
    characterId?.let { story.setVar(it, key, value) }
  }

  /**
   * Walk the map npc with decomp local id [localId] (its entityIdx) through [steps] and wait for
   * the whole path to finish. This is applymovement plus waitmovement for an npc.
   */
  suspend fun moveNpc(localId: Int, vararg steps: MovementStep) =
      movement.moveNpc(session, state, localId, steps.toList())

  /** Walk the player's own avatar through [steps] and wait for it to finish. */
  suspend fun moveSelf(vararg steps: MovementStep) =
      movement.moveSelf(session, state, steps.toList())

  private companion object {
    // Sign boxes have no speaker, npc boxes point at the entity.
    const val SIGN = 3
    const val NPC = 4
  }
}
