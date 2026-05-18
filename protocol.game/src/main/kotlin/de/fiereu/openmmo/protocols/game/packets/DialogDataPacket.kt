package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class DialogDataPacket(val entityId: Long, val unk1: Int, val type: Int, val data: ByteArray)

class DialogDataSerializer : PacketSerializer<DialogDataPacket> {
  override fun serialize(packet: DialogDataPacket, buffer: ByteBuf) {
    buffer.writeLongLE(packet.entityId)
    buffer.writeByte(packet.unk1)
    buffer.writeByte(packet.type)
    buffer.writeBytes(packet.data)
  }
}

class DialogDataDeserializer : PacketDeserializer<DialogDataPacket> {
  override fun deserialize(buffer: ByteBuf): DialogDataPacket {
    val entityId = buffer.readLongLE()
    val unk1 = buffer.readByte().toInt()
    val type = buffer.readByte().toInt()
    val data = ByteArray(buffer.readableBytes())
    buffer.readBytes(data)
    return DialogDataPacket(entityId, unk1, type, data)
  }
}
