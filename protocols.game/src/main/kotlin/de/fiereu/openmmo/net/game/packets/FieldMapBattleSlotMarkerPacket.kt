package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S16LE
import de.fiereu.bytecodec.S8

data class FieldMapBattleSlotMarkerPacket(
    val slot: Byte,
    val markerType: Byte,
    val value: Short,
)

object FieldMapBattleSlotMarkerPacketCodec : PacketCodec<FieldMapBattleSlotMarkerPacket>() {
    override fun CodecScope<FieldMapBattleSlotMarkerPacket>.body(): FieldMapBattleSlotMarkerPacket {
        val slot = field(S8) { it.slot }
        val markerType = field(S8) { it.markerType }
        val value = field(S16LE) { it.value }
        return FieldMapBattleSlotMarkerPacket(slot, markerType, value)
    }
}
