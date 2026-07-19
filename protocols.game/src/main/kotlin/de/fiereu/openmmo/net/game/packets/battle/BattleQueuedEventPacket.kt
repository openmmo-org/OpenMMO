package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8

data class BattleQueuedEventPacket(
    val packed: Byte,
) {
  val value: Byte
    get() = (packed.toInt() and 0x7F).toByte()

  val flag: Boolean
    get() = (packed.toInt() and 0x80) != 0
}

object BattleQueuedEventPacketCodec : PacketCodec<BattleQueuedEventPacket>() {
  override fun CodecScope<BattleQueuedEventPacket>.body(): BattleQueuedEventPacket {
    val packed = field(S8) { it.packed }
    return BattleQueuedEventPacket(packed)
  }
}
