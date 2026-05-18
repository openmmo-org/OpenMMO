package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class InteractivePacket(
    val id: Int,
    val type: Int,
    val unk1: Int,
    val unk2: Int,
    val targetEntityId: Long,
    val unk3: Int,
    val unk4: Int,
)

class InteractiveSerializer : PacketSerializer<InteractivePacket> {
  override fun serialize(packet: InteractivePacket, buffer: ByteBuf) {
    buffer.writeByte(packet.id)
    buffer.writeByte(packet.type)
    buffer.writeIntLE(packet.unk1)
    buffer.writeIntLE(packet.unk2)
    buffer.writeLongLE(packet.targetEntityId)
    buffer.writeShortLE(packet.unk3)
    buffer.writeShortLE(packet.unk4)
  }
}

class InteractiveDeserializer : PacketDeserializer<InteractivePacket> {
  override fun deserialize(buffer: ByteBuf): InteractivePacket {
    val id = buffer.readByte().toInt()
    val type = buffer.readByte().toInt()
    val unk1 = buffer.readIntLE()
    val unk2 = buffer.readIntLE()
    val targetEntityId = buffer.readLongLE()
    val unk3 = buffer.readShortLE().toInt()
    val unk4 = buffer.readShortLE().toInt()
    return InteractivePacket(id, type, unk1, unk2, targetEntityId, unk3, unk4)
  }
}
