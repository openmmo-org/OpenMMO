package de.fiereu.openmmo.server.game.services

import de.fiereu.openmmo.net.game.packets.PlayerVariableEntry
import de.fiereu.openmmo.net.game.packets.StoryFlagUpdatePacket
import de.fiereu.openmmo.story.generated.hoenn.HoennFlags
import de.fiereu.openmmo.story.generated.hoenn.HoennVars
import de.fiereu.openmmo.story.generated.kanto.KantoFlags
import de.fiereu.openmmo.story.generated.kanto.KantoVars

/** Converts persisted story keys to client ids. */
internal object StoryClientState {
  fun flags(regionId: Byte, flags: Collection<String>): List<StoryFlagUpdatePacket> =
      flags.mapNotNull { flagUpdate(regionId, it, enabled = true) }.sortedBy { it.flagId }

  fun flagUpdate(regionId: Byte, key: String, enabled: Boolean): StoryFlagUpdatePacket? {
    val id = flagId(regionId, key) ?: return null
    return StoryFlagUpdatePacket(regionId, id, enabled)
  }

  fun variables(regionId: Byte, vars: Map<String, Int>): List<PlayerVariableEntry> =
      vars
          .mapNotNull { (key, value) ->
            val id = varId(regionId, key) ?: return@mapNotNull null
            if (id !in GBA_VARS_START..GBA_VARS_END) return@mapNotNull null
            PlayerVariableEntry((id - GBA_VARS_START).toByte(), value.toShort())
          }
          .sortedBy { it.key.toInt() and 0xff }

  /**
   * Every var in the GBA range, zeros included. A var back at 0 is stored as absent, so sending
   * only what is stored would leave the client holding the old value. There is no var equivalent of
   * [de.fiereu.openmmo.net.game.packets.WorldFlagTableResetPacket] to clear it first.
   */
  fun allVariables(regionId: Byte, vars: Map<String, Int>): List<PlayerVariableEntry> {
    val byId = variables(regionId, vars).associate { it.key.toInt() and 0xff to it.value }
    return (0..(GBA_VARS_END - GBA_VARS_START)).map {
      PlayerVariableEntry(it.toByte(), byId[it] ?: 0)
    }
  }

  private fun flagId(regionId: Byte, key: String): Int? =
      when (regionId.toInt()) {
        KANTO_REGION -> KantoFlags.numericId(key)
        HOENN_REGION -> HoennFlags.numericId(key)
        else -> null
      }

  private fun varId(regionId: Byte, key: String): Int? =
      when (regionId.toInt()) {
        KANTO_REGION -> KantoVars.numericId(key)
        HOENN_REGION -> HoennVars.numericId(key)
        else -> null
      }

  private const val KANTO_REGION = 0
  private const val HOENN_REGION = 1
  private const val GBA_VARS_START = 0x4000
  private const val GBA_VARS_END = 0x40ff
}
