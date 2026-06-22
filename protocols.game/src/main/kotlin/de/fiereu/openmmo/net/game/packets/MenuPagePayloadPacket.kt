package de.fiereu.openmmo.net.game.packets

import de.fiereu.bytecodec.*

data class MenuPagePayloadPacket(
    val menuType: Byte,
    val page: ByteArray?,
) {
  override fun equals(other: Any?): Boolean {
    if (other !is MenuPagePayloadPacket) return false
    if (menuType != other.menuType) return false
    if (page == null) return other.page == null
    return other.page != null && page.contentEquals(other.page)
  }

  override fun hashCode(): Int {
    var r = menuType.toInt()
    r = r * 31 + (page?.contentHashCode() ?: 0)
    return r
  }
}

private val MenuPageBlob = bytesPrefixed(U16LE)

object MenuPagePayloadPacketCodec : PacketCodec<MenuPagePayloadPacket>() {
  override fun CodecScope<MenuPagePayloadPacket>.body(): MenuPagePayloadPacket {
    val menuType = field(S8) { it.menuType }
    val present = field(U8) { if (it.page != null) 1 else 0 } == 1
    val page: ByteArray? =
        if (present) {
          field(S64LE) { 0L }
          field(S8) { 0 }
          field(S8) { 0 }
          field(MenuPageBlob) { it.page!! }
        } else null
    return MenuPagePayloadPacket(menuType, page)
  }
}
