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
 * Runs a map's scripts when a player enters it. Today that is the decomp ON_TRANSITION script,
 * which fires on every entry however the player got there (login, a warp, or walking across a map
 * connection). The conditional ON_FRAME and ON_WARP tables are a later addition.
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
    val scripts = buildList {
      resolve(map.onTransitionScript)?.let { add(it) }
      if (charId != null) {
        map.onFrameScripts
            .firstOrNull { storyService.getVar(charId, it.varKey) == it.value }
            ?.let { resolve(it.script) }
            ?.let { add(it) }
      }
    }
    if (scripts.isNotEmpty()) scriptRunner.runAll(session, state, scripts, entityId = -1)
  }

  private fun resolve(label: String): Script? {
    if (label.isEmpty()) return null
    val script = scriptRegistry.forLabel(label)
    if (script == null) log.debug { "Map script $label is not ported yet" }
    return script
  }
}
