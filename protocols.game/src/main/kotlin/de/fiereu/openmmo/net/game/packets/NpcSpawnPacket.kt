package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.U16LE
import de.fiereu.bytecodec.U8

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

object NpcSpawnPacketCodec : PacketCodec<NpcSpawnPacket>() {
  override fun CodecScope<NpcSpawnPacket>.body(): NpcSpawnPacket {
    val entityId = field(S64LE) { it.entityId }
    val unk1 = field(U8) { it.unk1 }
    val unk2 = field(U16LE) { it.unk2 }
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
        entityId, unk1, unk2, unk3, unk4, regionId, bankId, mapId, x, y, facing, unk5, unk6)
  }
}
