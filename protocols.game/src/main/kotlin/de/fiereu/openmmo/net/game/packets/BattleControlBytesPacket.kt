package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.fixedBytes

data class BattleControlBytesPacket(
    val controlBytes: ByteArray,
) {
  override fun equals(other: Any?): Boolean =
      other is BattleControlBytesPacket && controlBytes.contentEquals(other.controlBytes)

  override fun hashCode(): Int = controlBytes.contentHashCode()
}

private val ControlBytes = fixedBytes(3)

object BattleControlBytesPacketCodec : PacketCodec<BattleControlBytesPacket>() {
  override fun CodecScope<BattleControlBytesPacket>.body(): BattleControlBytesPacket {
    val controlBytes = field(ControlBytes) { it.controlBytes }
    return BattleControlBytesPacket(controlBytes)
  }
}
