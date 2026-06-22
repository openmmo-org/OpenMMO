package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.U8

data class PlayerPositionUpdatePacket(
    val x: Short,
    val y: Short,
    val facing: Byte,
    val isRunning: Boolean,
    val isOnBike: Boolean,
)

object PlayerPositionUpdatePacketCodec : PacketCodec<PlayerPositionUpdatePacket>() {
  override fun CodecScope<PlayerPositionUpdatePacket>.body(): PlayerPositionUpdatePacket {
    val x = field(S16LE) { it.x }
    val y = field(S16LE) { it.y }
    val packedFacing =
        field(U8) {
          var f = it.facing.toInt() and 0xFF
          if (it.isRunning) f = f or 0x80
          if (it.isOnBike) f = f or 0x40
          f
        }
    val isRunning = (packedFacing and 0x80) != 0
    val isOnBike = (packedFacing and 0x40) != 0
    val facing = (packedFacing and 0x3F).toByte()
    return PlayerPositionUpdatePacket(x, y, facing, isRunning, isOnBike)
  }
}
