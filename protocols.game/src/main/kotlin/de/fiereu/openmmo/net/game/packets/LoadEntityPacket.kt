package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S32LE
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.U8
import de.fiereu.bytecodec.Utf16LeNullTerminated
import de.fiereu.bytecodec.reserved
import de.fiereu.openmmo.common.enums.Direction
import de.fiereu.openmmo.common.enums.EntityStatus
import de.fiereu.openmmo.net.game.codecs.DefaultSkinSetCodec
import de.fiereu.openmmo.net.game.codecs.SkinSet

data class LoadEntityPacket(
    val entityId: Long,
    val skin: SkinSet,
    val name: String,
    val regionId: Int,
    val bankId: Int,
    val mapId: Int,
    val x: Int,
    val y: Int,
    val z: Int,
    val facing: Direction,
    val transportation: Int = 0,
    val entityNameplateType: Int = 0,
    val status: EntityStatus = EntityStatus.NONE,
    val hasFollower: Boolean,
    val followerDexId: Short,
)

object LoadEntityPacketCodec : PacketCodec<LoadEntityPacket>() {
  override fun CodecScope<LoadEntityPacket>.body(): LoadEntityPacket {
    val entityId = field(S64LE, LoadEntityPacket::entityId)
    reserved(byte = 0)
    val skin = field(DefaultSkinSetCodec, LoadEntityPacket::skin)
    val name = field(Utf16LeNullTerminated, LoadEntityPacket::name)
    val regionId = field(U8, LoadEntityPacket::regionId)
    val bankId = field(U8, LoadEntityPacket::bankId)
    val mapId = field(U8, LoadEntityPacket::mapId)
    val x = field(S16LE) { it.x.toShort() }.toInt()
    val y = field(S16LE) { it.y.toShort() }.toInt()
    val z = field(U8, LoadEntityPacket::z)
    val facing = Direction.entries[field(U8) { it.facing.ordinal }]
    val transportation = field(U8, LoadEntityPacket::transportation)
    val entityNameplateType = field(U8, LoadEntityPacket::entityNameplateType)
    val flags = field(U16LE) { if (it.hasFollower) 0x04 else 0 }
    if (flags and 0x01 != 0) field(S8) { 0 }
    if (flags and 0x02 != 0) {
      field(S8) { 0 }
      field(U16LE) { 0 }
    }
    val hasFollower = (flags and 0x04) != 0
    val followerDexId: Short = if (hasFollower) field(S16LE, LoadEntityPacket::followerDexId) else 0
    if (flags and 0x08 != 0) field(S8) { 0 }
    if (flags and 0x10 != 0) {
      field(S32LE) { 0 }
      field(Utf16LeNullTerminated) { "" }
    }
    return LoadEntityPacket(
        entityId = entityId,
        skin = skin,
        name = name,
        regionId = regionId,
        bankId = bankId,
        mapId = mapId,
        x = x,
        y = y,
        z = z,
        facing = facing,
        transportation = transportation,
        entityNameplateType = entityNameplateType,
        status = EntityStatus.NONE,
        hasFollower = hasFollower,
        followerDexId = followerDexId,
    )
  }
}
