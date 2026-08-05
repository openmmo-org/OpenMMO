package de.fiereu.openmmo.net.game.packets.battle.moves

import de.fiereu.bytecodec.*

/** The moveset the player picked. A new move sits in the slot it takes over. */
data class MoveLearnReplyPacket(
    val entityId: Long,
    val moveIds: List<Short>,
    val offered: List<Short>,
)

object MoveLearnReplyPacketCodec : PacketCodec<MoveLearnReplyPacket>() {
  override fun CodecScope<MoveLearnReplyPacket>.body(): MoveLearnReplyPacket {
    val entityId = field(S64LE) { it.entityId }
    val moveIds = field(S16LE.listPrefixed(U8)) { it.moveIds }
    val offered = field(S16LE.listPrefixed(U8)) { it.offered }
    return MoveLearnReplyPacket(entityId, moveIds, offered)
  }
}
