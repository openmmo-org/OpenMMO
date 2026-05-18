package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class EntityLeavePacket(
    val entityId: Long,
)

class EntityLeaveSerializer : PacketSerializer<EntityLeavePacket> {
  override fun serialize(packet: EntityLeavePacket, buffer: ByteBuf) {
    buffer.writeLong(packet.entityId)
  }
}

class EntityLeaveDeserializer : PacketDeserializer<EntityLeavePacket> {
  override fun deserialize(buffer: ByteBuf): EntityLeavePacket {
    val entityId = buffer.readLong()
    return EntityLeavePacket(entityId)
  }
}
