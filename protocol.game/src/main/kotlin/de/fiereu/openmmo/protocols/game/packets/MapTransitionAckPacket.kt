package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class MapTransitionAckPacket(val unk: Int = 0)

class MapTransitionAckSerializer : PacketSerializer<MapTransitionAckPacket> {
  override fun serialize(packet: MapTransitionAckPacket, buffer: ByteBuf) {
    buffer.writeByte(packet.unk)
  }
}

class MapTransitionAckDeserializer : PacketDeserializer<MapTransitionAckPacket> {
  override fun deserialize(buffer: ByteBuf): MapTransitionAckPacket {
    return MapTransitionAckPacket(buffer.readByte().toInt())
  }
}
