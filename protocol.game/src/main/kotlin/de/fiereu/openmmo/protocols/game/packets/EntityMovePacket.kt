package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class EntityMovePacket(
    val entityId: Long,
    val marker: Byte = 0x33,
    val subtype: Byte = 0x03,
    val seq: Byte = 0,
    val x: Byte,
    val y: Byte,
    val direction: Direction,
)

class EntityMovePacketSerializer : PacketSerializer<EntityMovePacket> {
  override fun serialize(packet: EntityMovePacket, buffer: ByteBuf) {
    buffer.writeLong(packet.entityId)
    buffer.writeByte(packet.marker.toInt())
    buffer.writeByte(packet.subtype.toInt())
    buffer.writeByte(packet.seq.toInt())
    buffer.writeByte(packet.x.toInt())
    buffer.writeByte(packet.y.toInt())
    buffer.writeByte(packet.direction.ordinal)
  }
}

class EntityMovePacketDeserializer : PacketDeserializer<EntityMovePacket> {
  override fun deserialize(buffer: ByteBuf): EntityMovePacket {
    val entityId = buffer.readLong()
    val marker = buffer.readByte()
    val subtype = buffer.readByte()
    val seq = buffer.readByte()
    val x = buffer.readByte()
    val y = buffer.readByte()
    val direction = Direction.entries[buffer.readByte().toInt()]
    return EntityMovePacket(entityId, marker, subtype, seq, x, y, direction)
  }
}
