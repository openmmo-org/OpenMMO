package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class NpcUpdatePacket(
    val entityId: Long,
    val regionId: Int,
    val bankId: Int,
    val mapId: Int,
    val x: Int,
    val y: Int,
    val facing: Int,
    val unk: Int,
)

class NpcUpdateSerializer : PacketSerializer<NpcUpdatePacket> {
  override fun serialize(packet: NpcUpdatePacket, buffer: ByteBuf) {
    buffer.writeLongLE(packet.entityId)
    buffer.writeByte(packet.regionId)
    buffer.writeByte(packet.bankId)
    buffer.writeByte(packet.mapId)
    buffer.writeShortLE(packet.x)
    buffer.writeShortLE(packet.y)
    buffer.writeByte(packet.facing)
    buffer.writeByte(packet.unk)
  }
}

class NpcUpdateDeserializer : PacketDeserializer<NpcUpdatePacket> {
  override fun deserialize(buffer: ByteBuf): NpcUpdatePacket {
    val entityId = buffer.readLongLE()
    val regionId = buffer.readByte().toInt()
    val bankId = buffer.readByte().toInt()
    val mapId = buffer.readByte().toInt()
    val x = buffer.readShortLE().toInt()
    val y = buffer.readShortLE().toInt()
    val facing = buffer.readByte().toInt()
    val unk = buffer.readByte().toInt()
    return NpcUpdatePacket(entityId, regionId, bankId, mapId, x, y, facing, unk)
  }
}
