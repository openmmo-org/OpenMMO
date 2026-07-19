package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE
import de.fiereu.bytecodec.S8

data class BattleSideRemovePokemonPacket(
    val side: Byte,
    val entityId: Long,
)

object BattleSideRemovePokemonPacketCodec : PacketCodec<BattleSideRemovePokemonPacket>() {
  override fun CodecScope<BattleSideRemovePokemonPacket>.body(): BattleSideRemovePokemonPacket {
    val side = field(S8) { it.side }
    val entityId = field(S64LE) { it.entityId }
    return BattleSideRemovePokemonPacket(side, entityId)
  }
}
