package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.U8
import de.fiereu.openmmo.common.enums.Direction

data class MovementPacket(
    val x: Int,
    val y: Int,
    val direction: Direction,
    val running: Boolean = false,
)

object MovementPacketCodec : PacketCodec<MovementPacket>() {
  override fun CodecScope<MovementPacket>.body(): MovementPacket {
    val x = field(S16LE) { it.x.toShort() }
    val y = field(S16LE) { it.y.toShort() }
    val state = field(U8) { it.direction.ordinal or (if (it.running) 0x80 else 0) }
    val direction = Direction.entries[state and 0x03]
    val running = state and 0x80 != 0
    return MovementPacket(x.toInt(), y.toInt(), direction, running)
  }
}
