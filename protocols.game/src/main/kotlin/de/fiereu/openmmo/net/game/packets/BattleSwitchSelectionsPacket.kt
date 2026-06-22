package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class BattleSwitchSelectionsPacket(
    val selectedEntityIds: List<Long>,
)

private val SelectedEntityIdsCodec = S64LE.listPrefixed(U8)

object BattleSwitchSelectionsPacketCodec : PacketCodec<BattleSwitchSelectionsPacket>() {
  override fun CodecScope<BattleSwitchSelectionsPacket>.body(): BattleSwitchSelectionsPacket {
    val selectedEntityIds = field(SelectedEntityIdsCodec) { it.selectedEntityIds }
    return BattleSwitchSelectionsPacket(selectedEntityIds)
  }
}
