package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class MountMoveCooldownPacket(
    val entityId: Long,
    val category: Byte,
    val moveId: Short,
    val playAudio: Boolean,
)

object MountMoveCooldownPacketCodec : PacketCodec<MountMoveCooldownPacket>() {
  override fun CodecScope<MountMoveCooldownPacket>.body(): MountMoveCooldownPacket {
    val entityId = field(S64LE) { it.entityId }
    val category = field(S8) { it.category }
    val moveId = field(S16LE) { it.moveId }
    val playAudio = field(U8) { if (it.playAudio) 1 else 0 } == 1
    return MountMoveCooldownPacket(entityId, category, moveId, playAudio)
  }
}
