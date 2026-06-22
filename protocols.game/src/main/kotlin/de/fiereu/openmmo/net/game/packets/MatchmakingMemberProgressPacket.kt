package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S64LE

data class MatchmakingMemberProgressPacket(
    val entityId: Long,
    val current: Short,
    val total: Short,
)

object MatchmakingMemberProgressPacketCodec : PacketCodec<MatchmakingMemberProgressPacket>() {
  override fun CodecScope<MatchmakingMemberProgressPacket>.body(): MatchmakingMemberProgressPacket {
    val entityId = field(S64LE, MatchmakingMemberProgressPacket::entityId)
    val current = field(S16LE, MatchmakingMemberProgressPacket::current)
    val total = field(S16LE, MatchmakingMemberProgressPacket::total)
    return MatchmakingMemberProgressPacket(entityId, current, total)
  }
}
