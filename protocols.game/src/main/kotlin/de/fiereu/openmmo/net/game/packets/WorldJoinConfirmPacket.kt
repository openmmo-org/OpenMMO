package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class WorldJoinConfirmPacket(
    val success: Boolean,
    val entityId: Long?,
    val name: String?,
)

object WorldJoinConfirmPacketCodec : PacketCodec<WorldJoinConfirmPacket>() {
  override fun CodecScope<WorldJoinConfirmPacket>.body(): WorldJoinConfirmPacket {
    val success = field(Bool) { it.success }
    val entityId: Long?
    val name: String?
    if (success) {
      entityId = field(S64LE) { it.entityId!! }
      name = field(Utf16LeNullTerminated) { it.name!! }
    } else {
      entityId = null
      name = null
    }
    return WorldJoinConfirmPacket(success, entityId, name)
  }
}
