package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class KeepAlivePacket(
    val canJoin: Boolean,
    val sessionData: ByteArray,
)

class KeepAlivePacketSerializer : PacketSerializer<KeepAlivePacket> {
  override fun serialize(packet: KeepAlivePacket, buffer: ByteBuf) {
    buffer.writeBoolean(packet.canJoin)
    buffer.writeBytes(packet.sessionData)
  }
}

class KeepAlivePacketDeserializer : PacketDeserializer<KeepAlivePacket> {
  override fun deserialize(buffer: ByteBuf): KeepAlivePacket {
    val canJoin = buffer.readBoolean()
    val sessionData = ByteArray(buffer.readableBytes())
    buffer.readBytes(sessionData)
    return KeepAlivePacket(canJoin, sessionData)
  }
}
