package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class FaceDirectionPacket(
    val direction: Direction,
)

class FaceDirectionSerializer : PacketSerializer<FaceDirectionPacket> {
  override fun serialize(packet: FaceDirectionPacket, buffer: ByteBuf) {
    buffer.writeByte(packet.direction.ordinal)
  }
}

class FaceDirectionDeserializer : PacketDeserializer<FaceDirectionPacket> {
  override fun deserialize(buffer: ByteBuf): FaceDirectionPacket {
    val direction = Direction.entries[buffer.readByte().toInt()]
    return FaceDirectionPacket(direction)
  }
}
