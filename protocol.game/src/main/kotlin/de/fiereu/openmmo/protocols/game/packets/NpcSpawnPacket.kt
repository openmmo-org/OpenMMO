package de.fiereu.openmmo.protocols.game.packets

import de.fiereu.openmmo.protocols.PacketDeserializer
import de.fiereu.openmmo.protocols.PacketSerializer
import io.netty.buffer.ByteBuf

data class NpcSpawnPacket(
    val entityId: Long,
    val unk1: Int,
    val unk2: Int,
    val unk3: Int,
    val unk4: Int,
    val regionId: Int,
    val bankId: Int,
    val mapId: Int,
    val x: Int,
    val y: Int,
    val facing: Int,
    val unk5: Int,
    val unk6: Int,
)

class NpcSpawnSerializer : PacketSerializer<NpcSpawnPacket> {
  override fun serialize(packet: NpcSpawnPacket, buffer: ByteBuf) {
    buffer.writeLongLE(packet.entityId)
    buffer.writeByte(packet.unk1)
    buffer.writeShortLE(packet.unk2)
    buffer.writeShortLE(packet.unk3)
    buffer.writeShortLE(packet.unk4)
    buffer.writeByte(packet.regionId)
    buffer.writeByte(packet.bankId)
    buffer.writeByte(packet.mapId)
    buffer.writeShortLE(packet.x)
    buffer.writeShortLE(packet.y)
    buffer.writeByte(packet.unk5)
    buffer.writeByte(packet.facing)
    buffer.writeShortLE(packet.unk6)
  }
}

class NpcSpawnDeserializer : PacketDeserializer<NpcSpawnPacket> {
  override fun deserialize(buffer: ByteBuf): NpcSpawnPacket {
    val entityId = buffer.readLongLE()
    val unk1 = buffer.readByte().toInt()
    val unk2 = buffer.readShortLE().toInt()
    val unk3 = buffer.readShortLE().toInt()
    val unk4 = buffer.readShortLE().toInt()
    val regionId = buffer.readByte().toInt()
    val bankId = buffer.readByte().toInt()
    val mapId = buffer.readByte().toInt()
    val x = buffer.readShortLE().toInt()
    val y = buffer.readShortLE().toInt()
    val unk5 = buffer.readByte().toInt()
    val facing = buffer.readByte().toInt()
    val unk6 = buffer.readShortLE().toInt()
    return NpcSpawnPacket(
        entityId, unk1, unk2, unk3, unk4, regionId, bankId, mapId, x, y, facing, unk5, unk6)
  }
}
