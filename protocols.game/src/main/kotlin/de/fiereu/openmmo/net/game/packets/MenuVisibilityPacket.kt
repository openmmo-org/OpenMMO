package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.CodecScope
import de.fiereu.bytecodec.PacketCodec
import de.fiereu.bytecodec.S8
import de.fiereu.bytecodec.U8

data class MenuVisibilityPacket(
    val enabled: Boolean,
    val menuType: Byte?,
)

object MenuVisibilityPacketCodec : PacketCodec<MenuVisibilityPacket>() {
  override fun CodecScope<MenuVisibilityPacket>.body(): MenuVisibilityPacket {
    val enabled = field(U8) { if (it.enabled) 1 else 0 } == 1
    val menuType: Byte? = if (enabled) field(S8) { it.menuType!! } else null
    return MenuVisibilityPacket(enabled, menuType)
  }
}
