package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U16LE

/**
 * Opcode 0xE4 (s2c). Sent in a burst at battle resolution, once per step, all referencing the same
 * transient entity. Each packet carries a sequence value that increments across the burst. Its
 * meaning is not yet decoded, so the fields keep their wire names.
 */
data class EntityCounterPulsePacket(
    val entityId: Long,
    val tagA: Byte,
    val tagB: Byte,
    val sequence: Int,
    val endFlag: Byte,
)

object EntityCounterPulsePacketCodec : PacketCodec<EntityCounterPulsePacket>() {
  override fun CodecScope<EntityCounterPulsePacket>.body(): EntityCounterPulsePacket {
    val entityId = field(S64LE) { it.entityId }
    val tagA = field(S8) { it.tagA }
    val tagB = field(S8) { it.tagB }
    val sequence = field(U16LE) { it.sequence }
    val endFlag = field(S8) { it.endFlag }
    return EntityCounterPulsePacket(entityId, tagA, tagB, sequence, endFlag)
  }
}
