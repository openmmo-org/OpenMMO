package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class InteractiveResponsePacket(
    val id: Int,
    val unk: Int,
)

class InteractiveResponseSerializer : PacketSerializer<InteractiveResponsePacket> {
  override fun serialize(packet: InteractiveResponsePacket, buffer: ByteBuf) {
    buffer.writeByte(packet.id)
    buffer.writeByte(packet.unk)
  }
}

class InteractiveResponseDeserializer : PacketDeserializer<InteractiveResponsePacket> {
  override fun deserialize(buffer: ByteBuf): InteractiveResponsePacket {
    val id = buffer.readByte().toInt()
    val unk = buffer.readByte().toInt()
    return InteractiveResponsePacket(id, unk)
  }
}
