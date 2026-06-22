package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S64LE

data class WorldObjectInstanceDespawnPacket(
    val entityId: Long,
)

object WorldObjectInstanceDespawnPacketCodec : PacketCodec<WorldObjectInstanceDespawnPacket>() {
  override fun CodecScope<WorldObjectInstanceDespawnPacket>.body():
      WorldObjectInstanceDespawnPacket {
    val entityId = field(S64LE) { it.entityId }
    return WorldObjectInstanceDespawnPacket(entityId)
  }
}
