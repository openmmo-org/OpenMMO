package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region

data class CheckpointMon(val dexId: Int, val level: Int, val moveIds: List<Int>)

/**
 * [storyFlags] and [storyVars] replace the character's whole story state rather than merge into it.
 */
data class StoryCheckpoint(
    val name: String,
    val description: String,
    val region: Region,
    val bankId: Int,
    val mapId: Int,
    val x: Int,
    val y: Int,
    val facing: Direction,
    val storyFlags: Set<String>,
    val storyVars: Map<String, Int> = emptyMap(),
    val party: List<CheckpointMon> = emptyList(),
    val items: Map<Int, Int> = emptyMap(),
)
