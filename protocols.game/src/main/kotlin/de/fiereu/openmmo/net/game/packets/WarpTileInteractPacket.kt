package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class WarpTileInteractPacket(
    val floorId: Byte,
    val warpMode: Byte,
)

object WarpTileInteractPacketCodec : PacketCodec<WarpTileInteractPacket>() {
    override fun CodecScope<WarpTileInteractPacket>.body(): WarpTileInteractPacket {
        val floorId = field(S8) { it.floorId }
        val warpMode = field(S8) { it.warpMode }
        return WarpTileInteractPacket(floorId, warpMode)
    }
}
