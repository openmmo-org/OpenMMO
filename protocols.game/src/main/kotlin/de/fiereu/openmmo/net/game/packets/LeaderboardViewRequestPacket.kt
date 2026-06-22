package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class LeaderboardViewRequestPacket(
    val categoryId: Byte,
)

object LeaderboardViewRequestPacketCodec : PacketCodec<LeaderboardViewRequestPacket>() {
    override fun CodecScope<LeaderboardViewRequestPacket>.body(): LeaderboardViewRequestPacket {
        val categoryId = field(S8) { it.categoryId }
        return LeaderboardViewRequestPacket(categoryId)
    }
}
