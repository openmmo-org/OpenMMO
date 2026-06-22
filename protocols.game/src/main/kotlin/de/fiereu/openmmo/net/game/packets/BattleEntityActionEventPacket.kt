package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class BattleEntityActionEventPacket(
    val sourceId: Long,
    val targetId: Long,
)

object BattleEntityActionEventPacketCodec : PacketCodec<BattleEntityActionEventPacket>() {
  override fun CodecScope<BattleEntityActionEventPacket>.body(): BattleEntityActionEventPacket {
    val sourceId = field(S64LE) { it.sourceId }
    val targetId = field(S64LE) { it.targetId }
    return BattleEntityActionEventPacket(sourceId, targetId)
  }
}
