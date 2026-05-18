package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class DialogStatePacket(val active: Boolean)

class DialogStateSerializer : PacketSerializer<DialogStatePacket> {
  override fun serialize(packet: DialogStatePacket, buffer: ByteBuf) {
    buffer.writeBoolean(packet.active)
  }
}

class DialogStateDeserializer : PacketDeserializer<DialogStatePacket> {
  override fun deserialize(buffer: ByteBuf): DialogStatePacket {
    return DialogStatePacket(buffer.readBoolean())
  }
}
