package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class DialogOptionPacket(val data: Int)

class DialogOptionSerializer : PacketSerializer<DialogOptionPacket> {
  override fun serialize(packet: DialogOptionPacket, buffer: ByteBuf) {
    buffer.writeIntLE(packet.data)
  }
}

class DialogOptionDeserializer : PacketDeserializer<DialogOptionPacket> {
  override fun deserialize(buffer: ByteBuf): DialogOptionPacket {
    return DialogOptionPacket(buffer.readIntLE())
  }
}
