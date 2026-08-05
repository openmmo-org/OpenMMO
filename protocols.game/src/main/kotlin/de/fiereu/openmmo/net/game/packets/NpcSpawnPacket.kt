package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class NpcSpawnPacket(
    val entityId: Long,
    /** Which region's sprite table [graphicsId] is looked up in. */
    val spriteRegionId: Int,
    val graphicsId: Int,
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

object NpcSpawnPacketCodec : PacketCodec<NpcSpawnPacket>() {
  override fun CodecScope<NpcSpawnPacket>.body(): NpcSpawnPacket {
    val entityId = field(S64LE) { it.entityId }
    val spriteRegionId = field(U8) { it.spriteRegionId }
    val graphicsId = field(U16LE) { it.graphicsId }
    val unk3 = field(U16LE) { it.unk3 }
    val unk4 = field(U16LE) { it.unk4 }
    val regionId = field(U8) { it.regionId }
    val bankId = field(U8) { it.bankId }
    val mapId = field(U8) { it.mapId }
    val x = field(U16LE) { it.x }
    val y = field(U16LE) { it.y }
    val unk5 = field(U8) { it.unk5 }
    val facing = field(U8) { it.facing }
    val unk6 = field(U16LE) { it.unk6 }
    return NpcSpawnPacket(
        entityId,
        spriteRegionId,
        graphicsId,
        unk3,
        unk4,
        regionId,
        bankId,
        mapId,
        x,
        y,
        facing,
        unk5,
        unk6)
  }
}
