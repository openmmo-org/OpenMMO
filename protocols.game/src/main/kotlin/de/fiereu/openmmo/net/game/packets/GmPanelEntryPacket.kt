package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class GmPanelEntryPacket(
    val clearFlag: Byte,
    val label: String?,
    val value: String?,
    val count: Int?,
)

object GmPanelEntryPacketCodec : PacketCodec<GmPanelEntryPacket>() {
  override fun CodecScope<GmPanelEntryPacket>.body(): GmPanelEntryPacket {
    val clearFlag = field(S8) { it.clearFlag }
    val label = if (clearFlag.toInt() == 0) field(Utf16LeNullTerminated) { it.label!! } else null
    val value = if (clearFlag.toInt() == 0) field(Utf16LeNullTerminated) { it.value!! } else null
    val count = if (clearFlag.toInt() == 0) field(S32LE) { it.count!! } else null
    return GmPanelEntryPacket(clearFlag, label, value, count)
  }
}
