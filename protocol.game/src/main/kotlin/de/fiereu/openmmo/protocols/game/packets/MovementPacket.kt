package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class MovementPacket(
    val x: Int,
    val y: Int,
    val direction: Direction,
)

class MovementPacketSerializer : PacketSerializer<MovementPacket> {
  override fun serialize(packet: MovementPacket, buffer: ByteBuf) {
    buffer.writeShortLE(packet.x)
    buffer.writeShortLE(packet.y)
    buffer.writeByte(packet.direction.ordinal)
  }
}

class MovementPacketDeserializer : PacketDeserializer<MovementPacket> {
  override fun deserialize(buffer: ByteBuf): MovementPacket {
    val x = buffer.readShortLE().toInt()
    val y = buffer.readShortLE().toInt()
    val direction = Direction.entries[buffer.readByte().toInt()]
    return MovementPacket(x, y, direction)
  }
}
