package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class InteractivePacket(
    val id: Int,
    val type: Int,
    val unk1: Int,
    val unk2: Int,
    val targetEntityId: Long,
    val unk3: Int,
    val unk4: Int,
)

object InteractivePacketCodec : PacketCodec<InteractivePacket>() {
  override fun CodecScope<InteractivePacket>.body(): InteractivePacket {
    val id = field(U8) { it.id }
    val type = field(U8) { it.type }
    val unk1 = field(S32LE) { it.unk1 }
    val unk2 = field(S32LE) { it.unk2 }
    val targetEntityId = field(S64LE) { it.targetEntityId }
    val unk3 = field(U16LE) { it.unk3 }
    val unk4 = field(U16LE) { it.unk4 }
    return InteractivePacket(id, type, unk1, unk2, targetEntityId, unk3, unk4)
  }
}
