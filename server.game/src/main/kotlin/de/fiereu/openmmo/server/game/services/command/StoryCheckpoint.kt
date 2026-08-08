package de.fiereu.openmmo.server.game.services.command

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.Region

/** A party monster a checkpoint hands out, since some scenes do nothing without one. */
data class CheckpointMon(val dexId: Int, val level: Int, val moveIds: List<Int>)

/**
 * A story scene a developer can jump straight to. [storyFlags] and [storyVars] replace the
 * character's whole story state, they are not merged into it, so a checkpoint always lands the same
 * way however the character got there.
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
