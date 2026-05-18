package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class NpcAnimationPacket(
    val entityId: Long,
    val animation: Int,
)

class NpcAnimationSerializer : PacketSerializer<NpcAnimationPacket> {
  override fun serialize(packet: NpcAnimationPacket, buffer: ByteBuf) {
    buffer.writeLongLE(packet.entityId)
    buffer.writeByte(packet.animation)
  }
}

class NpcAnimationDeserializer : PacketDeserializer<NpcAnimationPacket> {
  override fun deserialize(buffer: ByteBuf): NpcAnimationPacket {
    val entityId = buffer.readLongLE()
    val animation = buffer.readByte().toInt()
    return NpcAnimationPacket(entityId, animation)
  }
}
