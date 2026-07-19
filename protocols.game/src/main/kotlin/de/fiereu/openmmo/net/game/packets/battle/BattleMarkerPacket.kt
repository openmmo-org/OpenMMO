package de.fiereu.openmmo.net.game.packets.battle

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class BattleMarkerPacket(
    val slot: Byte,
    val markerType: Byte,
    val value: Short,
)

object BattleMarkerPacketCodec : PacketCodec<BattleMarkerPacket>() {
  override fun CodecScope<BattleMarkerPacket>.body(): BattleMarkerPacket {
    val slot = field(S8) { it.slot }
    val markerType = field(S8) { it.markerType }
    val value = field(S16LE) { it.value }
    return BattleMarkerPacket(slot, markerType, value)
  }
}
