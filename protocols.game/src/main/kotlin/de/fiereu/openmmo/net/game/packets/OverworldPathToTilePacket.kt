package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class OverworldPathToTilePacket(
    val zoneId: Short,
    val targetX: Byte,
    val targetY: Byte,
)

object OverworldPathToTilePacketCodec : PacketCodec<OverworldPathToTilePacket>() {
    override fun CodecScope<OverworldPathToTilePacket>.body(): OverworldPathToTilePacket {
        val zoneId = field(S16LE) { it.zoneId }
        val targetX = field(S8) { it.targetX }
        val targetY = field(S8) { it.targetY }
        return OverworldPathToTilePacket(zoneId, targetX, targetY)
    }
}
