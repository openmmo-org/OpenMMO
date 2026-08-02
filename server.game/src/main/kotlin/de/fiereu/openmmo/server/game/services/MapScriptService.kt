package de.fiereu.openmmo.server.game.services

import de.fiereu.network.SessionContext
import de.fiereu.openmmo.maps.MapDef
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptRegistry
import de.fiereu.openmmo.server.game.script.ScriptRunner
import de.fiereu.openmmo.server.game.session.PlayerState
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/**
 * Runs a map's scripts when a player enters it. The decomp ON_TRANSITION script fires on every
 * entry however the player got there (login, a warp, or walking across a map connection), followed
 * by the first ON_FRAME entry whose story var matches. The conditional ON_WARP table is a later
 * addition.
 */
@Singleton
class MapScriptService
@Inject
constructor(
    private val scriptRegistry: ScriptRegistry,
    private val scriptRunner: ScriptRunner,
    private val storyService: StoryService,
) {
  fun onMapEnter(session: SessionContext, state: PlayerState, map: MapDef) {
    // A script is already running for this player, do not start a second one on top of it.
    if (state.inDialog) return
    val charId = state.characterId
    // The on-transition script runs on every entry, then the one on-frame script whose var matches.
    val entryScripts = buildList {
      resolve(map.onTransitionScript)?.let { add(it) }
      if (charId != null) {
        map.onFrameScripts
            .firstOrNull { storyService.getVar(charId, it.varKey) == it.value }
            ?.let { resolve(it.script) }
            ?.let { add(it) }
      }
    }
    val hasArrivalTrigger =
        map.coordScripts.any { it.x == state.x.toInt() && it.y == state.y.toInt() }
    if (entryScripts.isEmpty() && !hasArrivalTrigger) return

    // Entry scripts may trigger their landing coordinate.
    val entrySequence = Script { ctx ->
      entryScripts.forEach { it.run(ctx) }
      if (charId != null) {
        matchingCoordScript(map, charId, state.x.toInt(), state.y.toInt())?.run(ctx)
      }
    }
    scriptRunner.run(session, state, entrySequence, entityId = -1)
  }

  /** Run the matching conditional coordinate script after a completed player step. */
  fun onStep(
      session: SessionContext,
      state: PlayerState,
      map: MapDef,
      x: Int,
      y: Int,
  ): Boolean {
    if (state.inDialog) return false
    val charId = state.characterId ?: return false
    val script = matchingCoordScript(map, charId, x, y) ?: return false
    scriptRunner.run(session, state, script, entityId = -1)
    return true
  }

  private fun matchingCoordScript(map: MapDef, charId: Long, x: Int, y: Int): Script? {
    val trigger =
        map.coordScripts.firstOrNull {
          it.x == x && it.y == y && storyService.getVar(charId, it.varKey) == it.value
        } ?: return null
    return resolve(trigger.script)
  }

  private fun resolve(label: String): Script? {
    if (label.isEmpty()) return null
    val script = scriptRegistry.forLabel(label)
    if (script == null) log.debug { "Map script $label is not ported yet" }
    return script
  }
}
