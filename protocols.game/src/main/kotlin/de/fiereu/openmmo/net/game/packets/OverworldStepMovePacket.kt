package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class OverworldStepMovePacket(
    val targetX: Byte,
    val targetY: Byte,
)

object OverworldStepMovePacketCodec : PacketCodec<OverworldStepMovePacket>() {
  override fun CodecScope<OverworldStepMovePacket>.body(): OverworldStepMovePacket {
    val targetX = field(S8) { it.targetX }
    val targetY = field(S8) { it.targetY }
    return OverworldStepMovePacket(targetX, targetY)
  }
}
