package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.enumByOrdinalByte
import de.fiereu.openmmo.common.enums.Direction

data class MovementPacket(val x: Int, val y: Int, val direction: Direction)

object MovementPacketCodec : PacketCodec<MovementPacket>() {
    private val DirectionCodec = enumByOrdinalByte<Direction>()

    override fun CodecScope<MovementPacket>.body(): MovementPacket {
        val x = field(S16LE) { it.x.toShort() }
        val y = field(S16LE) { it.y.toShort() }
        val direction = field(DirectionCodec) { it.direction }
        return MovementPacket(x.toInt(), y.toInt(), direction)
    }
}
