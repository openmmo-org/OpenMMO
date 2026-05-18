package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class EntityInteractPacket(
    val entityId: Long,
    val token: Long,
)

class EntityInteractSerializer : PacketSerializer<EntityInteractPacket> {
  override fun serialize(packet: EntityInteractPacket, buffer: ByteBuf) {
    buffer.writeLongLE(packet.entityId)
    buffer.writeLongLE(packet.token)
  }
}

class EntityInteractDeserializer : PacketDeserializer<EntityInteractPacket> {
  override fun deserialize(buffer: ByteBuf): EntityInteractPacket {
    val entityId = buffer.readLongLE()
    val token = buffer.readLongLE()
    return EntityInteractPacket(entityId, token)
  }
}
