package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.enumByOrdinalByte
import de.fiereu.openmmo.common.enums.Direction

data class FaceDirectionPacket(val direction: Direction)

object FaceDirectionPacketCodec : PacketCodec<FaceDirectionPacket>() {
    private val DirectionCodec = enumByOrdinalByte<Direction>()

    override fun CodecScope<FaceDirectionPacket>.body(): FaceDirectionPacket {
        val direction = field(DirectionCodec) { it.direction }
        return FaceDirectionPacket(direction)
    }
}
