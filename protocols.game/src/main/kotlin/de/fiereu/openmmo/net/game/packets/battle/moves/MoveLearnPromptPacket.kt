package de.fiereu.openmmo.net.game.packets.battle.moves

import de.fiereu.bytecodec.*

/** Asks the player which move to drop for a new one. */
data class MoveLearnPromptPacket(
    val entityId: Long,
    val offered: List<Short>,
)

object MoveLearnPromptPacketCodec : PacketCodec<MoveLearnPromptPacket>() {
  override fun CodecScope<MoveLearnPromptPacket>.body(): MoveLearnPromptPacket {
    val entityId = field(S64LE) { it.entityId }
    val offered = field(S16LE.listPrefixed(U8)) { it.offered }
    return MoveLearnPromptPacket(entityId, offered)
  }
}
