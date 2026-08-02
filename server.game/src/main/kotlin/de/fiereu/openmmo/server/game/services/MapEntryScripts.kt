package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.maps.MapDef
import de.fiereu.openmmo.server.game.script.Script
import de.fiereu.openmmo.server.game.script.ScriptRegistry
import de.fiereu.openmmo.server.game.session.PlayerState
import io.github.oshai.kotlinlogging.KotlinLogging
import javax.inject.Inject
import javax.inject.Singleton

private val log = KotlinLogging.logger {}

/**
 * Works out which scripts a map runs when a player arrives on it: the ON_TRANSITION script, then
 * the first ON_FRAME entry whose story var matches, then the coordinate trigger the player landed
 * on. [MapScriptService] runs these on a fresh script coroutine when the player walks or logs in,
 * and a script that warps runs them inline on its own coroutine, the way the decomp's warp
 * continues into the destination map's scripts.
 */
@Singleton
class MapEntryScripts
@Inject
constructor(
    private val scriptRegistry: ScriptRegistry,
    private val storyService: StoryService,
) {
  fun onEntry(state: PlayerState, map: MapDef): List<Script> {
    val charId = state.characterId
    return buildList {
      resolve(map.onTransitionScript)?.let { add(it) }
      if (charId != null) {
        map.onFrameScripts
            .firstOrNull { storyService.getVar(charId, it.varKey) == it.value }
            ?.let { resolve(it.script) }
            ?.let { add(it) }
      }
    }
  }

  /** The conditional coordinate trigger on one tile, the decomp coord_events table. */
  fun atCoordinate(charId: Long, map: MapDef, x: Int, y: Int): Script? {
    val trigger =
        map.coordScripts.firstOrNull {
          it.x == x && it.y == y && storyService.getVar(charId, it.varKey) == it.value
        } ?: return null
    return resolve(trigger.script)
  }

  /** True when the tile has a trigger at all, whatever its var currently says. */
  fun hasCoordinate(map: MapDef, x: Int, y: Int): Boolean =
      map.coordScripts.any { it.x == x && it.y == y }

  private fun resolve(label: String): Script? {
    if (label.isEmpty()) return null
    val script = scriptRegistry.forLabel(label)
    if (script == null) log.debug { "Map script $label is not ported yet" }
    return script
  }
}
