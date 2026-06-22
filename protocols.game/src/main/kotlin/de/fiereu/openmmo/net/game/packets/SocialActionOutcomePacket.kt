package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class SocialActionOutcomePacket(
    val action: Int,
    val entityId: Long?,
    val value: Short?,
)

object SocialActionOutcomePacketCodec : PacketCodec<SocialActionOutcomePacket>() {
  override fun CodecScope<SocialActionOutcomePacket>.body(): SocialActionOutcomePacket {
    val action = field(U8) { it.action }
    val entityId: Long? = if (resolveKind(action) == 1) field(S64LE) { it.entityId!! } else null
    val value: Short? = if (resolveKind(action) == 2) field(S16LE) { it.value!! } else null
    return SocialActionOutcomePacket(action, entityId, value)
  }

  private fun resolveKind(action: Int): Int = 0
}
